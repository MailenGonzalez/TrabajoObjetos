
/*
    Esta clase se utiliza para poder realizar el sort de una lista de objetos Perfil
    devuelve 0 si el factorOrdenamiento de un objeto es igual a otro, -1 si es menor y 1 si es mayor
 */
import java.util.Comparator;

public class ComparadorPerfil implements Comparator<Perfil>{
	public int compare(Perfil p1, Perfil p2){
		if ((Long)p1.getFactorOrdenamiento() == (Long)p2.getFactorOrdenamiento()){
			return 0;
		}
		if ((Long)p1.getFactorOrdenamiento() > (Long)p2.getFactorOrdenamiento()){
			return -1;
		}
			return 1;
		
	}

}
