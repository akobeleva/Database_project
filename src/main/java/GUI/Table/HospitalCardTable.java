package GUI.Table;

import DAL.ConnectionManager;
import GUI.Row.HospitalCardRowView;
import GUI.Row.Mode;

import java.util.Arrays;
import java.util.Vector;

public class HospitalCardTable extends TableView{
    String [] nameColumns = {"ID", "Пациент", "Отделение", "Дата поступления", "Дата выписки", "Состояние", "Температура"};
    public HospitalCardTable(String name) {
        super(name);
        updateTable();
    }

    @Override
    public void updateTable() {
        Vector values = ConnectionManager.select("SELECT card_id, p.surname || ' ' || p.name || ' ' || p.patronymic, " +
                "d.name, start_date, end_date, health_status, temp FROM hospital_card JOIN patients p USING (patient_id) " +
                "JOIN departments d USING(department_id)", 7);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
    }

    @Override
    public void createNewRow() {
        HospitalCardRowView create = new HospitalCardRowView("Добавление стационарной карты", Mode.CREATE);
    }

    @Override
    public void editRow(Integer id) {

    }

    @Override
    public void deleteRow(Integer id) {

    }
}
