package com.srjheamtucozz.ufes.poo.t1.console.validators;
import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.srjheamtucozz.ufes.poo.common.util.Validation;

public class CmdArgsValidator {
    public static Validation validateCmdArgs(String[] args) {
        Validation validation = new Validation();

        if (args.length != 4) {
            validation.addError("Invalid number of arguments.");
        }

        if (args.length >= 1) {
            if (!args[0].equals("--estadual")
                && !args[0].equals("--federal")) {
                validation.addError("Invalid <opcao_de_cargo>. Valid options are: --estadual, --federal.");
            }
        }
        else {
            validation.addError("Missing <opcao_de_cargo>.");
        }
        
        if (args.length >= 2) {
            validateCsvFilePath(args[1], validation, "caminho_arquivo_candidatos");
        }
        else {
            validation.addError("Missing <caminho_arquivo_candidatos>.");
        }

        if (args.length >= 3) {
            validateCsvFilePath(args[2], validation, "caminho_arquivo_votacao");
        }
        else {
            validation.addError("Missing <caminho_arquivo_votacao>.");
        }

        if (args.length >= 4) {
            validateDate(args[3], validation);
        }
        else {
            validation.addError("Missing <data>.");
        }

        return validation;
    }

    private static void validateCsvFilePath(String path, Validation validation, String argName) {
        if (!path.endsWith(".csv")) {
            validation.addError("Invalid file extension for <" + argName + ">. Valid extension is: `.csv`.");
            return;
        }

        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            validation.addError("Invalid file path for <" + argName + ">.");
            return;
        }

        if (!(new File(path).isFile())) {
            validation.addError("File for does not exist for <" + argName + ">.");
        }
    }

    private static void validateDate(String date, Validation validation) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException ex) {
            validation.addError("Invalid date format for <data>.");
        }
    }
}
