package turmite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<String> lines=new ArrayList<>();
        Position startpos=new Position(50,50);
        Turmite thomas=new Turmite(startpos,90,0);

        BufferedReader br= new BufferedReader(new FileReader("program.txt"));
        String line;
        while((line = br.readLine()) != null){
            lines.add(line);
        }
        br.close();
        for(String l : lines){
            String[] parts = l.split("-");
            if(parts.length == 5){
                int inputCurrentAntState = Integer.parseInt(parts[0]);
                int inputCurrentCellState = Integer.parseInt(parts[1]);
                char inputDirection = parts[2].charAt(0);
                int inputNewAntState = Integer.parseInt(parts[3]);
                int inputNewCellState = Integer.parseInt(parts[4]);

                Pattern p=new Pattern(inputCurrentAntState,inputCurrentCellState,inputDirection,inputNewAntState,inputNewCellState);
                thomas.patterns.add(p);

                System.out.println("CurrentAntState: " + inputCurrentAntState);
                System.out.println("CurrentCellState: " + inputCurrentCellState);
                System.out.println("Direction: " + inputDirection);
                System.out.println("NewAntState: " + inputNewAntState);
                System.out.println("NewCellState: " + inputNewCellState);
                System.out.println();
            }
        }
    }
}