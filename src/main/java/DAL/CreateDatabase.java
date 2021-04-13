package DAL;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CreateDatabase {
    private List<String> tablesName;

    public CreateDatabase(){
        tablesName = new LinkedList<>();
        tablesName.add("Specialities.sql");
        tablesName.add("Doctors.sql");
        tablesName.add("Hospitals.sql");
    }

    private String readScriptFromFile(String path){
        InputStream is = this.getClass().getResourceAsStream("/" + path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        return reader.lines().collect(Collectors.joining());
    }

    private List<String> addSequencesScripts(){
        List<String> sequences = new LinkedList<>();
        for (String name: tablesName){
            sequences.add(readScriptFromFile("sequences/" + name));
        }
        return sequences;
    }

    private List<String> addAutoincrementScripts(){
        List<String> autoIncrements = new LinkedList<>();
        for (String name : tablesName){
            autoIncrements.add(readScriptFromFile("triggers/" + name));
        }
        return autoIncrements;
    }

    private List<String> dropSequencesScripts(){
        List<String> toDropSequences = new LinkedList<>();
        for (String name : tablesName){
            toDropSequences.add(readScriptFromFile("drop/dropSequences/" + name));
        }
        return toDropSequences;
    }

    private List<String> dropTablesScripts(){
        List<String> toDropTables = new LinkedList<>();
        for (String name : tablesName){
            ((LinkedList<String>)toDropTables).addFirst(readScriptFromFile("drop/dropTables/" + name));
        }
        return toDropTables;
    }

    private List<String> readListOfScriptsFromFile(String path){
        InputStream is = this.getClass().getResourceAsStream("/" + path);
        System.out.println(is);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        List<String> queries = new LinkedList<>();
        Object[] lines = reader.lines().toArray();

        for(Object line: lines) {
            queries.add(line.toString());
        }
        return queries;
    }

    private void insertDefaultData(){
        for (String name : tablesName){
            List<String> list = readListOfScriptsFromFile("inserts/" + name);
            if (!list.isEmpty()){
                ConnectionManager.insert(list);
            }
        }
    }

    public void create(){
        List<String> createTablesScripts = new LinkedList<>();
        for (String name : tablesName){
            createTablesScripts.add(readScriptFromFile("create/" + name));
        }
        ConnectionManager.executeQuery(dropTablesScripts());
        ConnectionManager.executeQuery(dropSequencesScripts());
        ConnectionManager.executeQuery(createTablesScripts);
        ConnectionManager.executeQuery(addSequencesScripts());
        ConnectionManager.executeQuery(addAutoincrementScripts());
        insertDefaultData();
    }
}
