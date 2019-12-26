package com.perfectial.goal.pizzahub.integration.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.perfectial.goal.pizzahub.integration.common.dto.ErrorDto;
import com.perfectial.goal.pizzahub.integration.common.dto.ResponseDto;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;

public class ResponseUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    public static  <T> ResponseDto<T> wrapResponse(HttpResponse<byte[]> httpResponse, Class<T> resultType) {
        return wrapResponse(httpResponse, new ResultType<T>(resultType));
    }

    public static  <T> ResponseDto<T> wrapResponse(HttpResponse<byte[]> httpResponse, TypeReference<T> resultType) {
        return wrapResponse(httpResponse, new ResultType<T>(resultType));
    }

    private static <T> ResponseDto<T> wrapResponse(HttpResponse<byte[]> httpResponse, ResultType<T> resultType) {
        ResponseDto<T> result = new ResponseDto<>();
        result.setHttpResponse(httpResponse);

        ErrorDto error = getErrorResponse(httpResponse);
        result.setError(error);
        if (error == null) {
            result.setData(getResponseData(httpResponse, resultType));
        }

        return result;
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private static <T> T getResponseData(HttpResponse<byte[]> httpResponse, ResultType<T> resultType) {
        if (httpResponse.body().length == 0) {
            return null;
        }
        Class<T> type = resultType.getClassType();
        if (type != null) {
            if (type.equals(String.class)) {
                return (T) new String(httpResponse.body(), StandardCharsets.UTF_8);
            } else if (type.equals(byte[].class)) {
                return (T) httpResponse.body();
            } else if (type.equals(void.class)) {
                throw new RuntimeException(String.format(
                        "Expecting empty body. But received: %s",
                        new String(httpResponse.body(), StandardCharsets.UTF_8)
                ));
            }
            return OBJECT_MAPPER.readValue(httpResponse.body(), type);
        } else {
            return OBJECT_MAPPER.readValue(httpResponse.body(), resultType.getTypeReference());
        }
    }

    @SneakyThrows
    private static ErrorDto getErrorResponse(HttpResponse<byte[]> httpResponse) {
        if (httpResponse.body().length != 0) {
            JsonNode jsonNode = OBJECT_MAPPER.readTree(httpResponse.body());
            if (jsonNode.hasNonNull("timestamp")
                    && jsonNode.hasNonNull("status")
                    && jsonNode.hasNonNull("path")) {
                return OBJECT_MAPPER.treeToValue(jsonNode, ErrorDto.class);
            }
        }
        return null;
    }

    @Getter
    private static class ResultType<T> {
        private Class<T> classType;
        private TypeReference<T> typeReference;

        public ResultType(@NonNull Class<T> classType) {
            this.classType = classType;
        }

        public ResultType(@NonNull TypeReference<T> typeReference) {
            this.typeReference = typeReference;
        }
    }
}
