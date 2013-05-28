package edu.ucla.loni.pipeline.client.Login;

import java.io.Serializable;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = -1744327211076049203L;
    private String username;
    private String password;
    
    public UserDTO() {
        this.username = "defaultUser";
        this.password = "defaultPassword";
    }
    
    public UserDTO(String username, String password) {
    		System.out.println("UserDTO: Constructor");
           this.username = username;
           this.password = password;
    }

    public String getUsername() {
            return username;
    }
    public void setUsername(String username) {
            this.username = username;
    }
    public String getPassword() {
            return password;
    }
    public void setPassword(String password) {
            this.password = password;
    }

}
    