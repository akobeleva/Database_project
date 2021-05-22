package GUI.Table;

import DAL.ConnectionManager;
import GUI.Role;
import GUI.Row.Mode;
import GUI.Row.ServiceStaffOfPolyclinicsRowView;

import java.util.Arrays;
import java.util.Vector;

public class ServiceStaffOfPolyclinicsTable extends TableView{
    String [] nameColumns = {"ID", "Больница", "ФИО обслуживающего персонала"};
    public ServiceStaffOfPolyclinicsTable(String name, String userID, Role role) {
        super(name, userID, role);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values = ConnectionManager.select("SELECT id, p.name, ss.surname || ' ' || ss.name || ' ' || ss.patronymic " +
                "FROM ss_of_polyclinics JOIN polyclinics p USING (polyclinic_id) " +
                "JOIN service_staff ss USING (ss_id) ORDER BY id", 3);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
    }

    @Override
    public void createNewRow() {
        ServiceStaffOfPolyclinicsRowView create = new ServiceStaffOfPolyclinicsRowView("Добавление места работы врача", Mode.CREATE);
    }


    @Override
    public void editRow(Integer id) {

    }

    @Override
    public void deleteRow(Integer id) {
        ConnectionManager.delete("DELETE FROM ss_of_polyclinics WHERE id = " + id);
    }
}
