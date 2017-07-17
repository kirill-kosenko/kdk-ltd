package kdk.ltd.site.root.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;


@Entity
@Table(name = "authorities",
    uniqueConstraints = {@UniqueConstraint(columnNames =
            {"username", "authority"} )
    }
)
public class Authority extends PersistableObject implements GrantedAuthority {

    public Authority() {
    }

    public Authority(User user, Role authority) {
        this.user = user;
        this.authority = authority;
    }

    public Authority(String string) {
        this.authority = Role.valueOf(string);
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role authority;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getAuthority() {
        return authority.toString();
    }

    public void setAuthority(Role authority) {
        this.authority = authority;
    }
}
