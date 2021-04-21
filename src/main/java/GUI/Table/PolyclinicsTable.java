package GUI.Table;

import DAL.ConnectionManager;
import GUI.Row.Mode;
import GUI.Row.PolyclinicRowView;

import java.util.Arrays;
import java.util.Vector;

public class PolyclinicsTable extends TableView{
    String [] nameColumns = {"ID", "Название", "Адрес", "Телефон", "Количество кабинетов"};
    public PolyclinicsTable(String name) {
        super(name);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values = ConnectionManager.select("SELECT * FROM polyclinics", 5);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
    }

    @Override
    public void createNewRow() {
        PolyclinicRowView createPolyclinic = new PolyclinicRowView("Добавление поликлиники", Mode.CREATE);
    }

    @Override
    public void editRow(Integer id) {
        PolyclinicRowView editPolyclinic = new PolyclinicRowView("Изменение поликлиники", Mode.EDIT);
        editPolyclinic.setSelectedRow(id);
        editPolyclinic.fillFields();
    }

    @Override
    public void deleteRow(Integer id) {
        ConnectionManager.delete("DELETE FROM polyclinics WHERE polyclinic_id = " + id);
    }
}
