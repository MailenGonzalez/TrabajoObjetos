import java.util.Comparator;

public class ComparadorPair implements Comparator<Pair> {

	@Override
	public int compare(Pair o1, Pair o2) {
		if ((Long)o1.getValor() >(Long) o2.getValor())
			return -1;
		if ((Long)o1.getValor() < (Long)o2.getValor())
			return 1;
		else 
			return 0;
	}

}
