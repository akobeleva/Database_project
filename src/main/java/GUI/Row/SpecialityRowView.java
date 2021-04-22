package GUI.Row;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.Vector;

public class SpecialityRowView extends RowView {
    private JTextField specTextField;
    private JLabel specLabel;
    private JLabel label;
    private JButton OkButton;
    private JPanel mainPanel;

    public SpecialityRowView(String viewName, Mode mode){
        super(viewName, mode);
        this.setContentPane(mainPanel);

        OkButton.addActionListener(e-> okActionListener());

        this.pack();
    }

    @Override
    public void insertRow() {
        if (mode == Mode.CREATE){
            ConnectionManager.insert("INSERT INTO specialities(name) VALUES (" + getRowFromForm() + ")");
        }
        else ConnectionManager.executeQuery("UPDATE specialities SET " + getRowFromForm() + "WHERE speciality_id = " + selectedRow);
    }

    public String getRowFromForm(){
        String spec = specTextField.getText();
        String row;
        if (mode == Mode.CREATE) {
            row = "'" + spec + "'";
        }
        else {
            row  = "name = " + "'" + spec + "'";
        }
        return row;
    }

    @Override
    public void fillFields() {
        Vector vectorRows = ConnectionManager.select("SELECT * from specialities WHERE speciality_id = " + selectedRow, 2);
        Vector<String> row = (Vector<String>) vectorRows.get(0);
        specTextField.setText(row.get(1));
    }
}
