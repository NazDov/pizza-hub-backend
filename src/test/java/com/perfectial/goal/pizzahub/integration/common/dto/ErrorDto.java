package com.perfectial.goal.pizzahub.integration.common.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * Default spring response error body.
 */
@Getter
@Setter
@ToString
public class ErrorDto {
    private Instant timestamp;

    @JsonDeserialize(using = HttpStatusDeserializer.class)
    private HttpStatus status;
    private String error;
    private String message;
    private String path;
    private List<Error> errors;

    @Getter
    @Setter
    @ToString
    public static class Error {
        private List<String> codes;
        private String defaultMessage;
        private String objectName;
        private String field;
        private Object rejectedValue;
        private Boolean bindingFailure;
        private String code;
        private List<Error> arguments;
    }

    private static class HttpStatusDeserializer extends JsonDeserializer<HttpStatus> {

        @Override
        public HttpStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return HttpStatus.valueOf(p.getIntValue());
        }
    }
}
