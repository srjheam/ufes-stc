package com.srjheamtucozz.ufes.poo.t1.stc.models;

import java.time.LocalDate;

import com.srjheamtucozz.ufes.poo.t1.stc.enums.Cargo;

public class Candidato {
    private Cargo cargo;
    private boolean deferido;
    private String numero;
    private String nomeUrna;
    private Partido partido;
    private LocalDate dataNascimento;
    private boolean sexo; // internamente o sexo sim é masculino e não é feminino

    public Candidato(Cargo cargo, boolean deferido, String numero, String nomeUrna, Partido partido, LocalDate dataNascimento, boolean sexo) {
        this.cargo = cargo;
        this.deferido = deferido;
        this.numero = numero;
        this.nomeUrna = nomeUrna;
        this.partido = partido;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public boolean isDeferido() {
        return deferido;
    }

    public String getNumero() {
        return numero;
    }

    public String getNomeUrna() {
        return nomeUrna;
    }

    public Partido getPartido() {
        return partido;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public boolean isSexo() {
        return sexo;
    }    
}
