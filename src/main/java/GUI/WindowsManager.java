package GUI;

import GUI.Row.RowView;
import GUI.Table.TableView;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class WindowsManager {
    private static Map<String, TableView> tableViewStringMap;
    private static Map<String, RowView> rowViewStringMap;
    private static Map<String, JFrame> mainFramesMap;

    public WindowsManager(){
        tableViewStringMap = new HashMap<>();
        rowViewStringMap = new HashMap<>();
        mainFramesMap = new HashMap<>();
    }

    public static void addMainFrame(JFrame frame, String name){
        mainFramesMap.put(name, frame);
    }

    public static void addTableWindow(TableView tableView, String name){
        tableViewStringMap.put(name, tableView);
    }

    public static void addRowWindow(RowView rowView, String name){
        rowViewStringMap.put(name, rowView);
    }

    public static void setMainFramesVisible(String windowName, boolean value) {
        mainFramesMap.get(windowName).setVisible(value);
    }

    public static void setTableWindowVisible(String windowName, boolean value){
        tableViewStringMap.get(windowName).setVisible(value);
    }

    public static void setRowWindowVisible(String windowName, boolean value){
        rowViewStringMap.get(windowName).setVisible(value);
    }

    public static boolean isMainFrameExists(String name){
        return mainFramesMap.containsKey(name);
    }

    public static boolean isTableWindowExists(String name){
        return tableViewStringMap.containsKey(name);
    }

    public static boolean isRowWindowExists(String name){
        return rowViewStringMap.containsKey(name);
    }
}
