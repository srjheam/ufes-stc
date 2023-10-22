import java.util.LinkedList;
import java.util.List;

public class CSVArgs {
    private LinkedList<String> importantColumns;

    public CSVArgs(){
        importantColumns = new LinkedList<String>();
    }
    
    public CSVArgs(String[] args){
        for(String arg : args){
            importantColumns.add(arg);
        }
    }

    public void addImportantColumn(String column){
        importantColumns.add(column);
    }

    public List<String> getImportantColumns(){
        return importantColumns;
    }
}
