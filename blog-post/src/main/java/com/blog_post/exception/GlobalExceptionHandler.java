package com.blog_post.exception;



import jakarta.servlet.http
.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;

import org.springframework.web.bind
.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation
.ExceptionHandler;

import org.springframework.web.bind.annotation
.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(
    ResourceNotFoundException.class
)
public ResponseEntity<ErrorResponse>
handleResourceNotFound(

    ResourceNotFoundException ex,

    HttpServletRequest request

) {

log.error(
        "Resource Not Found",
        ex
);

return buildErrorResponse(

        HttpStatus.NOT_FOUND,

        ErrorCode.RESOURCE_NOT_FOUND,

        ex.getMessage(),

        request
);
}

@ExceptionHandler(
    ValidationException.class
)
public ResponseEntity<ErrorResponse>
handleValidationException(

    ValidationException ex,

    HttpServletRequest request

) {

log.error(
        "Validation Error",
        ex
);

return buildErrorResponse(

        HttpStatus.BAD_REQUEST,

        ErrorCode.VALIDATION_ERROR,

        ex.getMessage(),

        request
);
}

@ExceptionHandler(
    RateLimitExceededException.class
)
public ResponseEntity<ErrorResponse>
handleRateLimitExceeded(

    RateLimitExceededException ex,

    HttpServletRequest request

) {

log.error(
        "Rate Limit Exceeded",
        ex
);

return buildErrorResponse(

        HttpStatus.TOO_MANY_REQUESTS,

        ErrorCode.RATE_LIMIT_EXCEEDED,

        ex.getMessage(),

        request
);
}

@ExceptionHandler(
    UnauthorizedException.class
)
public ResponseEntity<ErrorResponse>
handleUnauthorized(

    UnauthorizedException ex,

    HttpServletRequest request

) {

log.error(
        "Unauthorized Access",
        ex
);

return buildErrorResponse(

        HttpStatus.UNAUTHORIZED,

        ErrorCode.UNAUTHORIZED,

        ex.getMessage(),

        request
);
}

@ExceptionHandler(
    KafkaPublishException.class
)
public ResponseEntity<ErrorResponse>
handleKafkaException(

    KafkaPublishException ex,

    HttpServletRequest request

) {

log.error(
        "Kafka Publish Failed",
        ex
);

return buildErrorResponse(

        HttpStatus.INTERNAL_SERVER_ERROR,

        ErrorCode.KAFKA_PUBLISH_ERROR,

        ex.getMessage(),

        request
);
}

@ExceptionHandler(
    SagaException.class
)
public ResponseEntity<ErrorResponse>
handleSagaException(

    SagaException ex,

    HttpServletRequest request

) {

log.error(
        "Saga Error",
        ex
);

return buildErrorResponse(

        HttpStatus.INTERNAL_SERVER_ERROR,

        ErrorCode.SAGA_ERROR,

        ex.getMessage(),

        request
);
}

@ExceptionHandler(
    CacheException.class
)
public ResponseEntity<ErrorResponse>
handleCacheException(

    CacheException ex,

    HttpServletRequest request

) {

log.error(
        "Cache Error",
        ex
);

return buildErrorResponse(

        HttpStatus.INTERNAL_SERVER_ERROR,

        ErrorCode.CACHE_ERROR,

        ex.getMessage(),

        request
);
}

@ExceptionHandler(
    ShardRoutingException.class
)
public ResponseEntity<ErrorResponse>
handleShardException(

    ShardRoutingException ex,

    HttpServletRequest request

) {

log.error(
        "Shard Routing Error",
        ex
);

return buildErrorResponse(

        HttpStatus.INTERNAL_SERVER_ERROR,

        ErrorCode.SHARD_ERROR,

        ex.getMessage(),

        request
);
}

@ExceptionHandler(
    MethodArgumentNotValidException.class
)
public ResponseEntity<ValidationErrorResponse>
handleMethodArgumentNotValid(

    MethodArgumentNotValidException ex,

    HttpServletRequest request

) {

Map<String, String> fieldErrors =
        new HashMap<>();

for (FieldError error :
        ex.getBindingResult()
                .getFieldErrors()) {

    fieldErrors.put(
            error.getField(),
            error.getDefaultMessage()
    );
}

ValidationErrorResponse response =
        ValidationErrorResponse.builder()

                .timestamp(
                        Instant.now()
                )

                .status(
                        HttpStatus.BAD_REQUEST.value()
                )

                .message(
                        "Validation Failed"
                )

                .fieldErrors(
                        fieldErrors
                )

                .path(
                        request.getRequestURI()
                )

                .build();

return ResponseEntity
        .badRequest()
        .body(response);
}

@ExceptionHandler(Exception.class)
public ResponseEntity<ErrorResponse>
handleGenericException(

    Exception ex,

    HttpServletRequest request

) {

log.error(
        "Unhandled Exception",
        ex
);

return buildErrorResponse(

        HttpStatus.INTERNAL_SERVER_ERROR,

        ErrorCode.INTERNAL_SERVER_ERROR,

        ex.getMessage(),

        request
);
}

private ResponseEntity<ErrorResponse>
buildErrorResponse(

    HttpStatus status,

    ErrorCode errorCode,

    String message,

    HttpServletRequest request

) {

ErrorResponse response =
        ErrorResponse.builder()

                .timestamp(
                        Instant.now()
                )

                .status(
                        status.value()
                )

                .errorCode(
                        errorCode
                )

                .message(
                        message
                )

                .path(
                        request.getRequestURI()
                )

                .traceId(
                        UUID.randomUUID()
                                .toString()
                )

                .build();

return ResponseEntity
        .status(status)
        .body(response);
}
}