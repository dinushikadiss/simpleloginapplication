package util;

public class Constants {
    // Action selection
    public static final int ACTION_LOGIN = 1;
    public static final int ACTION_REGISTER = 2;
    public static final int ACTION_LIST_USERS = 3;
    public static final int ACTION_EXIT = 4;
    public static final int ACTION_LOGOUT = 5;

    // Messages
    public static final String USER_REGISTRATION_INVALID_USER_DETAILS = "All fields should be filled";
    public static final String USER_ADDED_MESSAGE = "User added successfully";
    public static final String USER_REGISTRATION_FAILED_MESSAGE = "User registration failed";
    public static final String USER_EXISTS = "User already exists";
    public static final String CREDENTIALS_INVALID = "Credentials invalid!";
    public static final String LOGGED_IN_MESSAGE = "Successfully logged in!";
    public static final String ALREADY_LOGGED_IN_MESSAGE = "Already logged in!";
    public static final String INVALID_INPUT = "Invalid input";
    public static final String INVALID_EMAIL = "Invalid email";

    // database configuration file path
    public static final String DB_CONFIG_FILE_PATH = "config.properties";

    // Logs file path
    public static final String LOGS_FILE_PATH = "logs.log";

    // SQL queries
    public static final String QUERY_INSERT_NEW_USER= "INSERT INTO user(email, name, password) VALUES (?,?,?)";
    public static final String QUERY_GET_USER = "SELECT * FROM user WHERE user.email=?";
    public static final String QUERY_GET_ALL_USERS = "SELECT * FROM user";

    //DB Fields
    public static final String DB_FIELD_USER_NAME = "name";
    public static final String DB_FIELD_USER_EMAIL = "email";
    public static final String DB_FIELD_USER_PASSWORD = "password";

    //Logs messages
    public static final String FAILED_LOGIN = "Invalid login with Email : ";
    public static final String SUCCESS_LOGIN = "Successful login with Email : ";
    public static final String SUCCESS_REGISTRATION = "Successful user registration - Email : ";
}
