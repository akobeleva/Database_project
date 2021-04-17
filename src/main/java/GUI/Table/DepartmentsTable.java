package GUI.Table;

import DAL.ConnectionManager;
import GUI.CreateNewRow.CreateDepartment;

import java.util.Arrays;
import java.util.Vector;

public class DepartmentsTable extends TableView{
    String [] nameColumns = {"ID", "Название", "Код корпуса", "Телефон", "Палаты", "Койки", "Свободные палаты", "Свободные койки"};
    public DepartmentsTable(String name) {
        super(name);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values = ConnectionManager.select("SELECT * FROM departments", 8);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
    }

    @Override
    public void createNewRow() {
        CreateDepartment createDepartment = new CreateDepartment("Добавление отделения");
    }

    @Override
    public void editRow() {

    }

    @Override
    public void deleteRow(Integer id) {
        ConnectionManager.delete("DELETE FROM departments WHERE department_id = " + id);
    }
}
