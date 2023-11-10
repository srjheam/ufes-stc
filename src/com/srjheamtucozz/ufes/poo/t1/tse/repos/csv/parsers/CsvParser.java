package com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.parsers;

import com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.raw.RawCandidato;
import com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.raw.RawVotacao;

public class CsvParser {
    public static RawCandidato parseCandidato(String[] args){
        return new RawCandidato(args[0], args[10], args[1], args[2], args[3], args[4], args[5], args[6],
                args[8], args[7], args[9]);
    }

    public static RawVotacao parseVotacao(String[] arg){
        return new RawVotacao(arg[0], arg[1], arg[2]);
    }
}
