package GUI.Table;

import DAL.ConnectionManager;
import GUI.Role;
import GUI.Row.DepartmentRowView;
import GUI.Row.Mode;

import java.util.Arrays;
import java.util.Vector;

public class DepartmentsTable extends TableView {
    String[] nameColumns = {"ID", "Название", "Больница", "Номер корпуса", "Телефон", "Палаты", "Койки", "Свободные палаты", "Свободные койки"};

    public DepartmentsTable(String name, String userID, Role role) {
        super(name, userID, role);
        if (role == Role.PATIENT || role == Role.POlYCLINIC_REGISTRY) addButton.setVisible(false);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values;
        if (role == Role.HOSPITAL_REGISTRY) {
            values = ConnectionManager.select("SELECT department_id, d.name, h.name, building_number, phone_number, " +
                    "wards, beds, free_wards, free_beds FROM departments d JOIN buildings USING(building_id) " +
                    "JOIN hospitals h USING(hospital_id) JOIN users_hospitals USING (hospital_id) WHERE " +
                    "(user_id = " + userID + ")", 9);
        } else {
            values = ConnectionManager.select("SELECT department_id, d.name, h.name, building_number, phone_number, " +
                    "wards, beds, free_wards, free_beds FROM departments d JOIN buildings USING(building_id) " +
                    "JOIN hospitals h USING(hospital_id)", 9);
        }
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
    }

    @Override
    public void createNewRow() {
        DepartmentRowView createDepartment = new DepartmentRowView("Добавление отделения", Mode.CREATE);
    }

    @Override
    public void editRow(Integer id) {
        if (role == Role.ADMIN || role == Role.HOSPITAL_REGISTRY) {
            DepartmentRowView editDepartment = new DepartmentRowView("Изменение отделения", Mode.EDIT);
            editDepartment.setSelectedRow(id);
            editDepartment.fillFields();
        }
    }

    @Override
    public void deleteRow(Integer id) {
        if (role == Role.ADMIN || role == Role.HOSPITAL_REGISTRY)
            ConnectionManager.delete("DELETE FROM departments WHERE department_id = " + id);
    }
}
