import java.io.IOException;

/**
 * Created by luismata on 12/26/15.
 */
abstract class Examen {

    protected double [] aListaNotasEx;

    public Examen(int numAlumnos){
        aListaNotasEx= new double[numAlumnos];
    }

    abstract void setCalificacion(int indice, Alumno[] alumnos);

    abstract void leerFichero (String nombreFichero) throws IOException;

    public double getCalificacion(int indice){
        return aListaNotasEx[indice];
    }

}
