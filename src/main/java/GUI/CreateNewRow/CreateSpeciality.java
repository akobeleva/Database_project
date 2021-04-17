package GUI.CreateNewRow;

import DAL.ConnectionManager;

import javax.swing.*;

public class CreateSpeciality extends CreateNewRowView{
    private JTextField specTextField;
    private JLabel specLabel;
    private JLabel label;
    private JButton OkButton;
    private JPanel mainPanel;

    public CreateSpeciality(String viewName){
        super(viewName);
        this.setContentPane(mainPanel);

        OkButton.addActionListener(e->{
            okActionListener();
        });

        this.pack();
    }

    @Override
    public void insertRow() {
        ConnectionManager.insert("INSERT INTO specialities(name) VALUES (" + getRowFromForm() + ")");
    }

    public String getRowFromForm(){
        String spec = specTextField.getText();
        String row = new String("'" + spec + "'");
        return row;
    }
}
