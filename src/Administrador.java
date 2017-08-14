import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONObject;

public class Administrador {
//private ArrayList<Perfil> perfilesActivos;
private LectorEncuesta lector;

public Administrador() {
	//this.perfilesActivos = new ArrayList<Perfil>();
	this.lector = new LectorEncuesta();
//	cargarLista();
}

public void cargarLista(){
	String sDirectorio = "./Perfiles";
	File directorio = new File(sDirectorio);
	if (directorio.exists()){ 
		File[] ficheros = directorio.listFiles();
		//for (int x=0;x<ficheros.length;x++){
			//perfilesActivos.add(new Perfil(ficheros[x].getName()));
	//	}
	}	
}

public static void main(String[] args) throws IOException{
	LectorEncuesta gestor= new LectorEncuesta();
	ProcesadorBelbin pb = new ProcesadorBelbin();
	File datos= new File("C:/Users/julio/Desktop/matriz.txt");
	ProcesadorManejoConflictos pmc= new ProcesadorManejoConflictos(datos);
	FileReader datosSymlog = new FileReader ("C:/Users/julio/Desktop/symlog.txt");
	ProcesadorSymlog ps= new ProcesadorSymlog(datosSymlog);
	ProcesadorTestFelder pf= new ProcesadorTestFelder();
	AdministradorPerfil ap = new AdministradorPerfil();
	ProcesadorFeedback procFeed = new ProcesadorFeedback();
	List <JSONObject> objetos=gestor.readExcelFile(new File("C:/Users/julio/Desktop/testSymlog.xls"));
	JSONObject resultado = new JSONObject();
	for(JSONObject jo:objetos){
		resultado = ps.getresultado(jo);
		if (resultado != null){
			String nombre = (String) jo.get("Nombre");
			if (jo.get("Apellido") != null)
				nombre+=" "+jo.get("Apellido");
			ap.agregarEncuesta(nombre,resultado,ps.getNombre() );
		}
	}
	/*CriterioMax critMax = new CriterioMax(10,"activo");
	CriterioMin critMin = new CriterioMin(10,"activo");
	CriterioSuma critSuma = new CriterioSuma();
	EstrategiaFelder ef = new EstrategiaFelder(critSuma);
	ArrayList<String> selec = new ArrayList<String>();
	selec.add("Carolina Bruscantini");
	selec.add("Emiliano Migliorata");
	selec.add("Eugenia Moris");
	selec.add("Franco D'achilli");
	selec.add("Gonzalo Aranda");
	selec.add("Joaquin Colacci");
	selec.add("Mailen Gozalez");
	selec.add("Milagros Safenreiter");
	selec.add("Tomas Viñals");
	selec.add("Valeria Lucchesi");
	ArrayList<Perfil> perfiles = new ArrayList<Perfil>();
	ArrayList<String> test= new ArrayList<String>();
	test.add("felder");
	for (String s: selec){
		perfiles.add(new Perfil(s, test));
	}
	for (Perfil p : perfiles){
		System.out.println(p.getNombre()+"  "+p.getTest("felder"));
	}
	ef.generarGrupos(perfiles, 3);
*/
	
CriterioMax criterio = new CriterioMax(1000, "positive");
	CriterioMin critMin = new CriterioMin (2,"negative");
	ArrayList<Criterio> list= new ArrayList<Criterio>();
	list.add(criterio);
	list.add(critMin);
	CriterioCompuesto critComp= new CriterioCompuesto(list);
	CriterioSuma suma = new CriterioSuma();
	EstrategiaDistintos eb= new EstrategiaDistintos(critComp,"symlog");
	ArrayList<String> selec = new ArrayList<String>();
	selec.add("Eugenia Moris");
	selec.add("Valeria Lucchesi");
	selec.add("Joaquin Colacci");
	selec.add("Mailen Gonzalez");
	selec.add("Juan Perez");
	selec.add("Mariana Lucchesi");
	selec.add("Martin Lucchesi");
	ArrayList<Perfil> perfiles = new ArrayList<Perfil>();
	ArrayList<String> test= new ArrayList<String>();
	test.add("symlog");
	for (String s: selec){
		perfiles.add(new Perfil(s, test));
	}
	for (Perfil p : perfiles){
		System.out.println(p.getNombre()+"  "+p.getTest("symlog"));
	}
	eb.generarGrupos(perfiles, 3);
	
	/*CriterioMax criterio = new CriterioMax(1000, "negative");
	CriterioMin critMin = new CriterioMin (3,"negative");
	CriterioSuma suma = new CriterioSuma();
	EstrategiaDistintos ed= new EstrategiaDistintos(criterio,"symlog");
	ArrayList<String> selec = new ArrayList<String>();
	selec.add("Mailen s");
	selec.add("Valeria dsadsa");
	selec.add("Eugenia s");
	selec.add("Joaquin dsadsa");
	selec.add("Franco ndjsnjasds");
	selec.add("Santiago gr");
	selec.add("Juan erw");
	selec.add("Martin fr");
	selec.add("Agustin safd");
	selec.add("Mariana fsd");
	selec.add("Agustina fsa");
	ArrayList<Perfil> perfiles = new ArrayList<Perfil>();
	ArrayList<String> test= new ArrayList<String>();
	test.add("symlog");
	for (String s: selec){
		perfiles.add(new Perfil(s, test));
	}
	ed.generarGrupos(perfiles,3 );
	*/
}
}
