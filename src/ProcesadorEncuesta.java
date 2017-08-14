import org.json.simple.*;
public abstract class ProcesadorEncuesta {
	protected String nombre;
	
public void setNombre(String nombre){
	this.nombre=nombre;
}
public String getNombre(){
	return nombre;
}
public abstract JSONObject getresultado(JSONObject respuestas);

}
