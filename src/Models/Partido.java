import java.utils.Set;

public class Partido {
    private String numero;
    private String sigla;
    private String numeroFederacao; // número da federação; null se não tem federacao
    private Set<Candidato> candidatos;
}
