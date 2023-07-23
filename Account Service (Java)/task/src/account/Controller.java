package account;

import account.data.UserRepository;
import account.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class Controller {
    Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private UserRepository userRepo;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    AuthenticationService authService;

    @PostMapping("api/auth/signup")
    public ResponseEntity singnup(@RequestBody Map<String, String> map) {
        return authService.signup(map);
    }

    @PostMapping("api/auth/changepass")
    public ResponseEntity changepass(@RequestBody Map<String, String> map) {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("api/empl/payment")
    public ResponseEntity payment(@RequestBody Map<String, String> map) {
        return new ResponseEntity(HttpStatus.OK);
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
