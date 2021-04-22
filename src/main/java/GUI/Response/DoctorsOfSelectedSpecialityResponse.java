package GUI.Response;

import DAL.ConnectionManager;

import java.util.Arrays;
import java.util.Vector;

public class DoctorsOfSelectedSpecialityResponse extends ResponseView{
    String [] nameColumns = {"ID", "ФИО"};
    public DoctorsOfSelectedSpecialityResponse(String request) {
        super(request);
        Vector values = ConnectionManager.select(request, 2);
        Vector header = new Vector(Arrays.asList(nameColumns));
        dtm.setDataVector(values, header);
        table.setModel(dtm);

        totalCount.setText(String.valueOf(values.size()));
    }
}
