package com.srjheamtucozz.ufes.poo.t1.stc.models;

import java.util.NavigableSet;
import java.util.TreeSet;

public class LegendaVotacao {
    private Partido legenda;
    private int numeroCandidatosEleitos;
    private int numeroVotosTotais;
    private int numeroVotosNominais;
    private int numeroVotosLegenda;
    private NavigableSet<CandidatoVotacao> candidatos;
    
    public LegendaVotacao(Partido legenda, NavigableSet<CandidatoVotacao> candidatos) {
        this.legenda = legenda;
        this.candidatos = candidatos;
    }

    public LegendaVotacao(Partido legenda, int numeroVotosLegenda, int numeroVotosNominais, NavigableSet<CandidatoVotacao> candidatos) {
        this(legenda, candidatos);

        this.numeroVotosLegenda = numeroVotosLegenda;
        this.numeroVotosNominais = numeroVotosNominais;
        this.numeroVotosTotais = numeroVotosLegenda + numeroVotosNominais;
        this.numeroCandidatosEleitos = (int) candidatos.stream().filter(CandidatoVotacao::isEleito).count();
    }

    public Partido getLegenda() {
        return legenda;
    }
    
    public int getNumeroVotosLegenda() {
        return numeroVotosLegenda;
    }

    public NavigableSet<CandidatoVotacao> getCandidatos() {
        return new TreeSet<>(candidatos);
    }

    public int getNumeroCandidatosEleitos() {
        return numeroCandidatosEleitos;
    }

    public int getNumeroVotosTotais() {
        return numeroVotosTotais;
    }

    public int getNumeroVotosNominais() {
        return numeroVotosNominais;
    }
}
