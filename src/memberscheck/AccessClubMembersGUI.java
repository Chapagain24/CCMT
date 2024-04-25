package memberscheck;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class AccessClubMembersGUI extends JFrame {

    private static AccessClubMembers clubManager = new AccessClubMembers();
    private static JFrame frame = new JFrame("Club Members Management");
    private static JTextArea textArea = new JTextArea();

  
    private static void addMember() {
        String name = JOptionPane.showInputDialog("Enter member name:");
        String email = JOptionPane.showInputDialog("Enter member email:");
        String joiningDate = JOptionPane.showInputDialog("Enter joining date:");

        clubManager.addMember(name, email, joiningDate);
        JOptionPane.showMessageDialog(frame, "Member added successfully!");
       
    }

    private static void viewMembers(JFrame frame) {
    	 StringBuilder message = new StringBuilder();
         message.append("Total members: ").append(AccessClubMembers.getTotalMembers()).append("\n");

         for (members member : clubManager.getAllMembers().values()) {
             message.append("\nName: ").append(member.getName())
                     .append("\nEmail: ").append(member.getEmail())
                     .append("\nJoining Date: ").append(member.getJoiningDate())
                     //.append("\nClub Description: ").append(member.getClubDescription())
                     .append("\n-----------------------------");
         }
         JOptionPane.showMessageDialog(frame, message.toString());

    }

    private static void deleteMember() {
        String email = JOptionPane.showInputDialog("Enter member email to delete:");

        members member = clubManager.accessMemberByEmail(email);
        if (member != null) {
            clubManager.deleteMember(email);
            JOptionPane.showMessageDialog(frame, "Member deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(frame, "Member not found.");
        }
    }
  
    public static void displayClubDetails(String name, String email, String joiningDate, String clubDescription) {
        JFrame detailsFrame = new JFrame("Club Details");
        detailsFrame.setSize(400, 300);
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel detailsPanel = new JPanel();
        JTextArea detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);

        detailsTextArea.append("Club Name: " + name + "\n");
        detailsTextArea.append("Email: " + email + "\n");
        detailsTextArea.append("Joining Date: " + joiningDate + "\n");
        detailsTextArea.append("Club Description: " + clubDescription + "\n");
        int totalMembers = AccessClubMembers.getTotalMembers();
        detailsTextArea.append("Total Members: " + totalMembers + "\n");

        JButton addButton = new JButton("Add Member");
        JButton viewButton = new JButton("View Members");
        JButton deleteButton = new JButton("Delete Member");
        JButton exitButton = new JButton("Exit");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addMember();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewMembers(frame);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteMember();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	exitApplication();
                detailsFrame.dispose();
            }
        });

        detailsPanel.add(new JScrollPane(detailsTextArea));
        detailsPanel.add(addButton);
        detailsPanel.add(viewButton);
        detailsPanel.add(deleteButton);
        detailsPanel.add(exitButton);

        detailsFrame.getContentPane().add(detailsPanel);
        detailsFrame.setVisible(true);
    }

 
    private static void exitApplication() {
        // Save members to file before exiting
        clubManager.writeMembersToFile();
        frame.dispose();
    }
}
