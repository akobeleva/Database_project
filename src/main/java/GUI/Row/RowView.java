package GUI.Row;

import javax.swing.*;

public abstract class RowView extends JFrame {
    Integer selectedRow;
    Mode mode;
    public RowView(String viewName, Mode mode){
        this.mode = mode;
        this.setTitle(viewName);
        this.setSize(800, 600);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public void okActionListener(){
        insertRow();
        this.setVisible(false);
    }

    public void setSelectedRow(Integer id){
        selectedRow = id;
    }

    public abstract void insertRow();

    public abstract String getRowFromForm();

    public abstract void fillFields();
}
