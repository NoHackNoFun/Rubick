package Rubik;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import Rubik.Problema.Estrategia;

public class Main {
	
	public static Scanner Teclado = new Scanner(System.in);
	public static final String tittle = "\r\n" + 
	"██████╗ ██╗   ██╗██████╗ ██╗ ██████╗██╗  ██╗███████╗     ██████╗██╗   ██╗██████╗ ███████╗\r\n" + 
	"██╔══██╗██║   ██║██╔══██╗██║██╔════╝██║ ██╔╝██╔════╝    ██╔════╝██║   ██║██╔══██╗██╔════╝\r\n" + 
	"██████╔╝██║   ██║██████╔╝██║██║     █████╔╝ ███████╗    ██║     ██║   ██║██████╔╝█████╗  \r\n" + 
	"██╔══██╗██║   ██║██╔══██╗██║██║     ██╔═██╗ ╚════██║    ██║     ██║   ██║██╔══██╗██╔══╝  \r\n" + 
	"██║  ██║╚██████╔╝██████╔╝██║╚██████╗██║  ██╗███████║    ╚██████╗╚██████╔╝██████╔╝███████╗\r\n" + 
	"╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝     ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝\r\n" + 
	"";
	
	public static final String help = "Los distintos algoritmos de busqueda que puede utilizar son:\n\tBusquedas Informadas\n\t\t- A*\n\t\t- Greddy (Voraz)\n\tBusqueda No Informadas\n\t\t" + 
			"- Depth (Profundidad)\n\t\t- Uniform (Coste Uniforme)\n\t\t- Breadth (Anchura)" +
			"\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\n" + 
			"La profundidad máxima deber siempre mayor que 0." +
			"\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\n" + 
			"Grupo de Teoría: 3ºB\r\n" + 
			"Grupo de Laboratorio: B2.\r\n\r\n"+
			"El programa ha sido realizado por:\n\tAndrés González Díaz - \t\t\t\tandres.gonzalez8@alu.uclm.es\n\tNoelia María Granados Carrasco - " + 
			"\t\tnoeliamaria.granados@alu.uclm.es\n\tJulian Vicente García Villarrubia Naranjo - \tjulianvicente.garcia@alu.uclm.es"+
			"\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\n";
	
	public static void main(String[] args) throws IOException {
		
//		menu();
		System.out.println("Comeeeee");
		System.out.println("Archivo: cube2");
		Problema.busquedaAcotada(6, new Estado(Reader.leerJSON("cube2.json")), Problema.Estrategia.Profundidad);
//		System.out.println(new Nodo(new Estado(Reader.leerJSON("cube2.json")),0,"none",0,null,1,0,Problema.Heuristica(new Estado(Reader.leerJSON("cube2.json")))).toString2());
		
		
		
	}
	
	public static void menu() throws IOException {
		String comand ;
		StringTokenizer st;
		String opcion ="";
		String json;
		int prof_max;
		String estrategia;
		
		System.out.println(tittle);
		System.out.println("BIENVENIDO AL CUBO DE RUBIK\n");
		System.out.println("Comandos:\n\tInformacion - \thelp\n\tEjecucion - \tresolve <nombre_archivo> <prof_max> <busqueda>\n\tSalir - \texit\n");
		
		do {
			comand = Teclado.nextLine();
			st = new StringTokenizer(comand);
			System.out.println();
			if(!comand.equals("")) 
				opcion = st.nextToken();
			if(opcion.equals("help")) 
				System.out.println(help);
			else if (opcion.equals("resolve")){
				try {
					json = new File (".").getCanonicalFile() + "\\" + st.nextToken();
					prof_max = Integer.parseInt(st.nextToken());
					estrategia = st.nextToken();
					if(prof_max <= 0)
						estrategia = "ERROR";
					if(st.hasMoreTokens())
						resolve(estrategia, prof_max, json, Integer.parseInt(st.nextToken()));
					else
						resolve(estrategia, prof_max, json);

				}catch(Exception e) {
					System.out.println("Error al introducir el comando, vuelve a intertalo con uno valido. En caso de duda realizar el comando \"help\".\n");
				}
			}else if (!opcion.equals("exit") && !opcion.equals(""))
				System.out.println("Error al introducir el comando, vuelve a intertalo con uno valido. En caso de duda realizar el comando \"help\".\n");

		}while(!opcion.equals("exit"));

		System.out.println("Fin de programa");
	}
	
	public static void resolve(String estrategia,int prof_max, String json) {
		switch (estrategia) {
		case "A*":
			ejecutar(Problema.Estrategia.A, prof_max, json);
			break;
		case "Greddy":
			ejecutar(Problema.Estrategia.Greddy, prof_max, json);
			break;
		case "Depth":
			ejecutar(Problema.Estrategia.Profundidad, prof_max, json);
			break;
		case "Breadth":
			ejecutar(Problema.Estrategia.Anchura, prof_max, json);
			break;
		case "Uniform":
			ejecutar(Problema.Estrategia.CosteUniforme, prof_max, json);
			break;
		default:
			System.out.println("Error al introducir el comando, vuelve a intertalo con uno valido. En caso de duda realizar el comando \"help\".\n");
			break;
		}
	}

	public static void resolve(String estrategia,int prof_max, String json, int id) {
		switch (estrategia) {
		case "A*":
			ejecutar(Problema.Estrategia.A, prof_max, json, id);
			break;
		case "Greddy":
			ejecutar(Problema.Estrategia.Greddy, prof_max, json, id);
			break;
		case "Depth":
			ejecutar(Problema.Estrategia.Profundidad, prof_max, json, id);
			break;
		case "Breadth":
			ejecutar(Problema.Estrategia.Anchura, prof_max, json, id);
			break;
		case "Uniform":
			ejecutar(Problema.Estrategia.CosteUniforme, prof_max, json, id);
			break;
		default:
			System.out.println("Error al introducir el comando, vuelve a intertalo con uno valido. En caso de duda realizar el comando \"help\".\n");
			break;
		}
	}

	public static void ejecutar(Problema.Estrategia estrategia, int profundidad, String json) {
		
		try {
			double comienzo = System.currentTimeMillis();
			if(estrategia == Estrategia.Profundidad)
				Problema.busquedaProfundidad(new Estado(Reader.leerJSON(json)), profundidad);
			else
				Problema.busquedaAcotada(profundidad, new Estado(Reader.leerJSON(json)), estrategia);
			double terminar = System.currentTimeMillis();
			System.out.println("El algoritmo ha tardado " + ((terminar - comienzo) / 1000 + " segundos\n"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void ejecutar(Problema.Estrategia estrategia, int profundidad, String json, int id) {
		
		try {
			double comienzo = System.currentTimeMillis();
			Problema.busquedaAcotada(profundidad, new Estado(Reader.leerJSON(json)), estrategia, id);
			double terminar = System.currentTimeMillis();
			System.out.println("El algoritmo ha tardado " + ((terminar - comienzo) / 1000 + " segundos\n"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}