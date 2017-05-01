/**
 * Created by luismata on 12/26/15.
 */
abstract class ExamenTeoria extends Examen {
    protected double[][] aNotasPartes;

    public ExamenTeoria(int numAlumnos){
        super(numAlumnos);
        aNotasPartes=new double[numAlumnos][2];
    }

}
