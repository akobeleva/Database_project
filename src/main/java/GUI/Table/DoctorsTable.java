package GUI.Table;

import DAL.ConnectionManager;
import GUI.CreateNewRow.CreateDoctor;

import java.util.Arrays;
import java.util.Vector;

public class DoctorsTable extends TableView {
    String [] nameColumns = {"ID", "Фамилия", "Имя", "Отчество", "Специальность", "докт. мед.наук", "канд. мед.наук", "Профессор", "Доцент", "Стаж"};
    public DoctorsTable(String name) {
        super(name);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values = ConnectionManager.select("SELECT doctor_id, surname, doctors.name, patronymic, specialities.name, dms, cms,  professor, docent, experience \n" +
                "FROM doctors JOIN specialities USING (speciality_id)", 10);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
    }

    @Override
    public void createNewRow() {
        CreateDoctor addDoctor = new CreateDoctor("Добавление врача");
    }

    @Override
    public void editRow() {

    }

    @Override
    public void deleteRow(Integer id) {
        ConnectionManager.delete("DELETE FROM doctors WHERE doctor_id = " + id);
    }
}
