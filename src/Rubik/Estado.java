package Rubik;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gson.Gson;

public class Estado {
	
	private Cube cube;
	private String accion;
	
	public Estado(Cube cube) {
		this.cube = cube;
	}
	
	public Estado(Cube cube, String accion) {
		this.cube = cube;
		this.accion = accion;
	}

	
	public static String escribirJSON(Cube cube) throws java.io.IOException {
		Gson gson = new Gson();
		//System.out.print(System.getProperties().getProperty("user.dir")+"\\cubeWritten.json");
		FileWriter file = new FileWriter(System.getProperties().getProperty("user.dir")+"\\cubeWritten.json");
		file.write(gson.toJson(cube));
		file.close();
	 
	 	return gson.toJson(cube).toString();
	 	//System.out.print("\n"+gson.toJson(cube));
	}
	
	//Sucesores
	public static ArrayList<Estado> sucesores(Estado estado) {
				
		String[] rotacion = {"+","-"};
		ArrayList<Estado> list = new ArrayList<Estado>();
		Cube cubo;
		for(String c : rotacion) 	
			for(int i = 0; i < estado.getCube().getBack().length; i++) {
				cubo = new Cube();
				copiarCubo(estado.getCube(), cubo);
				list.add(new Estado(cubo, moveBX(c,i,cubo)));
			}
		for(String c : rotacion) 	
			for(int i = 0; i < estado.getCube().getBack().length; i++) {
				cubo = new Cube();
				copiarCubo(estado.getCube(), cubo);
				list.add(new Estado(cubo, moveDX(c,i,cubo)));
			}
		for(String c : rotacion) 	
			for(int i = 0; i < estado.getCube().getBack().length; i++) {
				cubo = new Cube();
				copiarCubo(estado.getCube(), cubo);
				list.add(new Estado(cubo, moveLX(c,i,cubo)));
			}
		
//		Collections.sort(list, new Comparator<Estado>() {
//	        @Override
//	        public int compare(Estado e1, Estado e2)
//	        {
//	        	return e1.getCube().getMd5().compareTo(e2.getCube().getMd5());
//	        }
//	    });
		
		return list;
	}
	

	
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	//Move LX
	public static String moveLX(String b, int column, Cube cube) {
		if(column == 0) 
				cube.setLeft(traspuesta(cube.getLeft(),(b.charAt(0) == '+')? true : false));
		else if(column == (cube.getBack().length-1)) 
				cube.setRight(traspuesta(cube.getRight(),(b.charAt(0) == '+')? true : false));
		moveXZ((b.charAt(0) == '+')? true : false,column,cube);
		return ((b.charAt(0) == '+')? "L" : "l") + column;
				
	}
		
	public static void ejeY(Cube cube, int column) {
		int[][] aux = copiarMatriz(cube.getUp());
		int i,j;
		for(i = 0, j = cube.getBack().length-1; i < cube.getBack().length; i++, j--) {
			cube.getUp()[j][cube.getBack().length - 1 - column] = cube.getBack()[i][column];
			cube.getBack()[i][column] = cube.getDown()[i][column];
			cube.getDown()[i][column] = cube.getFront()[i][column];
			cube.getFront()[i][column] = aux[j][cube.getBack().length - 1 - column];
		}
	}
		
	public static void moveXZ(Boolean grade, int column, Cube cube) {
		if (grade)
			ejeY(cube, column);
		else
			for(int i = 0; i < 3; i++) ejeY(cube, column);
	}
			
			
	//Move DX
	public static String moveDX(String b, int column, Cube cube) {
		if(column == 0) 
			cube.setDown(traspuesta(cube.getDown(),(b.charAt(0) == '+')? true : false));
		else if(column == (cube.getBack().length-1)) 
			cube.setUp(traspuesta(cube.getUp(),(b.charAt(0) == '+')? true : false));
		moveXY((b.charAt(0) == '+')? true : false,column,cube);
		return ((b.charAt(0) == '+')? "D" : "d") + column;

	}
		
	public static void ejeZ(Cube cube, int column) {
		int[][] aux = copiarMatriz(cube.getBack());
		int i,j;
		for(i = 0, j =(cube.getBack().length-1); i< cube.getBack().length; i++, j--) {
			cube.getBack()[cube.getBack().length-1-column][j] = cube.getLeft()[i][cube.getBack().length-1-column];
			cube.getLeft()[i][cube.getBack().length-1-column] = cube.getFront()[column][i];
			cube.getFront()[column][i] = cube.getRight()[j][column];
			cube.getRight()[j][column] = aux[cube.getBack().length-1-column][j];
		}
	}
	
	public static void moveXY(Boolean grade, int column, Cube cube) {
		if (grade)
			ejeZ(cube, column);
		else
			for(int i = 0; i < 3; i++) ejeZ(cube, column);
	}
		
	
	//Move BX
	public static String moveBX(String b, int column, Cube cube) {
		if(column == 0) 
				cube.setBack(traspuesta(cube.getBack(),(b.charAt(0) == '+')? true : false));
		else if(column == (cube.getBack().length-1)) 
				cube.setFront(traspuesta(cube.getFront(),(b.charAt(0) == '+')? true : false));
		moveZY((b.charAt(0) == '+')? true : false,column,cube);
		return ((b.charAt(0) == '+')? "B" : "b") + column;
	}
		
	public static void ejeX(Cube cube, int column) {
		int[][] aux = copiarMatriz(cube.getDown());
		for(int i = 0; i< cube.getDown().length; i++) {
			cube.getDown()[column][i] = cube.getLeft()[column][i];
			cube.getLeft()[column][i] = cube.getUp()[column][i];
			cube.getUp()[column][i] = cube.getRight()[column][i];
			cube.getRight()[column][i] = aux[column][i];
		}
	}

	public static void moveZY(Boolean grade, int column, Cube cube) {
		if (grade)
			ejeX(cube, column);
		else
			for(int i = 0; i < 3; i++) ejeX(cube, column);
	}
		
		
	//Auxiliar Methods
		
	public static int[][] traspuesta(int matriz[][], Boolean b) {
		if(b)
			return giro(matriz);
		else 
			return giro(giro(giro(matriz)));
	}
			
	public static int[][] giro(int matriz[][]) {
		int[][] rotacion = new int[matriz.length][matriz.length];
		
		for(int i = 0, j = matriz.length - 1; i < matriz.length && j >= 0; i++, j--)
	        for(int k = 0; k < matriz.length; k++)
	            rotacion[k][j] = matriz[i][k]; 
		
		return rotacion;
	}
	
	public static void copiarCubo(Cube src, Cube dst) {
		dst.setBack( copiarMatriz(src.getBack()));
		dst.setDown( copiarMatriz(src.getDown()));
		dst.setUp( copiarMatriz(src.getUp()));
		dst.setRight( copiarMatriz(src.getRight()));
		dst.setLeft( copiarMatriz(src.getLeft()));
		dst.setFront( copiarMatriz(src.getFront()));

	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	
	public static void mostrarMatriz(int[][] matriz) {
		for (int i = 0; i < matriz.length; i++) {
			  for (int j=0; j < matriz.length; j++) 
				  System.out.print(matriz[i][j]+ " ");
			  System.out.println("");
		}
	}
		
	public static int[][] copiarMatriz(int[][] matriz) {
		int[][] rotacion = new int[matriz.length][matriz.length];
		for (int i = 0; i < matriz.length; i++)
			  for (int j=0; j < matriz.length; j++) 
				  rotacion[i][j]=matriz[i][j];
		return rotacion;
	}
		
	////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	
	public Cube getCube() {
		return cube;
	}

	public void setCube(Cube cube) {
		this.cube = cube;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

}
