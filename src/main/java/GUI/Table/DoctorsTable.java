package GUI.Table;

import DAL.ConnectionManager;
import GUI.Role;
import GUI.Row.DoctorRowView;
import GUI.Row.Mode;

import java.util.Arrays;
import java.util.Vector;

public class DoctorsTable extends TableView {
    String[] nameColumns = {"ID", "Фамилия", "Имя", "Отчество", "Специальность", "докт. мед.наук", "канд. мед.наук", "Профессор", "Доцент", "Стаж"};

    public DoctorsTable(String name, String userID, Role role) {
        super(name, userID, role);
        if (role == Role.PATIENT) addButton.setVisible(false);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values = ConnectionManager.select("SELECT doctor_id, surname, doctors.name, patronymic, specialities.name, dms, cms,  professor, docent, experience \n" +
                "FROM doctors JOIN specialities USING (speciality_id)", 10);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
    }

    @Override
    public void createNewRow() {
        DoctorRowView addDoctor = new DoctorRowView("Добавление врача", Mode.CREATE);
    }

    @Override
    public void editRow(Integer id) {
        if (role != Role.PATIENT) {
            DoctorRowView editDoctor = new DoctorRowView("Изменение врача", Mode.EDIT);
            editDoctor.setSelectedRow(id);
            editDoctor.fillFields();
        }
    }

    @Override
    public void deleteRow(Integer id) {
        if (role != Role.PATIENT)
            ConnectionManager.delete("DELETE FROM doctors WHERE doctor_id = " + id);
    }
}
