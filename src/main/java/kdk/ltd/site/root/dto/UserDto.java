package kdk.ltd.site.root.dto;

import kdk.ltd.site.root.entities.Authority;
import kdk.ltd.site.root.entities.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private List<String> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public static User build(UserDto dto) {
        Set<Authority> authorities = dto.getRoles().stream().map(Authority::new).collect(Collectors.toSet());
        return new User(dto.getUsername(), dto.password, dto.getEmail(), authorities);
    }
}
