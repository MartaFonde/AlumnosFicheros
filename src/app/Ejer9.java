package app;

import java.io.*;
import java.util.*;

public class Ejer9 {

    public static void altaAlumnos(File f, List<Alumno> lista) {
        try (FileOutputStream fos = new FileOutputStream(f, false); DataOutputStream out = new DataOutputStream(fos)) {
            for (Alumno a : lista) {
                out.writeInt(a.getCodigo());
                out.writeUTF(a.getNombre()); 
                out.writeInt(a.getFechaNacimiento());
            }
        } catch (IOException e) {
            System.out.println("Error de escritura: " + e.getMessage());
        }
    }

    public static void altaNuevoAlumno(File f, int codigo, String nombre, int fecha){

        if(!codExists(f, codigo)){
            try (FileOutputStream fos = new FileOutputStream(f, true); DataOutputStream out = new DataOutputStream(fos)) {
                out.writeInt(codigo);
                out.writeUTF(nombre);
                out.writeInt(fecha);
            } catch (IOException e) {
                System.out.println("Error al agregar alumno: "+e.getMessage());
            }
        } else {
            System.out.printf("Error: ya existe un alumno con ese código");
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

    public static void modificarAlumno(File f, int codigo, String nombreNuevo, int fechaNacNueva) {         
        int codigoLeido;
        boolean existe = false;
        File fichActualizado = new File(f.getAbsolutePath().replace(f.getName(), "alumnosModificado.dat"));

        try (FileInputStream fis = new FileInputStream(f); DataInputStream in = new DataInputStream(fis)) { //leemos do orixinal
            while (true) {      //mentres haxa algo que ler...                    
                try (FileOutputStream fos = new FileOutputStream(fichActualizado, true); //escribimos no novo
                        DataOutputStream out = new DataOutputStream(fos)) {
                    codigoLeido = in.readInt();         //NECESARIO, porque se no if != pérdese para write
                    if (codigo == codigoLeido) {
                        out.writeInt(codigo);   
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
    }

    public static void borrarAlumno(File f, int codigo){
        int codigoLeido;
        boolean exist = false;
        File fichActualizado = new File(f.getAbsolutePath().replace(f.getName(), "alumnosBorrado.dat"));

        try (FileInputStream fis = new FileInputStream(f); DataInputStream in = new DataInputStream(fis)) {
            while (true) {
                try (FileOutputStream fos = new FileOutputStream(fichActualizado, true);
                        DataOutputStream out = new DataOutputStream(fos)) {
                    codigoLeido = in.readInt();     
                    if (codigo == codigoLeido) {
                        in.readUTF();  
                        in.readInt();
                        exist = true;
                    } else {
                        out.writeInt(codigoLeido);
                        out.writeUTF(in.readUTF());
                        out.writeInt(in.readInt());
                    }
                }
            }
        } catch (EOFException e) {
            System.out.println("Fin de fichero");
        } catch (IOException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        fichActualizado.renameTo(f);

        if(!exist){
            System.out.println("Error: el código no se corresponde con ningún alumno");
        }                
    }

    public static boolean codExists(File f, int cod){
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