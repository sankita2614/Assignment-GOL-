package a_1;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
 
public class Main extends Canvas implements Runnable {    
    static String title = "GAME_OF_LIFE";
    private boolean running = false;
    private Thread thread;
    private JFrame frame;
    private Grid  grid;
    
    static int Width = 1200; 
    static int Height = 700; 
    private int Pixels_Of_Cell = 10; 
    private int Pixels_Of_Grid = 4; 
    private int Tick = 100; 
    private int Chance_Cell = 12; 
    private int Board_Life = 40; 
    private int Color_Life = 10; 
    
    public Main(){
        setPreferredSize(new Dimension(Width, Height));
        frame = new JFrame();
        grid = new Grid(Chance_Cell, Pixels_Of_Cell, Pixels_Of_Grid, Board_Life, Color_Life);
    }
    
    public synchronized void Start(){
        running = true;
        thread = new Thread(this, "Thread");
        thread.start();
    }
    
    public void run(){
        requestFocus();
        long lastTime = 0;
        while(running){
            long time = System.currentTimeMillis();
            if(time - lastTime >= Tick){
                lastTime = time;
                update();
                render();
                
            }
        }
        Stop();
    }
    
    public synchronized void Stop(){
        running = false;
        try{
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    
    
    public void update(){
        grid.update();
    }
    
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        grid.render(g);
        g.dispose();
        bs.show();
    }
    
    public static void main(String[] args){
        Main main = new Main();
        main.frame.setResizable(false);
        main.frame.setTitle(title);
        main.frame.add(main);
        main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.frame.setLocation(7, 0);
        main.frame.setVisible(true);
        main.frame.requestFocus();
        main.frame.pack();
        main.Start();
    }
}