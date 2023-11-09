package com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.raw;

import java.util.List;
import java.util.LinkedList;

public class RawEleicao {
    List<RawCandidato> candidatos;
    List<RawVotacao> votacoes;

    public RawEleicao(List<RawCandidato> candidatos, List<RawVotacao> votacoes) {
        this.candidatos = candidatos;
        this.votacoes = votacoes;
    }

    public List<RawCandidato> getCandidatos() {
        return new LinkedList<>(candidatos);
    }

    public List<RawVotacao> getVotacoes() {
        return new LinkedList<>(votacoes);
    }
}
