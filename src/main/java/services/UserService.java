package services;

import exceptions.InputInvalidException;
import util.Constants;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserService {

    private boolean isExited = false;
    private String loggedInUser = null;

    public void start(){
        while (!isExited){
            try {
                int action = selectAction();
                processUserAction(action);
            }catch (InputMismatchException | InputInvalidException e){
                e.printStackTrace();
            }
        }
    }

    private int selectAction() {
        System.out.print("Select Action\n* Login -> 1\n* Register -> 2\n* List Users -> 3\n* Exit -> 4\n");
        Scanner input = new Scanner(System.in);
        int action = -1;
        action = input.nextInt();
        while (action != 1 && action != 2 && action != 3 && action != 5) {
            System.out.println("Enter 1,2 or 3");
            action = input.nextInt();
        }
        return action;
    }

    private void exitUser(){
        logOutUser();
        isExited = true;
    }

    private void logOutUser(){
        loggedInUser = null;
    }

    private void processUserAction(int action) throws InputInvalidException {
        if(action == Constants.ACTION_EXIT){
            exitUser();
        }else if(action == Constants.ACTION_LOGOUT){
            logOutUser();
        }else if (action == Constants.ACTION_LOGIN){
            loggedInUser = "dinushika";
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
                throw new InputInvalidException(Constants.USER_REGISTRATION_PASSWORD_NOT_MATCHING_MESSAGE);
            }



        }else if (action == Constants.ACTION_LIST_USERS){

        }
    }

    private boolean validateNewUserDetails(String... userDetails){
        for (String userDetail : userDetails) {
            if (userDetail.isEmpty()) {
                return false;
            }
        }

        return true;
    }


}
