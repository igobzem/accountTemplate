package account.services;

import account.data.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    private static final String EMAIL_ENDS = "@acme.com";

    private UserRepository userRepo;

    public ResponseEntity signup(Map<String, String> map) {
        String name = map.get("name");
        String lastname = map.get("lastname");
        String email = map.get("email");
        String password = map.get("password");
        if (name == null || lastname == null || email == null || password == null) {
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        if (name.isEmpty() || lastname.isEmpty() || !email.endsWith(EMAIL_ENDS) || password.isEmpty()) {
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        Map<String, String> response = new HashMap<>();
        response.put("name", name);
        response.put("lastname", lastname);
        response.put("email", email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
