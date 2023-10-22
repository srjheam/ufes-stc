import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CSVReader {
    
    public static String[][] genericCSVread(String path, CSVArgs args){
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
            String[][] data = new String[(int)quantasLinhas(path)][args.getImportantColumns().size()];
            int j = 0;
            while(s.hasNextLine()){
                String[] importantArgs = new String[args.getImportantColumns().size()];
                String line = s.nextLine();
                try(Scanner lineScanner = new Scanner(line)){
                    int i = 0;
                    lineScanner.useDelimiter(";");
                    for(String arg : columns){
                        if(args.getImportantColumns().contains(arg)){
                            importantArgs[i++] = lineScanner.next();
                        }
                        else{
                            lineScanner.next();
                        }
                    }
                }
                data[j++] = importantArgs;
            }

            return data;
        
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static List<CandidatoVotacao> readCandidatosCSV(String path){
        String[] importantValues = {"CD_CARGO", "CD_SITUACAO_CANDIDATO_TOT", "NR_CANDIDATO", "NM_URNA_CANDIDATO",
                                    "NR_PARTIDO", "SG_PARTIDO", "NR_FEDERACAO", "DT_NASCIMENTO", "CD_SIR_TOT_TURNO",
                                    "CD_GENERO", "NM_TIPO_DESTINACAO_VOTOS", "CD_SITUACAO_CANDIDATO_TOT"};
        CSVArgs args = new CSVArgs(importantValues);

        String[][] data = genericCSVread(path, args);

        List<CandidatoVotacao> candidatos = new LinkedList<CandidatoVotacao>();
        for(String[] candidato : data){
            candidatos.add(CSVParser.parseCandidato(candidato));
        }

        return candidatos;
    }

    public static List<LegendaVotacao> readPartidosCSV(String path){
        String[] importantValues = {"CD_CARGO", "NR_VOTAVEL", "QT_VOTOS"};
        CSVArgs args = new CSVArgs(importantValues);

        String[][] data = genericCSVread(path, args);

        List<LegendaVotacao> votacao = new LinkedList<LegendaVotacao>();
        for(String[] legenda : data){
            votacao.add(CSVParser.parseVotacao(legenda));
        }

        return votacao;
    }

    public static int quantasLinhas(String fileName) {
      Path path = Paths.get(fileName);

      int lines = 0;
      try {
          lines = (int)Files.lines(path).count();
      } catch (IOException e) {
          e.printStackTrace();
      }

      return lines;

  }
}