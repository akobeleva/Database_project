package GUI.Table;

import DAL.ConnectionManager;
import GUI.Role;
import GUI.Row.Mode;
import GUI.Row.SurgeonsRowView;

import java.util.Arrays;
import java.util.Vector;

public class SurgeonsTable extends TableView{
    String [] nameColumns = {"ID", "ФИО врача", "Число операций", "Число операций с летальным исходом"};
    public SurgeonsTable(String name, String userID, Role role) {
        super(name, userID, role);
        addButton.setVisible(false);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values = ConnectionManager.select("SELECT doctor_id, d.surname || ' ' || d.name || ' ' || d.patronymic, " +
                "numb_op, numb_fatal_op FROM surgeons JOIN doctors d USING (doctor_id) " +
                "ORDER BY doctor_id", 4);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
    }

    @Override
    public void createNewRow() {

    }

    @Override
    public void editRow(Integer id) {
        SurgeonsRowView editDoctor = new SurgeonsRowView("Изменение хирурга", Mode.EDIT);
        editDoctor.setSelectedRow(id);
        editDoctor.fillFields();
    }

    @Override
    public void deleteRow(Integer id) {

    }
}
