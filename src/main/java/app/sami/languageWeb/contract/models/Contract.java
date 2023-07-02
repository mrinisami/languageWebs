package app.sami.languageWeb.contract.models;

import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.user.models.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@With
@Table
@Entity(name = "contract")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(insertable = false, updatable = false, referencedColumnName = "id")
    private User contractedUser;
    @Column(name = "contracted_user_id")
    private UUID contractedUserId;

    @LastModifiedDate
    private Instant modifiedAt;
    @CreatedDate
    private Instant createdAt;

    private Instant completedAt;

    private String filePath;

    @OneToOne
    @JoinColumn(insertable = false, updatable = false, referencedColumnName = "id")
    private Request request;
    @Column(name = "request_id")
    private Long requestId;
}
