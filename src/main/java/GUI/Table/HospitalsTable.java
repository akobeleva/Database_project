package GUI.Table;

import DAL.ConnectionManager;
import GUI.CreateNewRow.CreateHospital;

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
        CreateHospital createHospital = new CreateHospital("Добавление больницы");
    }

    @Override
    public void editRow() {

    }

    @Override
    public void deleteRow(Integer id) {
        ConnectionManager.delete("DELETE FROM hospitals WHERE hospital_id = " + id);
    }
}
