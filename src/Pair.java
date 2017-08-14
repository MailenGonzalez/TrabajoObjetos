
public class Pair {
private Object nombre;
private Object valor;

public Pair(Object n, Object v){
nombre = n;
valor = v;
}
public void setValor(Object valor){
	this.valor= valor;
}
public Object getValor() {
	return valor;
}
@Override
public String toString() {
	return "Pair [nombre=" + nombre + ", valor=" + valor + "]";
}
public Object getNombre() {
	return nombre;
}
public void setNombre(Object nombre) {
	this.nombre = nombre;
}

}