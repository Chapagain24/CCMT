package memberscheck;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClubMembershipGUI extends JFrame {
    private ClubMembershipSystem clubMembershipSystem;

    public ClubMembershipGUI() {
        super("Club Membership System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        clubMembershipSystem = new ClubMembershipSystem();
        clubMembershipSystem.readMembersFromFile(); // Load members from the file

        JButton addMemberButton = new JButton("Add Club");
        addMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMemberActionPerformed();
            }
        });

        JButton updateMemberButton = new JButton("Update Club");
        updateMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMemberActionPerformed();
            }
        });

        JButton deleteMemberButton = new JButton("Delete Club");
        deleteMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMemberActionPerformed();
            }
        });

        JButton accessMemberButton = new JButton("Access Club");
        accessMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accessMemberActionPerformed();
            }
        });

        JButton displayTotalMembersButton = new JButton("Display All Clubs");
        displayTotalMembersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayTotalMembersActionPerformed();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitActionPerformed();
            }
        });

        setLayout(new GridLayout(6, 1));
        add(addMemberButton);
        add(updateMemberButton);
        add(deleteMemberButton);
        add(accessMemberButton);
        add(displayTotalMembersButton);
        add(exitButton);

        setSize(300, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addMemberActionPerformed() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField joiningDateField = new JTextField();
        JTextField clubDescriptionField = new JTextField();

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Joining Date:"));
        panel.add(joiningDateField);
        panel.add(new JLabel("Club Description:"));
        panel.add(clubDescriptionField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Club Information",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String email = emailField.getText();
            String joiningDate = joiningDateField.getText();
            String clubDescription = clubDescriptionField.getText();

            // Now you can use these values as needed.
            ClubMembershipSystem.addMember(name, email, joiningDate, clubDescription);
            JOptionPane.showMessageDialog(this, "Club added successfully!");
        }
    }

    private void updateMemberActionPerformed() {
        String email;
        members member;

        // Step 1: Get the email
        do {
            email = JOptionPane.showInputDialog("Enter Club's Email to update:");

            // Check if the user pressed cancel or entered an empty string
            if (email == null || email.trim().isEmpty()) {
                // Cancelled the process
                return;
            }

            // Check if the member with the provided email exists
            member = ClubMembershipSystem.accessMemberByEmail(email);
            if (member == null) {
                JOptionPane.showMessageDialog(this, "No Clubs found with the given email. Try again.");
            }
        } while (member == null);

        // Step 2: Get the new name and joining date in a single prompt
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JTextField newNameField = new JTextField();
        JTextField newJoiningDateField = new JTextField();
        JTextField newClubDescriptionField = new JTextField();

        panel.add(new JLabel("New Name:"));
        panel.add(newNameField);
        panel.add(new JLabel("New Joining Date:"));
        panel.add(newJoiningDateField);
        panel.add(new JLabel("New Club Description:"));
        panel.add(newClubDescriptionField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Update Club Information",
                JOptionPane.OK_CANCEL_OPTION);

        // Check if the user pressed cancel in the prompt
        if (result == JOptionPane.OK_OPTION) {
            String newName = newNameField.getText();
            String newJoiningDate = newJoiningDateField.getText();
            String newClubDescription = newClubDescriptionField.getText();

            // Process the update
            ClubMembershipSystem.updateMember(email, newName, newJoiningDate, newClubDescription);
            JOptionPane.showMessageDialog(this, "Club updated successfully!");
        }
    }

    private void deleteMemberActionPerformed() {
        String email = JOptionPane.showInputDialog("Enter Club's Email to delete:");

        // Check if the user pressed cancel or entered an empty string
        if (email == null || email.trim().isEmpty()) {
            // Cancelled the process
            return;
        }

        members memberToDelete = ClubMembershipSystem.accessMemberByEmail(email);

        if (memberToDelete != null) {
            int confirmResult = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete the member?\nName: " + memberToDelete.getName() +
                            "\nEmail: " + memberToDelete.getEmail() +
                            "\nJoining Date: " + memberToDelete.getJoiningDate(),
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmResult == JOptionPane.YES_OPTION) {
                ClubMembershipSystem.deleteMember(email);
                JOptionPane.showMessageDialog(this, "Member deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Deletion canceled.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No member found with the given email.");
        }
    }

    private void accessMemberActionPerformed() {
        String email = JOptionPane.showInputDialog("Enter Club's Email to access:");
        members member = ClubMembershipSystem.accessMemberByEmail(email);
        if (member != null) {
            AccessClubMembersGUI.displayClubDetails(member.getName(), member.getEmail(),
                    member.getJoiningDate(), member.getClubDescription());
        } else {
            JOptionPane.showMessageDialog(this, "Club not found with the given email.");
        }
    
       /* if (member != null) {
            JOptionPane.showMessageDialog(this,
                    "Member Details:\nName: " + member.getName() +
                            "\nEmail: " + member.getEmail() +
                            "\nJoining Date: " + member.getJoiningDate() +
                            "\nClub Description: " + member.getClubDescription());
        } else {
            JOptionPane.showMessageDialog(this, "Club not found with the given email.");
        }*/
    }

    private void displayTotalMembersActionPerformed() {
        StringBuilder message = new StringBuilder();
        message.append("Total Clubs: ").append(clubMembershipSystem.getTotalMembers()).append("\n");

        for (members member : clubMembershipSystem.getAllMembers().values()) {
            message.append("\nName: ").append(member.getName())
                    .append("\nEmail: ").append(member.getEmail())
                    //.append("\nJoining Date: ").append(member.getJoiningDate())
                    //.append("\nClub Description: ").append(member.getClubDescription())
                    .append("\n-----------------------------");
        }

        JOptionPane.showMessageDialog(this, message.toString());
    }

    private void exitActionPerformed() {
        ClubMembershipSystem.writeMembersToFile();
        System.out.println("Exiting the Member Menu. Goodbye!");
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClubMembershipGUI();
            }
        });
    }
}

