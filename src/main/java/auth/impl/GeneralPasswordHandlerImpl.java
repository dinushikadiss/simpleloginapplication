package auth.impl;

import auth.PasswordHandler;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * General class to handle the password related operations
 */
public class GeneralPasswordHandlerImpl implements PasswordHandler {

    /**
     * Encrypts the password
     *
     * @param password password needed to be encrypted
     * @return Encrypted password
     */
    @Override
    public String encryptPassword(String password) {

        return  BCrypt.hashpw( password, BCrypt.gensalt(12) );
    }

    /**
     * Compares user entered password with stored password in the database
     *
     * @param password entered password
     * @param hash encrypted password in the database
     * @return boolean value after comparing two passwords
     */
    @Override
    public boolean matchPasswords(String password, String hash) {

        return  BCrypt.checkpw( password, hash);
    }
}
