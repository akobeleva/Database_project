package GUI.Request;

import javax.swing.*;

public abstract class RequestView extends JFrame {
    public RequestView(String viewName){
        this.setTitle(viewName);
        this.setSize(800, 600);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public void requestActionListener(){
        executeRequest();
        this.setVisible(false);
    }

    public abstract void executeRequest();

    public abstract String getRequestFromForm();
}
