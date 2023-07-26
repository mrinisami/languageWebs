package app.sami.languageWeb.payment;

import app.sami.languageWeb.contract.models.Contract;
import app.sami.languageWeb.user.models.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@With
@Table
@Entity(name = "payment")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = Contract.class)
    @JoinColumn(insertable = false, updatable = false, referencedColumnName = "id")
    private Contract contract;
    @Column(name = "contract_id")
    private Long contractId;

    @CreatedDate
    private Instant createdAt;

    private Double payment;
}
