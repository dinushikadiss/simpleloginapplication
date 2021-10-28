package dto;
/**
 * Data transfer class for the Users
 */
public class UserDTO {
    private String name;
    private String email;
    private String password;

    public UserDTO() {}

    public UserDTO(String email, String name,  String password) {

        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Returns the name of the user
     *
     * @return String
     */
    public String getName() {

        return name;
    }

    /**
     * Returns the email of the user
     *
     * @return String
     */
    public String getEmail() {

        return email;
    }

    /**
     * Returns the hashed password of the user
     *
     * @return String
     */
    public String getPassword() {

        return password;
    }
}
