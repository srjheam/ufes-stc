package com.srjheamtucozz.ufes.poo.t1.stc.models;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

    private NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));

    public EleicaoStats(Eleicao eleicao, Cargo cargo) {
        Locale.setDefault(new Locale("pt", "BR"));

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
        for(CandidatoVotacao cand : this.candidatosEleitos){
            Candidato c = cand.getCandidato();
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
        for(CandidatoVotacao cand : this.candidatosEleitos){
            Candidato c = cand.getCandidato();
            if(c.isSexo())
                incrementMap(sexos, 1);
            else
                incrementMap(sexos, 0);
        }

        return sexos;
    }

    private int computeNominais(){
        int votos = 0;
        for(LegendaVotacao c : this.eleicao.getVotacao().getLegendasSorted()){
            votos += c.getNumeroVotosNominais();
        }

        return votos;
    }

    private int computeLegenda(){
        int votos = 0;
        for(LegendaVotacao c : this.eleicao.getVotacao().getLegendasSorted()){
            votos += c.getNumeroVotosLegenda();
        }

        return votos;
    }

    public void print() {
        
        printNumeroVagas();
        System.out.println();

        printCandidatosEleitos();
        System.out.println();

        printCandidatosMaisVotados();
        System.out.println();

        printCandidatosNaoEleitos();
        System.out.println();

        printCandidatosEleitosSistemaProporcional();
        System.out.println();

        printVotacaoPartidos();
        System.out.println();

        printPrimeiroUltimoColocadoPartido();
        System.out.println();

        printDistribuicaoEleitosFaixaEtaria();
        System.out.println();

        printDistribuicaoEleitosSexo();
        System.out.println();

        printTotalVotos();
        System.out.println();
        System.out.println();
    }
    
    private static String linhaToString(int i, String content) {
        return i
            + " - "
            + content;
    }

    private String candidatoToString(CandidatoVotacao candidatoVotacao) {
        return (!candidatoVotacao.getCandidato().getNumeroFederacao().equals("-1") ? "*" : "")
                + candidatoVotacao.getCandidato().getNomeUrna()
                + " ("
                + this.eleicao.getPartidos().get(candidatoVotacao.getCandidato().getNumeroPartido()).getSigla()
                + ", "
                + this.nf.format(candidatoVotacao.getNumeroVotos())
                + " voto"
                + (candidatoVotacao.getNumeroVotos() > 1 ? "s" : "")
                + ")";
    }

    private String partidoToString(LegendaVotacao legendaVotacao) {
        return legendaVotacao.getLegenda().getSigla()
                + " - "
                + legendaVotacao.getLegenda().getNumero()
                + ", "
                + this.nf.format(legendaVotacao.getNumeroVotosTotais())
                + " voto"
                + (legendaVotacao.getNumeroVotosTotais() > 1 ? "s" : "")
                + " ("
                + this.nf.format(legendaVotacao.getNumeroVotosNominais())
                + " "
                + (legendaVotacao.getNumeroVotosNominais() > 1 ? "nominais" : "nominal")
                + " e "
                + this.nf.format(legendaVotacao.getNumeroVotosLegenda())
                + " de legenda"
                + "), "
                + this.nf.format(legendaVotacao.getNumeroCandidatosEleitos())
                + " candidato"
                + (legendaVotacao.getNumeroCandidatosEleitos() > 1 ? "s" : "")
                + " eleito"
                + (legendaVotacao.getNumeroCandidatosEleitos() > 1 ? "s" : "");
    }

    private String partidoPrimeiroUltimoToString(LegendaVotacao legendaVotacao) {
        return legendaVotacao.getLegenda().getSigla()
                + " - "
                + legendaVotacao.getLegenda().getNumero()
                + ", "
                + legendaVotacao.getCandidatos().first().getCandidato().getNomeUrna()
                + " ("
                + legendaVotacao.getCandidatos().first().getCandidato().getNumero()
                + ", "
                + this.nf.format(legendaVotacao.getCandidatos().first().getNumeroVotos())
                + " voto"
                + (legendaVotacao.getCandidatos().first().getNumeroVotos() > 1 ? "s" : "")
                + ") / "
                + legendaVotacao.getCandidatos().last().getCandidato().getNomeUrna()
                + " ("
                + legendaVotacao.getCandidatos().last().getCandidato().getNumero()
                + ", "
                + this.nf.format(legendaVotacao.getCandidatos().last().getNumeroVotos())
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
        
        int i = 0;
        for (CandidatoVotacao candidatoVotacao : this.eleicao.getVotacao().getCandidatosSorted()
                                                    .stream()
                                                    .limit(this.numeroVagas)
                                                    .collect(Collectors.toList())) {
            i++;
            if (candidatoVotacao.isEleito())
                continue;

            System.out.println(linhaToString(i, this.candidatoToString(candidatoVotacao)));            
        }
    }

    private void printCandidatosEleitosSistemaProporcional() {
        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:");
        System.out.println("(com sua posição no ranking de mais votados)");
        
        int i = this.numeroVagas;
        for (CandidatoVotacao candidatoVotacao : this.eleicao.getVotacao().getCandidatosSorted()
                                                    .stream()
                                                    .skip(this.numeroVagas)
                                                    .collect(Collectors.toList())) {
            i++;
            if (!candidatoVotacao.isEleito())
                continue;

            System.out.println(linhaToString(i, this.candidatoToString(candidatoVotacao)));            
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
        System.out.println("      Idade < 30: " + nf.format(eleitosFaixaEtaria.getOrDefault(0, 0)) + " (" + String.format("%.2f", (eleitosFaixaEtaria.getOrDefault(0, 0) * 100 / (double)numeroVagas)) + "%)");
        System.out.println("30 <= Idade < 40: " + nf.format(eleitosFaixaEtaria.getOrDefault(1, 0)) + " (" + String.format("%.2f", (eleitosFaixaEtaria.getOrDefault(1, 0) * 100 / (double)numeroVagas)) + "%)");
        System.out.println("40 <= Idade < 50: " + nf.format(eleitosFaixaEtaria.getOrDefault(2, 0)) + " (" + String.format("%.2f", (eleitosFaixaEtaria.getOrDefault(2, 0) * 100 / (double)numeroVagas)) + "%)");
        System.out.println("50 <= Idade < 60: " + nf.format(eleitosFaixaEtaria.getOrDefault(3, 0)) + " (" + String.format("%.2f", (eleitosFaixaEtaria.getOrDefault(3, 0) * 100 / (double)numeroVagas)) + "%)");
        System.out.println("60 <= Idade     : " + nf.format(eleitosFaixaEtaria.getOrDefault(4, 0)) + " (" + String.format("%.2f", (eleitosFaixaEtaria.getOrDefault(4, 0) * 100 / (double)numeroVagas)) + "%)");
    }

    private void printDistribuicaoEleitosSexo() {
        System.out.println("Eleitos, por gênero:");
        System.out.println("Feminino:  " + nf.format(eleitosSexo.get(0)) + " (" + String.format("%.2f", (eleitosSexo.get(0) * 100 / (double)numeroVagas)) + "%)");
        System.out.println("Masculino: " + nf.format(eleitosSexo.get(1)) + " (" + String.format("%.2f", (eleitosSexo.get(1) * 100 / (double)numeroVagas)) + "%)");
    }

    private void printTotalVotos() {
        int nominais = computeNominais();
        int legenda = computeLegenda();
        int totais = nominais + legenda;

        System.out.println("Total de votos válidos:    "
            + nf.format(totais));
        System.out.println("Total de votos nominais:   "
            + nf.format(nominais)
            + " ("
            + String.format("%.2f", nominais * 100 / (double)totais)
            +"%)");
        System.out.println("Total de votos de legenda: "
            + nf.format(legenda)
            + " ("
            + String.format("%.2f", (legenda * 100 / (double)totais))
            + "%)");
    }
}
