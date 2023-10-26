package turmite;

public class Grid {
    int grid[][];
    int cmax;
    int rmax;
    Grid(int cmax_in, int rmax_in){
        int cmax=cmax_in;
        int rmax=rmax_in;
        grid=new int[rmax_in][rmax_in];

        for(int i=0;i<cmax;i++){
            for(int j=0;j<rmax;j++){
                grid[i][j]=0;
            }
        }
    }
}
