package turmite;

public class Main {
    public static void main(String[] args) {
        Game main = new Game();

        Thread movementThread = new Thread(() -> {
            try {
                while (true) {
                    if (!main.stopped && !main.turmiteList.isEmpty()) {
                        for (Turmite t : main.turmiteList) {
                            t.move();
                            System.out.println("I moved" + t.currentPosition);
                        }
                    }
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        movementThread.start();
    }
}

