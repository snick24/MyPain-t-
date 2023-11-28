
package com.example.maspain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Logger class facilitates logging messages to a specified file with timestamps.
 * It provides methods to write log messages with timestamps and to close the logger.
 */
public class Logger {
    private BufferedWriter writer;

    /**
     * Constructs a Logger object that writes logs to the specified log file path.
     * @param logFilePath The file path where log messages will be written.
     */
    public Logger(String logFilePath) {
        try {
            writer = new BufferedWriter(new FileWriter(logFilePath, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs a message with a timestamp to the log file.
     * @param message The message to be logged.
     */
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

    /**
     * Closes the logger, releasing any system resources associated with it.
     * Ensures that all buffered data is properly flushed and the file is closed.
     */
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
