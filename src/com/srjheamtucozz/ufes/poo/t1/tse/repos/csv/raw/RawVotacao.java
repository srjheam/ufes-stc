package com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.raw;

public class RawVotacao {
    private String cdCargo;
    private String nrVotavel;
    private String qtVotos;

    public RawVotacao(String cdCargo, String nrVotavel, String qtVotos) {
        this.cdCargo = cdCargo;
        this.nrVotavel = nrVotavel;
        this.qtVotos = qtVotos;
    }

    public String getCdCargo() {
        return cdCargo;
    }

    public String getNrVotavel() {
        return nrVotavel;
    }

    public String getQtVotos() {
        return qtVotos;
    }
}
