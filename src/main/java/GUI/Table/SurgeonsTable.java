package GUI.Table;

import DAL.ConnectionManager;

import java.util.Arrays;
import java.util.Vector;

public class SurgeonsTable extends TableView{
    String [] nameColumns = {"ID", "ФИО врача", "Число операций", "Число операций с летальным исходом"};
    public SurgeonsTable(String name) {
        super(name);
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

    }

    @Override
    public void deleteRow(Integer id) {

    }
}
