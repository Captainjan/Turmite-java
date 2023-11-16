package turmite;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        Game main = new Game();

        try {
            main.latch.await();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupted while waiting for the newTurmiteActionListener to finish");
        }

        while (!main.stopped && !main.turmiteList.isEmpty()) {
            for (Turmite t : main.turmiteList) {
                main.changeButtonAtPosition(main.gridPanel, t.currentPosition);
                t.move(main.gameGrid);
                System.out.println("x: " + t.currentPosition.x + " " + "y: " + t.currentPosition.y);
            }
        }
    }
}