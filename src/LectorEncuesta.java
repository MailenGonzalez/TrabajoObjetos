import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.InputStream;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class LectorEncuesta {		
		public ArrayList<JSONObject> readExcelFile(File excelFile){
			ArrayList<JSONObject> respuestas = new ArrayList<JSONObject>();
	        InputStream excelStream = null;
	        try {
	            excelStream = new FileInputStream(excelFile);
	            // High level representation of a workbook.
	            // Representación del más alto nivel de la hoja excel.
	            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(excelStream);
	            // We chose the sheet is passed as parameter. 
	            // Elegimos la hoja que se pasa por parámetro.
	            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
	            // An object that allows us to read a row of the excel sheet, and extract from it the cell contents.
	            // Objeto que nos permite leer un fila de la hoja excel, y de aquí extraer el contenido de las celdas.
	            HSSFRow hssfRow;
	            // Initialize the object to read the value of the cell 
	            // Inicializo el objeto que leerá el valor de la celda
	            HSSFCell cell;                        
	            // I get the number of rows occupied on the sheet
	            // Obtengo el número de filas ocupadas en la hoja
	            int rows = hssfSheet.getLastRowNum();
	            // I get the number of columns occupied on the sheet
	            // Obtengo el número de columnas ocupadas en la hoja
	            int cols = 0;            
	            // A string used to store the reading cell
	            // Cadena que usamos para almacenar la lectura de la celda
	            String cellValue;  
	            // For this example we'll loop through the rows getting the data we want
	            // Para este ejemplo vamos a recorrer las filas obteniendo los datos que queremos
	           hssfRow = hssfSheet.getRow(0);
	           ArrayList<String> preguntas = new ArrayList(); 
	            for (int c = 0; c < (cols = hssfRow.getLastCellNum()); c++) {
	                cellValue = hssfRow.getCell(c) == null?"":
	                    (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_STRING)?hssfRow.getCell(c).getStringCellValue():
	                    (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_NUMERIC)?"" + hssfRow.getCell(c).getNumericCellValue():
	                    (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_BOOLEAN)?"" + hssfRow.getCell(c).getBooleanCellValue():
	                    (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_BLANK)?"BLANK":
	                    (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_FORMULA)?"FORMULA":
	                    (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_ERROR)?"ERROR":""; 
	                preguntas.add(cellValue);
	            }           
	            for (int r = 1; r <= hssfSheet.getLastRowNum(); r++) {
	        		JSONObject obj = new JSONObject(); 
	                hssfRow = hssfSheet.getRow(r);
	                if (hssfRow == null){
	                    break;
	                }else{
	                   // System.out.print("Row: " + r + " -> ");
	                    for (int c = 0; c < (cols = hssfRow.getLastCellNum()); c++) {
	           
	                        /* 
	                            We have those cell types (tenemos estos tipos de1 celda): 
	                                CELL_TYPE_BLANK, CELL_TYPE_NUMERIC, CELL_TYPE_BLANK, CELL_TYPE_FORMULA, CELL_TYPE_BOOLEAN, CELL_TYPE_ERROR
	                        */
	                        cellValue = hssfRow.getCell(c) == null?"":
	                                (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_STRING)?hssfRow.getCell(c).getStringCellValue():
	                                (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_NUMERIC)?"" + hssfRow.getCell(c).getNumericCellValue():
	                                (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_BOOLEAN)?"" + hssfRow.getCell(c).getBooleanCellValue():
	                                (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_BLANK)?"BLANK":
	                                (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_FORMULA)?"FORMULA":
	                                (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_ERROR)?"ERROR":""; 
	                      String clave= preguntas.get(c);
	                      if ((clave.startsWith("Nombre de tu compañero")) || (clave.startsWith("Tù Nombre"))){
	                    	  obj.put(clave, cellValue);
	                      }
	                      else{
	                    	  
	                    	  if (clave.startsWith("Nombre")){
	                    		  obj.put("Nombre", cellValue);
	                    	  
	                    	  }
	                    	  else
	                    		  obj.put(clave, cellValue);
	                      }
	                     
	                      
	                       // System.out.print("[Column " + c + ": " + cellValue + "] ");
	                    }
	                    //System.out.println();
	                }
	                respuestas.add(obj);
	             //Esto no va mas!
	                /*
	                System.out.println("objeto Json: " + obj); 
	               JSONObject result = new JSONObject();
	               result = procesador.getresultado(obj);
	               String nombre = (String) obj.get("Nombre");
	               String dCarpeta= "C:/Users/julio/Documents/Mailen facultad/TrabajoFinalObjetos/"+nombre;
	               String dArchivo= "/archivo.json";
	               File file = new File(dCarpeta+dArchivo);
	               File usuario = new File (dCarpeta);
	               if(!usuario.exists()){
	            	   usuario.mkdirs();
	            	   file.createNewFile();
	               }
	               
	            //guardar y leer JSON
	               /*
	       		try{
	       			FileWriter file = new FileWriter("C:/Users/julio/Desktop/prueba.json");
	       			file.write(result.toJSONString());
	       			file.flush();
	       			file.close();
	       		}catch(Exception ex){
	       			System.out.println("Error: "+ex.toString());
	       		}
	       		JSONParser parser = new JSONParser();
	    		try{
	    			Object json= parser.parse(new FileReader("C:/Users/julio/Desktop/prueba.json"));
	    			 
	    			JSONObject jsonObject = (JSONObject) json;
	    			
	    			
	    		}catch(Exception ex){
	    			System.err.println("Error: "+ex.toString());
	       		
	               
	            }
	    		*/
	        } 
	        }
	            catch (FileNotFoundException fileNotFoundException) {
	            System.out.println("The file not exists (No se encontró el fichero): " + fileNotFoundException);
	        } catch (IOException ex) {
	            System.out.println("Error in file procesing (Error al procesar el fichero): " + ex);
	        } finally {
	            try {
	                excelStream.close();
	            } catch (IOException ex){
	                System.out.println("Error in file processing after close it (Error al procesar el fichero después de cerrarlo): " + ex);
	            }
	        }   
	        return respuestas;
	    }
		


	}
