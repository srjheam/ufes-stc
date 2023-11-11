package com.srjheamtucozz.ufes.poo.t1.console;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.srjheamtucozz.ufes.poo.common.util.Validation;
import com.srjheamtucozz.ufes.poo.common.widgets.ContainerWidget;
import com.srjheamtucozz.ufes.poo.common.widgets.HelpWidget;
import com.srjheamtucozz.ufes.poo.common.widgets.ListWidget;
import com.srjheamtucozz.ufes.poo.common.widgets.Text;
import com.srjheamtucozz.ufes.poo.t1.console.entities.CmdArgs;
import com.srjheamtucozz.ufes.poo.t1.console.parsers.CmdArgsParser;
import com.srjheamtucozz.ufes.poo.t1.console.validators.CmdArgsValidator;
import com.srjheamtucozz.ufes.poo.t1.stc.models.Eleicao;
import com.srjheamtucozz.ufes.poo.t1.stc.models.EleicaoStats;
import com.srjheamtucozz.ufes.poo.t1.stc.util.Mapper;
import com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.raw.RawEleicao;
import com.srjheamtucozz.ufes.poo.t1.tse.repos.csv.readers.TseReader;

public class App {
    public static void main(String[] args) throws Exception {
        Validation val = CmdArgsValidator.validateCmdArgs(args);
        if (val.hasErrors()) {
            new ContainerWidget(
                new ListWidget(
                    List.of(
                        new ListWidget(val.getErrors()
                            .stream()
                            .map(Text::new)
                            .collect(Collectors.toList())),
                        new HelpWidget())
                    .stream()
                    .map(x -> new ContainerWidget(x, 0, 1))
                    .collect(Collectors.toList())))
                .show();

            return;
        }

        CmdArgs cmdArgs = CmdArgsParser.parseCmdArgs(args);

        // TODO:
        // adicionar um predicate ao readEleicao (e também ao CsvReader) para
        // filtrar os candidatos e votações a intenção por trás disso é
        // extrair apenas os dados produtivos do arquivo, que é grande e pode
        // ocupar muita memória atoa
        //
        // também existem candidatos indeferidos porque tem o mesmo número que
        // outro candidato deferido
        //
        // esses candidatos não importam na contagem e são danosos pro programa
        // porque ele usa muitos Map que depende da uniquidade do número do
        // candidato
        RawEleicao rawEleicao = TseReader.readEleicao(cmdArgs.getCaminhoArquivoCandidatos(),
                cmdArgs.getCaminhoArquivoVotacao(), cmdArgs.getTipoCargo());

        // TODO: Computar tudo que tem que computar com esses dados e imprimir na tela

        Eleicao eleicao = Mapper.fromTse(rawEleicao, cmdArgs.getData());

        EleicaoStats stats = new EleicaoStats(eleicao, cmdArgs.getTipoCargo());

        stats.print();

    }
}
