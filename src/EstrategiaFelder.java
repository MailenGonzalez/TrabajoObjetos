import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EstrategiaFelder extends EstrategiaSimple {
	public EstrategiaFelder(Criterio c) {
        super(c, Arrays.asList("activo", "reflexivo", "sensorial", "intuitivo", "secuencial", "global", "verbal", "visual" ));
    }
   
   
	private void extremo(Perfil p){
       String[] complementarios = {"reflexivo", "verbal", "intuitivo", "global"};
       for(String s: complementarios){
           if (p.getAtributo(s, "felder") != 0){
               p.setAtributo(s, "felder", p.getAtributo(s, "felder")* (-1));
           }
       }
    }
    public ArrayList<ArrayList<Perfil>> generarGrupos(ArrayList <Perfil> perfiles, int cantidad){
        ArrayList<ArrayList<Perfil>> grupos = new ArrayList<ArrayList<Perfil>>();
        int cantGrupos= perfiles.size() / cantidad;
        for (int i=0; i<cantGrupos;i++){
			grupos.add(i, new ArrayList<Perfil>());
		}
        for (Perfil p: perfiles){
            extremo(p);
        }
        ArrayList<Perfil> ordenada =criterio.ordenar(perfiles, "felder");
        while (ordenada.size() > 0){
        for(int i=0; i< cantGrupos; i++){
            for(int j= 0; j< cantidad; j++){
            	if (ordenada.size()>0){
                grupos.get(i).add(ordenada.get(0));
                ordenada.remove(perfiles.get(0));
            	}
            }
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
}
