import java.util.ArrayList;
import java.util.Set;

import org.json.simple.JSONObject;

public class Perfil {
private String nombre;
private ArrayList<JSONObject> tests;
private long factorOrdenamiento;
private AdministradorPerfil ap;

public Perfil (String nombre){
	this.nombre=nombre;
	tests= new ArrayList<JSONObject>();
	factorOrdenamiento=0;
	ap= new AdministradorPerfil();
}
public Perfil(String nombre , ArrayList<String> tests){
	this.nombre= nombre;
	this.tests= new ArrayList<JSONObject>();
	factorOrdenamiento=0;
	ap= new AdministradorPerfil();
	cargarTest(tests);
		
}

public void cargarTest(ArrayList<String> test){
	for (String t:test){
		tests.add(ap.getEncuesta(nombre, t));
	}
		
}
public Set<String> getClaves(String test){
	for(JSONObject jo: tests){
	if(jo.get("Test").equals(test)){
		return jo.keySet();
	}
}
    return null;
}
public Long getAtributo(String atributo,String test){
	Long valor=(long) 0;
	for (JSONObject jo: tests){
		if (jo.get("Test").equals(test)){
			if (jo.containsKey(atributo))
				valor=(Long)jo.get(atributo);
		}
	}
	return valor;
	
}
public JSONObject getTest(String test){
	for(JSONObject jo: tests){
		if(jo.get("Test").equals(test)){
			return jo;
		}
	}
	return null;
}
@Override
public String toString() {
	return "Perfil [nombre=" + nombre + "]";
}
public void setAtributo(String atributo, String test, Long valor){
	for(JSONObject jo: tests){
		if(jo.get("Test").equals(test)){
			jo.put(atributo,(long) valor);
		}
	}
}
public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}

public ArrayList<JSONObject> getTests() {
	return tests;
}

public void setTests(ArrayList<JSONObject> tests) {
	this.tests = tests;
}

public long getFactorOrdenamiento() {
	return factorOrdenamiento;
}

public void setFactorOrdenamiento(long factorOrdenamiento) {
	this.factorOrdenamiento = factorOrdenamiento;
}

}