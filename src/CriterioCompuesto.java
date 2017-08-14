
import java.util.ArrayList;

public class CriterioCompuesto extends Criterio{
    ArrayList<Criterio> criterios;
    public CriterioCompuesto(){}
    public CriterioCompuesto(ArrayList<Criterio>c){
    	this.criterios=c;
    }
    @Override
    public ArrayList<Perfil> ordenar(ArrayList<Perfil> perfiles, String test) {
        ArrayList<Perfil> resultado= perfiles;
        for(Criterio c: criterios){
            resultado = c.ordenar(resultado, test);
        }
        return resultado;
    }
    
}