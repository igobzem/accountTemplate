package account.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidPeriodException extends RuntimeException {
    public InvalidPeriodException(String message) {
        super(message);
    }
}

