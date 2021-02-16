package ru.academits.filimonov.csv;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("The program must have two arguments: input and output file paths");

            return;
        }

        File inputFile = new File(args[0]);

        if (!inputFile.exists()) {
            System.out.println("Incorrect path. There is no such input file");

            return;
        }

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
        } catch (FileNotFoundException e) {
            System.out.println("The input file is not found");
        }
    }

    private static String getConvertedCharacter(char currentCharacter) {
        if (currentCharacter == '<') {
            return "&lt;";
        }

        if (currentCharacter == '>') {
            return "&gt;";
        }

        if (currentCharacter == '&') {
            return "&amp;";
        }

        return Character.toString(currentCharacter);
    }
}