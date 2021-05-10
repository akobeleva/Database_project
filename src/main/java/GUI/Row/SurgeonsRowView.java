package GUI.Row;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.Vector;

public class SurgeonsRowView extends RowView{
    private JPanel mainPanel;
    private JLabel label;
    private JTextField numdOpTextField;
    private JTextField numbFatalOpTextField;
    private JButton OkButton;
    private JLabel numdOpLabel;
    private JLabel numbFatalOpLabel;
    private JLabel idLabel;
    private JLabel dotorIdLabel;

    public SurgeonsRowView(String viewName, Mode mode) {
        super(viewName, mode);
        this.setContentPane(mainPanel);

        OkButton.addActionListener(e -> okActionListener());

        pack();
    }

    @Override
    public void insertRow() {
        ConnectionManager.executeQuery("UPDATE surgeons SET " + getRowFromForm() + "WHERE doctor_id = " + selectedRow);
    }

    @Override
    public String getRowFromForm() {
        String numbOP = numdOpTextField.getText();
        String numbFatalOp = numbFatalOpTextField.getText();
        String row = "numb_op = " + numbOP + "," + "numb_fatal_op = " + numbFatalOp;
        return row;
    }

    @Override
    public void fillFields() {
        Vector vectorRows = ConnectionManager.select("SELECT * from surgeons WHERE doctor_id = " + selectedRow, 3);
        Vector<String> row = (Vector<String>) vectorRows.get(0);
        dotorIdLabel.setText(row.get(0));
        numdOpTextField.setText(row.get(1));
        numbFatalOpTextField.setText(row.get(2));
    }
}
