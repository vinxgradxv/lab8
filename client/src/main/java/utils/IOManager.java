package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;

public class IOManager {
    private File file;
    private BufferedReader reader;
    private PrintWriter writer;
    private final String prompter;
    private Boolean fileMode = false;
    private final Stack<BufferedReader> previousReaders = new Stack<>();
    private final Stack<File> currentFiles = new Stack<>();
    private Scanner fileScanner;

    public IOManager(BufferedReader reader, PrintWriter writer, String promter) {
        this.reader = reader;
        this.writer = writer;
        this.prompter = promter;
    }

    public  void setBufferReader(BufferedReader buf) {
        reader = buf;
    }

    public  void setPrintWriter(PrintWriter wr) {
        writer = wr;
    }

    public  BufferedReader getBufferedReader() {
        return reader;
    }

    public  PrintWriter getPrintWriter() {
        return writer;
    }

    public void turnOnFileMode(String filename) {
        try {
            file = new File(filename);
            if (file.exists()) {
                fileScanner = new Scanner(file);
                println("Started to execute script: " + file.getName());
                println("------------------------------------------");
                fileMode = true;
            } else if (!file.exists()) {
                printerr("File doesn't exist.");
            } else if (currentFiles.contains(file)) {
                printerr("The file was not executed due to recursion.");
                turnOffFileMode();
            }
        } catch (FileNotFoundException e) {
            printerr("Invalid file access rights.");
        }
    }

    public void turnOffFileMode() {
        fileMode = false;
        fileScanner.close();
        println("------------------------------------------");
        println("Finished to execute script: " + file.getName());
    }

    public Boolean getFileMode() {
        return fileMode;
    }

    public  String readLine() throws IOException {
        return reader.readLine();
    }

    public String readFile() throws FileNotFoundException, IOException {
        if (!fileScanner.hasNextLine()){
            turnOffFileMode();
            return null;
        }
        return fileScanner.nextLine();
    }

    public  void close() throws IOException {
        reader.close();
        writer.close();
    }

    public void prompt() {
        writer.printf("%s", prompter);
    }

    public  void print(Object o) {
        writer.printf("%s", o);
    }

    public  void println(Object o) {
        writer.println(o);
        writer.println("------------------------");
    }

    public  void printerr(Object o) {
        writer.println("Error! " + o);
    }
}
