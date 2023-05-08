package app.sami.languageWeb.error;

import app.sami.languageWeb.error.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Error.Response notFound(Exception ex){
        return new Error.Response(ex.getMessage());
    }
}
