package memberscheck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {

    private JTextField tfusername;  
    private JPasswordField tfpassword;

    Login() {
        // Setting up the frame
        setTitle("Login - Club Event Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 200));
        setBackground(Color.white);

        // Creating a panel with a grid layout
        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        mainPanel.setBackground(Color.white);

        // Adding components to the panel
        JLabel usernameLabel = new JLabel("Username:");
         tfusername = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
         tfpassword = new JPasswordField();
        JButton login = new JButton("Log In");

        // Styling components
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        usernameLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);

        // Highlighting and styling the login button
        login.setBackground(new Color(51, 153, 255));
        login.setForeground(Color.white);
        login.setFocusPainted(false);
        login.addActionListener(this);

        // Adding components to the panel
        mainPanel.add(usernameLabel);
        mainPanel.add(tfusername);
        mainPanel.add(passwordLabel);
        mainPanel.add(tfpassword);
        mainPanel.add(new JLabel()); // Empty label for spacing
        mainPanel.add(login);

        // Configuring the frame
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private boolean authenticate(String username, String password) {
        // Replace this with your actual authentication logic
        // For simplicity, considering a hardcoded username and password
        return "group3".equals(username) && "group3".equals(password);
    }


    public static void main(String[] args) {
        // Creating an instance of the Login class
        SwingUtilities.invokeLater(() -> new Login());
    }
    
    public void actionPerformed(ActionEvent ae) {
        // Handling button click action
        String username = tfusername.getText();
        String password = new String(tfpassword.getPassword());

        // Example authentication check (Replace this with your actual authentication logic)
        if (authenticate(username, password)) {
            // If authentication is successful, hide the login window and open the main application
            setVisible(false);
            new ButtonClass();
        } else {
            // If authentication fails, show an error message
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Authentication Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    
}

