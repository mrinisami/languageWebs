package app.sami.languageWeb.error.exceptions;

public class ContractTranslatedFileAbsent extends RuntimeException{
    public ContractTranslatedFileAbsent() {super("File must be added to terminate the contract.");}
}
