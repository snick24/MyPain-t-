package com.example.maspain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private BufferedWriter writer;

    public Logger(String logFilePath) {
        try {
            writer = new BufferedWriter(new FileWriter(logFilePath, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void log(String message) {
        if (writer != null) {
            try {
                String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
                writer.write(timestamp + " " + message);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void close() {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}