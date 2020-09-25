package Rubik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class Problema {
	
	public static Scanner TC = new Scanner(System.in);
	
	public enum Estrategia{
		Profundidad, Anchura, CosteUniforme, Greddy, A
	}
	private static int id;
	
	public Problema() {
		id=0;
	}
	
	public static void busquedaProfundidad(Estado estadoInicial, int profMax) {	
	Frontera frontera = new Frontera(3000000);

	frontera.insertar(new Nodo(estadoInicial,0,"none",0,null,1,id,Heuristica(estadoInicial)));
	ArrayList<String> visitados = new ArrayList<String>();
	Nodo actual;
	while(!frontera.estaVacia() && !EsObjetivo(frontera.first().getEstado().getCube())) {
		actual = frontera.peek();
		if(actual.getD()<profMax && !visitados.contains((actual.getEstado().getCube().getMd5()))) {
			ArrayList<Estado> ls = Estado.sucesores(actual.getEstado());
			ArrayList<Nodo> ln = creaListaNodo(ls, actual, actual.getD(), Estrategia.Profundidad);
			frontera.insertarLista(ln);
			visitados.add(actual.getEstado().getCube().getMd5());
		}
	}
	System.out.println("Consola");
	actual = frontera.peek();
	if(actual!=null) {
		if(EsObjetivo(actual.getEstado().getCube())) {
			generarSolucion(actual);
		}else{
			System.out.print("SIN SOLUCION");
		}
	}
	else
		System.out.print("SIN SOLUCION");
	
}
	
	public static void busquedaAcotada(int profMax, Estado estadoInicial, Estrategia estrategia, int id) {
		
		id=0;
		PriorityQueue<Nodo> frontera = new PriorityQueue<Nodo>();

		Nodo inc;
		if(estrategia == Estrategia.A)
			inc = new Nodo(estadoInicial,0,"none",0,null,Heuristica(estadoInicial),id,Heuristica(estadoInicial));
		else if( estrategia == Estrategia.Profundidad)
			inc = new Nodo(estadoInicial,0,"none",0,null,1,id,Heuristica(estadoInicial));
		else
			inc = new Nodo(estadoInicial,0,"none",0,null,0,id,Heuristica(estadoInicial));
		
		ArrayList<String> visitados = new ArrayList<String>();
		ArrayList<Estado> ls = Estado.sucesores(inc.getEstado());
		ArrayList<Nodo> ln = creaListaNodo(ls, inc, inc.getD(), estrategia);
		for(Nodo n : ln) 
			frontera.add(n);
		Nodo actual = null;
		while(!frontera.isEmpty() && !EsObjetivo((actual=frontera.remove()).getEstado().getCube())) {
			if(actual.getD()<profMax && !visitados.contains((actual.getEstado().getCube().getMd5()))) {
				if(actual.getId() == id)
					System.out.println("El nodo que buscas es :" + actual.toString2() + "\n");
				ls = Estado.sucesores(actual.getEstado());
				ln = creaListaNodo(ls, actual, actual.getD(), estrategia);
				for(Nodo n : ln) 
					frontera.add(n);
				visitados.add(actual.getEstado().getCube().getMd5());
			}
		}
		if(actual!=null)
			if(EsObjetivo(actual.getEstado().getCube())) {
				System.out.println(estrategia);
				Stack<String> st = new Stack<String>();	
				while(actual.getPadre() != null) {
					st.add(actual.toString());
					actual = actual.getPadre();
				}
				st.add(actual.toString());
				while(!st.isEmpty()) {
					System.out.println(st.pop());
				}
				System.out.println("\n");
			}
		else
			System.out.print("SIN SOLUCION");


	}
	
		public static void busquedaAcotada(int profMax, Estado estadoInicial, Estrategia estrategia) {
			
		id=0;
		
		PriorityQueue<Nodo> frontera = new PriorityQueue<Nodo>();

		Nodo inc;
		if(estrategia == Estrategia.A)
			inc = new Nodo(estadoInicial,0,"none",0,null,Heuristica(estadoInicial),id,Heuristica(estadoInicial));
		else if( estrategia == Estrategia.Profundidad)
			inc = new Nodo(estadoInicial,0,"none",0,null,1,id,Heuristica(estadoInicial));
		else
			inc = new Nodo(estadoInicial,0,"none",0,null,0,id,Heuristica(estadoInicial));
		
		ArrayList<String> visitados = new ArrayList<String>();
		ArrayList<Estado> ls = Estado.sucesores(inc.getEstado());
		ArrayList<Nodo> ln = creaListaNodo(ls, inc, inc.getD(), estrategia);
		for(Nodo n : ln) 
			frontera.add(n);
		Nodo actual = null;
		while(!frontera.isEmpty() && !EsObjetivo((actual=frontera.remove()).getEstado().getCube())) {
			if(actual.getD()<profMax && !visitados.contains((actual.getEstado().getCube().getMd5()))) {
				ls = Estado.sucesores(actual.getEstado());
				ln = creaListaNodo(ls, actual, actual.getD(), estrategia);
				for(Nodo n : ln) 
					frontera.add(n);
				visitados.add(actual.getEstado().getCube().getMd5());
			}
		}
		if(actual!=null)
			if(EsObjetivo(actual.getEstado().getCube())) {
				generarSolucion(actual);
			}
		else
			System.out.print("SIN SOLUCION");

	}

	
	public static ArrayList<Nodo> creaListaNodo(ArrayList<Estado> estados, Nodo actual, double prof, Estrategia estrategia) {
			
			ArrayList<Nodo> list = new ArrayList<Nodo>();	
			
			for(Estado e : estados) {
				id+=1;
				switch(estrategia) {
					case Profundidad:
						list.add(new Nodo(e, actual.getCosto()+1,e.getAccion(), prof+1, actual, 1/(prof+2), id, Heuristica(e)));
						break;
					case Anchura:
						list.add(new Nodo(e, actual.getCosto()+1,e.getAccion(), prof+1, actual,prof+1, id, Heuristica(e)));
						break;
					case CosteUniforme:
						list.add(new Nodo(e, actual.getCosto()+1,e.getAccion(), prof+1, actual,actual.getCosto()+1, id, Heuristica(e)));
						break;
					case Greddy:
						list.add(new Nodo(e, actual.getCosto()+1,e.getAccion(), prof+1, actual, Heuristica(e), id, Heuristica(e)));
						break;
					case A:
						list.add(new Nodo(e, actual.getCosto()+1,e.getAccion(), prof+1, actual, Heuristica(e)+actual.getCosto()+1, id, Heuristica(e)));
						break;
					default:
						System.out.println("DEFAULT");
						break;
				}
			}	
			return list;
		}
	
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////

	public static double Heuristica(Estado e) {
		return ((double)Math.round(Math.abs(Entropia(e.getCube().getBack())+Entropia(e.getCube().getFront())+Entropia(e.getCube().getUp())+
				Entropia(e.getCube().getDown())+Entropia(e.getCube().getLeft())+Entropia(e.getCube().getRight()))*100))/100;
	}
	
	public static double Entropia(int[][] m) {
		double entropia = 0;
		int[] contador = Contador(m);
		for(int i = 0; i <= 5; i++)
			if (contador[i] > 0)
				entropia += (contador[i]/Math.pow(m.length, 2))*(Math.log10((contador[i]/Math.pow(m.length, 2)))/Math.log10(6));
		return entropia;
	}
	
	public static int[] Contador(int[][] m) {
		int[] contador = new int[6];
		for(int k = 0; k <= 5; k++) 
			contador[k] = 0;
		for(int i = 0; i < m.length; i++)	
			for(int j = 0; j < m.length; j++) 
				contador[m[i][j]] += 1;
		
		return contador;
		
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////

	public static boolean EsObjetivo(Cube cube) {
		
		ArrayList<Integer> list = new ArrayList<Integer>();

		NoRepite(cube.getBack(), list);
		NoRepite(cube.getFront(), list);
		NoRepite(cube.getUp(), list);
		NoRepite(cube.getDown(), list);
		NoRepite(cube.getRight(), list);
		NoRepite(cube.getLeft(), list);

		if(list.size()==6) {
			for(int i : list) 
				if(Collections.frequency(list, i) != 1) 
					return false;
			return true;
		}else
			return false;
		
	}

//	public static boolean EsObjetivo(Cube cube) {
//		
//		ArrayList<Integer> list = new ArrayList<Integer>();
//
//		NoRepite(cube.getUp(), list);
//		NoRepite(cube.getDown(), list);
//		NoRepite(cube.getFront(), list);
//		NoRepite(cube.getBack(), list);
//		NoRepite(cube.getLeft(), list);
//		NoRepite(cube.getRight(), list);
//		for(int i = 0; i <list.size(); i++) 
//			if(list.get(i) != i) 
//				return false;
//
//		return true;
//		
//	}
	
	public static void NoRepite(int matriz[][], ArrayList<Integer> ceroalcinco  ) {
		boolean b = true;
		for(int i = 0; i< matriz.length; i++)	
			for(int j = 0; j< matriz.length; j++) 
				if(matriz[i][j] != matriz[0][0] || (matriz[i][j] < 0 && matriz[i][j] >5)) 
					b = false;
		if(b)
			ceroalcinco.add(matriz[0][0]);

	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	
	public static void generarSolucion(Nodo actual) {
		Stack<String> st = new Stack<String>();	
		while(actual.getPadre() != null) {
			st.add(actual.toString());
			actual = actual.getPadre();
		}
		st.add(actual.toString());
		while(!st.isEmpty()) {
			System.out.println(st.pop());
		}
		System.out.println("\n");
	}
	
	public static void mostrar(Nodo[] nodos) {
		for (int i = 0; i < nodos.length; i++)
			System.out.println("\t" + nodos[i].toString3());
	}
	
}
