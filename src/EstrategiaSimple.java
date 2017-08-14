import java.util.ArrayList;
import java.util.List;


public abstract class EstrategiaSimple extends Estrategia{
protected Criterio criterio;
protected List<String> atributos;
public EstrategiaSimple(Criterio c,List<String> atributos){
	atributos= new ArrayList<String>();
	this.criterio=c;
	this.atributos= atributos;
}

public EstrategiaSimple() {
	// TODO Auto-generated constructor stub
}

public abstract ArrayList<ArrayList<Perfil>> generarGrupos(ArrayList<Perfil> perfiles, int cantidad);
	

}
