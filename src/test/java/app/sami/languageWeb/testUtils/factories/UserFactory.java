package app.sami.languageWeb.testUtils.factories;

import app.sami.languageWeb.auth.Role;
import app.sami.languageWeb.testUtils.Randomize;
import app.sami.languageWeb.user.models.User;

public class UserFactory {

    public static User userGenerator(){
        User user = User.builder()
                .userPassword(Randomize.string("test"))
                .userRole(Role.USER)
                .email(Randomize.email())
                .firstName(Randomize.name())
                .lastName(Randomize.name())
                .build();

        return user;
    }

    public static User evaluatorGenerator(){
        User user = User.builder()
                .userPassword(Randomize.string("test"))
                .userRole(Role.EVALUATOR)
                .email(Randomize.email())
                .firstName(Randomize.name())
                .lastName(Randomize.name())
                .build();

        return user;
    }
}
