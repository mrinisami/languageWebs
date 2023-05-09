package app.sami.languageWeb.auth;

public enum Role {
    USER,
    ADMIN;

    public static class Raw{
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";
    }
}
