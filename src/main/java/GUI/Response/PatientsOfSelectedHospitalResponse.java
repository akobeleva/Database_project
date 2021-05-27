package GUI.Response;

import DAL.ConnectionManager;

import java.util.Arrays;
import java.util.Vector;

public class PatientsOfSelectedHospitalResponse extends ResponseView{
    String [] nameColumns = {"ФИО", "Больница", "Отдление", "Дата поступления", "Состояние", "Температура"};
    public PatientsOfSelectedHospitalResponse(String request) {
        super(request);
        Vector values = ConnectionManager.select(request, 6);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
        totalCountLabel.setText("Общее число пациентов указанной больницы или отделения");
        totalCount.setText(String.valueOf(values.size()));
    }
}
