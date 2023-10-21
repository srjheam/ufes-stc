package Utils.Reader;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.Scanner;

public class CSVReader {
    
    public static void readCandidatosCSV(String path, CSVArgs args){
        try(FileInputStream fin = new FileInputStream(path); 
        Scanner s = new Scanner(fin, "ISO-8859-1")){
            LinkedList<String> columns = new LinkedList<String>();
            String firstline = s.nextLine();
            try(Scanner lineScanner = new Scanner(firstline)){
                lineScanner.useDelimiter(";");
                while(lineScanner.hasNext()){
                    String token = lineScanner.next();
                    columns.add(token);
                }
            }
            while(s.hasNextLine()){
                int i = 0, j = 0;
                String[] candidatoArgs = new String[args.getImportantColumns().size()];
                String line = s.nextLine();
                try(Scanner lineScanner = new Scanner(line)){
                    lineScanner.useDelimiter(";");
                    for(String arg : columns){
                        if(args.getImportantColumns().contains(arg)){
                            candidatoArgs[i++] = lineScanner.next();
                        }
                        else{
                            lineScanner.next();
                        }
                    }
                }
                CandidatoVotacao candidato = parseCandidato(candidatoArgs);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}