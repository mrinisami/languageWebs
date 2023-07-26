package app.sami.languageWeb.error.exceptions;

import org.springframework.data.crossstore.ChangeSetPersister;

public class NotFoundException extends RuntimeException{

    public NotFoundException(){
        super("Not Found");
    }
    public NotFoundException(String msg){super(msg);}
}
