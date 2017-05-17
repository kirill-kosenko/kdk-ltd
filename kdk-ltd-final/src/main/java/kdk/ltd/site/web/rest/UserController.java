package kdk.ltd.site.web.rest;

import kdk.ltd.site.root.dto.UserDTO;
import kdk.ltd.site.root.entities.User;
import org.springframework.web.bind.annotation.*;
import kdk.ltd.site.root.repositories.UserRepository;
import kdk.ltd.site.root.services.UserService;

import javax.inject.Inject;
import java.util.List;


@RestController
@RequestMapping("users")
public class UserController {
    @Inject
    private UserRepository userRepository;
    @Inject
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> showAll() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public User getById(@PathVariable Long id) {
        return this.userRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody UserDTO user) {
        userService.save(user);
    }


}
