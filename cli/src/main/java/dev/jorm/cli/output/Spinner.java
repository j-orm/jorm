package dev.jorm.cli.output;

import picocli.CommandLine.Help.Ansi;

public class Spinner {
    private final String message;
    private Thread spinnerThread;
    private volatile boolean running;

    public Spinner(String message) {
        this.message = message;
    }

    public void start() {
        running = true;
        spinnerThread = new Thread(() -> {
            String[] frames = {"⠋", "⠙", "⠹", "⠸", "⠼", "⠴", "⠦", "⠧", "⠇", "⠏"};
            int i = 0;
            while (running) {
                System.out.print(Ansi.AUTO.string("\r@|cyan " + frames[i++ % frames.length] + " " + message + "|@"));
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        spinnerThread.setDaemon(true);
        spinnerThread.start();
    }

    public void stop(String successMessage) {
        running = false;
        if (spinnerThread != null) {
            try {
                spinnerThread.join(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.print("\r"); // Clear line
        Printer.success(successMessage);
    }
    
    public void stopWithError(String errorMessage) {
        running = false;
        if (spinnerThread != null) {
            try {
                spinnerThread.join(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.print("\r"); // Clear line
        Printer.error(errorMessage);
    }
}
