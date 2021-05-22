package GUI.Table;

import DAL.ConnectionManager;
import GUI.Role;
import GUI.Row.BuildingRowView;
import GUI.Row.Mode;

import java.util.Arrays;
import java.util.Vector;

public class BuildingsTable extends TableView{
    String [] nameColumns = {"ID", "Больница", "Номер корпуса", "Телефон", "Количество отделений"};
    public BuildingsTable(String name, String userID, Role role) {
        super(name, userID, role);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values = ConnectionManager.select("SELECT building_id, hospitals.name, building_number, buildings.phone_number, count_departments \n" +
                "FROM buildings JOIN hospitals USING (hospital_id)", 5);
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
        BuildingRowView editBuilding = new BuildingRowView("Изменение корпуса", Mode.EDIT);
        editBuilding.setSelectedRow(id);
        editBuilding.fillFields();
    }

    @Override
    public void deleteRow(Integer id) {
        ConnectionManager.delete("DELETE FROM buildings WHERE building_id = " + id);
    }
}
