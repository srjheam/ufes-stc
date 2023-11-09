package com.srjheamtucozz.ufes.poo.t1.stc.models;

import java.util.HashMap;
import java.util.Map;

public class Partido {
    private String numero;
    private String sigla;
    private String federacao;
    private Map<String, Candidato> candidatos;

    public Partido(String numero, String sigla, String federacao) {
        this.numero = numero;
        this.sigla = sigla;
        this.federacao = federacao;
    }

    public Partido(String numero, String sigla, String federacao, Map<String, Candidato> candidatos) {
        this(numero, sigla, federacao);

        this.candidatos = new HashMap<>(candidatos);
    }

    public String getNumero() {
        return numero;
    }

    public String getSigla() {
        return sigla;
    }

    public String getFederacao() {
        return federacao;
    }

    public Map<String, Candidato> getCandidatos() {
        return new HashMap<>(candidatos);
    }
}
