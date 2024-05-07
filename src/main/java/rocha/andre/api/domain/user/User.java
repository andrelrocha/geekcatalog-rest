package rocha.andre.api.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rocha.andre.api.domain.country.Country;
import rocha.andre.api.domain.user.DTO.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Table(name = "users")
@Entity(name = "User")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @Column(name = "login", unique = true)
    private String login;

    private String password;

    private String name;

    @Column(name = "cpf", length = 14, unique = true)
    private String cpf;

    @Column(name = "phone", length = 14)
    private String phone;
    @Column
    private LocalDate birthday;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "token_mail")
    private String tokenMail;

    @Column(name = "token_expiration")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime tokenExpiration;

    public User(UserCreateDTO dto) {
        this.login = dto.data().login();
        this.password = dto.data().password();
        this.name = dto.data().name();
        this.cpf = dto.data().cpf();
        this.phone = dto.data().phone();
        this.birthday = dto.birthday();
        this.country = dto.country();
        this.role = UserRole.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_USER"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void setAdmin() { this.role = UserRole.ADMIN; }


    public void forgotPassword(UserForgotDTO data) {
        this.tokenMail = data.tokenMail();
        this.tokenExpiration = data.tokenExpiration();
    }

    public void updateUser(UserUpdateDTO data) {
        if (data.name() != null ) {
            this.name = data.name();
        }
        if (data.phone() != null) {
            this.phone = data.phone();
        }
        if (data.birthday() != null) {
            this.birthday = data.birthday();
        }
    }

    public void updateCountry(Country country) {
        this.country = country;
    }

    public void setTokenExpiration(LocalDateTime time) {
        this.tokenExpiration = time;
    }
}
