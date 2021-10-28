package dao.impl;

import dao.UserDAO;
import database.JDBCDatabaseConnector;
import dto.UserDTO;
import util.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * User Database Access class
 */
public class JDBCUserDAOImpl implements UserDAO {

    /**
     * Returns the user dto corresponding to the email
     *
     * @param email Email of the user
     * @return UserDTO if the user is exists
     */
    @Override
    public UserDTO findUser(String email) {

        UserDTO user = null;
        PreparedStatement ps = null;
        try {
            String getNewUserByUsernameAndEmailQuery = Constants.QUERY_GET_USER;
            ps = JDBCDatabaseConnector.getInstance().getConnection().prepareStatement(getNewUserByUsernameAndEmailQuery);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new UserDTO(
                        rs.getString(Constants.DB_FIELD_USER_EMAIL),
                        rs.getString(Constants.DB_FIELD_USER_NAME),
                        rs.getString(Constants.DB_FIELD_USER_PASSWORD));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                assert ps != null;
                ps.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        return user;
    }

    /**
     * Adds a new user to the database
     *
     * @param user dto with user data
     * @return boolean value of user added or not
     */
    @Override
    public boolean addUser(UserDTO user) {

        PreparedStatement ps = null;
        try {
            String addNewUserQuery = Constants.QUERY_INSERT_NEW_USER;
            ps = JDBCDatabaseConnector.getInstance().getConnection().prepareStatement(addNewUserQuery);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.execute();
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                assert ps != null;
                ps.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Returns list of all users
     *
     * @return ArrayList of the names of the users
     */
    @Override
    public ArrayList<String> getAllUsers() {

        PreparedStatement ps = null;
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            String getAllUsers = Constants.QUERY_GET_ALL_USERS;
            ps = JDBCDatabaseConnector.getInstance().getConnection().prepareStatement(getAllUsers);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name =  rs.getString(Constants.DB_FIELD_USER_NAME);
                arrayList.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                assert ps != null;
                ps.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        return arrayList;
    }
}
