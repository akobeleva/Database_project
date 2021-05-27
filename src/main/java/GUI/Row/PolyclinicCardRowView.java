package GUI.Row;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class PolyclinicCardRowView extends RowView{
    private JComboBox polComboBox;
    private JComboBox patComboBox;
    private JComboBox docComboBox;
    private JButton OkButton;
    private JLabel label;
    private JLabel polLabel;
    private JLabel patLabel;
    private JLabel docLabel;
    private JLabel timeLabel;
    private JPanel mainPanel;
    private JTextField dayTextField;
    private JTextField monthTextField;
    private JTextField yearTextField;
    private JTextField hourTextField;
    private JTextField minTextField;
    private JLabel dateLabel;
    private JLabel numberLabel;
    private JLabel monthLabel;
    private JLabel yearLabel;
    private JLabel hourLabel;
    private JLabel minuteLabel;
    private final Map<String, String> polyclinics = new HashMap<>();
    private final Map<String, String> patients = new HashMap<>();
    private final Map<String, String> doctors = new HashMap<>();

    public PolyclinicCardRowView(String viewName, Mode mode) {
        super(viewName, mode);
        this.setContentPane(mainPanel);

        OkButton.addActionListener(e-> okActionListener());
        setComboBoxItems();
        pack();
    }

    @Override
    public void insertRow() {
        if (mode == Mode.CREATE) {
            ConnectionManager.insert("INSERT INTO polyclinic_card(polyclinic_id, patient_id, doctor_id, visit_date) " +
                    "VALUES (" + getRowFromForm() + ")");
        }
    }

    @Override
    public String getRowFromForm() {
        String polyclinic = polyclinics.get(polComboBox.getSelectedItem());
        String patient = patients.get(patComboBox.getSelectedItem());
        String doctor = doctors.get(docComboBox.getSelectedItem());
        String day = dayTextField.getText();
        String month = monthTextField.getText();
        String year = yearTextField.getText();
        String hour = hourTextField.getText();
        String min = minTextField.getText();
        String row = polyclinic + "," + patient + "," + doctor + "," + "TO_DATE('" + day +"/" + month + "/" + year + " " + hour + ":"
                + min + "', 'DD/MM/YYYY HH24:MI')";
        return row;
    }

    @Override
    public void fillFields() {

    }

    private void setComboBoxItems(){
        Vector polyclinicsItems = ConnectionManager.select("SELECT polyclinic_id, name from polyclinics", 2);
        for (Object vectorItem : polyclinicsItems) {
            Vector<String> vec = (Vector<String>) vectorItem;
            polyclinics.put(vec.get(1), vec.get(0));
            polComboBox.addItem(vec.get(1));
        }
        polComboBox.setSelectedIndex(-1);

        Vector patientsItems = ConnectionManager.select("SELECT patient_id, p.surname || ' ' || p.name || ' ' || p.patronymic from patients p", 2);
        for (Object vectorItem : patientsItems) {
            Vector<String> vec = (Vector<String>) vectorItem;
            patients.put(vec.get(1), vec.get(0));
            patComboBox.addItem(vec.get(1));
        }
        patComboBox.setSelectedIndex(-1);

        polComboBox.addItemListener(e -> {
            docComboBox.removeAllItems();
            String pol_id = polyclinics.get(polComboBox.getSelectedItem());
            Vector doctorsItems = ConnectionManager.select("SELECT doctor_id, d.surname || ' ' || d.name || ' ' || d.patronymic " +
                   "from doctors_of_polyclinics JOIN doctors d USING (doctor_id) WHERE (polyclinic_id = " + pol_id + ")", 2);
            for (Object doctorItem : doctorsItems) {
                Vector<String> vec = (Vector<String>) doctorItem;
                doctors.put(vec.get(1), vec.get(0));
                docComboBox.addItem(vec.get(1));
            }
            docComboBox.setSelectedIndex(-1);
        });
    }
}
