package app.sami.languageWeb.auth;

import java.util.ArrayList;
import java.util.List;

public enum Role {
    USER,
    ADMIN,
    EVALUATOR;

    public static class Raw{
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";
        public static final String EVALUATOR = "EVALUATOR";
    }

    public List<String> impliedRoles(Role role){
        List<String> roles = new ArrayList<>();
        roles.add(role.toString());

        if (role != Role.USER){
            roles.add(USER.toString());
        }


        return roles;
    }
}
