package com.srjheamtucozz.ufes.poo.t1.stc.models;

import java.util.HashMap;
import java.util.Map;

public class Eleicao {
    private Map<String, Federacao> federacoes;
    private Map<String, Partido> partidos;
    private Map<String, Candidato> candidatos;
    private Votacao votacao;

    public Eleicao(Map<String, Federacao> federacoes, Map<String, Partido> partidos,
            Map<String, Candidato> candidatos) {
        this.federacoes = new HashMap<>(federacoes);
        this.partidos = new HashMap<>(partidos);
        this.candidatos = new HashMap<>(candidatos);
    }

    public Eleicao(Map<String, Federacao> federacoes, Map<String, Partido> partidos, Map<String, Candidato> candidatos,
            Votacao votacao) {
        this(federacoes, partidos, candidatos);
        
        this.votacao = votacao;
    }

    public Map<String, Federacao> getFederacoes() {
        return federacoes;
    }

    public Map<String, Partido> getPartidos() {
        return partidos;
    }

    public Map<String, Candidato> getCandidatos() {
        return candidatos;
    }

    public Votacao getVotacao() {
        return votacao;
    }
}
