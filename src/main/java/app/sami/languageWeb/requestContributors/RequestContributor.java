package app.sami.languageWeb.requestContributors;

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
@Entity(name = "request_contributor")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RequestContributor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(insertable = false, updatable = false, referencedColumnName = "id")
    private User user;
    @Column(name = "user_id")
    private UUID userId;

    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant modifiedAt;

    private Double contribution;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, referencedColumnName = "id")
    private Request request;
    @Column(name = "request_id")
    private Long requestId;
}
