/**
 * Created by luismata on 12/26/15.
 */
public class Asignatura {
    private String nombre;
    private double [] notaMedia;
    private Examen[] examenes;
    private int contExamenes;

    public Asignatura(String nombre, int numAlumnos){
        this.nombre=nombre;
        notaMedia= new double[numAlumnos];
        for (int i=0; i<numAlumnos; i++) notaMedia[i]=0;
        examenes= new Examen[4];
        contExamenes=0;
    }

    public Examen getExamen(int indice){
        return examenes[indice];
    }

    public String getNombre(){
        return nombre;
    }

    public double getMedia(int indice){
        return notaMedia[indice];
    }

    public void ponerMedia(){
        for (int i=0; i<4; i++){
            for (int j=0;j<notaMedia.length;j++){
                notaMedia[j]+=examenes[i].getCalificacion(j);
            }
        }
        for (int j=0;j<notaMedia.length;j++){
            notaMedia[j]=(notaMedia[j]/4);
        }
    }

    public void aniadirExamen(Examen examen){
        if(contExamenes<4){
            examenes[contExamenes++]=examen;
        }
    }
}
