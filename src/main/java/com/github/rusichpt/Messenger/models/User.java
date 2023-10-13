package com.github.rusichpt.Messenger.models;

import com.github.rusichpt.Messenger.dto.UserProfile;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private String password;
    @NotNull
    @Column(unique = true)
    private String username;
    private String name;
    private String surname;
    private boolean emailConfirmed;
    private String confirmationCode;

    public void setProfile(UserProfile profile) {
        username = profile.getUsername();
        email = profile.getEmail();
        name = profile.getName();
        surname = profile.getSurname();
    }

    public UserProfile getProfile() {
        return new UserProfile(username, email, name, surname);
    }
}
