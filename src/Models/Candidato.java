import java.time.LocalDate;

public class Candidato {
    private Cargo cargo;
    private boolean deferido;
    private String numero;
    private String nomeUrna;
    private Partido partido;
    private LocalDate dataNascimento;
    private boolean sexo; // internamente o sexo sim é masculino e não é feminino

    public Candidato(Cargo cargo, boolean deferido, String numero, String nomeUrna, Partido partido, LocalDate dataNascimento, boolean sexo) {
        this.cargo = cargo;
        this.deferido = deferido;
        this.numero = numero;
        this.nomeUrna = nomeUrna;
        this.partido = partido;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
    }
}
