package com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.raw;

import java.util.List;

public class RawEleicao {
    List<RawCandidato> candidatos;
    List<RawVotacao> votacoes;

    public RawEleicao(List<RawCandidato> candidatos, List<RawVotacao> votacoes) {
        this.candidatos = candidatos;
        this.votacoes = votacoes;
    }

    public List<RawCandidato> getCandidatos() {
        return candidatos;
    }

    public List<RawVotacao> getVotacoes() {
        return votacoes;
    }
}
