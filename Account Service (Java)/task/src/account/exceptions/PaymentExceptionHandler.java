package account.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class PaymentExceptionHandler extends ResponseEntityExceptionHandler {
    Logger logger = LoggerFactory.getLogger(PaymentExceptionHandler.class);


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map> handleSQLIntegrityExceptions(JdbcSQLIntegrityConstraintViolationException ex
            , HttpServletRequest request) {
        Map<String, Object> response = getStringObjectMap(request);
        return ResponseEntity.badRequest().body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map> handleValidationExceptions(ConstraintViolationException ex
            , HttpServletRequest request) {
        Map<String, Object> response = getStringObjectMap(request);
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(InvalidPeriodException.class)
    @ResponseBody
    public ResponseEntity<Map> handleInvalidPeriodException(final InvalidPeriodException ex, HttpServletRequest request) {
        logger.info("&&&&&&&& InvalidPeriodException "+ request);
        Map<String, Object> response = getStringObjectMap(request);
        logger.info("&&&&&&&& InvalidPeriodException "+ response);
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<Map<String, Object>> handlePaymentNotFoundException(JsonMappingException ex, HttpServletRequest request) {
        Map<String, Object> response = getStringObjectMap(request);
        return ResponseEntity.badRequest().body(response);
    }

    private Map<String, Object> getStringObjectMap(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", new Date());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.put("path", request.getRequestURI());
        return response;
    }


}
