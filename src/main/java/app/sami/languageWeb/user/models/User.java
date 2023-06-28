package app.sami.languageWeb.user.models;

import app.sami.languageWeb.auth.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;


import java.time.Instant;
import java.util.UUID;

@Entity(name = "users")
@Table
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@With
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;
    private String lastName;
    private String email;
    @Column(name = "userPassword")
    private String password;
    private String avatarUri;
    @CreatedDate
    private Instant createdAt;

    @Column(name= "userRole")
    @Enumerated(EnumType.STRING)
    private Role role;
}
