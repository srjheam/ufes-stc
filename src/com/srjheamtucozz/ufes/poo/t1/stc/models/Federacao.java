package com.srjheamtucozz.ufes.poo.t1.stc.models;

import java.util.HashMap;
import java.util.Map;

public class Federacao {
    private String numero;
    private Map<String, Partido> partidos;

    public Federacao(String numero, Map<String, Partido> partidos) {
        this.numero = numero;
        this.partidos = new HashMap<String, Partido> (partidos);
    }

    public String getNumero() {
        return numero;
    }

    public Map<String, Partido> getPartidos() {
        return partidos;
    }
}
