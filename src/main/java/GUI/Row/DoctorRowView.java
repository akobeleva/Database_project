package GUI.Row;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class DoctorRowView extends RowView {
    private JTextField surnameTextField;
    private JTextField nameTextField;
    private JTextField patronymicTextField;
    private JTextField experienceTextField;
    private JPanel mainPanel;
    private JLabel label;
    public JLabel surnameLabel;
    public JLabel nameLabel;
    private JLabel patronymicLabel;
    private JButton OkButton;
    private JLabel experienceLabel;
    private JLabel specLabel;
    private JComboBox comboBox;
    private JCheckBox dmsCheckBox;
    private JCheckBox cmsCheckBox;
    private JCheckBox professorCheckBox;
    private JCheckBox docentCheckBox;
    private final Map<String, String> specialities = new HashMap<>();

    public DoctorRowView(String viewName, Mode mode){
        super(viewName, mode);
        this.setContentPane(mainPanel);

        OkButton.addActionListener(e-> okActionListener());

        Vector vectorItems = ConnectionManager.select("SELECT * from specialities", 2);
        for (Object vectorItem : vectorItems) {
            Vector<String> vec = (Vector<String>) vectorItem;
            specialities.put(vec.get(1), vec.get(0));
            comboBox.addItem(vec.get(1));
        }
        comboBox.setSelectedIndex(-1);

        pack();
    }

    private int boolToInt(boolean b) {
        return b ? 1 : 0;
    }

    private boolean stringToBoolean(String s){
        return s.equals("1");
    }

    @Override
    public void insertRow() {
        if (mode == Mode.CREATE) {
            ConnectionManager.insert("INSERT INTO doctors(surname, name, patronymic, speciality_id, " +
                    "dms, cms, professor, docent, experience) VALUES (" + getRowFromForm() + ")");
        } else ConnectionManager.executeQuery("UPDATE doctors SET " + getRowFromForm() + "WHERE doctor_id = " + selectedRow);
    }

    @Override
    public String getRowFromForm(){
        String surname = surnameTextField.getText();
        String name = nameTextField.getText();
        String patronymic = patronymicTextField.getText();
        String speciality = specialities.get(comboBox.getSelectedItem());
        int dms = boolToInt(dmsCheckBox.isSelected());
        int cms = boolToInt(cmsCheckBox.isSelected());
        int professor = boolToInt(professorCheckBox.isSelected());
        int docent = boolToInt(docentCheckBox.isSelected());
        String exper = experienceTextField.getText();
        String row;
        if (mode == Mode.CREATE) {
            row = "'" + surname + "'" + ", " + "'" + name + "'" + ", " + "'" + patronymic + "'"
                    + ", " + speciality + ", " + dms + ", " + cms + ", " + professor + ", " + docent + ", " + exper;
        }
        else {
            row  = "surname = " + "'" + surname + "'," + "name = " + "'" + name + "'," + "patronymic = "
                        + "'" + patronymic + "'," + "speciality_id = " + speciality + "," + "dms = " + dms + "," + "cms = " + cms +
                            "," + "professor = " + professor + "," + "docent = " + docent + "," + "experience = " + exper;
        }
        return row;
    }

    @Override
    public void fillFields(){
        Vector vectorRows = ConnectionManager.select("SELECT * from doctors WHERE doctor_id = " + selectedRow, 10);
        Vector<String> row = (Vector<String>) vectorRows.get(0);
        surnameTextField.setText(row.get(1));
        nameTextField.setText(row.get(2));
        patronymicTextField.setText(row.get(3));
        comboBox.setSelectedItem(row.get(4));
        dmsCheckBox.setSelected(stringToBoolean(row.get(5)));
        cmsCheckBox.setSelected(stringToBoolean(row.get(6)));
        professorCheckBox.setSelected(stringToBoolean(row.get(7)));
        docentCheckBox.setSelected(stringToBoolean(row.get(8)));
        experienceTextField.setText(row.get(9));
    }
}
