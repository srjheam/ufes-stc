import java.time.LocalDate;

public class Candidato {
    private String numero;
    private Partido partido;
    private String nome;
    private String nomeUrna;
    private LocalDate dataNascimento;
    private boolean sexo; // internamente o sexo sim é masculino e não é feminino
    private Cargo cargo;

    public Candidato(String numero, Partido partido, String nome, String nomeUrna, LocalDate dataNascimento, boolean sexo, Cargo cargo) {
        this.numero = numero;
        this.partido = partido;
        this.nome = nome;
        this.nomeUrna = nomeUrna;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.cargo = cargo;
    }
}
