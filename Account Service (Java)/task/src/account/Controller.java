package account;

import account.data.User;
import account.services.AuthenticationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Validated
public class Controller {
    Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    public AuthenticationService authService;

    @PostMapping("api/auth/signup")
    public ResponseEntity singnup(@RequestBody @Valid User user) {
        authService.signup(user);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PostMapping("api/auth/changepass")
    public ResponseEntity changepass(@RequestBody Map<String, String> map) {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("api/empl/payment")
    public ResponseEntity payment(@AuthenticationPrincipal UserDetails details) {
        if (details != null) {
            Optional<User> user = authService.getUser(details.getUsername());
            if (user.isPresent()) {
                return new ResponseEntity(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("api/acct/payments")
    public ResponseEntity payments(@RequestBody Map<String, String> map) {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("api/acct/payments")
    public ResponseEntity putPayments(@RequestBody Map<String, String> map) {
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("api/admin/user")
    public ResponseEntity getUser(@RequestBody Map<String, String> map) {
        return new ResponseEntity(HttpStatus.OK);
    }
    @DeleteMapping("api/admin/user")
    public ResponseEntity deleteUser(@RequestBody Map<String, String> map) {
        return new ResponseEntity(HttpStatus.OK);
    }
    @PutMapping("api/admin/user/role")
    public ResponseEntity putRole(@RequestBody Map<String, String> map) {
        return new ResponseEntity(HttpStatus.OK);
    }

}
