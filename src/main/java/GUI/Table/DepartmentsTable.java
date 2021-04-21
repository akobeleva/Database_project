package GUI.Table;

import DAL.ConnectionManager;
import GUI.Row.DepartmentRowView;
import GUI.Row.Mode;

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
        DepartmentRowView createDepartment = new DepartmentRowView("Добавление отделения", Mode.CREATE);
    }

    @Override
    public void editRow(Integer id) {
        DepartmentRowView editDepartment = new DepartmentRowView("Изменение отделения", Mode.EDIT);
        editDepartment.setSelectedRow(id);
        editDepartment.fillFields();
    }

    @Override
    public void deleteRow(Integer id) {
        ConnectionManager.delete("DELETE FROM departments WHERE department_id = " + id);
    }
}
