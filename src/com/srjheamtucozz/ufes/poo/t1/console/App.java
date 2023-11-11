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

        RawEleicao rawEleicao = TseReader.readEleicao(cmdArgs.getCaminhoArquivoCandidatos(),
                cmdArgs.getCaminhoArquivoVotacao(), cmdArgs.getTipoCargo());

        Eleicao eleicao = Mapper.fromTse(rawEleicao, cmdArgs.getData());

        EleicaoStats stats = new EleicaoStats(eleicao, cmdArgs.getTipoCargo());

        stats.print();
    }
}
