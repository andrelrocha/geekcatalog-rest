package rocha.andre.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.user.UserRepository;

@Service
public class AuthenticateService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //caso inicial passando o login
        UserDetails userDetails = userRepository.findByLogin(username);
        if (userDetails == null) {
            userDetails = userRepository.findByUsername(username);
        }
        return userDetails;
    }
}
