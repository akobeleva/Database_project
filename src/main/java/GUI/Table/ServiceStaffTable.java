package GUI.Table;

import DAL.ConnectionManager;
import GUI.Role;

import java.util.Arrays;
import java.util.Vector;

public class ServiceStaffTable extends TableView{
    String [] nameColumns = {"ID", "Фамилия", "Имя", "Отчество", "Специальность", "Стаж"};
    public ServiceStaffTable(String name, String userID, Role role) {
        super(name, userID, role);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values = ConnectionManager.select("SELECT ss_id, surname, ss.name, patronymic, spec.name, experience \n" +
                "FROM service_staff ss JOIN spec_of_service_staff spec USING (spec_ss_id)", 6);
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
        ConnectionManager.delete("DELETE FROM service_staff WHERE ss_id = " + id);
    }
}
