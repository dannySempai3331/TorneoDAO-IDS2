package dtos;

/**
 * @Author José Daniel Pérez Mejía
 */
public class Jugador {

    private int id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private int edad;

    public Jugador() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id = " + this.id +
                ", nombre = '" + this.nombre + '\'' +
                ", apellido1 = '" + this.apellido1 + '\'' +
                ", apellido2 = '" + this.apellido2 + '\'' +
                ", edad = " + this.edad +
                '}';
    }
}
