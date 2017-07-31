package kdk.ltd.site.root.services;

import kdk.ltd.site.root.dto.UserDto;
import kdk.ltd.site.root.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void save(User user);
    void save(UserDto dto);
}
