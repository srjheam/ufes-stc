package com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.readers;

import java.util.LinkedList;
import java.util.List;

import com.srjheamtucozz.ufes.poo.common.csv.CsvReader;

import com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.parsers.CsvParser;
import com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.raw.RawCandidato;
import com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.raw.RawEleicao;
import com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.raw.RawVotacao;

public class TseReader {
    public static RawEleicao readEleicao(String pathCandidatos, String pathVotacao){
        List<RawCandidato> candidatos = readConsultaCandidatos(pathCandidatos);
        List<RawVotacao> votacoes = readVotacaoSecao(pathVotacao);
    
        return new RawEleicao(candidatos, votacoes);
    }

    public static List<RawCandidato> readConsultaCandidatos(String path){
        String[] importantValues = {"CD_CARGO", "CD_SITUACAO_CANDIDATO_TOT", "NR_CANDIDATO", "NM_URNA_CANDIDATO",
                                    "NR_PARTIDO", "SG_PARTIDO", "NR_FEDERACAO", "DT_NASCIMENTO", "CD_SIR_TOT_TURNO",
                                    "CD_GENERO", "NM_TIPO_DESTINACAO_VOTOS"};
    
        CsvReader csv = new CsvReader(path, "ISO-8859-1", ";", importantValues);
    
        List<RawCandidato> candidatos = new LinkedList<RawCandidato>();
        for(String[] candidato : csv.readAll()){
            candidatos.add(CsvParser.parseCandidato(candidato));
        }
    
        return candidatos;
    }
    
    public static List<RawVotacao> readVotacaoSecao(String path){
        String[] importantValues = {"CD_CARGO", "NR_VOTAVEL", "QT_VOTOS"};
    
        CsvReader csv = new CsvReader(path, "ISO-8859-1", ";", importantValues);
    
        List<RawVotacao> votacoes = new LinkedList<RawVotacao>();
        for(String[] legenda : csv.readAll()){
            votacoes.add(CsvParser.parseVotacao(legenda));
        }
    
        return votacoes;
    }
}