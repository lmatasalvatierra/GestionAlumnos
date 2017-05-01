import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by luismata on 12/27/15.
 */
public class ExamenMoodle extends ExamenTeoria {

    private int[] aTiempoEx;


    public ExamenMoodle(int numAlumnos){
        super(numAlumnos);
        aTiempoEx=new int[numAlumnos];
    }

    public void setCalificacion(int indice, Alumno [] alumnos){
        double notaFinal;
        notaFinal= aNotasPartes[indice][0] + aNotasPartes[indice][1];
        aListaNotasEx[indice] = notaFinal+ notaFinal*((4-aTiempoEx[indice])/100.0);
    }

    /*
        El formato para leer los Examenes de Moodle es:
        "[aNotasPartes[0]] [aNotasPartes[0]] [Tiempo]"
     */

    public void leerFichero(String nombreFichero) throws IOException {
        BufferedReader entrada;
        String str;
        entrada = new BufferedReader(new FileReader(nombreFichero));
        str = entrada.readLine();
        int indice=0;
        try {
            while (str != null && indice < aTiempoEx.length) {

                String[] campos = str.split(" ");
                double nota1 = Double.parseDouble(campos[0]);
                double nota2 = Double.parseDouble(campos[1]);
                int tiempo = Integer.parseInt(campos[2]);
                aNotasPartes[indice][0] = nota1;
                aNotasPartes[indice][1] = nota2;
                aTiempoEx[indice] = tiempo;
                str = entrada.readLine();
                indice++;
            }
        }catch (NumberFormatException e) {
        System.out.println("Error Formato ExamenMoodle");
        } catch (IOException e) {
        System.out.println("error e/s");
        }
        entrada.close();
    }
}
