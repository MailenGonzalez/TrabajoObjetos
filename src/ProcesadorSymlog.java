import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

import org.json.simple.JSONObject;


public class ProcesadorSymlog extends ProcesadorEncuesta{
	private int upward;
	private int downward;
	private int positive;
	private int negative;
	private int forward;
	private int backward;
	private FileReader f;
	private Hashtable<String, ArrayList<Integer>> table;
	
	public ProcesadorSymlog(FileReader f) throws IOException{
		upward=0;
		downward=0;
		positive=0;
		negative=0;
		forward=0;
		backward=0;
		this.f=f;
		table = new Hashtable<>();
		cargarHash(); 
		super.nombre="symlog";
		
	}
	public void cargarHash() throws IOException{
		// En esta Hash se carga que variables afecta cada pregunta
		BufferedReader br = new BufferedReader(f);
		String line = br.readLine();
		ArrayList<Integer> dimensiones = new ArrayList<>();
		while (line != null){
			int space= line.indexOf(" ");
			String key= line.substring(0, space);
			for (int i = space; i < line.length(); i++){
				char dimension = line.charAt(i);
				if (dimension != ' '){
					dimensiones.add(Integer.parseInt(dimension+""));
				}
			}
			table.put(key, new ArrayList<Integer>(dimensiones));
			dimensiones.clear();
			line= br.readLine();
		}
		
	}
	@Override
	public JSONObject getresultado(JSONObject respuestas) {
		// Dimensiones hace referencia a las 6 variables (Upward,Downward,Positive,Negative,Backward,Forward)
		int dimensiones[]= new int[6];
		for (int i =0; i<6; i++){
			dimensiones[i]=0;
		}
		ArrayList<Integer> variables = new ArrayList<Integer>();
		Set<String> claves= respuestas.keySet();
		for (String k : claves){
				int posicion = k.indexOf('-');
			if (posicion != -1){
				String dimension = k.substring(0,posicion);
				variables = table.get(dimension);
				int respuesta = getRespuesta((String) respuestas.get(k));
				for (int v: variables){
					dimensiones[v] += respuesta;
				}
			}
		}
		JSONObject resultado = new JSONObject();
		int dim1 = dimensiones[0] - dimensiones[1];
		if(dim1>0)
			resultado.put("upward", (long)dim1);
		else resultado.put("downward", (long)Math.abs(dim1));
		int dim2 = dimensiones[2] - dimensiones[3];
		if(dim2 >0 )
			resultado.put("positive",(long) dim2);
		else resultado.put("negative", (long)Math.abs(dim2));
		int dim3 = dimensiones[4]- dimensiones[5];
		if(dim3 >0 )
			resultado.put("forward", (long)dim3);
		else resultado.put("backward", (long)Math.abs(dim3));
		return resultado;
	}
	private int getRespuesta(String respuesta) {
		if (respuesta.equals("NUNCA"))
			return 0;
		if (respuesta.equals("A VECES"))
			return 1;
		if (respuesta.equals("SIEMPRE"))
			return 2;
		return 0;
	}

}
