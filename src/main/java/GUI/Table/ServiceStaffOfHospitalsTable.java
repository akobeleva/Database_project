package GUI.Table;

import DAL.ConnectionManager;
import GUI.Role;
import GUI.Row.DoctorsOfHospitalsRowView;
import GUI.Row.Mode;
import GUI.Row.ServiceStaffOfHospitalsRowView;

import java.util.Arrays;
import java.util.Vector;

public class ServiceStaffOfHospitalsTable extends TableView{
    String [] nameColumns = {"ID", "Больница", "ФИО обслуживающего персонала"};
    public ServiceStaffOfHospitalsTable(String name, String userID, Role role) {
        super(name, userID, role);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values = ConnectionManager.select("SELECT id, h.name, ss.surname || ' ' || ss.name || ' ' || ss.patronymic " +
                "FROM ss_of_hospitals JOIN hospitals h USING (hospital_id) " +
                "JOIN service_staff ss USING (ss_id) ORDER BY id", 3);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
    }

    @Override
    public void createNewRow() {
        ServiceStaffOfHospitalsRowView create = new ServiceStaffOfHospitalsRowView("Добавление место работы обслуживающего персонала", Mode.CREATE);
    }

    @Override
    public void editRow(Integer id) {

    }

    @Override
    public void deleteRow(Integer id) {
        ConnectionManager.delete("DELETE FROM ss_of_hospitals WHERE id = " + id);
    }
}
