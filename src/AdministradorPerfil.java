import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class AdministradorPerfil {
public boolean existePerfil(String nombre){
	File perfil = new File("./Perfiles/"+nombre);
	if (perfil.exists())
		return true;
	else
		return false;
}
public  void agregarEncuesta(String nombre,JSONObject resultado, String encuesta) throws IOException{
    String dCarpeta= "./Perfiles/"+nombre;
    String dArchivo= "/"+encuesta+".json";
    File file = new File(dCarpeta+dArchivo);
    File usuario = new File (dCarpeta);
    if(!file.exists()){
 	   usuario.mkdirs();
		file.createNewFile();
		FileWriter fw = new FileWriter(file);
		fw.write(resultado.toJSONString());
		fw.flush();
		fw.close();
	} 
    
 	else{
 		  FileWriter fw = new FileWriter(file);
 		  fw.write(resultado.toJSONString());
 			fw.flush();
 			fw.close();
 	   }
} 

public JSONObject getEncuesta(String nombre, String encuesta){
	JSONObject resultado = new JSONObject();
	String direccion = "./Perfiles/"+nombre+"/"+encuesta+".json";
	File file = new File(direccion);
	if(file.exists()){
		JSONParser parser = new JSONParser();
		Object json;
		try {
			json = parser.parse(new FileReader(file));
			resultado=(JSONObject) json;
		    resultado.put("Test", encuesta);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}	
	else
		return null;
}
public JSONObject getEncuestaGrupal(String nombre, String encuesta){
	JSONObject resultado = new JSONObject();
	String direccion = "./PerfilesGrupales/"+nombre+"/"+encuesta+".json";
	File file = new File(direccion);
	if(file.exists()){
		JSONParser parser = new JSONParser();
		Object json;
		try {
			json = parser.parse(new FileReader(file));
			resultado=(JSONObject) json;
		    resultado.put("Test", encuesta);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}	
	else
		return null;
}
public  void agregarEncuestaGrupal(String nombre,JSONObject resultado, String encuesta) throws IOException{
    String dCarpeta= "./PerfilesGrupales/"+nombre;
    String dArchivo= "/"+encuesta+".json";
    File file = new File(dCarpeta+dArchivo);
    File usuario = new File (dCarpeta);
    if(!file.exists()){
 	   usuario.mkdirs();
		file.createNewFile();
		FileWriter fw = new FileWriter(file);
		fw.write(resultado.toJSONString());
		fw.flush();
		fw.close();
	} 
    
 	else{
 		  FileWriter fw = new FileWriter(file);
 		  fw.write(resultado.toJSONString());
 			fw.flush();
 			fw.close();
 	   }
} 
}
