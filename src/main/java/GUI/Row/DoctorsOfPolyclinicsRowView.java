package GUI.Row;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class DoctorsOfPolyclinicsRowView extends RowView{
    private JPanel mainPanel;
    private JComboBox polyclinicComboBox;
    private JComboBox doctorComboBox;
    private JButton OkButton;
    private JLabel label;
    private JLabel polyclinicLabel;
    private JLabel doctorLabel;
    private final Map<String, String> polyclinics = new HashMap<>();
    private final Map<String, String> doctors = new HashMap<>();

    public DoctorsOfPolyclinicsRowView(String viewName, Mode mode) {
        super(viewName, mode);
        this.setContentPane(mainPanel);

        OkButton.addActionListener(e-> okActionListener());

        Vector polyclinicsItems = ConnectionManager.select("SELECT polyclinic_id, name from polyclinics", 2);
        for (Object vectorItem : polyclinicsItems) {
            Vector<String> vec = (Vector<String>) vectorItem;
            polyclinics.put(vec.get(1), vec.get(0));
            polyclinicComboBox.addItem(vec.get(1));
        }
        polyclinicComboBox.setSelectedIndex(-1);

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
        ConnectionManager.insert("INSERT INTO doctors_of_polyclinics(polyclinic_id, doctor_id) VALUES (" + getRowFromForm() + ")");
    }

    @Override
    public String getRowFromForm() {
        String polyclinic = polyclinics.get(polyclinicComboBox.getSelectedItem());
        String doctor = doctors.get(doctorComboBox.getSelectedItem());
        String row = polyclinic + "," + doctor;
        return row;
    }

    @Override
    public void fillFields() {

    }
}
