
/**
 * Created by luismata on 12/26/15.
 */
public class Alumno {

    private String nombre;
    private int grupoPracticas;

    public Alumno(String nombre, int grupoPracticas){
        this.nombre=nombre;
        this.grupoPracticas=grupoPracticas;
    }

    public String getNombre(){
        return nombre;
    }

    public int getGrupoPracticas(){
        return grupoPracticas;
    }

    public boolean equals(Alumno alumno){
        return nombre==alumno.getNombre();
    }

}
