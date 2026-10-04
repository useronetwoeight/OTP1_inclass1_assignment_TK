package org.example;

// JavaFX apps cant start from the jar directly if the main class extends Application,
// so this class starts Main for us
public class Launcher {
    public static void main(String[] args) {
        Main.main(args);
    }
}
