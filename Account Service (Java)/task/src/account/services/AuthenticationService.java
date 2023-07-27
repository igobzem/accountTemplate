package account.services;

import account.Controller;
import account.data.User;
import account.data.UserRepository;
import account.exceptions.UserValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {
    Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private static final String[] BREACHED = {"PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember"};
    @Autowired
    private UserRepository userRepo;
    @Autowired
    PasswordEncoder encoder;

    public void signup(User user) throws UserValidationException {
        validatePass(user.getPassword());
        Optional<User> optional = userRepo.findByEmailIgnoreCase(user.getEmail());
        if (optional.isPresent()) {
            throw new UserValidationException("User exist!");
        }
        user.setPassword(encoder.encode(user.getPassword()));
//        user.setPassword(user.getPassword());
        userRepo.save(user);
    }

    public Optional<User> getUser(String username) {
        return userRepo.findByEmailIgnoreCase(username);
    }

    private void validatePass(String password) {
        logger.info("========================================="+password);
        if (password.length() < 12) {
            logger.info("The password length must be at least 12 chars!!!!!!");
            throw new UserValidationException("Password length must be 12 chars minimum!");
        }
        if (Arrays.asList(BREACHED).contains(password)) {
            throw new UserValidationException("The password is in the hacker's database!");
        }
    }

    public void changepass(User user, String newPassword) {
        if (newPassword == null) {
            logger.info("The password length must be at least 12 chars!");
            throw new UserValidationException("Password length must be 12 chars minimum!");
        }
 logger.info("++++<"+newPassword+"><"+user.getPassword()+"><"+encoder.encode(newPassword)+">");
        if (encoder.matches(newPassword, user.getPassword())) {
            throw new UserValidationException("The passwords must be different!");
        }
        validatePass(newPassword);
        user.setPassword(encoder.encode(newPassword));
        userRepo.save(user);
    }
}
