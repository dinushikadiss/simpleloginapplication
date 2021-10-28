package services;

import auth.impl.GeneralPasswordHandlerImpl;
import dao.UserDAO;
import dao.impl.JDBCUserDAOImpl;
import dto.UserDTO;
import exceptions.InputInvalidException;
import exceptions.UserRegistrationFailedException;
import util.LogConfig;
import util.Constants;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Service class for user actions
 */
public class UserService {

    private boolean isExited = false;
    private String loggedInUser = null;

    /**
     * Program entry point
     */
    public void start() {

        while (!isExited){
            try {
                int action = selectAction();
                processUserAction(action);
            }catch (InputInvalidException  | UserRegistrationFailedException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private int selectAction() throws InputInvalidException {

        System.out.print("\nSelect Action\n" +
                "* Login -> 1\n" +
                "* Register -> 2\n" +
                "* List Users -> 3\n" +
                "* Exit -> 4\n" +
                "* Logout -> 5\n");
        Scanner input = new Scanner(System.in);
        int action = -1;
        try {
            action = input.nextInt();
        }catch (InputMismatchException e){
            throw new InputInvalidException(Constants.INVALID_INPUT);
        }
        while (action != 1 && action != 2 && action != 3 && action != 4 && action != 5) {
            System.out.println("Enter 1,2,3,4 or 5");
            try {
                action = input.nextInt();
            }catch (InputMismatchException e){
                throw new InputInvalidException(Constants.INVALID_INPUT);
            }

        }
        return action;
    }

    private void exitUser() {

        logOutUser();
        isExited = true;
    }

    private void logOutUser(){

        loggedInUser = null;
    }

    private void processUserAction (int action) throws InputInvalidException, UserRegistrationFailedException {

        if(action == Constants.ACTION_EXIT){
            exitUser();

        } else if(action == Constants.ACTION_LOGOUT){

            if(isLoggedIn()){
                logOutUser();
                System.out.println("Successfully logged out!");
            } else {
                System.out.println("You are not logged in!");
            }

        } else if (action == Constants.ACTION_LOGIN){

            if(isLoggedIn()) {
                System.out.println(Constants.ALREADY_LOGGED_IN_MESSAGE);
            } else {
                Scanner input = new Scanner(System.in);
                System.out.print("Email: ");
                String email = input.nextLine();
                System.out.print("Password: ");
                String password = input.nextLine();

                boolean isValidUserDetails = validateNewUserDetails(email,password);
                if(!isValidUserDetails){
                    throw new InputInvalidException(Constants.USER_REGISTRATION_INVALID_USER_DETAILS);
                }

                UserDTO existingUser = getUser(email);
                if(existingUser == null){
                    LogConfig.makeLog(Constants.FAILED_LOGIN+email);
                    System.out.println(Constants.CREDENTIALS_INVALID);
                }else {
                    GeneralPasswordHandlerImpl passwordHandler = new GeneralPasswordHandlerImpl();
                    boolean validCredentials = passwordHandler.matchPasswords(password,existingUser.getPassword());
                    if (validCredentials){
                        LogConfig.makeLog(Constants.SUCCESS_LOGIN+email);
                        loggedInUser = existingUser.getEmail();
                        System.out.println(Constants.LOGGED_IN_MESSAGE);
                    }else {
                        LogConfig.makeLog(Constants.FAILED_LOGIN+email);
                        System.out.println(Constants.CREDENTIALS_INVALID);
                    }
                }
            }

        }else if (action == Constants.ACTION_REGISTER){

            Scanner input = new Scanner(System.in);
            System.out.println("\nRegistering New User\n");
            System.out.print("Email: ");
            String email = input.nextLine();
            System.out.print("Name: ");
            String name = input.nextLine();
            System.out.print("Password: ");
            String password = input.nextLine();

            boolean isValidUserDetails = validateNewUserDetails(email,name,password);
            if(!isValidUserDetails){
                throw new InputInvalidException(Constants.USER_REGISTRATION_INVALID_USER_DETAILS);
            }

            boolean isValidEmail = isValidEmail(email);
            if(!isValidEmail){
                throw new InputInvalidException(Constants.INVALID_EMAIL);
            }

            UserDTO existingUser = getUser(email);
            if(existingUser == null){
                GeneralPasswordHandlerImpl passwordHandler = new GeneralPasswordHandlerImpl();
                UserDTO user = new UserDTO(email, name, passwordHandler.encryptPassword(password));
                boolean isAdded = addUser(user);
                if(isAdded){
                    System.out.println(Constants.USER_ADDED_MESSAGE+"\n");
                } else {
                    throw new UserRegistrationFailedException(Constants.USER_REGISTRATION_FAILED_MESSAGE);
                }
            } else {
                System.out.println(Constants.USER_EXISTS+"\n");
            }

        } else if (action == Constants.ACTION_LIST_USERS){

            if(isLoggedIn()){
                ArrayList<String> usersList = getAllUsers();
                System.out.println("\nList of All Users\n");
                usersList.forEach(value -> System.out.print(value+"\n"));
            } else {
                System.out.println("Please login!");
            }
        }
    }

    private boolean validateNewUserDetails(String... userDetails) {


        for (String userDetail : userDetails) {
            if (userDetail.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidEmail(String email) {

        final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
        return EMAIL_REGEX.matcher(email).matches();
    }

    private UserDTO getUser(String email) {

        UserDAO userDAO = new JDBCUserDAOImpl();
        return userDAO.findUser(email);
    }

    private boolean addUser(UserDTO user){

        UserDAO userDAO = new JDBCUserDAOImpl();
        return userDAO.addUser(user);
    }

    private boolean isLoggedIn(){

        return loggedInUser != null;
    }

    private ArrayList<String> getAllUsers(){

        UserDAO userDAO = new JDBCUserDAOImpl();
        return userDAO.getAllUsers();
    }
}
