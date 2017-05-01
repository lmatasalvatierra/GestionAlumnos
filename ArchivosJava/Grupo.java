import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Created by luismata on 12/26/15.
 */
public class Grupo {

    private Alumno[] alumnos;
    private int contAlumnos;
    private Asignatura[] asignaturas;
    private int contAsignaturas;

    private static Scanner sc= new Scanner(System.in);
    private static DecimalFormat df= new DecimalFormat("##.##");

    public Grupo(){
        alumnos= new Alumno[25];
        contAlumnos=0;
        asignaturas= new Asignatura[5];
        contAsignaturas= 0;
    }

    public void menu(){
        int opcion;
        boolean inicializadoGrupo=false;
        boolean evaluadoAsignaturas=false;
        do{

            System.out.println("1. Inicializar un grupo");
            System.out.println("2. Evaluar asignaturas");
            System.out.println("3. Mostrar no presentados");
            System.out.println("4. Mostrar expedientes");
            System.out.println("5. Salir de la aplicacion");
            System.out.print("Introducir una opcion:");
            opcion= sc.nextInt();
            if(opcion<1 || opcion>5)
                System.out.println("Opcion incorrecta.");
            else {
                switch (opcion){
                    case 1:
                        try {
                            System.out.println();
                            leerAlumnos();
                            leerAsignaturas();
                            leerExamenes();
                            inicializadoGrupo=true;
                        }catch (IOException e){
                            System.out.println("Error en la lectura de los ficheros.");
                        }
                        break;
                    case 2:
                        if (inicializadoGrupo){
                            System.out.println();
                            ponerNotas();
                            evaluadoAsignaturas=true;
                        }
                        else System.out.println("\n\n\n\n\n\nEs necesario inicializar primero el grupo.");
                        break;
                    case 3:
                        if (inicializadoGrupo && evaluadoAsignaturas){
                            System.out.println();
                            noPresentados();
                        }
                        else if(inicializadoGrupo && !evaluadoAsignaturas) System.out.println("\n\n\n\n\n\nEs necesario evaluar primero el grupo.");
                        else System.out.println("\n\n\n\n\n\nEs necesario inicializar primero el grupo.");

                        break;
                    case 4:
                        if (inicializadoGrupo){
                            System.out.println();
                            expediente();
                        }
                        else if(inicializadoGrupo && !evaluadoAsignaturas) System.out.println("\n\n\n\n\n\nEs necesario evaluar primero el grupo.");
                        else System.out.println("\n\n\n\n\n\nEs necesario inicializar primero el grupo.");
                        break;
                    case 5: break;
                }
            }

        }while(opcion!=5);
    }

    /*
        Los alumnos y sus notas estan indexados por la posicion del alumno en el Array. Por esto, es necesario escribir
        la notas de los alumnos en el orden en el que fueron introducidos en el fichero de alumnos.
     */

    private void leerAlumnos() throws IOException{
        BufferedReader entrada;
        String str;
        entrada = new BufferedReader(new FileReader("Alumnos.txt"));
        str = entrada.readLine();
        while (str != null) {
            try {
                String[] campos = str.split(" ");
                String nombre = campos[0];
                int grupoPracticas = Integer.parseInt(campos[1]);
                Alumno alumno= new Alumno(nombre, grupoPracticas);
                alumnos[contAlumnos++]= alumno;
                str = entrada.readLine();
            } catch (NumberFormatException e) {
                System.out.println("Error int");
            } catch (IOException e) {
                System.out.println("error e/s");
            }

        }
        entrada.close();
    }

    private void leerAsignaturas() throws IOException{
        BufferedReader entrada;
        Scanner sc= new Scanner(System.in);
        String str;
        entrada = new BufferedReader(new FileReader("Asignaturas.txt"));
        int examenesT;
        int examenesPresenciales;
        int i=0;
        str = entrada.readLine();
        while (str != null && contAsignaturas<5) {
            try {
                asignaturas[i]=new Asignatura(str, contAlumnos);
                contAsignaturas++;
                System.out.println(asignaturas[i].getNombre().toUpperCase() + ":");
                System.out.print("Cuantos examenes de teoria habra[1,4]:");
                examenesT=sc.nextInt();
                System.out.print("Cuantos examenes presenciales de teoria habra:");
                examenesPresenciales=sc.nextInt();
                for (int j=0; j<examenesPresenciales; j++)
                    asignaturas[i].aniadirExamen(new ExamenPresencial(contAlumnos));
                for (int y=examenesPresenciales; y<examenesT; y++)
                    asignaturas[i].aniadirExamen(new ExamenMoodle(contAlumnos));
                for (int k=examenesT; k<4; k++)
                    asignaturas[i].aniadirExamen(new ExamenPractica(contAlumnos));

                str = entrada.readLine();
            } catch (IOException e) {
                System.out.println("error e/s");
            }
            i++;
        }
        entrada.close();

        System.out.println("---------------------------------------------------------------");
    }

    /*
        Se lee cada uno de los examenes de todas las asignaturas. El nombre de los ficheros de examen debera seguir el
        siguiente formato: "Examen" + [Nombre de la Asignatura] + [Numero de examen (0,3)]
        Los examenes se leeran en el orden que fueron inicializados. Siendo los examenes presenciales los primeros,
        seguidos por los examenes de Moodle y finalmente los examenes practicos.
     */

    private void leerExamenes(){
        int i=0;
        int j;
        while (i<contAsignaturas){
            j=0;
            while (j<4){
                try {
                    asignaturas[i].getExamen(j).leerFichero("Examen" + asignaturas[i].getNombre() + j + ".txt");
                }
                catch (IOException nci){
                    System.out.println("ERROR en la Apertura de Ficheros de Examen: " +"Examen" + asignaturas[i].getNombre() + j);
                }
                j++;
            }
            i++;
        }
    }

    private void ponerNotas(){
        for (int i=0; i< contAsignaturas; i++){
            for (int j=0; j<4; j++){
                for (int k=0; k<contAlumnos; k++) {
                    asignaturas[i].getExamen(j).setCalificacion(k, alumnos);
                }
            }
        }

        for (int i=0; i< contAsignaturas; i++){
            System.out.println("Asignatura: " +asignaturas[i].getNombre().toUpperCase());
            for (int j=0; j<4; j++){
                System.out.println("Examen " + (j+1));
                for (int k=0; k<contAlumnos; k++) {
                    System.out.println(alumnos[k].getNombre() + " " + "Grupo:" +alumnos[k].getGrupoPracticas()+ " " + df.format(asignaturas[i].getExamen(j).getCalificacion(k)));
                }
                System.out.println();
            }
        }

        System.out.println("--------------------------------------------------------------");

        for (int i=0; i< contAsignaturas; i++) {
            asignaturas[i].ponerMedia();
        }
    }

    private void noPresentados(){
        boolean hayNoPresentados=false;
        for (int i=0; i< contAsignaturas; i++){
            System.out.println("Asignatura:" + asignaturas[i].getNombre().toUpperCase());
            for (int k=0; k<contAlumnos && !hayNoPresentados; k++) {
                if(asignaturas[i].getMedia(k)==0){hayNoPresentados=true;}
            }
            if (hayNoPresentados){
                System.out.println("No presentados:");
                for (int k=0; k<contAlumnos; k++) {
                      if(asignaturas[i].getMedia(k)==0){
                        System.out.println(alumnos[k].getNombre());
                      }
                }
            }
            else System.out.println("Todos se han presentado en la asignatura.");

            System.out.println("--------------------------------------------------------------");
        }
    }

    private void expediente(){
        double notaPromedio;
        for (int i=0; i< contAlumnos; i++){
            notaPromedio=0;
            System.out.print(alumnos[i].getNombre() + " " + "Grupo: " + alumnos[i].getGrupoPracticas() + " " );
                for (int k=0; k<contAsignaturas; k++) {
                    System.out.print(asignaturas[k].getNombre() + " " + df.format(asignaturas[k].getMedia(i)) + " ");
                    notaPromedio+= asignaturas[k].getMedia(i);
                }
            if(contAsignaturas!=0) {
                notaPromedio /= contAsignaturas;
                System.out.println(" " + "Media: " + df.format(notaPromedio));
            }
            else System.out.println("No se ha presentado en ninguna asignatura");
        }
        System.out.println("---------------------------------------------------------------------");
    }
}


