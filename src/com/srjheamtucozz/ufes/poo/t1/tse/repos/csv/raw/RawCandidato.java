package com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.raw;

public class RawCandidato {
    private String cdCargo;
    private String cdSituacaoCandidatoTot;
    private String nrCandidato;
    private String nmUrnaCandidato;
    private String nrPartido;
    private String sgPartido;
    private String nrFederacao;
    private String dtNascimento;
    private String cdSitTotTurno;
    private String cdGenero;
    private String nmTipoDestinacaoVotos;
    
    public RawCandidato(String cdCargo, String cdSituacaoCandidatoTot, String nrCandidato, String nmUrnaCandidato,
            String nrPartido, String sgPartido, String nrFederacao, String dtNascimento, String cdSirTotTurno,
            String cdGenero, String nmTipoDestinacaoVotos) {
        this.cdCargo = cdCargo;
        this.cdSituacaoCandidatoTot = cdSituacaoCandidatoTot;
        this.nrCandidato = nrCandidato;
        this.nmUrnaCandidato = nmUrnaCandidato;
        this.nrPartido = nrPartido;
        this.sgPartido = sgPartido;
        this.nrFederacao = nrFederacao;
        this.dtNascimento = dtNascimento;
        this.cdSitTotTurno = cdSirTotTurno;
        this.cdGenero = cdGenero;
        this.nmTipoDestinacaoVotos = nmTipoDestinacaoVotos;
    }

    public String getCdCargo() {
        return cdCargo;
    }

    public String getCdSituacaoCandidatoTot() {
        return cdSituacaoCandidatoTot;
    }

    public String getNrCandidato() {
        return nrCandidato;
    }

    public String getNmUrnaCandidato() {
        return nmUrnaCandidato;
    }

    public String getNrPartido() {
        return nrPartido;
    }

    public String getSgPartido() {
        return sgPartido;
    }

    public String getNrFederacao() {
        return nrFederacao;
    }

    public String getDtNascimento() {
        return dtNascimento;
    }

    public String getCdSitTotTurno() {
        return cdSitTotTurno;
    }

    public String getCdGenero() {
        return cdGenero;
    }

    public String getNmTipoDestinacaoVotos() {
        return nmTipoDestinacaoVotos;
    }
}
