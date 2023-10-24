package com.srjheamtucozz.ufes.poo.t1.stc.models;

public class CandidatoVotacao {
    private boolean eleito;
    private boolean destinacaoLegenda;
    private int numeroVotos;
    private Candidato candidato;    

    public CandidatoVotacao(boolean eleito, boolean destinacaoLegenda, Candidato candidato) {
        this.eleito = eleito;
        this.destinacaoLegenda = destinacaoLegenda;
        this.candidato = candidato;
    }

    public boolean isEleito() {
        return this.eleito;
    }

    public boolean isDestinacaoLegenda() {
        return this.destinacaoLegenda;
    }

    public int getNumeroVotos() {
        return this.numeroVotos;
    }

    public Candidato getCandidato() {
        return this.candidato;
    }

    public void incNumeroVotos(int value) {
        this.numeroVotos += value;
    }
}
