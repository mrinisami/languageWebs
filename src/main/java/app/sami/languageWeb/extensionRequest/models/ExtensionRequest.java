package app.sami.languageWeb.extensionRequest.models;

import app.sami.languageWeb.contract.models.Contract;
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
@Entity(name = "extension_request")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ExtensionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @LastModifiedDate
    private Instant modifiedAt;
    @CreatedDate
    private Instant createdAt;
    private Instant proposedDate;

    @Enumerated(EnumType.STRING)
    private ExtensionRequestStatus status;
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(insertable = false, updatable = false, referencedColumnName = "id")
    private User user;
    @Column(name = "user_id")
    private UUID userId;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Contract.class)
    @JoinColumn(insertable = false, updatable = false, referencedColumnName = "id")
    private Contract contract;
    @Column(name = "contract_id")
    private Long contractId;

    public void updateStatus(UUID subject, ExtensionRequestStatus status){
        if (isRequester(subject)) {
            if (status == ExtensionRequestStatus.CANCELLED) {
                this.status = status;
                return;
            }
        }
        this.status = status;
    }

    public boolean isContractor(UUID subject){
        return this.contract.isContractor(subject);
    }
    public boolean isRequester(UUID subject){
        return this.userId.equals(subject);
    }

    public boolean isAccepted(){
        return this.status.equals(ExtensionRequestStatus.ACCEPTED);
    }
}
