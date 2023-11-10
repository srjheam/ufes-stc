package com.srjheamtucozz.ufes.poo.t1.stc.models;

import java.time.LocalDate;

import com.srjheamtucozz.ufes.poo.t1.stc.enums.Cargo;

public class Candidato {
    private Cargo cargo;
    private boolean deferido;
    private String numero;
    private String nomeUrna;
    private String numeroPartido;
    private String numeroFederacao;
    private LocalDate dataNascimento;
    private int idade;
    private boolean sexo; // internamente o sexo sim é masculino e não é feminino

    public Candidato(Cargo cargo, boolean deferido, String numero, String nomeUrna, String numeroPartido, String numeroFederacao, LocalDate dataNascimento, int idade, boolean sexo) {
        this.cargo = cargo;
        this.deferido = deferido;
        this.numero = numero;
        this.nomeUrna = nomeUrna;
        this.numeroPartido = numeroPartido;
        this.numeroFederacao = numeroFederacao;
        this.dataNascimento = dataNascimento;
        this.idade = idade;
        this.sexo = sexo;
    }

    public Cargo getCargo() {
        return this.cargo;
    }

    public boolean isDeferido() {
        return this.deferido;
    }

    public String getNumero() {
        return this.numero;
    }

    public String getNomeUrna() {
        return this.nomeUrna;
    }

    public String getNumeroPartido() {
        return this.numeroPartido;
    }

    public String getNumeroFederacao() {
        return this.numeroFederacao;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public int getIdade() {
        return this.idade;
    }

    public boolean isSexo() {
        return this.sexo;
    }    
}
