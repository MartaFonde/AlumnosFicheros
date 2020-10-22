package app;

public class Alumno {
    private String nombre;
    private int fechaNacimiento;
    private int codigo;

    public Alumno(int codigo, String nombre, int fechaNacimiento){
        this.codigo = codigo;
        this.nombre=nombre;
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setCodigo(int cod){
        this.codigo = cod;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setFechaNacimiento(int fechaNacimiento){
        this.fechaNacimiento = fechaNacimiento;
    }


    public int getCodigo(){
        return codigo;
    }

    public String getNombre(){
        return nombre;
    }
    public int getFechaNacimiento(){
        return fechaNacimiento;
    }
    
}