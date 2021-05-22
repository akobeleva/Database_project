package GUI.Table;

import DAL.ConnectionManager;
import GUI.Role;

import java.util.Arrays;
import java.util.Vector;

public class SpecialitiesOfServiceStaffTable extends TableView{
    String [] nameColumns = {"ID", "Название"};
    public SpecialitiesOfServiceStaffTable(String name, String userID, Role role) {
        super(name, userID, role);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values = ConnectionManager.select("SELECT * FROM spec_of_service_staff", 2);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
    }

    @Override
    public void createNewRow() {

    }

    @Override
    public void editRow(Integer id) {

    }

    @Override
    public void deleteRow(Integer id) {

    }
}
