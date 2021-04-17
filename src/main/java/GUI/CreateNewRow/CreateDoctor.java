package GUI.CreateNewRow;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CreateDoctor extends CreateNewRowView{
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
    private Map<String, String> specialities = new HashMap<>();

    public CreateDoctor(String viewName){
        super(viewName);
        this.setContentPane(mainPanel);

        OkButton.addActionListener(e->{
            okActionListener();
        });

        Vector vectorItems = ConnectionManager.select("SELECT * from specialities", 2);
        for (int i = 0; i < vectorItems.size(); i++){
            Vector<String> vec = (Vector<String>) vectorItems.get(i);
            specialities.put(vec.get(1), vec.get(0));
            comboBox.addItem(vec.get(1));
        }
        pack();
    }

    private int boolToInt(boolean b) {
        return b ? 1 : 0;
    }

    @Override
    public void insertRow() {
        ConnectionManager.insert("INSERT INTO doctors(surname, name, patronymic, speciality_id, " +
                "dms, cms, professor, docent, experience) VALUES (" + getRowFromForm() + ")");
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
        String row = new String("'" + surname + "'" + ", " + "'" + name + "'" + ", " + "'" + patronymic + "'"
                + ", " + speciality + ", " + dms + ", " + cms + ", " + professor + ", " + docent + ", " + exper);
        return row;
    }
}
