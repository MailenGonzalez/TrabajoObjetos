import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.json.simple.JSONObject;

public class ProcesadorTestFelder extends ProcesadorEncuesta {
	private int[] dimensiones = new int[4];
	private HashMap<String, Pair> puntuacion = new HashMap <String, Pair>();
	
	
	private void leerArchivoDatos() throws FileNotFoundException{
		DataInputStream dato = new DataInputStream(new FileInputStream("./datosFelder.txt"));
		String tmp;
		try{
			while ((tmp= dato.readLine()) != null){
				String[] temporal= tmp.split(";");
				Pair par = new Pair(temporal[1], temporal[2]);
				puntuacion.put(temporal[0], par);
			}
		}
		catch(IOException exc){
			
		}
	}
	
	public ProcesadorTestFelder(){
		for (int i=0; i< 4; i++){
			dimensiones[i] = 0;
		}
		try{
			leerArchivoDatos();
		}
		catch(IOException exc){
			
		}
		super.nombre="felder";
	}
	
	public JSONObject getresultado(JSONObject respuesta){
	for (int i=0; i< 4; i++){
		dimensiones[i] = 0;
	}
	JSONObject obj = new JSONObject();
	Set<String> claves = respuesta.keySet();
	Set<String> claveP = puntuacion.keySet();
	for(String s: claves){
		Object res = respuesta.get(s);
		Pair par = puntuacion.get( res );
		if (par != null){
			Integer numero = Integer.parseInt(par.getNombre().toString().trim());// chequear que reseteo las dimensiones en cada json
			dimensiones[(numero% 4)] = dimensiones[(numero% 4)] + Integer.parseInt(par.getValor().toString().trim());
		}
	}
	for (int i=0; i<4; i++){
		obj.put(getAtributo(i), Math.abs(dimensiones[i]));
	}
	return obj;
	
	}

	private String getAtributo(int i) {
		if (i==0){
			if (dimensiones[i] < 0)
				return "global";
			else
				return "secuencial";
		}
		else if (i==1){
			if (dimensiones[i]<0)
				return "reflexivo";
			else
				return "activo";
		}
		else if(i==2){
			if(dimensiones[i]<0)
				return "intuitivo";
			else
				return "sensorial";
		}
		else
			if(dimensiones[i]<0)
				return "verbal";
			else
				return "visual";
	}


}
