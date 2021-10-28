package dto;

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

    public String getName() {

        return name;
    }

    public String getEmail() {

        return email;
    }

    public String getPassword() {

        return password;
    }
}
