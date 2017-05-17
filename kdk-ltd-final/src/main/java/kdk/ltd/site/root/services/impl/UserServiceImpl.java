package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.dto.UserDTO;
import kdk.ltd.site.root.entities.User;
import kdk.ltd.site.root.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import kdk.ltd.site.root.repositories.UserRepository;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;
  //  @Inject
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(User user) {
        if (findByUsername(user.getUsername()).isPresent())
            throw new ConstraintViolationException("The provided username already exists!", null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getAuthorities().forEach(a -> a.setUser(user));
        userRepository.save(user);
    }

    @Override
    public void save(UserDTO dto) {
        User newUser = UserDTO.build(dto);
        this.save(newUser);
    }
}
