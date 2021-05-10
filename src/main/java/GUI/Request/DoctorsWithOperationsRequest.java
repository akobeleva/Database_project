package GUI.Request;

import DAL.ConnectionManager;
import GUI.Response.DoctorsOfSelectedSpecialityResponse;
import GUI.Response.DoctorsWithOperationsResponse;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class DoctorsWithOperationsRequest extends RequestView{
    private JPanel mainPanel;
    private JLabel label;
    private JComboBox specComboBox;
    private JComboBox typeComboBox;
    private JComboBox instComboBox;
    private JTextField countTextField;
    private JButton requestButton;
    private JLabel specLabel;
    private JLabel instLabel;
    private JLabel countLabel;
    private JLabel typeLabel;
    private final Map<String, String> specialities = new HashMap<>();
    private final Map<String, String> hospitals = new HashMap<>();
    private final Map<String, String> polyclinics = new HashMap<>();

    public DoctorsWithOperationsRequest(String viewName) {
        super(viewName);
        this.setContentPane(mainPanel);

        requestButton.addActionListener(e -> requestActionListener());

        setComboBoxItems();

        pack();
    }

    @Override
    public void executeRequest() {
        DoctorsWithOperationsResponse response = new DoctorsWithOperationsResponse(getRequestFromForm());
    }

    @Override
    public String getRequestFromForm() {
        String spec = specialities.get(specComboBox.getSelectedItem());
        String count = countTextField.getText();
        String request = null;
        if (typeComboBox.getSelectedIndex() == 0) {
            request = "SELECT doctor_id, d.surname || ' ' || d.name || ' ' || d.patronymic, numb_op" +
                    "FROM doctors d JOIN surgeons USING (doctor_id) JOIN doctors_of_hospitals USING (doctor_id) WHERE hospital_id = "
                    + hospitals.get(instComboBox.getSelectedItem()) + " AND speciality_id = " + spec + "AND numb_op >= " + count;
        }
        if (typeComboBox.getSelectedIndex() == 1) {
            request = "SELECT doctor_id, d.surname || ' ' || d.name || ' ' || d.patronymic, numb_op " +
                    "FROM doctors d JOIN surgeons USING (doctor_id) JOIN doctors_of_polyclinics USING (doctor_id) WHERE polyclinic_id = "
                    + polyclinics.get(instComboBox.getSelectedItem()) + " AND speciality_id = " + spec + "AND numb_op >= " + count;
        }
        if (typeComboBox.getSelectedIndex() == 2) {
            request = "SELECT doctor_id, surname || ' ' || name || ' ' || patronymic, numb_op " +
                    "FROM doctors JOIN surgeons USING (doctor_id) WHERE speciality_id = " +  spec + "AND numb_op >= " + count;
        }
        return request;
    }

    private void setComboBoxItems() {
        Vector vectorItems = ConnectionManager.select("SELECT * from specialities WHERE speciality_id=1", 2);
        for (Object vectorItem : vectorItems) {
            Vector<String> vec = (Vector<String>) vectorItem;
            specialities.put(vec.get(1), vec.get(0));
            specComboBox.addItem(vec.get(1));
        }
        specComboBox.setSelectedIndex(-1);

        typeComboBox.addItem("Больница");
        typeComboBox.addItem("Поликлиника");
        typeComboBox.addItem("Все учреждения");
        typeComboBox.addItemListener(e -> {
            instComboBox.removeAllItems();
            if (typeComboBox.getSelectedIndex() == 0) {
                Vector hospitalsItems = ConnectionManager.select("SELECT hospital_id, name from hospitals", 2);
                for (Object vectorItem : hospitalsItems) {
                    Vector<String> vec = (Vector<String>) vectorItem;
                    hospitals.put(vec.get(1), vec.get(0));
                    instComboBox.addItem(vec.get(1));
                }
            }
            if (typeComboBox.getSelectedIndex() == 1) {
                Vector polyclinicsItems = ConnectionManager.select("SELECT polyclinic_id, name from polyclinics", 2);
                for (Object vectorItem : polyclinicsItems) {
                    Vector<String> vec = (Vector<String>) vectorItem;
                    polyclinics.put(vec.get(1), vec.get(0));
                    instComboBox.addItem(vec.get(1));
                }
            }
        });
        typeComboBox.setSelectedIndex(-1);
    }
}
