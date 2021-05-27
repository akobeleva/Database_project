package GUI.Response;

import DAL.ConnectionManager;

import java.util.Arrays;
import java.util.Vector;

public class PatientsOfSelectedPolyclinicAndDoctorResponse extends ResponseView{
    String [] nameColumns = {"ФИО"};
    public PatientsOfSelectedPolyclinicAndDoctorResponse(String request) {
        super(request);
        Vector values = ConnectionManager.select(request, 1);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
        totalCountLabel.setText("Общее число пациентов, наблюдающихся у указанного врача");
        totalCount.setText(String.valueOf(values.size()));
    }
}
