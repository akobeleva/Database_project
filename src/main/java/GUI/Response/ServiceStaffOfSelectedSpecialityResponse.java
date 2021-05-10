package GUI.Response;

import DAL.ConnectionManager;

import java.util.Arrays;
import java.util.Vector;

public class ServiceStaffOfSelectedSpecialityResponse extends ResponseView{
    String [] nameColumns = {"ID", "ФИО"};
    public ServiceStaffOfSelectedSpecialityResponse(String request) {
        super(request);
        Vector values = ConnectionManager.select(request, 2);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);

        totalCountLabel.setText("Общее число обслуживающего персонала указанной специальности");
        totalCount.setText(String.valueOf(values.size()));
    }
}
