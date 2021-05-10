package GUI.Response;

import DAL.ConnectionManager;

import java.util.Arrays;
import java.util.Vector;

public class DoctorsWithOperationsResponse extends ResponseView{
    String [] nameColumns = {"ID", "ФИО", "Число операций"};
    public DoctorsWithOperationsResponse(String request) {
        super(request);
        Vector values = ConnectionManager.select(request, 3);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
        totalCountLabel.setText("Общее число врачей с большим числом операций");
        totalCount.setText(String.valueOf(values.size()));
    }
}
