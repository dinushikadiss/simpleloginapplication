package dao;

import dto.UserDTO;
import java.util.ArrayList;

public interface UserDAO {

    UserDTO findUser(String email);
    boolean addUser(UserDTO user);
    ArrayList<String> getAllUsers();
}
