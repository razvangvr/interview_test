package raz.inteview_test.everest.brynjolf.util;

import raz.inteview_test.everest.brynjolf.room.Element;
import raz.inteview_test.everest.brynjolf.room.ElementValue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatrixFileConverter {

    static final String delimiter = "  ";

    public Element[][] loadFromFile(Path filePath) throws IOException {
        Element[][] matrix;

        List<String> fileRows = readCsvLines(filePath);
        matrix = new Element[fileRows.size()][];

        int rowIdx = 0;
        for (String oneRow : fileRows) {
            String[] rowValuesArr = oneRow.split(Pattern.quote(delimiter));

            List<String> rowValues = Stream.of(rowValuesArr)
                    .map(s -> s.trim())
                    .collect(Collectors.toList());

            Element[] oneMatrixRow = oneMatrixRow(rowValues, rowIdx);

            matrix[rowIdx] = oneMatrixRow;

            rowIdx++;
        }

        return matrix;
    }

    public Element[][] loadFromFile(String fileContent) throws IOException {
        Element[][] matrix;

        String [] lines = fileContent.split(System.lineSeparator());
        Stream<String> linesStream = Arrays.stream(lines).sequential();
        List<String> fileRows = linesStream
                .filter(strLine -> !strLine.isEmpty())
                .collect(Collectors.toList());
        matrix = new Element[fileRows.size()][];

        int rowIdx = 0;
        for (String oneRow : fileRows) {
            String[] rowValuesArr = oneRow.split(Pattern.quote(delimiter));

            List<String> rowValues = Stream.of(rowValuesArr)
                    .map(s -> s.trim())
                    .collect(Collectors.toList());

            Element[] oneMatrixRow = oneMatrixRow(rowValues, rowIdx);

            matrix[rowIdx] = oneMatrixRow;

            rowIdx++;
        }

        return matrix;
    }

    private List<String> readCsvLines(Path csvFilePath) throws IOException {
        try (Stream<String> csvLine = Files.lines(csvFilePath)) {
            List<String> matrixRows = csvLine
                    .filter(strLine -> !strLine.isEmpty())
                    .collect(Collectors.toList());
            return matrixRows;
        }
    }

    private Element[] oneMatrixRow(List<String> rowValues, int rowIdx) {
        Element[] oneMatrixRow = new Element[rowValues.size()];
        Element element = null;


        for (int idx = 0; idx <= rowValues.size() - 1; idx++) {
            element = new Element(
                    ElementValue.fromValue(rowValues.get(idx)),
                    rowIdx,
                    idx
            );
            oneMatrixRow[idx] = element;
        }

        return oneMatrixRow;
    }

}
