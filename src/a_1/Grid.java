package a_1;


import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
 
public class Grid
{
    private int Cell_Pixel, Grid_Width, Grid_Height, Gap, Shift, Chance, Time_Of_Board_Life, Time_Of_Color_Life, Steps;
    private int[][] grid = new int[Grid_Width][Grid_Height];
    private int[][] gridNext = new int[Grid_Width][Grid_Height];
    private Color color_BG, color_A, color_B, color_C, color_D;
    private Random rand = new Random();
    
    public Grid(int c, int cPixel, int g, int bLife, int cLife) {
    	 Steps = 0;
         Chance = c;
         Cell_Pixel = cPixel;
         Time_Of_Board_Life = bLife;
         Time_Of_Color_Life = cLife;
         Grid_Width = Main.Width / Cell_Pixel;
         Grid_Height = Main.Height / Cell_Pixel;
         grid = new int[Grid_Width][Grid_Height];
         gridNext = new int[Grid_Width][Grid_Height];
         Gap = g;
         Shift = Gap / 2;
    }
    
    public void update(){
        calculateNext();
        setNext();
        chooseColors();
        
        if(Steps % Time_Of_Color_Life == 0) createColors();
        if(Steps % Time_Of_Board_Life == 0) randomizeGrid();
        Steps++;
    }
    
    public void createColors(){
        color_BG = new Color(0, 0, 0);
        
        int r = 0, g = 0, b = 0;
        int rand6 = rand.nextInt(6);
        int rand256 = rand.nextInt(256);
        if(rand6 == 0){
            r = 0;
            g = rand256;
            b = 255;
        }
        else if(rand6 == 1){
            r = 255;
            g = rand256;
            b = 0;
        }
        else if(rand6 == 2){
            r = rand256;
            g = 255;
            b = 0;
        }
        else if(rand6 == 3){
            r = rand256;
            g = 0;
            b = 255;
        }
        else if(rand6 == 4){
            r = 255;
            g = 0;
            b = rand256;
        }
        else if(rand6 == 5){
            r = 0;
            g = 255;
            b = rand256;
        }
        
        color_A = new Color((r+g)/2, (g+b)/2, (b+r)/2);
        color_B = new Color(r, g, b);        
        color_C = new Color((r+b)/2, (g+r)/2, (b+g)/2);
        color_D = new Color((r+b)/4, (g+r)/4, (b+g)/4);
    }
    
    public void calculateNext(){
        int neighbors;
        for(int i=0; i<Grid_Width; i++)
            for(int j=0; j<Grid_Height; j++){
                neighbors = getNeighbors(i,j);
                if(neighbors==3 || (neighbors==2 && grid[i][j]>0)) gridNext[i][j] = 1;
                else gridNext[i][j] = 0;
            }
    }
    
    public int getNeighbors(int i, int j){
        int num = 0;
        i += Grid_Width;
        j += Grid_Height;
        if(grid[(i+1) % Grid_Width][j     % Grid_Height] > 0) num++;
        if(grid[(i-1) % Grid_Width][j     % Grid_Height] > 0) num++;
        if(grid[i     % Grid_Width][(j+1) % Grid_Height] > 0) num++;
        if(grid[i     % Grid_Width][(j-1) % Grid_Height] > 0) num++;
        if(grid[(i+1) % Grid_Width][(j+1) % Grid_Height] > 0) num++;
        if(grid[(i-1) % Grid_Width][(j-1) % Grid_Height] > 0) num++;
        if(grid[(i+1) % Grid_Width][(j-1) % Grid_Height] > 0) num++;
        if(grid[(i-1) % Grid_Width][(j+1) % Grid_Height] > 0) num++;
        return num;
    }
    
    public void setNext(){
        for(int i=0; i<Grid_Width; i++)
            for(int j=0; j<Grid_Height; j++){
                grid[i][j] = gridNext[i][j];
            }
    }
    
    public void render(Graphics g){
        g.setColor(color_BG);
        g.fillRect(0, 0, Main.Width, Main.Height);
        for(int i=0; i<Grid_Width; i++){
            for(int j=0; j<Grid_Height; j++){
                if(grid[i][j] != 0){
                    if(grid[i][j] == 1) g.setColor(color_D);
                    else if(grid[i][j] == 2) g.setColor(color_C);
                    else if(grid[i][j] == 3) g.setColor(color_B);
                    else g.setColor(color_A);
                    g.fillRect(i*Cell_Pixel +Shift, j*Cell_Pixel +Shift, Cell_Pixel-Gap, Cell_Pixel-Gap);
                }
             }
        }
    }
    
    private void randomizeGrid(){
        for(int i=0; i<Grid_Width; i++)
            for(int j=0; j<Grid_Height; j++){
                if(rand.nextInt(100) < Chance) grid[i][j] = 1;
                else grid[i][j] = 0;
            }
    }
    
    public void chooseColors(){
        for(int i=0; i<Grid_Width; i++)
            for(int j=0; j<Grid_Height; j++){
                if(grid[i][j] > 0) grid[i][j] = getNeighbors(i,j);
            }
    }
}