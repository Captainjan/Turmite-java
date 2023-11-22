package turmite;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Game main = new Game();



        while (!main.stopped /* && !main.turmiteList.isEmpty()*/) {
            if (main.latch.getCount() == 1){
                try {
                    main.latch.await();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupted while waiting for the newTurmiteActionListener to finish");
                }
            }
            for (Turmite t : main.turmiteList) {
            t.move();
            }
            Thread.sleep(1000);
        }
    }
}

