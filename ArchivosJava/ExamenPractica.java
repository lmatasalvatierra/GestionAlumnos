import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by luismata on 12/26/15.
 */
public class ExamenPractica extends Examen{

    private double [] aNotaExPr;

    public ExamenPractica(int numAlumnos){
        super(numAlumnos);
        aNotaExPr= new double[numAlumnos];
    }


    public void setCalificacion (int indice, Alumno [] alumnos){

        double notaFinal=0;
        int i=0;
        boolean resul=false;
        while(i<aNotaExPr.length && !resul){
            if (alumnos[indice].getGrupoPracticas()==alumnos[i].getGrupoPracticas() && !alumnos[indice].equals(alumnos[i])){
                resul=true;
                notaFinal=aNotaExPr[i]+aNotaExPr[indice];
            }
            i++;
        }
        aListaNotasEx[indice] =(notaFinal/2.0);
    }

    /*
        El formato para leer los Examenes Practicos es:
        "[NotaPractica]"
     */

    public void leerFichero(String nombreFichero) throws IOException{
        BufferedReader entrada;
        String str;
        entrada = new BufferedReader(new FileReader(nombreFichero));
        str = entrada.readLine();
        double notaPractica;
        int i=0;
        try {
            while (str != null && i<aNotaExPr.length) {
                notaPractica = Double.parseDouble(str);
                aNotaExPr[i]=notaPractica;
                str = entrada.readLine();
                i++;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error Formato ExamenPractica");
        } catch (IOException e) {
            System.out.println("error e/s");
        }
        entrada.close();
    }
}
