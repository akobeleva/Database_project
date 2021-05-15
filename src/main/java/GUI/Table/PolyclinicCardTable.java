package GUI.Table;

import DAL.ConnectionManager;
import GUI.Row.Mode;
import GUI.Row.PolyclinicCardRowView;

import java.util.Arrays;
import java.util.Vector;

public class PolyclinicCardTable extends TableView{
    String [] nameColumns = {"ID", "Поликлиника", "Пациент", "Врач", "Дата посещения"};
    public PolyclinicCardTable(String name) {
        super(name);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values = ConnectionManager.select("SELECT card_id, polyclinics.name, p.surname || ' ' || p.name || ' ' || p.patronymic, " +
                "d.surname || ' ' || d.name || ' ' || d.patronymic, visit_date FROM polyclinic_card JOIN polyclinics USING (polyclinic_id)" +
                " JOIN patients p USING (patient_id) JOIN doctors d USING(doctor_id)", 5);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
    }

    @Override
    public void createNewRow() {
        PolyclinicCardRowView create = new PolyclinicCardRowView("Добавление карты", Mode.CREATE);
    }

    @Override
    public void editRow(Integer id) {

    }

    @Override
    public void deleteRow(Integer id) {

    }
}
