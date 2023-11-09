package com.srjheamtucozz.ufes.poo.t1.stc.models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EleicaoStats {
    private Eleicao eleicao;
    private int numeroVagas;
    private List<Candidato> candidatosOrdenados;
    private List<Candidato> candidatosEleitos;
    private Map<Partido, Candidato> partidos;
    private Map<Integer, Integer> eleitosFaixaEtaria;
    private Map<Integer, Integer> eleitosSexo;
    private int totalVotos;
    private int totalVotosNominais;
    private int totalVotosLegenda;


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

    public EleicaoStats(Eleicao eleicao) {
        this.eleicao = eleicao;
    }

    public void compute() {
    
        Candidato[] candidatos = this.eleicao.getCandidatos().values().toArray();
        // Favor ordenar o vetor aqui
        candidatosEleitos = computeCandidatosEleitos(candidatos);
        partidos = computePartidos(candidatos);
        eleitosFaixaEtaria = computeFaixaEtaria(candidatos);
        eleitosSexo = computeSexo(candidatos);
        totalVotos = computeVotos(candidatos);
        totalVotosNominais = computeNominais(candidatos);
        totalVotosLegenda = computeLegenda(candidatos);

    }

    private List<Candidato> computeCandidatosEleitos(Candidato[] candidatos){
        
        List<Candidato> eleitos = new LinkedList<>();

        for(Candidato c : candidatos){
            if(c.foiEleito()){
                eleitos.add(c);
            }
        }
        return eleitos;
    }

    private Map<Partido, Candidato> computePartidos(Candidato[] candidatos){
        Map<Partido, Candidato> partidos = new HashMap<>();
        
        for(Candidato c : candidatos){
            partidos.put(c.getNumeroPartido(), c);
        }

        return partidos;
    }

    private void incrementMap(Map map, int idx){
        int value = map.get(idx);
        map.replace(idx, value + 1);
    }

    private Map<Integer, Integer> computeFaixaEtaria(Candidato[] candidatos){
        Map<Integer, Integer> faixas = new HashMap<>();
        for(Candidato c : candidatos){
            if(getIdade(c.getDataNascimento()) < 30)
                incrementMap(faixas, 0);
            else if(getIdade(c.getDataNascimento()) < 40)
                incrementMap(faixas, 1);
            else if(getIdade(c.getDataNascimento()) < 50)
                incrementMap(faixas, 2);
            else if(getIdade(c.getDataNascimento()) < 60)
                incrementMap(faixas, 3);
            else
                incrementMap(faixas, 4);
        }

        return faixas;
    }

    private Map<Integer, Integer> computeSexo(Candidato[] candidatos){
        Map<Integer, Integer> sexos = new HashMap<>();
        for(Candidato c : candidatos){
            if(c.isSexo())
                incrementMap(sexos, 1);
            else
                incrementMap(sexos, 0);
        }

        return sexos;
    }

    private int computeVotos(Candidato[] candidatos){
        int votos = 0;
        for(Candidato c : candidatos){
            votos += c.quantosVotos;
        }

        return votos;
    }

    private int computeNominais(Candidato[] candidatos){
        int votos = 0;
        for(Candidato c : candidatos){
            votos += c.quatosVotosNominais;
        }

        return votos;
    }

    private int computeLegenda(Candidato[] candidatos){
        int votos = 0;
        for(Candidato c : candidatos){
            votos += c.quatosVotosLegenda;
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

    private void printNumeroVagas() {
        System.out.println("Numero de vagas: " + numeroVagas);
    }

    private void printCandidatosEleitos() {
        //TODO: trocar para diferentes tipos de eleição
        // Exemplo: "Deputados estatuais eleitos:" ou "Deputados federais eleitos:"
        System.out.println("Candidatos eleitos:");
        int i = 1;
        for (Candidato candidato : candidatosEleitos) {
            System.out.println(i++ + " - " candidato.getNomeUrna() + " (" + candidato.getPartido() + ", " + candidato.getQtVotos + " votos)");
        }
    }

    private void printCandidatosMaisVotados() {

        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
        int i = 1;
        for (Candidato candidato : candidatosMaisVotados) {
            System.out.println(i++ + " - " candidato.getNomeUrna() + " (" + candidato.getPartido() + ", " + candidato.getQtVotos + " votos)");
        }
    }

    private void printCandidatosNaoEleitos() {
        System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");
        System.out.println("(com sua posição no ranking de mais votados)");
        //TODO: imprimir antes a posição dele no array de mais votados
        for (Candidato candidato : candidatosNaoEleitos) {
            System.out.println(candidato.getNomeUrna() + " (" + candidato.getPartido() + ", " + candidato.getQtVotos + " votos)");
        }
    }

    private void printCandidatosEleitosSistemaProporcional() {
        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:");
        System.out.println("(com sua posição no ranking de mais votados)");
        for(Candidato candidato : candidatosEleitosSistemaProporcional) {
            System.out.println(candidato.getNomeUrna() + " (" + candidato.getPartido() + ", " + candidato.getQtVotos + " votos)");
        }
    }

    private void printVotacaoPartidos(){
        System.out.println("Votação dos partidos e número de candidatos eleitos:");
        // TODO: Tem que manter conta de quantos são nominais e quantos sao de legenda.
        for(Partido partido : partidos) {
            System.out.println(partido.getNome() + " - " + partido.getNumero() + ", " + partido.getVotosNominais() + " votos (" + partido.getQtEleitos() + " candidatos eleitos)");
        }
    }

    private void printPrimeiroUltimoColocadoPartido() {
        System.out.println("Primeiro e último colocados de cada partido:");
        for(Partido partido : partidosComCandidatosEleitos) {
            System.out.println(partido.getNome() + " - " + partido.getNumero() + ", " +
                                partido.getPrimeiroColocado().getNomeUrna() + " (" + partido.getPrimeiroColocado().getNumero() + ", " + partido.getPrimeiroColocado().getQtVotos() + " votos)" + " / " 
                                partido.getUltimoColocado().getNomeUrna() +  " (" + partido.getUltimoColocado().getNumero() + ", " + partido.getUltimoColocado().getQtVotos() + " votos)");
                            
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
        System.out.println("Total de votos válidos: " + totalVotos);
        System.out.println("Total de votos nominais: " + totalVotosNominais);
        System.out.println("Total de votos de legenda: " + totalVotosLegenda);
    }



}
