package GUI;

import DAL.CreateDatabase;
import GUI.Request.RequestView;
import GUI.Table.TableView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class RequestsWindow extends JFrame{
    private JLabel label;
    private JTable table;
    private JPanel mainPanel;
    private Map<String, String> nameRequests = new HashMap<String, String>(){{
        put("Перечень врачей по специальности", "GUI.Request.DoctorsOfSelectedSpecialityRequest");
    }
    };
    public RequestsWindow(){
        this.setTitle("Возможные запросы");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 500);

        this.setContentPane(mainPanel);

        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int i, int j){
                return false;
            }
        };
        Vector<String> requests = new Vector(Arrays.asList(nameRequests.keySet().toArray()));
        dtm.addColumn("Requests", requests);
        table.setModel(dtm);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                    int rowIndex = table.rowAtPoint(e.getPoint());
                    String row = requests.get(rowIndex);
                    try {
                        RequestView requestView = (RequestView)Class.forName(nameRequests.get(row)).getConstructor(String.class).newInstance(row);
                    } catch (InstantiationException instantiationException) {
                        instantiationException.printStackTrace();
                    } catch (IllegalAccessException illegalAccessException) {
                        illegalAccessException.printStackTrace();
                    } catch (InvocationTargetException invocationTargetException) {
                        invocationTargetException.printStackTrace();
                    } catch (NoSuchMethodException noSuchMethodException) {
                        noSuchMethodException.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
            }
        });

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
