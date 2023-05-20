package app.sami.languageWeb.error.exceptions;

public class LanguageNotRegisteredException extends RuntimeException{

    public LanguageNotRegisteredException(){
        super("User has not registered this language as one of his.");
    }
}
