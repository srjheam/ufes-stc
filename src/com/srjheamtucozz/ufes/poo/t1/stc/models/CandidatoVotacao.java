package com.srjheamtucozz.ufes.poo.t1.stc.models;

import java.time.LocalDate;

public class CandidatoVotacao {
    private boolean eleito;
    private boolean destinacao;
    private int numeroVotos;
    private Candidato candidato;    

    public CandidatoVotacao(boolean eleito, boolean destinacao, int numeroVotos, Candidato candidato) {
        this.eleito = eleito;
        this.destinacao = destinacao;
        this.numeroVotos = numeroVotos;
        this.candidato = candidato;
    }
}
