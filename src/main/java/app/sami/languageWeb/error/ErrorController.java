package app.sami.languageWeb.error;

import app.sami.languageWeb.error.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler({NotFoundException.class, LanguageNotRegisteredException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Error.Response notFound(Exception ex){
        return new Error.Response(ex.getMessage());
    }

    @ExceptionHandler({UserNotAllowedException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public Error.Response unauthorized(Exception ex) {return new Error.Response(ex.getMessage());}

    @ExceptionHandler({MinioPutUriException.class, ContractTranslatedFileAbsent.class})
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    public Error.Response storageProblem(Exception ex){return new Error.Response(ex.getMessage());}
}
