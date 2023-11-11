package com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.readers;

import java.util.LinkedList;
import java.util.List;

import com.srjheamtucozz.ufes.poo.common.csv.CsvReader;
import com.srjheamtucozz.ufes.poo.t1.stc.enums.Cargo;
import com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.parsers.CsvParser;
import com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.raw.RawCandidato;
import com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.raw.RawEleicao;
import com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.raw.RawVotacao;

public class TseReader {
    public static RawEleicao readEleicao(String pathCandidatos, String pathVotacao, Cargo cargo){
        List<RawCandidato> candidatos = readConsultaCandidatos(pathCandidatos, cargo);
        List<RawVotacao> votacoes = readVotacaoSecao(pathVotacao, cargo);
    
        return new RawEleicao(candidatos, votacoes);
    }

    private static List<RawCandidato> readConsultaCandidatos(String path, Cargo cargo){
        String[] importantValues = {"CD_CARGO", "NR_CANDIDATO", "NM_URNA_CANDIDATO", "NR_PARTIDO",
                                    "SG_PARTIDO", "NR_FEDERACAO", "DT_NASCIMENTO", "CD_GENERO",
                                    "CD_SIT_TOT_TURNO", "NM_TIPO_DESTINACAO_VOTOS", "CD_SITUACAO_CANDIDATO_TOT"};
    
        CsvReader csv = new CsvReader(path, "ISO-8859-1", ";", importantValues);
    
        List<RawCandidato> candidatos = new LinkedList<RawCandidato>();
        for(String[] candidato : csv.readAll(
            (cols) ->
                cols[0].equals(cargo == Cargo.DEPUTADO_ESTADUAL ? "7" : "6")
                && (cols[10].equals("2")
                    || cols[10].equals("16")))){
            candidatos.add(CsvParser.parseCandidato(candidato));
        }
    
        return candidatos;
    }
    
    private static List<RawVotacao> readVotacaoSecao(String path, Cargo cargo){
        String[] importantValues = {"CD_CARGO", "NR_VOTAVEL", "QT_VOTOS"};
    
        CsvReader csv = new CsvReader(path, "ISO-8859-1", ";", importantValues);
    
        List<RawVotacao> votacoes = new LinkedList<RawVotacao>();
        for(String[] legenda : csv.readAll(
            (cols) ->
                cols[0].equals(cargo == Cargo.DEPUTADO_ESTADUAL ? "7" : "6"))){
            votacoes.add(CsvParser.parseVotacao(legenda));
        }
    
        return votacoes;
    }
}