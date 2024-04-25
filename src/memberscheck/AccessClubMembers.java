package memberscheck;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AccessClubMembers {
	private static Map<String, members> membersMap;
    private static int totalMembers = 0;
    private static final String DATA_FILE = "club_members.dat";
 
    
    public AccessClubMembers() {
        membersMap = new HashMap<>();
        readMembersFromFile(); 
    }
    
    public static void addMember(String name, String email, String joiningDate) {
        members member = new members(name, email, joiningDate);
        membersMap.put(email, member);
        totalMembers++;
    }

    public static void updateMember(String email, String newName, String newJoiningDate) {
        members member = membersMap.get(email);

        if (member != null) {
            member.setName(newName);
            member.setJoiningDate(newJoiningDate);
          
        }
    }

    public static void deleteMember(String email) {
        members member = membersMap.remove(email);

        if (member != null) {
            totalMembers--;
        }
    }

    public static members accessMemberByEmail(String email) {
        return membersMap.get(email);
    }

    public static int getTotalMembers() {
        return totalMembers;
    }
    public Map<String, members> getAllMembers() {
        return membersMap;
    }
    
    public static void writeMembersToFile() {
        if (membersMap != null) { // Only write to file if membersMap is not null
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
                oos.writeObject(membersMap);
                System.out.println("Member data saved to file: " + DATA_FILE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    @SuppressWarnings("unchecked")
    public static void readMembersFromFile() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
                membersMap = (Map<String, members>) ois.readObject();
                totalMembers = membersMap.size();
                System.out.println("Member data loaded from file: " + DATA_FILE);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

	
}

