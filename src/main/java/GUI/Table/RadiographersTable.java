package GUI.Table;

import DAL.ConnectionManager;
import GUI.Role;
import GUI.Row.Mode;
import GUI.Row.RadiographersRowView;

import java.util.Arrays;
import java.util.Vector;

public class RadiographersTable extends TableView {
    String[] nameColumns = {"ID", "ФИО врача", "Коэффициент к з/п", "Длительность отпуска"};

    public RadiographersTable(String name, String userID, Role role) {
        super(name, userID, role);
        addButton.setVisible(false);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values = ConnectionManager.select("SELECT doctor_id, d.surname || ' ' || d.name || ' ' || d.patronymic, " +
                "salary_kf, vacation_days FROM radiographers JOIN doctors d USING (doctor_id) " +
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
        if (role != Role.PATIENT) {
            RadiographersRowView editDoctor = new RadiographersRowView("Изменение рентгенолога", Mode.EDIT);
            editDoctor.setSelectedRow(id);
            editDoctor.fillFields();
        }
    }

    @Override
    public void deleteRow(Integer id) {
    }
}
