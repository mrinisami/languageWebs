package app.sami.languageWeb.request.models;

public enum Status {
    PENDING,
    ACCEPTED,
    COMPLETED;

    public static class Raw{
        public static final String PENDING = "PENDING";
        public static final String ACCEPTED = "ACCEPTED";
        public static final String COMPLETED = "COMPLETED";
    }
}
