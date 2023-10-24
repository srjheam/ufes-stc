package com.srjheamtucozz.ufes.poo.t1.console.entities;

import java.time.LocalDate;

import com.srjheamtucozz.ufes.poo.t1.stc.enums.Cargo;

public class CmdArgs {
    private Cargo tipoCargo;
    private String caminhoArquivoCandidatos;
    private String caminhoArquivoVotacao;
    private LocalDate data;

    public CmdArgs(Cargo tipoCargo, String caminhoArquivoCandidatos, String caminhoArquivoVotacao, LocalDate data) {
        this.tipoCargo = tipoCargo;
        this.caminhoArquivoCandidatos = caminhoArquivoCandidatos;
        this.caminhoArquivoVotacao = caminhoArquivoVotacao;
        this.data = data;
    }

    public Cargo getTipoCargo() {
        return tipoCargo;
    }

    public String getCaminhoArquivoCandidatos() {
        return caminhoArquivoCandidatos;
    }

    public String getCaminhoArquivoVotacao() {
        return caminhoArquivoVotacao;
    }

    public LocalDate getData() {
        return data;
    }
}
