package account.services;

import account.data.User;
import account.data.UserDetailsImpl;
import account.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepo.findByName(name);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Not found: " + name);
        }

        return new UserDetailsImpl(optionalUser.get());
    }
}
