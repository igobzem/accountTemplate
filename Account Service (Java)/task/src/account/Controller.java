package account;

import account.data.NewPasswordDto;
import account.data.PaymentDto;
import account.data.User;
import account.services.AccountService;
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
    private AuthenticationService authService;
    @Autowired
    private AccountService accountService;

    @PostMapping("api/auth/signup")
    public User singnup(@RequestBody @Valid User user) {
        return authService.signup(user);
    }

    @PostMapping("api/auth/changepass")
    public ResponseEntity<Map> changepass(@AuthenticationPrincipal UserDetails details, @RequestBody @Valid NewPasswordDto newPassword) {
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
    public ResponseEntity payments(@AuthenticationPrincipal UserDetails details, @RequestParam(required = false) String period) {
        if (details == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        User user = authService.getUser(details.getUsername());
        if (period == null) {
            return new ResponseEntity(accountService.getPayments(user), HttpStatus.OK);
        }
        return new ResponseEntity(accountService.getPayment(user, period), HttpStatus.OK);
    }

    @PostMapping("api/acct/payments")
    public ResponseEntity<Map<String,String>> payments(@RequestBody List<@Valid PaymentDto> dtos) {
        logger.info("+++++++post+++payments+++++");
        accountService.addPayments(dtos);
        Map<String,String> map = new HashMap<>();
        map.put("status", "Added successfully!");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PutMapping("api/acct/payments")
    public ResponseEntity putPayment(@RequestBody PaymentDto dto) {
        logger.info("+++++++put+payment+++++++"+dto);
        accountService.changePayment(dto);
        Map<String,String> map = new HashMap<>();
        map.put("status", "Updated successfully!");
        return new ResponseEntity(map, HttpStatus.OK);
    }

}
