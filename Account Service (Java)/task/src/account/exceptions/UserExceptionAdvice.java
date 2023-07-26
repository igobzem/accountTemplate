package account.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class UserExceptionAdvice {


    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<CustomErrorMessage> handleUserNotFound(
            UserExistException e, WebRequest request) {

        CustomErrorMessage body = new CustomErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                LocalDateTime.now(),
                e.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
//        Map<String, Object> response = new HashMap<>();
//        response.put("timestamp", LocalDate.now());
//        return ResponseEntity.badRequest().body(request);
//    }

}