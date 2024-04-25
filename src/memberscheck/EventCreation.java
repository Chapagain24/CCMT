package memberscheck;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class EventCreation extends JFrame {

    static Map<Date, Event> eventsMap = new HashMap<>();
    private JComboBox<String> dateDropdown;
    private JTextField membersField;

    private static final String DATA_FILE = "events.dat";
    
    public Map<Date, Event> getEventsMap() {
        return eventsMap;
    }

    public EventCreation() {
        super("EventCreation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize dateDropdown before calling updateDateDropdown
        dateDropdown = new JComboBox<>();
        membersField = new JTextField(); // Initialize the membersField
        loadEventsFromFile(); // Load events from file when the app starts
        

       
        JButton createEventButton = new JButton("Create Event");
        createEventButton.setBackground(Color.GREEN);
        createEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createEventActionPerformed();
                saveEventsToFile();
            }
        });

        JButton viewEventButton = new JButton("View Events");
        viewEventButton.setBackground(Color.BLUE);
        viewEventButton.setForeground(Color.WHITE);
        viewEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewEventsActionPerformed();
            }
        });

        JButton deleteEventButton = new JButton("Delete Event");
        deleteEventButton.setBackground(Color.RED);
        deleteEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEventActionPerformed();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.GRAY);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEventsToFile(); // Save events to file when exiting
                dispose();
                
            }
        });

        setLayout(new GridLayout(7, 2));

        add(new JLabel("Date (yyyy-MM-dd):"));
        JTextField dateField = new JTextField();
        add(dateField);
        

        add(new JLabel("Event Name:"));
        JTextField eventNameField = new JTextField();
        add(eventNameField);

        add(new JLabel("Description:"));
        JTextField descriptionField = new JTextField();
        add(descriptionField);

        add(new JLabel("Number of Members Attending:"));
        add(membersField);

        // Add initialized dateDropdown to the layout
        
       

        add(createEventButton);
        add(viewEventButton);
        add(dateDropdown);
        add(deleteEventButton);
        
        add(exitButton);
        getContentPane().setBackground(Color.YELLOW);

        setSize(400, 350);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createEventActionPerformed() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(((JTextField) getContentPane().getComponent(1)).getText());
            String eventName = ((JTextField) getContentPane().getComponent(3)).getText();
            String description = ((JTextField) getContentPane().getComponent(5)).getText();

            int numberOfMembers = Integer.parseInt(membersField.getText()); // Parse the number of members

            Event event = new Event(date, eventName, description, numberOfMembers);
            eventsMap.put(date, event);

            updateDateDropdown();
            JOptionPane.showMessageDialog(this, "Event created successfully!");
        } catch (ParseException | ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please check your date format and ensure the number of members is a valid integer.");
        }
    }

    private void viewEventsActionPerformed() {
        if (eventsMap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No events available.");
        } else {
            StringBuilder eventsInfo = new StringBuilder("Events:\n");
            for (Map.Entry<Date, Event> entry : eventsMap.entrySet()) {
                Event event = entry.getValue();
                eventsInfo.append("Date: ").append(entry.getKey()).append(", Event Name: ").append(event.getEventName()).append(", Description: ").append(event.getDescription()).append(", Members: ").append(event.getNumberOfMembers()).append("\n");
            }
            JOptionPane.showMessageDialog(this, eventsInfo.toString());
        }
    }

    private void deleteEventActionPerformed() {
        if (eventsMap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No events available to delete.");
            return;
        }

        String selectedDate = (String) dateDropdown.getSelectedItem();

        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Please select a date to delete.");
            return;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateToDelete = dateFormat.parse(selectedDate);

            Event deletedEvent = eventsMap.remove(dateToDelete);

            if (deletedEvent != null) {
                updateDateDropdown();
                JOptionPane.showMessageDialog(this, "Event deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No event found for the selected date.");
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format.");
        }
    }

    public void updateDateDropdown() {
        dateDropdown.removeAllItems();
        for (Date date : eventsMap.keySet()) {
            dateDropdown.addItem(new SimpleDateFormat("yyyy-MM-dd").format(date));
        }
    }

    @SuppressWarnings("unchecked")
    private void loadEventsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            eventsMap = (Map<Date, Event>) ois.readObject();
            updateDateDropdown();
        } catch (IOException | ClassNotFoundException e) {
            // If the file doesn't exist or there's an issue reading from it, ignore and continue with an empty eventsMap.
        }
    }

    private void saveEventsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(eventsMap);
            System.out.println("Member data saved to file: " + DATA_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        saveEventsToFile(); // Save events to file when the app is closed
        System.out.println("dispose() called");
        super.dispose();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EventCreation();
            }
        });
    }

}

class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date date;
    private String eventName;
    private String description;
    private int numberOfMembers;

    public Event(Date date, String eventName, String description, int numberOfMembers) {
        this.date = date;
        this.eventName = eventName;
        this.description = description;
        this.numberOfMembers = numberOfMembers;
    }

    public Date getDate() {
        return date;
    }

    public String getEventName() {
        return eventName;
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfMembers() {
        return numberOfMembers;
    }
  
}
