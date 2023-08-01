package account;

import account.data.NewPasswordDto;
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
    public User singnup(@RequestBody @Valid User user) {
        return authService.signup(user);
    }

    @PostMapping("api/auth/changepass")
    public ResponseEntity changepass(@AuthenticationPrincipal UserDetails details, @RequestBody @Valid NewPasswordDto newPassword) {
        if (details == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        User user = authService.changepass(details.getUsername(), newPassword.getNewPassword());
        Map<String,String> map = new HashMap<>();
        map.put( "email", user.getEmail().toLowerCase());
        map.put("status", "The password has been updated successfully");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("api/empl/payment")
    public ResponseEntity payment(@AuthenticationPrincipal UserDetails details) {
        if (details == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        User user = authService.getUser(details.getUsername());
        return new ResponseEntity(user, HttpStatus.OK);
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
