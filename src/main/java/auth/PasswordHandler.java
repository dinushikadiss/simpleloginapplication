package auth;

public interface PasswordHandler {

    String encryptPassword(String password);
    boolean matchPasswords(String password,String hash);
}
