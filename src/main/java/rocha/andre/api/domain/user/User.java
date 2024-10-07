package rocha.andre.api.domain.user;

import jakarta.persistence.*;
import lombok.*;
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
@Setter
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
    @Column(name = "username", unique = true, length = 20)
    private String username;

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

    @Enumerated(EnumType.STRING)
    private UserTheme theme;

    @Column(name = "access_failed_count")
    private int accessFailedCount = 0;

    @Column(name = "lockout_enabled")
    private boolean lockoutEnabled = false;

    @Column(name = "lockout_end")
    private LocalDateTime lockoutEnd;

    @Column(name = "refresh_token_enabled")
    private boolean refreshTokenEnabled = false;

    @Column(name = "two_factor_enabled")
    private boolean twoFactorEnabled = false;

    @Column(name = "token_mail")
    private String tokenMail;

    @Column(name = "token_expiration")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime tokenExpiration;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public User(UserCreateDTO dto) {
        this.login = dto.data().login();
        this.password = dto.data().password();
        this.name = dto.data().name();
        this.username = dto.data().username();
        this.cpf = dto.data().cpf();
        this.phone = dto.data().phone();
        this.birthday = dto.birthday();
        this.country = dto.country();
        this.twoFactorEnabled = dto.data().twoFactorEnabled();
        this.refreshTokenEnabled = dto.data().refreshTokenEnabled();
        this.theme = UserTheme.valueOf(dto.data().theme());
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
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !lockoutEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !lockoutEnabled;
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

        if (data.username() != null) {
            this.username = data.username();
        }

        if (data.phone() != null) {
            this.phone = data.phone();
        }

        if (data.birthday() != null) {
            this.birthday = data.birthday();
        }

        if (data.twoFactorEnabled() != null) {
            this.twoFactorEnabled = data.twoFactorEnabled();
        }

        if (data.refreshTokenEnabled() != null) {
            this.refreshTokenEnabled = data.refreshTokenEnabled();
        }

        if (data.theme() != null) {
            this.theme = UserTheme.valueOf(data.theme());
        }

        if (data.country() != null) {
            this.country = data.country();
        }
    }

    public void resetAccessCount() {
        this.accessFailedCount = 0;
        this.setLockoutEnabled(false);
        this.setLockoutEnd(null);
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void updateCountry(Country country) {
        this.country = country;
    }

    public void updateBirthday(LocalDate date) { this.birthday = date; }

    public void setTokenExpiration(LocalDateTime time) {
        this.tokenExpiration = time;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
