package memberscheck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Financial extends JFrame implements ActionListener {

    private JComboBox<String> eventDropdown;
    private JTextArea reportTextArea;
    
   

    public Financial(Map<Date, Event> eventsMap) {
        super("Financial Report");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        eventDropdown = new JComboBox<>();
        populateEventDropdown(eventsMap);

        JButton generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(this);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the Financial window  
              
            }
        });

        reportTextArea = new JTextArea();
        reportTextArea.setEditable(false);

        setLayout(new GridLayout(4, 1));

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Select Event:"));
        topPanel.add(eventDropdown);

        add(topPanel);
        add(generateReportButton);
        add(backButton);
        add(new JScrollPane(reportTextArea));

        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void populateEventDropdown(Map<Date, Event> eventsMap) {
        for (Date date : eventsMap.keySet()) {
            eventDropdown.addItem(new SimpleDateFormat("yyyy-MM-dd").format(date));
        }
    }

    private Event getSelectedEvent(Map<Date, Event> eventsMap, String selectedDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date selectedEventDate = dateFormat.parse(selectedDate);
            return eventsMap.get(selectedEventDate);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void generateReportActionPerformed() {
        String selectedDate = (String) eventDropdown.getSelectedItem();

        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Please select an event to generate a report.");
            return;
        }

        Event selectedEvent = getSelectedEvent(EventCreation.eventsMap, selectedDate);

        if (selectedEvent != null) {
            int estimatedBudget = calculateEstimatedBudget(selectedEvent.getNumberOfMembers());
            String report = generateReport(selectedEvent, estimatedBudget);
            reportTextArea.setText(report);
        } else {
            JOptionPane.showMessageDialog(this, "Error fetching event details.");
        }
    }

    private int calculateEstimatedBudget(int numberOfMembers) {
        // Replace this with your own logic for calculating the estimated budget based on the number of members
        // For demonstration purposes, assuming a fixed budget per member
        int budgetPerMember = 50;
        return numberOfMembers * budgetPerMember;
    }

    private String generateReport(Event event, int estimatedBudget) {
        StringBuilder report = new StringBuilder("Financial Report\n");
        report.append("Event Date: ").append(new SimpleDateFormat("yyyy-MM-dd").format(event.getDate())).append("\n");
        report.append("Event Name: ").append(event.getEventName()).append("\n");
        report.append("Description: ").append(event.getDescription()).append("\n");
        report.append("Estimated Budget: $").append(estimatedBudget).append("\n");
        return report.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        generateReportActionPerformed();
    }

    public static void main(String[] args) {
    	EventCreation eventCreation = new EventCreation();
    	eventCreation.dispose();
        SwingUtilities.invokeLater(() -> new Financial(EventCreation.eventsMap));
    }
}