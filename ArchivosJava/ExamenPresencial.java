import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by luismata on 12/27/15.
 */
public class ExamenPresencial extends ExamenTeoria{
    private int [] aConvocatoriaEx;

    public ExamenPresencial(int numAlumnos){
        super(numAlumnos);
        aConvocatoriaEx=new int[numAlumnos];
    }

    public void setCalificacion(int indice, Alumno[] alumnos){
        double notaFinal;
        notaFinal=aNotasPartes[indice][0]+ aNotasPartes[indice][1];
        if (aConvocatoriaEx[indice]==1){
            aListaNotasEx[indice]=notaFinal+(notaFinal*0.02);
        }
        else aListaNotasEx[indice]=notaFinal;
    }

    /*
        El formato para leer los Examenes Presenciales es:
        "[aNotasPartes[0]] [aNotasPartes[0]] [Convocatoria]"
     */

    public void leerFichero(String nombreFichero) throws IOException {
        BufferedReader entrada;
        String str;
        entrada = new BufferedReader(new FileReader(nombreFichero));
        str = entrada.readLine();
        int indice=0;
        try {
            while (str != null && indice<aConvocatoriaEx.length) {
                String[] campos = str.split(" ");
                double nota1 = Double.parseDouble(campos[0]);
                double nota2 = Double.parseDouble(campos[1]);
                int convocatoria= Integer.parseInt(campos[2]);
                aNotasPartes[indice][0]= nota1;
                aNotasPartes[indice][1]= nota2;
                aConvocatoriaEx[indice]=convocatoria;
                str = entrada.readLine();
                indice++;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error Formato ExamenPresencial");
        } catch (IOException e) {
            System.out.println("Error e/s");
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Error Formato ExamenPresencial");
        }
        entrada.close();

    }

}
