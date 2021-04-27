import GUI.StartWindow;
import GUI.WindowsManager;

import java.sql.SQLException;

public class App {
    public static void main(String[] agrs) throws SQLException {
        WindowsManager windowsManager = new WindowsManager();
        windowsManager.addMainFrame(new StartWindow(), "startWindow");
    }
}
