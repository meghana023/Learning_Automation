package apiTests;


public class Constants {

    public static final String USER_DIR = System.getProperty("user.dir");

    public static final class RestMethod {
        public enum TYPE {
            POST, GET, PUT, DELETE, CONTENT_LESS_PUT
        }
    }
}
