package GUI.Response;

import DAL.ConnectionManager;

import java.util.Arrays;
import java.util.Vector;

public class PatientsOfSelectedHospitalAndTimeReponse extends ResponseView{
    String [] nameColumns = {"ФИО", "Больница", "Отдление", "Дата поступления", "Дата выписки"};
    public PatientsOfSelectedHospitalAndTimeReponse(String request) {
        super(request);
        Vector values = ConnectionManager.select(request, 5);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
        totalCountLabel.setText("Общее число пациентов указанной больницы за данный период");
        totalCount.setText(String.valueOf(values.size()));
    }
}
