import java.util.ArrayList;
import java.util.Set;

/*
    Clase simple que hereda de CriterioSimple, donde se maximiza un atributo multiplicando el valor de ese atributo por cierto 
    factor y luego se ordenan de mayor a menor los perfiles mediante la suma de todas sus caracteristicas
 */
public class CriterioMax extends CriterioSimple {
	CriterioMax(int factor, String atributo) {
		super(factor, atributo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<Perfil> ordenar(ArrayList<Perfil> perfiles, String test) {
		ArrayList<Perfil> resultado = perfiles;
		for(Perfil p: resultado){
			Long valor= (Long) (p.getAtributo(atributo, test)* Math.abs(super.factor));
			p.setAtributo(atributo, test, valor);
			//sumar(test, p); 
			Set<String> claves = p.getClaves(test);
			Long atributoOrdenamiento = (long) 0;
			for(String s: claves){
				if (s != "Test")
					atributoOrdenamiento += p.getAtributo(s, test);
			}
			p.setFactorOrdenamiento(atributoOrdenamiento);  
		    }
		System.out.println("Lista sin ordenar " + resultado);
		ComparadorPerfil c = new ComparadorPerfil();
		resultado.sort(c);
		System.out.println("Lista ordenada   " +resultado);
		return resultado;
	}

}
