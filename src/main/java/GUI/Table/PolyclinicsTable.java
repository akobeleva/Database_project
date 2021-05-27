package GUI.Table;

import DAL.ConnectionManager;
import GUI.Role;
import GUI.Row.Mode;
import GUI.Row.PolyclinicRowView;

import java.util.Arrays;
import java.util.Vector;

public class PolyclinicsTable extends TableView {
    String[] nameColumns = {"ID", "Название", "Адрес", "Телефон", "Количество кабинетов"};

    public PolyclinicsTable(String name, String userID, Role role) {
        super(name, userID, role);
        if (role != Role.ADMIN) addButton.setVisible(false);
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
        if (role == Role.ADMIN) {
            PolyclinicRowView editPolyclinic = new PolyclinicRowView("Изменение поликлиники", Mode.EDIT);
            editPolyclinic.setSelectedRow(id);
            editPolyclinic.fillFields();
        }
    }

    @Override
    public void deleteRow(Integer id) {
        if (role == Role.ADMIN)
            ConnectionManager.delete("DELETE FROM polyclinics WHERE polyclinic_id = " + id);
    }
}
