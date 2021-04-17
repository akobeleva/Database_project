package GUI.CreateNewRow;

import javax.swing.*;

public abstract class CreateNewRowView extends JFrame {
    public CreateNewRowView(String viewName){
        this.setTitle(viewName);
        this.setSize(800, 600);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public void okActionListener(){
        insertRow();
        this.setVisible(false);
    }

    public abstract void insertRow();

    public abstract String getRowFromForm();
}
