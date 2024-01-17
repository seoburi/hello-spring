package hello.hellospring.util;

public class CustomJWTException extends RuntimeException {

    public CustomJWTException(String msg) {
        super(msg);
    }
}
