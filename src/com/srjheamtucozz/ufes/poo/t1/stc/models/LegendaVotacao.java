package com.srjheamtucozz.ufes.poo.t1.stc.models;

import java.util.NavigableSet;

public class LegendaVotacao {
    private Partido legenda;
    private int numeroVotosLegenda;
    private NavigableSet<CandidatoVotacao> candidatos;
    
    public LegendaVotacao(Partido legenda, NavigableSet<CandidatoVotacao> candidatos) {
        this.legenda = legenda;
        this.candidatos = candidatos;
    }

    public LegendaVotacao(Partido legenda, int numeroVotosLegenda, NavigableSet<CandidatoVotacao> candidatos) {
        this(legenda, candidatos);

        this.numeroVotosLegenda = numeroVotosLegenda;
    }

    public Partido getLegenda() {
        return legenda;
    }
    
    public int getNumeroVotosLegenda() {
        return numeroVotosLegenda;
    }

    public NavigableSet<CandidatoVotacao> getCandidatos() {
        return candidatos;
    }

    public void incNumeroVotosLegenda(int value) {
        this.numeroVotosLegenda += value;
    }
}
