package app.sami.languageWeb.stripe;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StripeAccountRepository extends JpaRepository<StripeAccount, Long> {
    Optional<StripeAccount> findByUserId(UUID userId);
}
