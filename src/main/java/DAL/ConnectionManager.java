package DAL;

import java.sql.*;
import java.util.List;
import java.util.Vector;

public class ConnectionManager {
    private static MyConnection conn;

    public ConnectionManager(MyConnection connection) {
        conn = connection;
    }

    public static void executeQuery(List<String> queryList) {
        for (String query : queryList) {
            try (PreparedStatement preStatemnet = conn.getConnection().prepareStatement(query)) {
                preStatemnet.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void executeQuery(String query) {
        try (PreparedStatement preStatemnet = conn.getConnection().prepareStatement(query)) {
            preStatemnet.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insert(List<String> queryList) {
        for (String query : queryList) {
            try (PreparedStatement preStatemnet = conn.getConnection().prepareStatement(query)) {
                preStatemnet.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void insert(String query) {
        try (PreparedStatement preStatemnet = conn.getConnection().prepareStatement(query)) {
            preStatemnet.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Vector select(String sqlQuery, int countColumns) {
        Vector resultVector = new Vector();
        ResultSet resultSet;
        try (Statement statement = conn.getConnection().createStatement()) {
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                Vector row = new Vector();
                for (int i = 1; i <= countColumns; i++) {
                    String element = resultSet.getString(i);
                    row.add(element);
                }
                resultVector.add(row);
            }
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultVector;
    }
    /*public List<List<String>> select(String sqlQuery){
        List<List<String>> list = new LinkedList<>();

        ArrayList resultList = new ArrayList<>();
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sqlQuery);
        while (result.next()){
            ArrayList<String> list = new ArrayList<>();
            resultList.add()
        }
    }*/

    public static void delete(String query) {
        try (PreparedStatement preStatement = conn.getConnection().prepareStatement(query)) {
            preStatement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Vector getMetaData(){
        Vector nameTables = null;
        try {
            DatabaseMetaData metaData = conn.getConnection().getMetaData();
            String [] types = {"TABLE"};
            ResultSet tables = metaData.getTables(null, null, "%", types);
            while (tables.next()) {
                nameTables.add(tables.getString("TABLE_NAME"));
                System.out.println(tables.getString("TABLE_NAME"));
            }
            tables.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return nameTables;
    }
}
