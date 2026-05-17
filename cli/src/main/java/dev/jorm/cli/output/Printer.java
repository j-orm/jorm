package dev.jorm.cli.output;

import picocli.CommandLine.Help.Ansi;

public final class Printer {
    
    private Printer() {}
    
    public static void info(String message) {
        System.out.println(Ansi.AUTO.string("@|cyan " + message + "|@"));
    }
    
    public static void success(String message) {
        System.out.println(Ansi.AUTO.string("@|bold,green ✔ " + message + "|@"));
    }
    
    public static void warn(String message) {
        System.out.println(Ansi.AUTO.string("@|bold,yellow ⚠ " + message + "|@"));
    }
    
    public static void error(String message) {
        System.err.println(Ansi.AUTO.string("@|bold,red ✘ Error:|@ " + message));
    }
    
    public static void print(String message) {
        System.out.println(Ansi.AUTO.string(message));
    }
}
