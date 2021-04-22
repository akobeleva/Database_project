package GUI.Row;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class DoctorsOfHospitalsRowView extends RowView{
    private JPanel mainPanel;
    private JComboBox hospitalComboBox;
    private JComboBox doctorComboBox;
    private JButton OkButton;
    private JLabel label;
    private JLabel hospitalLabel;
    private JLabel doctorLabel;
    private final Map<String, String> hospitals = new HashMap<>();
    private final Map<String, String> doctors = new HashMap<>();

    public DoctorsOfHospitalsRowView(String viewName, Mode mode) {
        super(viewName, mode);
        this.setContentPane(mainPanel);

        OkButton.addActionListener(e-> okActionListener());

        Vector hospitalsItems = ConnectionManager.select("SELECT hospital_id, name from hospitals", 2);
        for (Object vectorItem : hospitalsItems) {
            Vector<String> vec = (Vector<String>) vectorItem;
            hospitals.put(vec.get(1), vec.get(0));
            hospitalComboBox.addItem(vec.get(1));
        }
        hospitalComboBox.setSelectedIndex(-1);

        Vector doctorsItems = ConnectionManager.select("SELECT doctor_id, d.surname || ' ' || d.name || ' ' || d.patronymic from doctors d", 2);
        for (Object doctorItem : doctorsItems) {
            Vector<String> vec = (Vector<String>) doctorItem;
            doctors.put(vec.get(1), vec.get(0));
            doctorComboBox.addItem(vec.get(1));
        }
        doctorComboBox.setSelectedIndex(-1);

        pack();
    }

    @Override
    public void insertRow() {
        ConnectionManager.insert("INSERT INTO doctors_of_hospitals(hospital_id, doctor_id) VALUES (" + getRowFromForm() + ")");
    }

    @Override
    public String getRowFromForm() {
        String hospital = hospitals.get(hospitalComboBox.getSelectedItem());
        String doctor = doctors.get(doctorComboBox.getSelectedItem());
        String row = hospital + "," + doctor;
        return row;
    }

    @Override
    public void fillFields() {

    }
}
