package app;

import java.io.*;
import java.util.*;

public class Ejer9 {

    public static void altaAlumnos(File f, List<Alumno> lista) {
        try (FileOutputStream fos = new FileOutputStream(f, false); DataOutputStream out = new DataOutputStream(fos)) {
            for (Alumno a : lista) {
                out.writeInt(a.getCodigo());
                out.writeUTF(a.getNombre()); // get porque xa existen na lista
                out.writeInt(a.getFechaNacimiento());
            }
        } catch (IOException e) {
            System.out.println("Error de escritura: " + e.getMessage());
        }
    }

    public static void altaNuevoAlumno(File f, int codigo, String nombre, int fecha){

        if(!codExistsFichero(f, codigo)){
            try (FileOutputStream fos = new FileOutputStream(f, true); DataOutputStream out = new DataOutputStream(fos)) {
                out.writeInt(codigo);
                out.writeUTF(nombre);
                out.writeInt(fecha);
            } catch (IOException e) {
                System.out.println("Error al agregar alumno: "+e.getMessage());
            }
        } else {
            System.out.printf("Error: ya existe un alumno con el código "+codigo+"\n");
        }       
    }

    public static void consultarTodosAlumnos(File f) {
        try (FileInputStream fis = new FileInputStream(f); DataInputStream in = new DataInputStream(fis)) {
            while (true) {
                System.out.printf("Código: %d\nNombre: %s\nFechaNacimiento: %d\n\n", in.readInt(), in.readUTF(),
                        in.readInt());
            }
        } catch (EOFException e) {
            System.out.println("Fin de fichero");
        } catch (IOException exGen) {
            System.out.println("Error de consulta: " + exGen.getMessage());
        }
    }

    public static void consultarUnAlumno(File f, int codigo){  
            int codLeido;
            boolean existe = false;

            try(FileInputStream fis = new FileInputStream(f); DataInputStream in = new DataInputStream(fis)){
                while(true){
                    codLeido = in.readInt();
                    if(codigo == codLeido){
                        System.out.printf("Código: %d\nNombre: %s\nFechaNacimiento: %d\n\n", codLeido, in.readUTF(),
                            in.readInt());
                        existe = true;
                        break;      
                    } else {
                        in.readUTF();
                        in.readInt();
                    }
                }
            }catch(EOFException eof){
                System.out.println("Fin de fichero");
            } catch(IOException e){
                System.out.println("Error: "+e.getMessage());
            }

        if(!existe){
            System.out.println("Error: el código no se corresponde con ningún alumno");
        }
    }

    public static void modificarAlumno(File f, int codigo, int codigoNuevo, String nombreNuevo, int fechaNacNueva) {
        int i = 0;
        boolean append = false;
        boolean codInvalido = false;
        int codigoLeido;
        boolean existe = false;
        File fichActualizado = new File(f.getAbsolutePath().replace(f.getName(), "alumnosModificado.dat"));

        if(codigo != codigoNuevo){
            codInvalido = codExistsFichero(f, codigoNuevo);
        }

        if(!codInvalido){
            try (FileInputStream fis = new FileInputStream(f); DataInputStream in = new DataInputStream(fis)) { //leemos do orixinal
                while (true) {      //mentres haxa algo que ler...
                    if (i > 0) {
                        append = true;      //no 1ºalumno sobreescribir(false), despois ir engadindo ao final(true)
                    }
                    try (FileOutputStream fos = new FileOutputStream(fichActualizado, append); //escribimos no novo
                            DataOutputStream out = new DataOutputStream(fos)) {
                        codigoLeido = in.readInt();     //NECESARIO, porque se no if != pérdese para write
                        if (codigo == codigoLeido) {
                            out.writeInt(codigoNuevo);
                            out.writeUTF(nombreNuevo);
                            out.writeInt(fechaNacNueva);
                            in.readUTF();  //IMPORTANTE!!!
                            in.readInt();
                            existe = true;
                        } else {
                            out.writeInt(codigoLeido);  
                            out.writeUTF(in.readUTF());                        
                            out.writeInt(in.readInt());
                        }
                    }
                    i++;
                }
            } catch (EOFException e) {
                System.out.println("Fin de fichero");
            } catch (IOException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
            fichActualizado.renameTo(f);
            if(!existe){
                System.out.println("Error: el código no se corresponde con ningún alumno");
            }
        }else{
            System.out.println("Error: ya existe un alumno con el código "+codigoNuevo+"\n");
        }
    }

    public static void borrarAlumno(File f, int codigo){
        int codigoLeido;
        boolean append = false;
        int i = 0;
        File fichActualizado = new File(f.getAbsolutePath().replace(f.getName(), "alumnosBorrado.dat"));

        if(codExistsFichero(f, codigo)){
            try (FileInputStream fis = new FileInputStream(f); DataInputStream in = new DataInputStream(fis)) {
                while (true) {
                    if (i > 0) {
                        append = true;
                    }
                    try (FileOutputStream fos = new FileOutputStream(fichActualizado, append);
                            DataOutputStream out = new DataOutputStream(fos)) {
                        codigoLeido = in.readInt();     
                        if (codigo == codigoLeido) {
                            in.readUTF();  
                            in.readInt();
                        } else {
                            out.writeInt(codigoLeido);
                            out.writeUTF(in.readUTF());
                            out.writeInt(in.readInt());
                        }
                    }
                    i++;
                }
            } catch (EOFException e) {
                System.out.println("Fin de fichero");
            } catch (IOException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
            fichActualizado.renameTo(f);
        }else{
            System.out.println("Error: el código no se corresponde con ningún alumno");
        }
    }

    public static boolean codExistsFichero(File f, int cod){
        try (FileInputStream fis = new FileInputStream(f); DataInputStream in = new DataInputStream(fis)){
            while(true){
                if(cod == in.readInt()){
                    return true;    
                }else{
                    in.readUTF();
                    in.readInt();
                }
            }
        }catch (EOFException eof) {
            System.out.println("Fin de fichero");
        } 
        catch (IOException e) {
            System.out.println("Error al leer el archivo: "+e.getMessage());
        }
        return false;
    }
}