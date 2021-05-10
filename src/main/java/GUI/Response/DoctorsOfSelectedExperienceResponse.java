package GUI.Response;

import DAL.ConnectionManager;

import java.util.Arrays;
import java.util.Vector;

public class DoctorsOfSelectedExperienceResponse extends ResponseView{
    String [] nameColumns = {"ID", "ФИО", "Стаж"};
    public DoctorsOfSelectedExperienceResponse(String request) {
        super(request);
        Vector values = ConnectionManager.select(request, 3);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);
        totalCountLabel.setText("Общее число врачей, стаж которых больше указанного");
        totalCount.setText(String.valueOf(values.size()));
    }
}
