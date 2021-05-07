package DAL;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CreateDatabase {
    private final List<String> tablesNameWithSeq;
    private final List<String> tablesNameWithoutSeq;
    public CreateDatabase(){
        tablesNameWithSeq = new LinkedList<>();
        tablesNameWithSeq.add("Specialities.sql");
        tablesNameWithSeq.add("Polyclinics.sql");
        tablesNameWithSeq.add("Doctors.sql");
        tablesNameWithSeq.add("Hospitals.sql");
        tablesNameWithSeq.add("Buildings.sql");
        tablesNameWithSeq.add("Departments.sql");
        tablesNameWithSeq.add("DoctorsOfHospitals.sql");
        tablesNameWithSeq.add("DoctorsOfPolyclinics.sql");
        tablesNameWithSeq.add("SpecialitiesOfServiceStaff.sql");
        tablesNameWithSeq.add("ServiceStaff.sql");
        tablesNameWithoutSeq = new LinkedList<>();
        tablesNameWithoutSeq.add("Surgeons.sql");
        tablesNameWithoutSeq.add("Radiographers.sql");
    }

    private String readScriptFromFile(String path){
        InputStream is = this.getClass().getResourceAsStream("/" + path);
        BufferedReader reader;
        reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

        return reader.lines().collect(Collectors.joining());
    }

    private List<String> addSequencesScripts(){
        List<String> sequences = new LinkedList<>();
        for (String name: tablesNameWithSeq){
            sequences.add(readScriptFromFile("sequences/" + name));
        }
        return sequences;
    }

    private List<String> addAutoincrementScripts(){
        List<String> autoIncrements = new LinkedList<>();
        for (String name : tablesNameWithSeq){
            autoIncrements.add(readScriptFromFile("triggers/" + name));
        }
        return autoIncrements;
    }

    private List<String> dropSequencesScripts(){
        List<String> toDropSequences = new LinkedList<>();
        for (String name : tablesNameWithSeq){
            toDropSequences.add(readScriptFromFile("drop/dropSequences/" + name));
        }
        return toDropSequences;
    }

    private List<String> dropTablesScripts(){
        LinkedList<String> toDropTables = new LinkedList<>();
        for (String name : tablesNameWithSeq){
            toDropTables.addFirst(readScriptFromFile("drop/dropTables/" + name));
        }
        for (String name : tablesNameWithoutSeq){
            toDropTables.addFirst(readScriptFromFile("drop/dropTables/" + name));
        }
        return toDropTables;
    }

    private List<String> readListOfScriptsFromFile(String path){
        InputStream is = this.getClass().getResourceAsStream("/" + path);
        System.out.println(is);
        BufferedReader reader;
        reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        List<String> queries = new LinkedList<>();
        Object[] lines = reader.lines().toArray();

        for(Object line: lines) {
            queries.add(line.toString());
        }
        return queries;
    }

    private void insertDefaultData(){
        for (String name : tablesNameWithSeq){
            List<String> list = readListOfScriptsFromFile("inserts/" + name);
            if (!list.isEmpty()){
                ConnectionManager.insert(list);
            }
        }
        for (String name : tablesNameWithoutSeq){
            List<String> list = readListOfScriptsFromFile("inserts/" + name);
            if (!list.isEmpty()){
                ConnectionManager.insert(list);
            }
        }
    }

    public void create(){
        List<String> createTablesScripts = new LinkedList<>();
        for (String name : tablesNameWithSeq){
            createTablesScripts.add(readScriptFromFile("create/" + name));
        }
        for (String name : tablesNameWithoutSeq){
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
