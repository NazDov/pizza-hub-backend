package com.perfectial.goal.pizzahub.integration.common.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.perfectial.goal.pizzahub.integration.common.util.JsonUtil;
import com.perfectial.goal.pizzahub.integration.common.util.ResponseUtil;
import java.net.http.HttpResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.junit.Assert;

@Getter
@Setter
@ToString
public class ResponseDto<T> {

    private T data;
    private ErrorDto error;
    private HttpResponse<byte[]> httpResponse;

    public T getData() {
        assertSuccess();
        return data;
    }

    @SneakyThrows
    public void assertSuccess() {
        if (error != null) {
            Assert.fail(JsonUtil.toPrettyJson(error));
        }
    }

    public static <T> ResponseDto<T> wrapResponse(HttpResponse<byte[]> httpResponse, Class<T> resultType) {
        return ResponseUtil.wrapResponse(httpResponse, resultType);
    }

    public static <T> ResponseDto<T> wrapResponse(HttpResponse<byte[]> httpResponse, TypeReference<T> resultType) {
        return ResponseUtil.wrapResponse(httpResponse, resultType);
    }

}
