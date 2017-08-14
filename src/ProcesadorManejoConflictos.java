import org.json.simple.JSONObject;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
public class ProcesadorManejoConflictos extends ProcesadorEncuesta{

	private int[][] puntajes = new int[30][2];
    private int competencia;
    private int colaboracion;
    private int compromiso;
    private int evasion;
    private int acomodo;

    public ProcesadorManejoConflictos(File datos) throws IOException {
        this.competencia = 0;
        this.colaboracion = 0;
        this.compromiso = 0;
        this.evasion = 0;
        this.acomodo = 0;
        crearMatriz(datos);
        super.setNombre("conflictos");
    }

    @SuppressWarnings("deprecation")
	public void crearMatriz(File datos) throws IOException {
    	DataInputStream ds = new DataInputStream (new FileInputStream(datos));
    	String dato;
    	for (int i =0; i <= 29; i++)
    		for (int j =0; j<=1; j++){
    			dato=ds.readLine();
    			puntajes[i][j]=Integer.parseInt(dato);
      		}
    }

    public JSONObject getresultado(JSONObject respuestas) {
        JSONObject resultado = new JSONObject();
        for (int i = 1; i < 31; i++) {
            String key = Integer.toString(i);
            //System.out.println("i: " + i);
            String valor = (String) respuestas.get(""+i+"");
            //System.out.println("valor: " + valor);
            int variable;
            //System.out.println("i-1: " + (i-1));
            int j= i-1;
            if (valor.startsWith("a.")) {
                variable = puntajes[j][0];
            } else {
                variable = puntajes[j][1];
            }
            //System.out.println("variable: " + variable);
            switch (variable) {
                case 1:
                    competencia++;
                    break;
                case 2:
                    colaboracion++;
                    break;
                case 3:
                    compromiso++;
                    break;
                case 4:
                    evasion++;
                    break;
                case 5:
                    acomodo++;
                    break;
            }
        }
            String[] nombres= {"competencia","colaboracion","compromiso","evasion","acomodo"};
            int[] valores = {competencia,colaboracion,compromiso,evasion,acomodo};
            for (int i=0; i<4 ; i++)
                for (int j=0; j< 4; j++)
                    if (valores[j]< valores [j+1])
                    {
                        int auxValor = valores[j+1];
                        String auxNombre = nombres[j+1];
                        valores[j+1]=valores[j];
                        nombres[j+1]= nombres[j];
                        valores[j]=auxValor;
                        nombres[j]=auxNombre;
                    }

            resultado.put("competencia", competencia);
            resultado.put("colaboracion", colaboracion);
            resultado.put("compromiso",compromiso);
            resultado.put("evasion", evasion);
            resultado.put("acomodo", acomodo);
            competencia=0;
            colaboracion=0;
            compromiso=0;
            evasion=0;
            acomodo=0;
          //  resultado.put(nombres[0],valores[0]);
            //resultado.put(nombres[1], valores[1]);
            
        return resultado;
    }


}
