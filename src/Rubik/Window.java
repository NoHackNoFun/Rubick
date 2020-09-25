package Rubik;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private Cube cube;

    public Window(Cube cube) {
    	this.cube = cube;
    	make();
    }
    
    public void make() {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, cube.getBack().length*250, cube.getBack().length*200);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
    }
    
    public static Color colorSwitch(int id) {
    	switch(id) {
    	case 0:
    		return Color.red;
    	case 1:
    		return Color.blue;
    	case 2:
    		return Color.yellow;
    	case 3:
    		return new Color(0,153,0);
    	case 4:
    		return Color.orange;
    	case 5:
    		return Color.white;
    	default:
    		return Color.magenta;
    	}
    	
    }
    
    public void paint (Graphics g)
    {
        super.paint(g);
        int n = cube.getBack().length;
		paintMatriz(g, cube.getBack(), n*50+50+5, 50);
		g.drawString("BACK", n*50+50+5, 45);
		g.drawString(cube.getMd5(), n*100+50+10, 45);
		paintMatriz(g, cube.getLeft(), 50, n*50+50+5);
		g.drawString("LEFT", 50, n*50+50);
		paintMatriz(g, cube.getDown(), n*50+50+5, n*50+50+5);
		g.drawString("DOWN", n*50+10, n*50+50);
		paintMatriz(g, cube.getRight(), n*100+50+10, n*50+50+5);
		g.drawString("RIGHT", n*100+50+10, n*50+50);
		paintMatriz(g, cube.getUp(), n*150+50+15, n*50+50+5);
		g.drawString("UP", n*150+50+15, n*50+50);
		paintMatriz(g, cube.getFront(), n*50+50+5, n*100+50+10);
		g.drawString("FRONT", n*50+10, n*100+50+20);

//		FileWriter f = new FileWriter("C:\\Users\\AGD\\image.png");
//		f.write(g);
//	   	  g.drawChars("Prueba".toCharArray(), 10, 10, 10, 10);
//        int [] vx2 = {500, 550, 450};
//        int [] vy2 = {270, 320, 320};
//        g.fillPolygon (vx2, vy2, 3);
    }
    
    public void paintMatriz(Graphics g, int matriz[][], int x, int y) {
    	int aux = x;
		for (int i = 0; i < matriz.length; i++) {
			x = aux;
			  for (int j=0; j < matriz.length; j++) {
				  	g.setColor(colorSwitch(matriz[i][j]));
			        g.fillRect(x, y, 50, 50);
			        g.setColor(Color.black);
			        g.drawRect(x, y, 50, 50);
//			        g.drawString("(" + i + "," + j + ")", x, y+10);
			        x +=50;
			  }
			  y +=50;
		}
    }

	public Cube getCube() {
		return cube;
	}

	public void setCube(Cube cube) {
		this.cube = cube;
	}
    
    
}
