package GUI.DoctorsView;

import DAL.ConnectionManager;

import javax.swing.*;

public class AddDoctor extends JFrame{
    private JTextField surnameTextField;
    private JTextField nameTextField;
    private JTextField patronymicTextField;
    private JTextField dmsTextField;
    private JTextField cmsTextField;
    private JTextField professorTextField;
    private JTextField docentTextField;
    private JTextField experienceTextField;
    private JPanel mainPanel;
    private JLabel label;
    private JLabel surnameLabel;
    private JLabel nameLabel;
    private JLabel patronymicLabel;
    private JButton OkButton;
    private JLabel dmsLabel;
    private JLabel cmsLabel;
    private JLabel professorLabel;
    private JLabel docentLabel;
    private JLabel experienceLabel;
    private JTextField specTextField;
    private JLabel specLabel;

    public AddDoctor(){
        this.setTitle("Добавление врача");
        this.setSize(800, 600);
        this.setContentPane(mainPanel);

        OkButton.addActionListener(e->{
            ConnectionManager.insert("INSERT INTO doctors(surname, name, patronymic, speciality_id, " +
                    "doctor_of_medical_sciences, candidate_of_medical_sciences, professor, docent, experience) " +
                    "VALUES (" + getRowFromForm() + ")");
            this.setVisible(false);
        });

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private String getRowFromForm(){
        String surname = surnameTextField.getText();
        String name = nameTextField.getText();
        String patronymic = patronymicTextField.getText();
        String speciality = specTextField.getText();
        String dms = dmsTextField.getText();
        String cms = cmsTextField.getText();
        String professor = professorTextField.getText();
        String docent = docentTextField.getText();
        String exper = experienceTextField.getText();
        String row = new String("'" + surname + "'" + ", " + "'" + name + "'" + ", " + "'" + patronymic + "'"
                + ", " + speciality + ", " + dms + ", " + cms + ", " + professor + ", " + docent + ", " + exper);
        return row;
    }
}
