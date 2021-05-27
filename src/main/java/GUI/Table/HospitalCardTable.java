package GUI.Table;

import DAL.ConnectionManager;
import GUI.Role;
import GUI.Row.HospitalCardRowView;
import GUI.Row.Mode;

import java.util.Arrays;
import java.util.Vector;

import static GUI.Role.ADMIN;
import static GUI.Role.HOSPITAL_REGISTRY;

public class HospitalCardTable extends TableView {
    String[] nameColumns = {"ID", "Пациент", "Отделение", "Дата поступления", "Дата выписки", "Состояние", "Температура"};

    public HospitalCardTable(String name, String userID, Role role) {
        super(name, userID, role);
        if (role == Role.PATIENT) addButton.setVisible(false);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values;
        if (role == Role.PATIENT) {
            values = ConnectionManager.select("SELECT card_id, p.surname || ' ' || p.name || ' ' || p.patronymic, " +
                    "d.name, start_date, end_date, health_status, temp FROM hospital_card JOIN patients p USING (patient_id) " +
                    "JOIN departments d USING(department_id) JOIN users_patients USING (patient_id) WHERE (user_id = " + userID + ")", 7);
        } else if (role == Role.HOSPITAL_REGISTRY) {
            values = ConnectionManager.select("SELECT card_id, p.surname || ' ' || p.name || ' ' || p.patronymic, " +
                    "d.name, start_date, end_date, health_status, temp FROM hospital_card JOIN patients p USING (patient_id) " +
                    "JOIN departments d USING(department_id) JOIN buildings USING(building_id) JOIN hospitals USING(hospital_id)" +
                    "JOIN users_hospitals USING (hospital_id) WHERE (user_id = " + userID + ")", 7);
        } else {
            values = ConnectionManager.select("SELECT card_id, p.surname || ' ' || p.name || ' ' || p.patronymic, " +
                    "d.name, start_date, end_date, health_status, temp FROM hospital_card JOIN patients p USING (patient_id) " +
                    "JOIN departments d USING(department_id)", 7);
        }
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
    }

    @Override
    public void createNewRow() {
        HospitalCardRowView create = new HospitalCardRowView("Добавление стационарной карты", Mode.CREATE);
    }

    @Override
    public void editRow(Integer id) {

    }

    @Override
    public void deleteRow(Integer id) {
        if (role == ADMIN || role == HOSPITAL_REGISTRY)
            ConnectionManager.delete("DELETE FROM hospital_card WHERE card_id = " + id);
    }
}

