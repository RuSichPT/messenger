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
    @Column(unique = true)
    @NotNull
    private String email;
    @NotNull
    private String password;
    @Column(unique = true)
    @NotNull
    private String username;
    private String name;
    private String surname;

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
