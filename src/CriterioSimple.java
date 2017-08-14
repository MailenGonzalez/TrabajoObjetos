import java.util.ArrayList;


public abstract class CriterioSimple extends Criterio{
	protected int factor;
	protected String atributo;
	CriterioSimple(int factor, String atributo){
		this.factor= factor;
		this.atributo= atributo;
	}
	CriterioSimple(){};
	public abstract ArrayList<Perfil> ordenar(ArrayList<Perfil> perfiles, String test);

}
