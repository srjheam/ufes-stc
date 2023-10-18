import java.util.LinkedList;

public class Validation {
    private LinkedList<String> errors;

    public Validation(){
        errors = new LinkedList<String>();
    }

    public void addError(String error){
        errors.add(error);
    }

    public boolean hasErrors(){
        return errors.size() > 0;
    }

    public void printErrors(){
        for(String s : errors){
            System.out.println(s);
        }
    }
}
