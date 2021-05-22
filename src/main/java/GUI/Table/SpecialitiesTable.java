package GUI.Table;

import DAL.ConnectionManager;
import GUI.Role;
import GUI.Row.Mode;
import GUI.Row.SpecialityRowView;

import java.util.Arrays;
import java.util.Vector;

public class SpecialitiesTable extends TableView {
    String [] nameColumns = {"ID", "Название"};
    public SpecialitiesTable(String name, String userID, Role role) {
        super(name, userID, role);
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
        SpecialityRowView createSpeciality = new SpecialityRowView("Добавление специальности", Mode.CREATE);
    }

    @Override
    public void editRow(Integer id) {
        SpecialityRowView editSpeciality = new SpecialityRowView("Изменение специальности", Mode.EDIT);
        editSpeciality.setSelectedRow(id);
        editSpeciality.fillFields();
    }

    @Override
    public void deleteRow(Integer id) {
        ConnectionManager.delete("DELETE FROM specialities WHERE speciality_id = " + id);
    }
}
