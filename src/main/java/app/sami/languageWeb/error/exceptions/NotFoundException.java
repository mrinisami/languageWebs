package app.sami.languageWeb.error.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(){
        super("Not Found");
    }
}
