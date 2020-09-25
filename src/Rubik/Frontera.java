package Rubik;

import java.util.ArrayList;
import java.util.Arrays;

public class Frontera {
	
	   private Nodo[] monticulo;
	   private int numElementos;
	   private int maxElementos;

	   public Frontera (int max) {
	      monticulo = new Nodo[max];
	      numElementos = 0;
	      maxElementos = max;
	   }

	   public Frontera (Nodo[] vector, int max) {
	      monticulo = new Nodo[max];
	      numElementos = vector.length-1;
	      maxElementos = max;
	      monticulo = Arrays.copyOf(vector, numElementos+1);
	      for (int i=0; i < monticulo.length; i++) 
	          this.flotar(i);
	   }
	   
	   public boolean estaVacia() {
		   return (numElementos == 0)? true : false;
		 }
	   
	   public void insertarLista(ArrayList<Nodo> nodos) {
		   for(Nodo n : nodos) 
			   insertar(n);
	   }
	   
	   public void flotar (int elemento) {
		   
		    while (elemento > 0 && 
		    		(monticulo[elemento-1].getF() > monticulo[elemento].getF() || 
		    				(monticulo[elemento-1].getF() == monticulo[elemento].getF() && (monticulo[elemento-1].getId() > monticulo[elemento].getId())))) {
		       mSwitch (elemento, elemento-1); 
		       elemento = elemento-1;
		   }
		 }
	   
	   public void hundir (int elemento) {
		   while (elemento < this.numElementos-1 && 
		    		(monticulo[elemento].getF() > monticulo[elemento+1].getF() || 
		    				(monticulo[elemento+1].getF() == monticulo[elemento].getF() && (monticulo[elemento+1].getId() < monticulo[elemento].getId())))) {
		       mSwitch (elemento, elemento+1); 
		       elemento = elemento+1;
		   }
		 }
	   
	   public void insertar(Nodo elemento) {
		    if (this.numElementos == this.maxElementos) {
		       System.out.println("Frontera llena y no podemos añadir nuevos elementos");
		    } else {
//		    	Boolean b = false;
//		    	for(int i = 0; i < this.numElementos; i++)
//		    		if(monticulo[i].getEstado().getCube().getMd5() == elemento.getEstado().getCube().getMd5() && elemento.getF() < monticulo[i].getF()) {
//		    			monticulo[i] = copiarNodo(elemento);
//		    			b = true;
//		    		}
//		    	if(!b) {
		    		monticulo[this.numElementos] = elemento;
		       		this.numElementos++;
//		    	}
		    	flotar(this.getNumElementos()-1);
		    }
		 }
	   
	   public Nodo first() {
		    return (this.estaVacia())? null:monticulo[0];
		 }
	   
	   public Nodo peek() {
		    if (this.numElementos != 0) {
		    	Nodo element = monticulo[0];
		    	for(int i = 0; i < this.numElementos; i++)
		    		mSwitch(i,i+1);
		    	this.numElementos--;
		    	
		    	return element;
		    }
		    return null;
		 }
	   

	protected void mSwitch(int i, int j) {
		if(this.monticulo[j] != null) {
			Nodo aux = copiarNodo(this.monticulo[i]);
			this.monticulo[i] = copiarNodo(this.monticulo[j]);
			this.monticulo[j] = copiarNodo(aux);
		}
	}
	
	public static Nodo copiarNodo(Nodo src) {
		//Estado estado, int costo, String accion, double d, Nodo padre, double f, int id
		Nodo dst = new Nodo(copiarEstado(src.getEstado()), src.getCosto(), src.getAccion(), src.getD(), src.getPadre(), src.getF(), src.getId(), src.getH());
		return dst;
	}
	
	public static Estado copiarEstado(Estado src) {
		Estado dst = new Estado(copiarCubo(src.getCube()));
		return dst;
	}
	
	public static Cube copiarCubo(Cube src) {
		Cube dst = new Cube();
		dst.setBack( copiarMatriz(src.getBack()));
		dst.setDown( copiarMatriz(src.getDown()));
		dst.setUp( copiarMatriz(src.getUp()));
		dst.setRight( copiarMatriz(src.getRight()));
		dst.setLeft( copiarMatriz(src.getLeft()));
		dst.setFront( copiarMatriz(src.getFront()));
		return dst;

	}
	
	public static int[][] copiarMatriz(int[][] matriz) {
		int[][] rotacion = new int[matriz.length][matriz.length];
		for (int i = 0; i < matriz.length; i++)
			  for (int j=0; j < matriz.length; j++) 
				  rotacion[i][j]=matriz[i][j];
		return rotacion;
	}
	
	public static void burbuja(Nodo[] matrix){
        Nodo temp;
        for(int i=1;i < matrix.length;i++){
            for (int j=0 ; j < matrix.length- 1; j++){
                if (matrix[j].getF() > matrix[j+1].getF()){
                    temp = copiarNodo(matrix[j]);
                    matrix[j] = copiarNodo(matrix[j+1]);
                    matrix[j+1] = copiarNodo(temp);
                }
            }
        }
    }

	public Nodo[] getMonticulo() {
		return monticulo;
	}

	public void setMonticulo(Nodo[] monticulo) {
		this.monticulo = monticulo;
	}

	public int getNumElementos() {
		return numElementos;
	}

	public void setNumElementos(int numElementos) {
		this.numElementos = numElementos;
	}

	public int getMaxElementos() {
		return maxElementos;
	}

	public void setMaxElementos(int maxElementos) {
		this.maxElementos = maxElementos;
	}
	   
	
	   

}
