import services.UserService;

public class SimpleLogin {
    public static void main(String[] args){
        UserService userService = new UserService();
        userService.start();
    }
}
