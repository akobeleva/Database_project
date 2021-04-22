package GUI.Table;

import DAL.ConnectionManager;
import GUI.Row.DoctorsOfPolyclinicsRowView;
import GUI.Row.Mode;

import java.util.Arrays;
import java.util.Vector;

public class DoctorsOfPolyclinicsTable extends TableView{
    String [] nameColumns = {"ID", "Больница", "ФИО врача"};
    public DoctorsOfPolyclinicsTable(String name) {
        super(name);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values = ConnectionManager.select("SELECT id, p.name, d.surname || ' ' || d.name || ' ' || d.patronymic " +
                "FROM doctors_of_polyclinics JOIN polyclinics p USING (polyclinic_id) " +
                "JOIN doctors d USING (doctor_id) ORDER BY id", 3);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
    }

    @Override
    public void createNewRow() {
        DoctorsOfPolyclinicsRowView create = new DoctorsOfPolyclinicsRowView("Добавление места работы врача", Mode.CREATE);
    }

    @Override
    public void editRow(Integer id) {

    }

    @Override
    public void deleteRow(Integer id) {
        ConnectionManager.delete("DELETE FROM doctors_of_polyclinics WHERE id = " + id);
    }
}
