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
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values = ConnectionManager.select("SELECT patient_id, surname, name, patronymic, birthday, phone_number \n" +
                "FROM patients", 6);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
    }

    @Override
    public void createNewRow() {
        PatientRowView add = new PatientRowView("Добавление врача", Mode.CREATE);
    }

    @Override
    public void editRow(Integer id) {
    }

    @Override
    public void deleteRow(Integer id) {
        ConnectionManager.delete("DELETE FROM patients WHERE patient_id = " + id);
    }
}
