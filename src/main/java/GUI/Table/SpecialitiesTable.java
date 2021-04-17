package GUI.Table;

import DAL.ConnectionManager;
import GUI.CreateNewRow.CreateSpeciality;

import java.util.Arrays;
import java.util.Vector;

public class SpecialitiesTable extends TableView {
    String [] nameColumns = {"ID", "Название"};
    public SpecialitiesTable(String name) {
        super(name);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values = ConnectionManager.select("SELECT * FROM specialities", 2);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
    }

    @Override
    public void createNewRow() {
        CreateSpeciality cr = new CreateSpeciality("Добавление специальности");
    }

    @Override
    public void editRow() {

    }

    @Override
    public void deleteRow(Integer id) {
        ConnectionManager.delete("DELETE FROM specialities WHERE speciality_id = " + id);
    }
}
