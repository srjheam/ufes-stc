package com.srjheamtucozz.ufes.poo.t1.stc.models;

import java.util.Set;

public class Partido {
    private String numero;
    private String sigla;
    private String numeroFederacao; // número da federação; null se não tem federacao
    private Set<Candidato> candidatos;

    public Partido(String numero, String sigla, String numeroFederacao) {
        this.numero = numero;
        this.sigla = sigla;
        this.numeroFederacao = numeroFederacao;
    }
}
