package app;

import java.io.*;
import java.util.*;

public class App {
    public static void main(String[] args) {
        List<Alumno> lista = new ArrayList<Alumno>();
        Alumno alum1 = new Alumno(1, "María Zambrano", 22041904);
        Alumno alum2 = new Alumno(2, "Jean-Paul Sartre", 21101905);
        Alumno alum3 = new Alumno(3, "Albert Camus", 7111913);
        lista.add(alum1);
        lista.add(alum2);
        lista.add(alum3);

        File f = new File("src/alumnos.dat");

        Ejer9.altaAlumnos(f, lista);
        Ejer9.consultarTodosAlumnos(f);

        System.out.println("\n///// CONSULTAR UN ALUMNO /////");
        Ejer9.consultarUnAlumno(lista, f, 1);
        Ejer9.consultarUnAlumno(lista, f, 17);

        System.out.println("\n++++ ALTA ALUMNO ++++");
        Ejer9.altaNuevoAlumno(lista, f, 10, "Slavoj Zizek", 21031949);
        Ejer9.consultarTodosAlumnos(f);

        System.out.println("\n---- MODIFICA ----");
        Ejer9.modificarAlumno(lista, f, 2, 5, "JPSartre", 21061905);
        Ejer9.consultarTodosAlumnos(f);

        System.out.println("\n***** BORRA *****");
        Ejer9.borrarAlumno(lista, f, 3);
        Ejer9.consultarTodosAlumnos(f);
    }
}