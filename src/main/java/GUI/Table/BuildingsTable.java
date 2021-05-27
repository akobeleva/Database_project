package GUI.Table;

import DAL.ConnectionManager;
import GUI.Role;
import GUI.Row.BuildingRowView;
import GUI.Row.Mode;

import java.util.Arrays;
import java.util.Vector;

import static GUI.Role.*;

public class BuildingsTable extends TableView{
    String [] nameColumns = {"ID", "Больница", "Номер корпуса", "Телефон", "Количество отделений"};
    public BuildingsTable(String name, String userID, Role role) {
        super(name, userID, role);
        if (role == Role.PATIENT || role == POlYCLINIC_REGISTRY) addButton.setVisible(false);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values;
        if (role == HOSPITAL_REGISTRY) {
            values = ConnectionManager.select("SELECT building_id, hospitals.name, building_number, buildings.phone_number, count_departments \n" +
                    "FROM buildings JOIN hospitals USING (hospital_id) JOIN users_hospitals USING (hospital_id) WHERE " +
                    "(user_id = " + userID + ")", 5);
        }
        else {
            values = ConnectionManager.select("SELECT building_id, hospitals.name, building_number, buildings.phone_number, count_departments \n" +
                    "FROM buildings JOIN hospitals USING (hospital_id) JOIN users_hospitals USING (hospital_id) WHERE " +
                    "(user_id = " + userID + ")", 5);
        }
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
    }

    @Override
    public void createNewRow() {
        BuildingRowView createBuilding = new BuildingRowView("Добавление корпуса", Mode.CREATE);
    }

    @Override
    public void editRow(Integer id) {
        if (role == ADMIN || role == HOSPITAL_REGISTRY) {
            BuildingRowView editBuilding = new BuildingRowView("Изменение корпуса", Mode.EDIT);
            editBuilding.setSelectedRow(id);
            editBuilding.fillFields();
        }
    }

    @Override
    public void deleteRow(Integer id) {
        if (role == ADMIN || role == HOSPITAL_REGISTRY)
            ConnectionManager.delete("DELETE FROM buildings WHERE building_id = " + id);
    }
}
