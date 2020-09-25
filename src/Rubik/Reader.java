package Rubik;
import java.io.FileReader;
import com.google.gson.*;

public class Reader {

	
	public static Cube leerJSON(String file) throws java.io.IOException {
		Cube cube = new Cube();
        JsonParser parser = new JsonParser();
        FileReader fr = new FileReader(file);
        JsonElement datos = parser.parse(fr);
        dumpJSONElement(datos, cube);
        return cube;
    }	
	
	public static void dumpJSONElement(JsonElement elemento, Cube cube) {
	        JsonObject obj = elemento.getAsJsonObject();
	        java.util.Set<java.util.Map.Entry<String,JsonElement>> entradas = obj.entrySet();
	        java.util.Iterator<java.util.Map.Entry<String,JsonElement>> iter = entradas.iterator();
	        while (iter.hasNext()) {
	            java.util.Map.Entry<String,JsonElement> entrada = iter.next();
	            dumpJSONKey(entrada.getKey(), entrada.getValue(), cube);
	        }
	}
	
	public static void dumpJSONKey(String key, JsonElement entrada, Cube cube){
		switch(key) {
			case "BACK":
				cube.setBack(dumpJSONArray(entrada));
				break;
			case "LEFT":
				cube.setLeft(dumpJSONArray(entrada));
				break;
			case "DOWN":
				cube.setDown(dumpJSONArray(entrada));
			case "RIGHT":
				cube.setRight(dumpJSONArray(entrada));
				break;
			case "UP":
				cube.setUp(dumpJSONArray(entrada));
				break;
			case "FRONT":
				cube.setFront(dumpJSONArray(entrada));
				break;
		}
				
	}

	public static int[][] dumpJSONArray(JsonElement elemento){
		JsonArray array = elemento.getAsJsonArray();
		int[][] matriz = new int[array.size()][array.size()];
        java.util.Iterator<JsonElement> iter = array.iterator();
//		System.out.println("ARRAY: \t"+ array.toString());
        for(int i = 0; i < array.size() & iter.hasNext(); i++) {
            JsonElement entrada = iter.next();
            java.util.Iterator<JsonElement> iter2 = entrada.getAsJsonArray().iterator();
            for(int j = 0; j < array.size() & iter2.hasNext();j++) 
            	matriz[i][j] = Integer.parseInt(iter2.next().getAsJsonPrimitive().getAsString());
        }
        return matriz;
	}
	
	
}
