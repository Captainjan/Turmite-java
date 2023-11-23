package turmite;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Game main = new Game();

        Thread movementThread = new Thread(() -> {
            try {
                while (true) {
                    if (!main.stopped && !main.turmiteList.isEmpty()) {
                        for (Turmite t : main.turmiteList) {
                            t.move();
                            main.indicateTurmite(t.currentPosition);
                            System.out.println("I moved" + " " + t.currentPosition);
                        }
                    }
                    Thread.sleep(main.speed);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        movementThread.start();
    }
}

