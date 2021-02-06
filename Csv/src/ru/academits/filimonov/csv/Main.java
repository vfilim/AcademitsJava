package ru.academits.filimonov.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        if (args[0] == null) {
            System.out.println("The program must have two arguments: input and output file paths. The input argument isn't set now");
        } else if (args[1] == null) {
            System.out.println("The program must have two arguments: input and output file paths. The output argument isn't set now");
        } else {
            File inputFile = new File(args[0]);

            if (!inputFile.exists()) {
                System.out.println("Incorrect path. There is no such input file");
            } else {
                try (Scanner scanner = new Scanner(new FileInputStream(args[0]));
                     PrintWriter writer = new PrintWriter(args[1])) {
                    writer.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">");

                    writer.println("<html>");

                    writer.println("<head>");
                    writer.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
                    writer.println("<title>Пример веб-страницы</title>");
                    writer.println("</head>");

                    writer.println("<body>");

                    writer.println("<table>");

                    boolean isRow = false;
                    boolean isDetail = false;
                    boolean isEscaped = false;

                    while (scanner.hasNextLine()) {
                        String currentRow = scanner.nextLine();

                        int currentRowSize = currentRow.length();

                        if (currentRowSize == 0 && isRow) {
                            writer.print("<br/>");
                        }

                        for (int i = 0; i < currentRowSize; i++) {
                            char currentCharacter = currentRow.charAt(i);

                            if (!isRow) {
                                writer.print("<tr>");

                                isRow = true;
                            }

                            if (!isDetail) {
                                writer.print("<td>");

                                isDetail = true;
                            }

                            if (!isEscaped) {
                                if (currentCharacter == '"') {
                                    isEscaped = true;
                                } else if (currentCharacter == ',') {
                                    writer.print("</td>");

                                    isDetail = false;

                                    if (i == currentRowSize - 1) {
                                        writer.println("<td></td></tr>");

                                        isRow = false;
                                    }
                                } else if (i == currentRowSize - 1) {
                                    writer.print(getConvertedCharacter(currentCharacter));
                                    writer.println("</td></tr>");

                                    isDetail = false;
                                    isRow = false;
                                } else {
                                    writer.print(getConvertedCharacter(currentCharacter));
                                }
                            } else {
                                if (currentCharacter == '"') {
                                    if (i == currentRowSize - 1) {
                                        writer.println("</td></tr>");

                                        isDetail = false;
                                        isRow = false;
                                        isEscaped = false;

                                        continue;
                                    }

                                    if (currentRow.charAt(i + 1) == '"') {
                                        writer.print('"');

                                        i++;
                                    } else {
                                        isEscaped = false;
                                    }
                                } else {
                                    writer.print(getConvertedCharacter(currentCharacter));
                                }

                                if (i == currentRowSize - 1) {
                                    writer.print("<br/>");
                                }
                            }
                        }
                    }
                    writer.println("</table>");

                    writer.println("</body>");

                    writer.print("</html>");
                }
            }
        }
    }

    private static String getConvertedCharacter(char currentCharacter) {
        if (currentCharacter == '<') {
            return "&lt;";
        } else if (currentCharacter == '>') {
            return "&gt;";
        } else if (currentCharacter == '&') {
            return "&amp;";
        } else {
            return Character.toString(currentCharacter);
        }
    }
}