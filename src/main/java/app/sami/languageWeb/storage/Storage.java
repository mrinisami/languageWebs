package app.sami.languageWeb.storage;

public interface Storage {

    public String getUploadPresignedUrl(String name);
    public String getDownloadPresignedUrl(String name);

    public String getDeletePresignedUrl(String name);

}
