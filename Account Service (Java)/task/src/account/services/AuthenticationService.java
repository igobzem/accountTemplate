package account.services;

import account.data.User;
import account.data.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Service
public class AuthenticationService {
    Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    @Autowired
    private UserRepository userRepo;
    @Autowired
    PasswordEncoder encoder;

    public User signup(User user) {
        if (userRepo.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        }
        ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mr.Green", "Mr.Yellow", "Mr.Red"));
        nameList.stream().forEach(x -> System.out.println(x));
        user.setEmail(user.getEmail().toLowerCase());
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public User getUser(String username) {
        return userRepo.findByEmailIgnoreCase(username).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    public User changepass(String userName, String newPassword) {
        User user = getUser(userName);
        if (encoder.matches(newPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The passwords must be different!");
        }
        user.setPassword(encoder.encode(newPassword));
        return userRepo.save(user);
    }
}
