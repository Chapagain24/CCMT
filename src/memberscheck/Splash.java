package memberscheck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Splash extends JFrame implements ActionListener {

    Splash() {
        // Setting up the frame
        setTitle("Club Event Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1170, 650));

        // Creating a panel with a border layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.white);

        // Adding the heading label to the center of the panel
        JLabel heading = new JLabel("CLUB EVENT MANAGEMENT SYSTEM");
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setFont(new Font("serif", Font.BOLD, 40));
        mainPanel.add(heading, BorderLayout.CENTER);

        // Adding the "CLICK HERE TO CONTINUE" button to the bottom of the panel
        JButton clickHere = new JButton("CLICK HERE TO CONTINUE");
        clickHere.addActionListener(this);

        // Creating a panel for the button and centering it
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(clickHere);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Adding the main panel to the frame
        add(mainPanel);

        // Centering the frame on the screen
     
        setLocation(100,50);

        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        // Handling button click action
        setVisible(false);
        new Login();
    }

    public static void main(String args[]) {
        // Creating an instance of the Splash class
        SwingUtilities.invokeLater(() -> new Splash());
    }
}
