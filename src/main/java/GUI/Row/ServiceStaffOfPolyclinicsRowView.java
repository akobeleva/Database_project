package GUI.Row;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ServiceStaffOfPolyclinicsRowView extends RowView{
    private JComboBox polComboBox;
    private JComboBox ssComboBox;
    private JButton OkButton;
    private JLabel label;
    private JPanel mainPanel;
    private JLabel polLabel;
    private JLabel ssLabel;
    private final Map<String, String> polyclinics = new HashMap<>();
    private final Map<String, String> serviceStaff = new HashMap<>();

    public ServiceStaffOfPolyclinicsRowView(String viewName, Mode mode) {
        super(viewName, mode);
        this.setContentPane(mainPanel);

        OkButton.addActionListener(e-> okActionListener());

        Vector polyclinicsItems = ConnectionManager.select("SELECT polyclinic_id, name from polyclinics", 2);
        for (Object vectorItem : polyclinicsItems) {
            Vector<String> vec = (Vector<String>) vectorItem;
            polyclinics.put(vec.get(1), vec.get(0));
            polComboBox.addItem(vec.get(1));
        }
        polComboBox.setSelectedIndex(-1);

        Vector doctorsItems = ConnectionManager.select("SELECT ss_id, ss.surname || ' ' || ss.name || ' ' || ss.patronymic from service_staff ss", 2);
        for (Object doctorItem : doctorsItems) {
            Vector<String> vec = (Vector<String>) doctorItem;
            serviceStaff.put(vec.get(1), vec.get(0));
            ssComboBox.addItem(vec.get(1));
        }
        ssComboBox.setSelectedIndex(-1);

        pack();
    }

    @Override
    public void insertRow() {
        ConnectionManager.insert("INSERT INTO ss_of_polyclinics(polyclinic_id, ss_id) VALUES (" + getRowFromForm() + ")");
    }

    @Override
    public String getRowFromForm() {
        String polyclinic = polyclinics.get(polComboBox.getSelectedItem());
        String ss = serviceStaff.get(ssComboBox.getSelectedItem());
        String row = polyclinic + "," + ss;
        return row;
    }

    @Override
    public void fillFields() {

    }
}
