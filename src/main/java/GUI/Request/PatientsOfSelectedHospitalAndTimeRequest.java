package GUI.Request;

import DAL.ConnectionManager;
import GUI.Response.PatientsOfSelectedHospitalAndTimeReponse;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class PatientsOfSelectedHospitalAndTimeRequest extends RequestView {
    private JLabel label;
    private JPanel mainPanel;
    private JComboBox hospComboBox;
    private JTextField startDayTextField;
    private JTextField startMonthTextField;
    private JTextField startYearTextField;
    private JTextField endDayTextField;
    private JTextField endMonthTextField;
    private JTextField endYearTextField;
    private JButton requestButton;
    private JLabel hospLabel;
    private JLabel startLabel;
    private JLabel endLabel;
    private JLabel startDayLabel;
    private JLabel endDayLabel;
    private JLabel startMonthLabel;
    private JLabel endMonthLabel;
    private JLabel startYearLabel;
    private JLabel endYearLabel;
    private final Map<String, String> hospitals = new HashMap<>();

    public PatientsOfSelectedHospitalAndTimeRequest(String viewName) {
        super(viewName);
        this.setContentPane(mainPanel);
        requestButton.addActionListener(e -> requestActionListener());
        setComboBoxItems();
        pack();
    }

    private void setComboBoxItems() {
        Vector hospitalsItems = ConnectionManager.select("SELECT hospital_id, name from hospitals", 2);
        for (Object vectorItem : hospitalsItems) {
            Vector<String> vec = (Vector<String>) vectorItem;
            hospitals.put(vec.get(1), vec.get(0));
            hospComboBox.addItem(vec.get(1));
        }
        hospComboBox.setSelectedIndex(-1);
    }

    @Override
    public void executeRequest() {
        PatientsOfSelectedHospitalAndTimeReponse response = new PatientsOfSelectedHospitalAndTimeReponse(getRequestFromForm());
    }

    @Override
    public String getRequestFromForm() {
        String hosp = hospitals.get(hospComboBox.getSelectedItem());
        String startDay = startDayTextField.getText();
        String startMonth = startMonthTextField.getText();
        String startYear = startYearTextField.getText();
        String endDay = endDayTextField.getText();
        String endMonth = endMonthTextField.getText();
        String endYear = endYearTextField.getText();
        String request = "SELECT p.surname || ' ' || p.name || ' ' || p.patronymic, h.name, d.name, start_date, end_date " +
                "FROM hospital_card JOIN patients p USING(patient_id) JOIN departments d USING(department_id) JOIN buildings USING(building_id) " +
                "JOIN hospitals h USING(hospital_id) WHERE hospital_id = " + hosp + " AND start_date >= " +
                "TO_DATE('" + startDay + "/" + startMonth + "/" + startYear + "', 'DD/MM/YYYY') AND end_date <= " +
                "TO_DATE('" + endDay +"/" + endMonth + "/" + endYear + "', 'DD/MM/YYYY')";
        return request;
    }
}
