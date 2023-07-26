package account.services;

import account.Controller;
import account.data.User;
import account.data.UserRepository;
import account.exceptions.UserExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {
    Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    @Autowired
    private UserRepository userRepo;
    @Autowired
    PasswordEncoder encoder;

    public void signup(User user) {
        Optional<User> optional = userRepo.findByEmailIgnoreCase(user.getEmail());
        if (optional.isPresent()) {
            throw new UserExistException("User exist!");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user = userRepo.save(user);
    }

    public Optional<User> getUser(String username) {
        return userRepo.findByEmailIgnoreCase(username);
    }

}
