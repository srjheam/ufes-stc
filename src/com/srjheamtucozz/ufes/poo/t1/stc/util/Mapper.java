package com.srjheamtucozz.ufes.poo.t1.stc.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.srjheamtucozz.ufes.poo.t1.stc.enums.Cargo;
import com.srjheamtucozz.ufes.poo.t1.stc.models.Candidato;
import com.srjheamtucozz.ufes.poo.t1.stc.models.CandidatoVotacao;
import com.srjheamtucozz.ufes.poo.t1.stc.models.Eleicao;
import com.srjheamtucozz.ufes.poo.t1.stc.models.Federacao;
import com.srjheamtucozz.ufes.poo.t1.stc.models.LegendaVotacao;
import com.srjheamtucozz.ufes.poo.t1.stc.models.Partido;
import com.srjheamtucozz.ufes.poo.t1.stc.models.Votacao;
import com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.raw.RawCandidato;
import com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.raw.RawEleicao;
import com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.raw.RawVotacao;

public class Mapper {
   public static Eleicao fromTse(RawEleicao raw, LocalDate dataRelatorio) {
      Eleicao mapEleicao = mapEleicao(raw.getCandidatos(), dataRelatorio);
      Votacao mapVotacao = mapVotacao(raw.getVotacoes(), mapEleicao);

      Eleicao eleicao = new Eleicao(mapEleicao.getFederacoes(), mapEleicao.getPartidos(), mapEleicao.getCandidatos(),
            mapVotacao);

      return eleicao;
   }

   private static Eleicao mapEleicao(List<RawCandidato> raw, LocalDate dataRelatorio) {
      Map<String, CandidatoVotacao> candidatosVotacao = new HashMap<>();
      Map<String, Candidato> candidatos = new HashMap<>();

      Map<String, Partido> mapPartidos = new HashMap<>();
      Map<String, Map<String, Candidato>> mapPartidosCandidatos = new HashMap<>();

      Map<String, Map<String, Partido>> mapFederacoesPartidos = new HashMap<>();

      for (var candidatoRaw : raw) {
         var candidato = fromTse(candidatoRaw, dataRelatorio);
         candidatos.put(candidatoRaw.getNrCandidato(), candidato);
         candidatosVotacao.put(candidatoRaw.getNrCandidato(),
               new CandidatoVotacao(
                     parseEleitoFromTse(candidatoRaw.getCdSitTotTurno()),
                     parseDestinacaoFromTse(candidatoRaw.getNmTipoDestinacaoVotos()),
                     candidato));

         var partido = mapPartidos.get(candidatoRaw.getNrPartido());
         if (partido == null) {
            partido = parsePartido(candidatoRaw);
            mapPartidos.put(candidatoRaw.getNrPartido(), partido);
         }

         var federacaoPartidos = mapFederacoesPartidos.get(candidatoRaw.getNrFederacao());
         if (federacaoPartidos == null) {
            federacaoPartidos = new HashMap<String, Partido>();
            federacaoPartidos.put(partido.getNumero(), partido);

            mapFederacoesPartidos.put(candidatoRaw.getNrFederacao(), federacaoPartidos);
         }
         else {
            federacaoPartidos.put(partido.getNumero(), partido);
         }

         var partidosCandidato = mapPartidosCandidatos.get(candidatoRaw.getNrPartido());
         if (partidosCandidato == null) {
            partidosCandidato = new HashMap<String, Candidato>();
            partidosCandidato.put(candidato.getNumero(), candidato);

            mapPartidosCandidatos.put(candidatoRaw.getNrPartido(), partidosCandidato);
         }
         else {
            partidosCandidato.put(candidato.getNumero(), candidato);
         }
      }

      var partidos = new HashMap<String, Partido>();
      for (var partidoCandidatos : mapPartidosCandidatos.entrySet()) {
         var partido = mapPartidos.get(partidoCandidatos.getKey());

         partidos.put(partidoCandidatos.getKey(),
               new Partido(partidoCandidatos.getKey(), partido.getSigla(), partido.getFederacao(),
                     partidoCandidatos.getValue()));
      }

      var federacoes = new HashMap<String, Federacao>();
      for (var federacaoPartidos : mapFederacoesPartidos.entrySet()) {
         var pp = new HashMap<String, Partido>();
         for (var partido : federacaoPartidos.getValue().entrySet()) {
            pp.put(partido.getKey(), partidos.get(partido.getKey()));
         }

         federacoes.put(federacaoPartidos.getKey(), new Federacao(federacaoPartidos.getKey(), pp));
      }

      var votacao = new Votacao(null, candidatosVotacao);
      return new Eleicao(federacoes, partidos, candidatos, votacao);
   }

   private static Votacao mapVotacao(List<RawVotacao> raw, Eleicao mapEleicao) {
      var candidatosVotacao = mapEleicao.getVotacao().getCandidatos();
      var legendasVotos = new HashMap<String, Integer>();
      var legendasNominais = new HashMap<String, Integer>();

      for (var votacaoRaw : raw) {
         var nr = votacaoRaw.getNrVotavel();
         var candidatoVotacao = candidatosVotacao.getOrDefault(nr, null);
         if (nr.length() <= 2 || (candidatoVotacao != null && candidatoVotacao.isDestinacaoLegenda())) {
            int votos = legendasVotos.getOrDefault(nr.substring(0, 2), 0);
            legendasVotos.put(nr.substring(0, 2), votos + Integer.parseInt(votacaoRaw.getQtVotos()));
         }

         if (nr.length() <= 2 || candidatoVotacao == null) {
            nr = nr.substring(0, 2);
            var partido = mapEleicao.getPartidos().get(nr);
            if (partido == null)
               continue;

            continue;
         }

         candidatoVotacao.incNumeroVotos(Integer.parseInt(votacaoRaw.getQtVotos()));

         if (!candidatoVotacao.isDestinacaoLegenda()) {
            int votos = legendasNominais.getOrDefault(candidatoVotacao.getCandidato().getNumeroPartido(), 0);
            legendasNominais.put(candidatoVotacao.getCandidato().getNumeroPartido(),
                  votos + Integer.parseInt(votacaoRaw.getQtVotos()));
         }
      }

      var candidatosSorted = new TreeSet<CandidatoVotacao>(new Comparator<CandidatoVotacao>() {
         @Override
         public int compare(CandidatoVotacao s1, CandidatoVotacao s2) {
            int r = s2.getNumeroVotos() - s1.getNumeroVotos();
            if (r == 0)
               return s2.getCandidato().getIdade() - s1.getCandidato().getIdade();
            
            return r;
         }
      });
      candidatosSorted.addAll(candidatosVotacao.values());

      var legendasVotacao = new HashMap<String, LegendaVotacao>();

      var legendasSorted = new TreeSet<LegendaVotacao>(new Comparator<LegendaVotacao>() {
         @Override
         public int compare(LegendaVotacao s1, LegendaVotacao s2) {
            int r = s2.getNumeroVotosTotais() - s1.getNumeroVotosTotais();
            if (r == 0)
               return Integer.valueOf(s1.getLegenda().getNumero()) - Integer.valueOf(s2.getLegenda().getNumero());

            return r;
         }
      });
      for (Partido partido : mapEleicao.getPartidos().values()) {
         var candidatos = new TreeSet<CandidatoVotacao>(new Comparator<CandidatoVotacao>() {
            @Override
            public int compare(CandidatoVotacao s1, CandidatoVotacao s2) {
               int r = s2.getNumeroVotos() - s1.getNumeroVotos();
               if (r == 0)
                  return s2.getCandidato().getIdade() - s1.getCandidato().getIdade();
               
               return r;
            }
            });
         for (Candidato candidato : partido.getCandidatos().values()) {
            candidatos.add(candidatosVotacao.get(candidato.getNumero()));
         }

         var legenda = new LegendaVotacao(partido,
                                          legendasVotos.getOrDefault(partido.getNumero(), 0),
                                          legendasNominais.getOrDefault(partido.getNumero(), 0),
                                          candidatos);
         legendasVotacao.put(partido.getNumero(), legenda);

         legendasSorted.add(legenda);
      }

      var votacao = new Votacao(legendasVotacao, candidatosVotacao, legendasSorted, candidatosSorted);
      return votacao;
   }

   private static Candidato fromTse(RawCandidato raw, LocalDate dataRelatorio) {
      Cargo cargo = parseCargoFromTse(raw.getCdCargo());
      boolean deferido = parseDeferidoFromTse(raw.getCdSituacaoCandidatoTot());
      String numero = raw.getNrCandidato();
      String nomeUrna = raw.getNmUrnaCandidato();
      String numeroPartido = raw.getNrPartido();
      String numeroFederacao = raw.getNrFederacao();
      LocalDate dataNascimento = parseDataFromTse(raw.getDtNascimento());
      boolean sexo = parseSexoFromTse(raw.getCdGenero());

      return new Candidato(cargo, deferido, numero, nomeUrna, numeroPartido, numeroFederacao, dataNascimento, (int)ChronoUnit.YEARS.between(dataNascimento, dataRelatorio), sexo);
   }

   private static LocalDate parseDataFromTse(String data) {
      try {
         DateTimeFormatter var1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
         return LocalDate.parse(data, var1);
      } catch (DateTimeParseException var2) {
         System.out.println("Invalid date format. Valid format is: dd/MM/yyyy.");
         System.exit(1);
         return null;
      }
   }

   private static Partido parsePartido(RawCandidato raw) {
      String numero = raw.getNrPartido();
      String sigla = raw.getSgPartido();
      String federacao = raw.getNrFederacao();

      return new Partido(numero, sigla, federacao);
   }

   private static boolean parseSexoFromTse(String sexo) {
      if (sexo.equals("2")) {
         return true;
      } else if (sexo.equals("4")) {
         return false;
      } else {
         throw new IllegalArgumentException("Invalid <opcao_de_cargo>. Valid options are: 2, 4.");
      }
   }

   private static Cargo parseCargoFromTse(String codigoCargo) {
      if (codigoCargo.equals("7")) {
         return Cargo.DEPUTADO_ESTADUAL;
      } else if (codigoCargo.equals("6")) {
         return Cargo.DEPUTADO_FEDERAL;
      } else {
         throw new IllegalArgumentException("Invalid <opcao_de_cargo>. Valid options are: 6, 7");
      }
   }

   private static boolean parseEleitoFromTse(String codigoStatus) {
      return codigoStatus.equals("2") || codigoStatus.equals("3");
   }

   private static boolean parseDestinacaoFromTse(String tipoDestinacao) {
      return tipoDestinacao.equals("Válido (legenda)");
   }

   private static boolean parseDeferidoFromTse(String codigoStatus) {
      return codigoStatus.equals("2") || codigoStatus.equals("16");
   }
}
