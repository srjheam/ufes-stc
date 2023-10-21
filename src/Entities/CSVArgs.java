import java.util.LinkedList;
import java.util.List;

public class CSVArgs {
    private LinkedList<String> importantColumns;

    public void addImportantColumn(String column){
        importantColumns.add(column);
    }

    public List<String> getImportantColumns(){
        return importantColumns;
    }
}
