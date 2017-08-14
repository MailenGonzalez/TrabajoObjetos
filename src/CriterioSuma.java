import java.util.ArrayList;
import java.util.Set;

public class CriterioSuma extends CriterioSimple{

	    CriterioSuma(int factor, String atributo) {
		super(factor, atributo);
		// TODO Auto-generated constructor stub
	}

		public CriterioSuma() {
			// TODO Auto-generated constructor stub
		}

		@Override
	    public ArrayList<Perfil> ordenar(ArrayList<Perfil> perfiles, String test) {
	        ArrayList<Perfil> resultado = perfiles;
	        for(Perfil p: resultado){
				Set<String> claves = p.getClaves(test);
				Long atributoOrdenamiento = (long) 0;
				for(String s: claves){
					if (s != "Test")
						atributoOrdenamiento += p.getAtributo(s, test);
				}
				p.setFactorOrdenamiento(atributoOrdenamiento);      
	        }
	        ComparadorPerfil c = new ComparadorPerfil();
	        resultado.sort(c);
	        System.out.println("Lista ordenada   "+ resultado);
	        return resultado;
	        
	    }
	}
