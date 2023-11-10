package com.srjheamtucozz.ufes.poo.t1.stc.models;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.srjheamtucozz.ufes.poo.t1.stc.enums.Cargo;

public class EleicaoStats {
    private Eleicao eleicao;
    private Cargo cargo;

    private int numeroVagas;
    private List<CandidatoVotacao> candidatosEleitos;

    private Map<Integer, Integer> eleitosFaixaEtaria;
    private Map<Integer, Integer> eleitosSexo;

        // Seu programa deve ler os dados dos dois arquivos descritos acima (cujos nomes serão passados pela linha de
        // comando) e gerar diversos relatórios na saída padrão:

        // 1. Número de vagas (= número de eleitos);
        // 2. Candidatos eleitos (nome completo e na urna), indicado partido e número de votos nominais;
        // 3. Candidatos mais votados dentro do número de vagas;
        // 4. Candidatos não eleitos e que seriam eleitos se a votação fosse majoritária;
        // 5. Candidatos eleitos no sistema proporcional vigente, e que não seriam eleitos se a votação fosse
        // majoritária, isto é, pelo número de votos apenas que um candidato recebe diretamente;
        // 6. Votos totalizados por partido e número de candidatos eleitos;
        // 7. Primeiro e último colocados de cada partido (com nome da urna, número do candidato e total de votos
        // nominais). Partidos que não possuírem candidatos com um número positivo de votos válidos devem ser
        // ignorados;
        // 8. Distribuição de eleitos por faixa etária, considerando a idade do candidato no dia da eleição;
        // 9. Distribuição de eleitos por sexo;
        // 10. Total de votos, total de votos nominais e total de votos de legenda.

    public EleicaoStats(Eleicao eleicao, Cargo cargo) {
        this.eleicao = eleicao;
        this.cargo = cargo;

        this.compute();
    }

    private void compute() {
        this.candidatosEleitos = computeCandidatosEleitos(this.eleicao);
        this.numeroVagas = candidatosEleitos.size();

        this.eleitosFaixaEtaria = computeFaixaEtaria(this.eleicao);
        this.eleitosSexo = computeSexo(this.eleicao);
    }

    private static List<CandidatoVotacao> computeCandidatosEleitos(Eleicao eleicao){
        return eleicao.getVotacao().getCandidatosSorted()
            .stream()
            .filter(CandidatoVotacao::isEleito)
            .collect(Collectors.toList());
    }

    // private Map<Partido, Candidato> computePartidos(Candidato[] candidatos){
    //     Map<Partido, Candidato> partidos = new HashMap<>();
        
    //     for(CandidatoVotacao c : this.eleicao.getVotacao().getCandidatosSorted()){
    //         partidos.put(this.eleicao.getPartidos().get(c.getCandidato().getNumeroPartido()), c.getCandidato());
    //     }

    //     return partidos;
    // }

    private void incrementMap(Map<Integer, Integer> map, int idx){
        map.merge((Integer)idx, (Integer)1, (a, b) -> a + 1); // map.put(idx, map.get(idx) + 1);
    }

    private Map<Integer, Integer> computeFaixaEtaria(Eleicao eleicao){
        Map<Integer, Integer> faixas = new HashMap<>();
        for(Candidato c : this.eleicao.getCandidatos().values()){
            if(c.getIdade() < 30)
                incrementMap(faixas, 0);
            else if(c.getIdade() < 40)
                incrementMap(faixas, 1);
            else if(c.getIdade() < 50)
                incrementMap(faixas, 2);
            else if(c.getIdade() < 60)
                incrementMap(faixas, 3);
            else
                incrementMap(faixas, 4);
        }

        return faixas;
    }

    private Map<Integer, Integer> computeSexo(Eleicao eleicao){
        Map<Integer, Integer> sexos = new HashMap<>();
        for(Candidato c : this.eleicao.getCandidatos().values()){
            if(c.isSexo())
                incrementMap(sexos, 1);
            else
                incrementMap(sexos, 0);
        }

        return sexos;
    }

    private int computeVotos(){
        int votos = 0;
        for (CandidatoVotacao c : this.eleicao.getVotacao().getCandidatosSorted()) {
            votos += c.getNumeroVotos();
        }

        return votos;
    }

    private int computeNominais(){
        int votos = 0;
        for(CandidatoVotacao c : this.eleicao.getVotacao().getCandidatosSorted()){
            votos += c.getNumeroVotos();
        }

        return votos;
    }

    private int computeLegenda(){
        int votos = 0;
        for(CandidatoVotacao c : this.eleicao.getVotacao().getCandidatosSorted()){
            votos += c.getNumeroVotos();
        }

        return votos;
    }

    public void print() {
        
        printNumeroVagas();
        printCandidatosEleitos();
        printCandidatosMaisVotados();
        printCandidatosNaoEleitos();
        printCandidatosEleitosSistemaProporcional();
        printVotacaoPartidos();
        printPrimeiroUltimoColocadoPartido();
        printDistribuicaoEleitosFaixaEtaria();
        printDistribuicaoEleitosSexo();
        printTotalVotos();

    }
    
    private static String linhaToString(int i, String content) {
        return i
            + " - "
            + content;
    }

    private String candidatoToString(CandidatoVotacao candidatoVotacao) {
        return (candidatoVotacao.isDestinacaoLegenda() ? "*" : "")
                + candidatoVotacao.getCandidato().getNomeUrna()
                + " ("
                + this.eleicao.getPartidos().get(candidatoVotacao.getCandidato().getNumeroPartido()).getSigla()
                + ", "
                + candidatoVotacao.getNumeroVotos()
                + " voto"
                + (candidatoVotacao.getNumeroVotos() > 1 ? "s" : "")
                + ")";
    }

    private static String partidoToString(LegendaVotacao legendaVotacao) {
        return legendaVotacao.getLegenda().getSigla()
                + " - "
                + legendaVotacao.getLegenda().getNumero()
                + ", "
                + legendaVotacao.getNumeroVotosTotais()
                + " voto"
                + (legendaVotacao.getNumeroVotosTotais() > 1 ? "s" : "")
                + " ("
                + legendaVotacao.getNumeroVotosNominais()
                + " "
                + (legendaVotacao.getNumeroVotosNominais() > 1 ? "nominais" : "nominal")
                + " e "
                + legendaVotacao.getNumeroVotosLegenda()
                + " de legenda"
                + "), "
                + legendaVotacao.getNumeroCandidatosEleitos()
                + " candidato"
                + (legendaVotacao.getNumeroCandidatosEleitos() > 1 ? "s" : "")
                + " eleito"
                + (legendaVotacao.getNumeroCandidatosEleitos() > 1 ? "s" : "");
    }

    private static String partidoPrimeiroUltimoToString(LegendaVotacao legendaVotacao) {
        return legendaVotacao.getLegenda().getSigla()
                + " - "
                + legendaVotacao.getLegenda().getNumero()
                + ", "
                + legendaVotacao.getCandidatos().first().getCandidato().getNomeUrna()
                + " ("
                + legendaVotacao.getCandidatos().first().getCandidato().getNumero()
                + ", "
                + legendaVotacao.getCandidatos().first().getNumeroVotos()
                + " voto"
                + (legendaVotacao.getCandidatos().first().getNumeroVotos() > 1 ? "s" : "")
                + ") / "
                + legendaVotacao.getCandidatos().last().getCandidato().getNomeUrna()
                + " ("
                + legendaVotacao.getCandidatos().last().getCandidato().getNumero()
                + ", "
                + legendaVotacao.getCandidatos().last().getNumeroVotos()
                + " voto"
                + (legendaVotacao.getCandidatos().last().getNumeroVotos() > 1 ? "s" : "")
                + ")";
    }

    private void printNumeroVagas() {
        System.out.println("Numero de vagas: " + numeroVagas);
        System.out.println();
    }

    private void printCandidatosEleitos() {
        System.out.println("Deputados " + (this.cargo == Cargo.DEPUTADO_ESTADUAL ? "estaduais" : "federais") + " eleitos:");
        int i = 1;
        for (CandidatoVotacao candidatoVotacao : this.candidatosEleitos) {
            System.out.println(linhaToString(i++, this.candidatoToString(candidatoVotacao)));
        }
    }

    private void printCandidatosMaisVotados() {

        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
        int i = 1;
        for (CandidatoVotacao candidatoVotacao : this.eleicao.getVotacao().getCandidatosSorted()
                                                    .stream()
                                                    .limit(this.numeroVagas)
                                                    .collect(Collectors.toList())) {
            System.out.println(linhaToString(i++, this.candidatoToString(candidatoVotacao)));
        }
    }

    private void printCandidatosNaoEleitos() {
        System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");
        System.out.println("(com sua posição no ranking de mais votados)");
        
        int i = 1;
        for (CandidatoVotacao candidatoVotacao : this.eleicao.getVotacao().getCandidatosSorted()
                                                    .stream()
                                                    .limit(this.numeroVagas)
                                                    .collect(Collectors.toList())) {
            if (candidatoVotacao.isEleito())
                continue;

            System.out.println(linhaToString(i++, this.candidatoToString(candidatoVotacao)));            
        }
    }

    private void printCandidatosEleitosSistemaProporcional() {
        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:");
        System.out.println("(com sua posição no ranking de mais votados)");
        
        int i = 1;
        for (CandidatoVotacao candidatoVotacao : this.eleicao.getVotacao().getCandidatosSorted()
                                                    .stream()
                                                    .skip(this.numeroVagas)
                                                    .collect(Collectors.toList())) {
            if (!candidatoVotacao.isEleito())
                continue;

            System.out.println(linhaToString(i++, this.candidatoToString(candidatoVotacao)));            
        }
    }

    private void printVotacaoPartidos(){
        System.out.println("Votação dos partidos e número de candidatos eleitos:");
        int i = 1;
        for(LegendaVotacao legendaVotacao : this.eleicao.getVotacao().getLegendasSorted()) {
            System.out.println(linhaToString(i++, partidoToString(legendaVotacao)));
        }
    }

    private void printPrimeiroUltimoColocadoPartido() {
        System.out.println("Primeiro e último colocados de cada partido:");

        NavigableSet<LegendaVotacao> partidosPrimeiroUltimo = new TreeSet<LegendaVotacao>(new Comparator<LegendaVotacao>() {
            @Override
            public int compare(LegendaVotacao p1, LegendaVotacao p2) {
                int r = p2.getCandidatos().first().getNumeroVotos() - p1.getCandidatos().first().getNumeroVotos();
                if (r != 0)
                    return r;

                int r2 = p2.getCandidatos().first().getCandidato().getIdade() - p1.getCandidatos().first().getCandidato().getIdade();
                if (r2 != 0)
                    return r2;

                return Integer.valueOf(p1.getLegenda().getNumero()) - Integer.valueOf(p2.getLegenda().getNumero());
            }
        });
        partidosPrimeiroUltimo.addAll(this.eleicao.getVotacao().getLegendasSorted());

        int i = 1;
        for(LegendaVotacao partido : partidosPrimeiroUltimo) {
            System.out.println(linhaToString(i++, partidoPrimeiroUltimoToString(partido)));
        }
    }

    private void printDistribuicaoEleitosFaixaEtaria() {
        System.out.println("Eleitos, por faixa etária (na data da eleição)");
        System.out.println("      Idade < 30: " + eleitosFaixaEtaria.get(0) + " (" + (eleitosFaixaEtaria.get(0) * 100 / numeroVagas) + "%)");
        System.out.println("30 <= Idade < 40: " + eleitosFaixaEtaria.get(1) + " (" + (eleitosFaixaEtaria.get(1) * 100 / numeroVagas) + "%)");
        System.out.println("40 <= Idade < 50: " + eleitosFaixaEtaria.get(2) + " (" + (eleitosFaixaEtaria.get(2) * 100 / numeroVagas) + "%)");
        System.out.println("50 <= Idade < 60: " + eleitosFaixaEtaria.get(3) + " (" + (eleitosFaixaEtaria.get(3) * 100 / numeroVagas) + "%)");
        System.out.println("60 <= Idade     : " + eleitosFaixaEtaria.get(4) + " (" + (eleitosFaixaEtaria.get(4) * 100 / numeroVagas) + "%)");
    }

    private void printDistribuicaoEleitosSexo() {
        System.out.println("Eleitos, por gênero:");
        System.out.println("Feminino: " + eleitosSexo.get(0) + " (" + (eleitosSexo.get(0) * 100 / numeroVagas) + "%)");
        System.out.println("Masculino: " + eleitosSexo.get(1) + " (" + (eleitosSexo.get(1) * 100 / numeroVagas) + "%)");
    }

    private void printTotalVotos() {
        System.out.println("Total de votos válidos: " + computeVotos());
        System.out.println("Total de votos nominais: " + computeNominais());
        System.out.println("Total de votos de legenda: " + computeLegenda());
    }
}
