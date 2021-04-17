package GUI.Table;

import DAL.ConnectionManager;
import GUI.CreateNewRow.CreateBuilding;

import java.util.Arrays;
import java.util.Vector;

public class BuildingsTable extends TableView{
    String [] nameColumns = {"ID", "Больница", "Номер корпуса", "Телефон", "Количество отделений"};
    public BuildingsTable(String name) {
        super(name);
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
        CreateBuilding createBuilding = new CreateBuilding("Добавление отделения");
    }

    @Override
    public void editRow() {

    }

    @Override
    public void deleteRow(Integer id) {
        ConnectionManager.delete("DELETE FROM buildings WHERE building_id = " + id);
    }
}
