package app.sami.languageWeb.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity(name = "texts")
@Table
@NoArgsConstructor
@Data
public class texts {
    @Id
    @GeneratedValue
    private UUID id;

    private String textContent;
    private String textLink;
    private String textLanguage;
    private Date dateAdded;
    private UUID userId;
}
