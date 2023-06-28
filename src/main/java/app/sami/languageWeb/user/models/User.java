package app.sami.languageWeb.user.models;

import app.sami.languageWeb.auth.Role;
import app.sami.languageWeb.request.models.Request;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    private String userPassword;
    private String avatarUri;
    @CreatedDate
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    private Role userRole;
}
