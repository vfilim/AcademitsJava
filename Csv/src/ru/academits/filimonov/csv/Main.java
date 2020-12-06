package ru.academits.filimonov.csv;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileInputStream("Csv\\input.txt"));
             PrintWriter writer = new PrintWriter("Csv\\output.txt")) {
            StringBuilder htmlCode = new StringBuilder();

            htmlCode.append("<table>\r\n");

            boolean isRow = false;
            boolean isDetail = false;
            boolean isEscaped = false;

            while (scanner.hasNextLine()) {
                String currentRow = scanner.nextLine();

                int currentRowSize = currentRow.length();

                for (int i = 0; i < currentRowSize; i++) {
                    char currentCharacter = currentRow.charAt(i);

                    if (!isRow) {
                        htmlCode.append("<tr>");

                        isRow = true;
                    }

                    if (!isDetail) {
                        htmlCode.append("<td>");

                        isDetail = true;
                    }

                    if (!isEscaped) {
                        if (currentCharacter == '"') {
                            isEscaped = true;
                        } else if (currentCharacter == ',') {
                            htmlCode.append("</td>");

                            isDetail = false;
                        } else if (i == currentRowSize - 1) {
                            htmlCode.append(currentCharacter);
                            htmlCode.append("</td></tr>\r\n");

                            isDetail = false;
                            isRow = false;

                            i++;
                        } else {
                            htmlCode.append(currentCharacter);
                        }
                    } else {
                        if (currentCharacter == '"') {
                            if (i == currentRowSize - 1){
                                htmlCode.append("</td></tr>\r\n");

                                isDetail = false;
                                isRow = false;
                                isEscaped = false;

                                continue;
                            }

                            if (currentRow.charAt(i + 1) == '"') {
                                htmlCode.append('"');

                                i++;
                            } else {
                                isEscaped = false;
                            }
                        } else {
                            htmlCode.append(currentCharacter);
                        }

                        if (i == currentRowSize - 1) {
                            htmlCode.append("<br/>");
                        }
                    }
                }
            }

            htmlCode.append("</table>");

            writer.println(htmlCode);
        }
    }
}

