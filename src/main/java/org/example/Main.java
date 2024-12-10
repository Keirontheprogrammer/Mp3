package org.example;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args )throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        try {
            new frame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}