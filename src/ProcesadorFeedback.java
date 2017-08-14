import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import org.json.simple.JSONObject;


public class ProcesadorFeedback extends ProcesadorEncuesta {
	private File archivoRoles;
	private Hashtable<String,String> roles ;
	private AdministradorPerfil admPerfil;
	private File preguntasSymlog;
	private ProcesadorSymlog procSymlog;
	private ArrayList<String> miembros;
	
public ProcesadorFeedback(){
	archivoRoles = new File ("./rolesFeedback.txt");
	roles = new Hashtable<String, String>();
	admPerfil = new AdministradorPerfil();
	preguntasSymlog= new File ("./symlogFeedback.txt");
	super.nombre="feedback";
	try {
		cargarHash();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		procSymlog= new ProcesadorSymlog(new FileReader ("C:/Users/julio/Desktop/symlog.txt"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	
}
	
	private void cargarHash() throws FileNotFoundException {
		DataInputStream dato = new DataInputStream(new FileInputStream(archivoRoles));
		String tmp;
		try{
			while ((tmp= dato.readLine()) != null){
				String[] temporal= tmp.split(",");
				roles.put(temporal[0], temporal[1]);
			}
		}
		catch(IOException exc){
			
		}
}

	@Override
	public JSONObject getresultado(JSONObject respuestas) {
		try {
			setRoles(respuestas);
			setSymlog(respuestas);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void setRoles(JSONObject respuestas) throws IOException{
		//Deberia abrir los feedback de cada persona y setear su valor y volver a guardarlo..
		Hashtable <String,String >integrantes = new Hashtable<String,String>();
		Set<String> claves = respuestas.keySet();
		// obtengo los integrantes del grupo y los identifico como i1 por ejemplo
		integrantes.put("Tú",respuestas.get("Tù Nombre (0)")+" "+ respuestas.get("Tù Apellido (0)"));
		for (int i=1; i<7; i++){
			integrantes.put("i"+i, (String)respuestas.get("Nombre de tu compañero (i"+i+")") +" "+ (String)respuestas.get("Apellido de tu compañero (i"+i+")"));
		}
	
		//Cargo los roles con las personas que se eligieron
		Hashtable<String,String> asignaciones = new Hashtable<String,String>();
		Set<String> nombreRoles=roles.keySet();
		for(String rol:nombreRoles){
			for(String key:claves){
				if(key.endsWith(rol+"]")){
					asignaciones.put(rol, (String) respuestas.get(key));
					break;
				}
					
			}
		}
		claves= asignaciones.keySet();
		//Por cada rol, modifico el archivo feedback de roles de cada persona 
		JSONObject feedBack;
		for (String rol : claves){
			String integrante =integrantes.get(asignaciones.get(rol));
			feedBack=admPerfil.getEncuesta(integrante, "feedbackRoles");
			if (feedBack == null){
				feedBack = new JSONObject();
				for(String s:nombreRoles){
					String rolBelbin= roles.get(s);
					if (s.equals(rol))
						feedBack.put(rolBelbin,(int) 1);
					else
						feedBack.put(rolBelbin,(int) 0);
				}
				admPerfil.agregarEncuesta(integrante, feedBack, "feedbackRoles");		
			}
			else{
				for(String s: nombreRoles){
					String rolBelbin = roles.get(s);
					if (s.equals(rol)){
						Long valor= (Long) feedBack.get(rolBelbin);
						feedBack.put(rolBelbin, valor++);
					}
				}
				admPerfil.agregarEncuesta(integrante, feedBack, "feedBackRoles");
			}
		}
		
	
	}
	public void setSymlog(JSONObject respuestas) throws IOException{
		//Deberia modificar el symlog del grupo.
		JSONObject symlog = new JSONObject();
		DataInputStream dato = new DataInputStream(new FileInputStream(preguntasSymlog));
		String tmp;
		try{
			while ((tmp= dato.readLine()) != null){
				String[] temporal= tmp.split(";");
				String valor;
				if ((respuestas.get(temporal[0])).equals("2.0"))
					valor ="A VECES";
				else if ((respuestas.get(temporal[0])).equals("1.0"))
					valor ="NUNCA";
				else valor = "SIEMPRE";
					
				symlog.put(temporal[1], valor);
			}
		}
		catch(IOException exc){
			System.out.println("No se pudo leer el archivo de SymlogFeedback");
		}
		JSONObject resultado= procSymlog.getresultado(symlog);
		String grupo = (String) respuestas.get("Grupo Nro:");
		JSONObject feedGrupo = admPerfil.getEncuestaGrupal(grupo, "feedBackSymlog");
		System.out.println(resultado);
		Set<String> keys= resultado.keySet();
		if (feedGrupo != null){
			for(String s: keys){
				if(feedGrupo.get(s) == null)
					feedGrupo.put(s, (long)resultado.get(s));
				else{
					Long v1= (Long) resultado.get(s);
					Long v2 =  (Long) feedGrupo.get(s);
					feedGrupo.put(s, (long)v1+v2);
				}
			}
		}
		else
		{
			feedGrupo= new JSONObject();
			for(String s: keys)
					feedGrupo.put(s, (long)resultado.get(s));
		}
		System.out.println(feedGrupo);
		admPerfil.agregarEncuestaGrupal(grupo, feedGrupo, "feedBackSymlog");
	}

}
