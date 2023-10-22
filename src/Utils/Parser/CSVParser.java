import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CSVParser {
    public static CandidatoVotacao parseCandidato(String[] args){
        Cargo cargo = parseCargo(args[0]);
        boolean deferido = parseDeferido(args[1]);
        String numero = args[2];
        String nomeUrna = args[3];
        Partido partido = parsePartido(args[4], args[5], args[6]);
        LocalDate dataNascimento = parseData(args[7]);
        boolean eleito = parseEleito(args[8]);
        boolean sexo = parseSexo(args[9]);
        boolean destinacao = parseDestinacao(args[10]);
        int numeroVotos = 0;

        return new CandidatoVotacao(cargo, deferido, numero, nomeUrna, partido, dataNascimento, sexo, eleito, destinacao, numeroVotos);
    }

    public static LegendaVotacao parseVotacao(String arg){
        
        //TODO: PARSER DO ARQUIVO DE PARTIDOS

    }

    public static Partido parsePartido(String numero, String sigla, String federacao){
        return new Partido(numero, sigla, federacao);
    }

    public static LocalDate parseData(String arg){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(arg, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Valid format is: dd/MM/yyyy.");
            System.exit(1);
        }

        return null;
    }

    public static boolean parseSexo(String arg){
        if (arg.equals("2")) {
            return true;
        }
        else if (arg.equals("4")) {
            return false;
        }
        else {
            throw new IllegalArgumentException("Invalid <opcao_de_cargo>. Valid options are: 2, 4.");
        }
    }

    public static Cargo parseCargo(String arg){
        if (arg.equals("7")) {
            return Cargo.DEPUTADO_ESTADUAL;
        }
        else if (arg.equals("6")) {
            return Cargo.DEPUTADO_FEDERAL;
        }
        else {
            throw new IllegalArgumentException("Invalid <opcao_de_cargo>. Valid options are: 6, 7");
        }
    }

    public static boolean parseEleito(String arg){
        return arg.equals("2") || arg.equals("3");
    }

    public static boolean parseDestinacao(String arg){
        return arg.equals("Válido (legenda)");
    }

    public static boolean parseDeferido(String arg){
        return arg.equals("2") || arg.equals("16");
    }
}
