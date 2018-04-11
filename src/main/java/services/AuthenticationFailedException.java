package services;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException(String s) {
        super(s);
    }
}
