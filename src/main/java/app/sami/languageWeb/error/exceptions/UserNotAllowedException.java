package app.sami.languageWeb.error.exceptions;

public class UserNotAllowedException extends RuntimeException{
   public UserNotAllowedException() {
       super("Not the same user who has created the review");
   }
}
