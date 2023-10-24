package com.srjheamtucozz.ufes.poo.t1.stc.models;

import java.util.Map;
import java.util.NavigableSet;

public class Votacao {
    Map<String, LegendaVotacao> legendas;
    Map<String, CandidatoVotacao> candidatos;
    NavigableSet<LegendaVotacao> legendasSorted;
    NavigableSet<CandidatoVotacao> candidatosSorted;

    public Votacao(Map<String, LegendaVotacao> legendas, Map<String, CandidatoVotacao> candidatos) {
        this.legendas = legendas;
        this.candidatos = candidatos;
    }

    public Votacao(Map<String, LegendaVotacao> legendas, Map<String, CandidatoVotacao> candidatos,
            NavigableSet<LegendaVotacao> legendasSorted, NavigableSet<CandidatoVotacao> candidatosSorted) {
        this(legendas, candidatos);

        this.legendasSorted = legendasSorted;
        this.candidatosSorted = candidatosSorted;
    }

    public Map<String, LegendaVotacao> getLegendas() {
        return legendas;
    }
    public Map<String, CandidatoVotacao> getCandidatos() {
        return candidatos;
    }    
}
