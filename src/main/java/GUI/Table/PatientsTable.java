package GUI.Table;

import DAL.ConnectionManager;
import GUI.Role;
import GUI.Row.Mode;
import GUI.Row.PatientRowView;

import java.util.Arrays;
import java.util.Vector;

public class PatientsTable extends TableView{
    String [] nameColumns = {"ID", "Фамилия", "Имя", "Отчество", "Дата рождения", "Телефон"};
    public PatientsTable(String name, String userID, Role role) {
        super(name, userID, role);
        if (role == Role.PATIENT) addButton.setVisible(false);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values;
        if (role == Role.PATIENT) {
            values = ConnectionManager.select("SELECT patient_id, surname, name, patronymic, birthday, phone_number " +
                    "FROM patients JOIN users_patients USING (patient_id) WHERE (user_id = " + userID + ")", 6);
        } else {
            values = ConnectionManager.select("SELECT patient_id, surname, name, patronymic, birthday, phone_number " +
                    "FROM patients", 6);
        }
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
    }

    @Override
    public void createNewRow() {
        PatientRowView add = new PatientRowView("Добавление пациента", Mode.CREATE);
    }

    @Override
    public void editRow(Integer id) {
        PatientRowView edit = new PatientRowView("Изменение пациента", Mode.EDIT);
        edit.setSelectedRow(id);
        edit.fillFields();
    }

    @Override
    public void deleteRow(Integer id) {
        if (role!=Role.PATIENT) ConnectionManager.delete("DELETE FROM patients WHERE patient_id = " + id);
    }
}
