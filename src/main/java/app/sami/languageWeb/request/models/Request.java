package app.sami.languageWeb.request.models;

import app.sami.languageWeb.language.models.Language;
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
@Entity(name = "request")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Language sourceLanguage;
    @Enumerated(EnumType.STRING)
    private Language translatedLanguage;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(insertable = false, updatable = false, referencedColumnName = "id")
    private User user;
    @Column(name = "user_id")
    private UUID userId;
    private Double price;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant modifiedAt;
    private Double estimatedTime;
    private Integer nbWords;
    private String description;
    private String filePath;
    private String name;
    private Instant dueDate;

    public boolean isRequester(UUID subject){
        return this.userId.equals(subject);
    }
    public void updateDueDate(UUID subject, Instant date){
        if (isRequester(subject)){
            this.dueDate = date;
        }
    }
    public Double totalPrice(){
        return this.price * this.nbWords;
    }
}
