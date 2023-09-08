package com.matchgorithm.view;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class Helper {

    public static String getRandomItemFromFile(String filePath, String separator) {
        String[] fileData = Helper.readFromResourceFile(filePath).split(separator);
        Random rand = new Random();
        return fileData[rand.nextInt(fileData.length)];
    }

    // read a txt file, and print it in bright foreground color
    public static void printFile(String fileName, Ansi.Color foreColor){

        String fileContent = readFromResourceFile(fileName);

        printColor(fileContent, foreColor);
    }

    // read from a file, and print it in default color
    public static void printFile(String fileName){
        printFile(fileName, Ansi.Color.BLACK);
    }

    // print String in color at cursor location (x, y)
    public static void printColor(String string, Ansi.Color color, int x, int y) {
        System.out.print(Ansi.ansi().cursor(y, x).fg(color).a(string + "\n").reset());
    }

    // print String in bright color
    public static void printColor(String string, Ansi.Color color) {
        System.out.print(Ansi.ansi().fgBright(color).a(string).reset());
    }

    public static Ansi colorText(String string, Ansi.Color color) {
        return Ansi.ansi().fgBright(color).a(string).reset();
    }

    // retrieve content as a String from resource file
    public static String readFromResourceFile(String fileName) {

        String fileContent = "";
        // First, try to access the resource from the classpath (e.g., JAR file)
        InputStream inputStream = Helper.class.getResourceAsStream("/" + fileName);

        if (inputStream != null) {
            // Resource is found (in JAR), process the input stream as needed
            try {
                // Read content from the resource
                byte[] resourceBytes = inputStream.readAllBytes();
                fileContent = new String(resourceBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileContent;
    }
}