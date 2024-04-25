package memberscheck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}


public class ButtonClass extends JFrame {
	

   public ButtonClass() {
    	 BackgroundPanel backgroundPanel = new BackgroundPanel("homepage.png");//"C:\\Users\\akshy\\eclipse-workspace\\memberscheck\\homepage.png");
        JButton member = new JButton("Manage Membership");
        member.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 new ClubMembershipGUI();
            }
        });
        JButton eventsystem = new JButton("Manage Events");
        eventsystem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	  new EventCreation();
            }
        });
        
        JButton financialButton = new JButton("Manage Finance");
        financialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                EventCreation eventCreation = new EventCreation();
                eventCreation.dispose();
                SwingUtilities.invokeLater(() -> {
                    Financial financial = new Financial(eventCreation.getEventsMap());
                    eventCreation.updateDateDropdown();  // Ensure that the dropdown is updated
                });
            }
        });
        
        
        add(backgroundPanel, BorderLayout.CENTER);
       
        backgroundPanel.setOpaque(false);
        backgroundPanel.setLayout(new FlowLayout());
        backgroundPanel.add(member);
        backgroundPanel.add(eventsystem);
        backgroundPanel.add(financialButton);
        
        add(backgroundPanel);

      /* member.setBounds(150, 100, 200,50);
        eventsystem.setBounds(400, 100, 200,50);
        financialButton.setBounds(650, 100, 200, 50);*/
        
       

        setSize(1170, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ButtonClass();
            }
        });
    }
}