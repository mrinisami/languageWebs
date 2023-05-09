package app.sami.languageWeb.props;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class JksProps {

    @NotBlank
    public final String filePath;
    @NotBlank
    public final String password;

}
