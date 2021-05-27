package GUI.Table;

import DAL.ConnectionManager;
import GUI.Role;
import GUI.Row.DoctorsOfHospitalsRowView;
import GUI.Row.Mode;

import java.util.Arrays;
import java.util.Vector;

import static GUI.Role.*;

public class DoctorsOfHospitalsTable extends TableView {
    String[] nameColumns = {"ID", "Больница", "ФИО врача"};

    public DoctorsOfHospitalsTable(String name, String userID, Role role) {
        super(name, userID, role);
        if (role == Role.PATIENT || role == POlYCLINIC_REGISTRY) addButton.setVisible(false);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values;
        if (role == HOSPITAL_REGISTRY) {
            values = ConnectionManager.select("SELECT id, h.name, d.surname || ' ' || d.name || ' ' || d.patronymic " +
                    "FROM doctors_of_hospitals JOIN hospitals h USING (hospital_id) " +
                    "JOIN doctors d USING (doctor_id) JOIN users_hospitals USING (hospital_id) WHERE " +
                    "(user_id = " + userID + ") ORDER BY id", 3);
        } else {
            values = ConnectionManager.select("SELECT id, h.name, d.surname || ' ' || d.name || ' ' || d.patronymic " +
                    "FROM doctors_of_hospitals JOIN hospitals h USING (hospital_id) " +
                    "JOIN doctors d USING (doctor_id) ORDER BY id", 3);
        }
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
    }

    @Override
    public void createNewRow() {
        DoctorsOfHospitalsRowView create = new DoctorsOfHospitalsRowView("Добавление место работы врача", Mode.CREATE);
    }

    @Override
    public void editRow(Integer id) {

    }

    @Override
    public void deleteRow(Integer id) {
        if (role == ADMIN || role == HOSPITAL_REGISTRY)
            ConnectionManager.delete("DELETE FROM doctors_of_hospitals WHERE id = " + id);
    }
}
