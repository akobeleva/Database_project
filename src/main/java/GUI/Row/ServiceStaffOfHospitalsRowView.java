package GUI.Row;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ServiceStaffOfHospitalsRowView extends RowView{
    private JPanel mainPanel;
    private JComboBox hospitalComboBox;
    private JComboBox ssComboBox;
    private JButton OkButton;
    private JLabel label;
    private JLabel hospitalLabel;
    private JLabel ssLabel;
    private final Map<String, String> hospitals = new HashMap<>();
    private final Map<String, String> serviceStaff = new HashMap<>();

    public ServiceStaffOfHospitalsRowView(String viewName, Mode mode) {
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

        Vector ssItems = ConnectionManager.select("SELECT ss_id, ss.surname || ' ' || ss.name || ' ' || ss.patronymic from service_staff ss", 2);
        for (Object doctorItem : ssItems) {
            Vector<String> vec = (Vector<String>) doctorItem;
            serviceStaff.put(vec.get(1), vec.get(0));
            ssComboBox.addItem(vec.get(1));
        }
        ssComboBox.setSelectedIndex(-1);

        pack();
    }

    @Override
    public void insertRow() {
        ConnectionManager.insert("INSERT INTO ss_of_hospitals(hospital_id, ss_id) VALUES (" + getRowFromForm() + ")");
    }

    @Override
    public String getRowFromForm() {
        String hospital = hospitals.get(hospitalComboBox.getSelectedItem());
        String ss = serviceStaff.get(ssComboBox.getSelectedItem());
        String row = hospital + "," + ss;
        return row;
    }

    @Override
    public void fillFields() {

    }
}
