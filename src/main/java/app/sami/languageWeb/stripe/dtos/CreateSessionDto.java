package app.sami.languageWeb.stripe.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class CreateSessionDto {
    private String successUrl;
    private String cancelUrl;
    private Double price;
    private Long nbWords;
}
