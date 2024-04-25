package memberscheck;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class members implements Serializable{
	private static final long serialVersionUID = 1L;
	
    private String name;
    private String email;
    private String joiningDate;
    private String clubDescription;
   

    public members(String name, String email, String joiningDate,String clubDescription) {
        this.name = name;
        this.email = email;
        this.joiningDate = joiningDate;
        this.clubDescription = clubDescription;
       
    }
    public members(String name, String email, String joiningDate) {
        this.name = name;
        this.email = email;
        this.joiningDate = joiningDate;
         
    }

	public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getJoiningDate() {
        return joiningDate;
    }
    public String getClubDescription() {
        return clubDescription;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
  
    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }
    
    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }
    
}
 