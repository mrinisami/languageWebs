package app.sami.languageWeb.props;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.URL;

@AllArgsConstructor
public class MinioProps {
    @NotBlank
    public final String bucketName;
    @NotBlank
    public final String username;
    @NotBlank
    public final String password;
    @URL
    public final String url;
}
