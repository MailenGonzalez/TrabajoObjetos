import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;

/*
 * Esta estrategia es compartida con los test de Belbin y los de conflictos (Kilman). Su manera de agrupar es igual.
 * En el constructor se declara que test es por parametro.
 */
public class EstrategiaDistintos extends EstrategiaSimple{
	private String test;
public EstrategiaDistintos(Criterio c,String test){
	super();
	super.criterio=c;
	if (test.equals("belbin")){
		super.atributos= Arrays.asList("implementer","coordinator","shaper","plant","resourceInvestigator","monitor","teamWorker","completer");	}
	else if (test.equals("conflictos")){
		super.atributos= Arrays.asList("evasion","acomodo","compromiso","competencia","colaboracion");
	}
	else{
		super.atributos= Arrays.asList("upward","downward","positive","negative","forward","backward");
	}
	this.test=test;
}
	@Override
	public ArrayList<ArrayList<Perfil>> generarGrupos(ArrayList<Perfil> seleccionados, int cantidad) {
		ArrayList<Perfil>perfiles= super.criterio.ordenar(seleccionados, test);
		ArrayList<ArrayList<Perfil>> grupos= new ArrayList<ArrayList<Perfil>>();
		int cantGrupos= perfiles.size() / cantidad;
		for (int i=0; i<cantGrupos;i++){
			grupos.add(i, new ArrayList<Perfil>());
		}
		boolean caractUsadas[][] = new boolean [cantGrupos][(super.atributos).size()];
		for(int fila=0; fila<cantGrupos; fila++){
			for(int col = 0; col < (super.atributos).size(); col++)
				caractUsadas[fila][col]=false;
		}
		ArrayList<Pair> atributos;
		int index;
		while (perfiles.size() >0){			
				for(int perfil = 0; perfil< cantGrupos; perfil++){
					index=0;
					if (perfiles.size() > 0){
						atributos = createList(perfiles.get(0));
						String caract = getAtributo(atributos,index);
						while((index < super.atributos.size()) && (caractUsadas[perfil][super.atributos.indexOf(caract)] == true)){
							caract = getAtributo(atributos, index);
							index++;

						}
						caractUsadas[perfil][super.atributos.indexOf(caract)] = true;
						grupos.get(perfil).add(perfiles.get(0));
						perfiles.remove(0);
						atributos.clear();
					}
				}
				int grupo=0;
				int aux = perfiles.size()-1;
				for (int perfil = perfiles.size()-1; (perfil >=0 && perfil > aux - cantGrupos) ; perfil --){
					index=0;
				
					atributos = createList(perfiles.get(perfiles.size()-1));
					String caract ;
					if (perfiles.size() > 0){
						caract = getAtributo(atributos, index);
						while ((index< super.atributos.size()) && (caractUsadas[grupo][super.atributos.indexOf(caract)] == true)){
							caract = getAtributo(atributos, index);
							index++;
						}
						caractUsadas[grupo][super.atributos.indexOf(caract)] = true;
						grupos.get(grupo).add(perfiles.get(perfiles.size()-1));
						perfiles.remove(perfiles.size()-1);	
						atributos.clear();
					}
					grupo++;
				}
			}
			for (ArrayList<Perfil> al:grupos){
				System.out.println("grupo");
				for (Perfil p : al){
					System.out.println(p);
				}
			}
		return grupos;
	
	}
	
	
	//Este metodo arma una lista ordenada de los roles. Ya que la estructura de JSON no permite su almacenamiento ordenado
		public ArrayList<Pair> createList(Perfil p){
			JSONObject test= p.getTest(this.test);
			Set<String> keys = test.keySet();
			ArrayList<Pair> list = new ArrayList<Pair>();
			for(String s:keys){
				if (s!= "Test"){
					Long valor= (Long) test.get(s);
					
					list.add(new Pair((String)s,valor));
				}
			}
			ComparadorPair cp= new ComparadorPair();
			list.sort(cp);
			return list;
		}
	
	public String getAtributo(ArrayList<Pair> list, int index){
		return (String) list.get(index).getNombre();
	}
	
	

}
