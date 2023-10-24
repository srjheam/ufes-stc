package com.srjheamtucozz.ufes.poo.common.csv;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CsvReader {
    private LinkedList<String> importantColumns = new LinkedList<>();
    private String filePath;
    private String charsetName;
    private String delimiter;

    public CsvReader(String filePath, String charsetName, String delimiter) {
        this.filePath = filePath;
        this.charsetName = charsetName;
        this.delimiter = delimiter;
    }

    public CsvReader(String filePath, String charsetName, String delimiter, String[] columns) {
        this(filePath, charsetName, delimiter);

        for (String arg : columns) {
            importantColumns.add(arg);
        }
    }

    public void addImportantColumn(String column) {
        importantColumns.add(column);
    }

    public List<String> getImportantColumns() {
        return new LinkedList<String>(importantColumns);
    }

    public String getFilePath() {
        return filePath;
    }

    public String getCharsetName() {
        return charsetName;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public String[][] readAll() {
        try (FileInputStream fin = new FileInputStream(this.getFilePath());
                Scanner s = new Scanner(fin, this.getCharsetName())) {
            LinkedList<String> columns = new LinkedList<String>();
            String firstline = s.nextLine();
            try (Scanner lineScanner = new Scanner(firstline)) {
                lineScanner.useDelimiter(this.getDelimiter());
                while (lineScanner.hasNext()) {
                    String token = lineScanner.next();
                    columns.add(token);
                }
            }
            String[][] data = new String[(int) quantasLinhas(this.getFilePath())][this.importantColumns.size()];
            int j = 0;
            while (s.hasNextLine()) {
                String[] importantArgs = new String[this.importantColumns.size()];
                String line = s.nextLine();
                try (Scanner lineScanner = new Scanner(line)) {
                    int i = 0;
                    lineScanner.useDelimiter(this.getDelimiter());
                    for (String arg : columns) {
                        if (this.importantColumns.contains(arg)) {
                            importantArgs[i++] = lineScanner.next();
                        } else {
                            lineScanner.next();
                        }
                    }
                }
                data[j++] = importantArgs;
            }

            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return new String[0][0];
        }
    }

    private static int quantasLinhas(String fileName) {
        Path path = Paths.get(fileName);

        int lines = 0;
        try {
            lines = (int) Files.lines(path).count();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
}