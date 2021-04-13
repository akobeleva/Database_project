package GUI.DoctorsView;

import DAL.ConnectionManager;

import javax.swing.*;

public class DeleteDoctor extends JFrame{
    private JTextField idTextField;
    private JButton OKButton;
    private JLabel label;
    private JLabel idLabel;
    private JPanel mainPanel;

    public DeleteDoctor(){
        this.setTitle("Удаление врача");
        this.setSize(200, 200);
        this.setContentPane(mainPanel);

        OKButton.addActionListener(e->{
            ConnectionManager.delete("DELETE FROM doctors WHERE doctor_id = " + idTextField.getText());
            this.setVisible(false);
        });
        //this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
