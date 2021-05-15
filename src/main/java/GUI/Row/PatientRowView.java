package GUI.Row;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

public class PatientRowView extends RowView{
    private JTextField surnameTextField;
    private JTextField nameTextField;
    private JTextField patrTextField;
    private JTextField phoneTextField;
    private JButton OkButton;
    private JLabel label;
    private JPanel mainPanel;
    private JLabel surnameLabel;
    private JLabel nameLabel;
    private JLabel patrLabel;
    private JLabel birthLabel;
    private JLabel phoneLabel;
    private JTextField dayTextField;
    private JTextField monthTextField;
    private JTextField yaerTextField;

    public PatientRowView(String viewName, Mode mode) {
        super(viewName, mode);
        this.setContentPane(mainPanel);

        OkButton.addActionListener(e -> okActionListener());


        pack();
    }

    @Override
    public void insertRow() {
        if (mode == Mode.CREATE) {
            ConnectionManager.insert("INSERT INTO patients(surname, name, patronymic, birthday, " +
                "phone_number) VALUES (" + getRowFromForm() + ")");
        } else
            ConnectionManager.executeQuery("UPDATE patients SET " + getRowFromForm() + "WHERE patient_id = " + selectedRow);
    }

    @Override
    public String getRowFromForm() {
        String surname = surnameTextField.getText();
        String name = nameTextField.getText();
        String patronymic = patrTextField.getText();
        String day = dayTextField.getText();
        String month = monthTextField.getText();
        String year = yaerTextField.getText();
        String phone = phoneTextField.getText();
        String row = new String();
        if (mode == Mode.CREATE) {
            row = "'" + surname + "','" + name + "','" + patronymic + "', TO_DATE('" + day +"/" + month + "/" + year + "', 'DD/MM/YYYY'),'"
                    + phone + "'" ;
        }
        return row;
    }

    @Override
    public void fillFields() {

    }
}
