package GUI.Table;

import DAL.ConnectionManager;
import GUI.Row.HospitalRowView;
import GUI.Row.Mode;

import java.util.Arrays;
import java.util.Vector;

public class HospitalsTable extends TableView{
    String [] nameColumns = {"ID", "Название", "Адрес", "Главный врач", "Телефон"};
    public HospitalsTable(String name) {
        super(name);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values = ConnectionManager.select("SELECT hospital_id, h.name, address, d.surname || ' ' || d.name || ' ' || d.patronymic, phone_number \n" +
                "FROM hospitals h JOIN doctors d ON h.main_doctor_id = d.doctor_id", 5);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
    }

    @Override
    public void createNewRow() {
        HospitalRowView createHospital = new HospitalRowView("Добавление больницы", Mode.CREATE);
    }

    @Override
    public void editRow(Integer id) {
        HospitalRowView editHospital = new HospitalRowView("зменение больницы", Mode.EDIT);
        editHospital.setSelectedRow(id);
        editHospital.fillFields();
    }

    @Override
    public void deleteRow(Integer id) {
        ConnectionManager.delete("DELETE FROM hospitals WHERE hospital_id = " + id);
    }
}
