package app.sami.languageWeb.error.exceptions;

public class PaymentAlreadyMadeException extends RuntimeException{
    public PaymentAlreadyMadeException(){super ("Payment has already been made for this contract");}
}
