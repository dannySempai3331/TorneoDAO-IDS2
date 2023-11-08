package prueva;

import dtos.Jugador;
import dtos.Usuario;
import postgres.JugadorDaoImp;
import postgres.UsuarioDaoImp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        /*1. Corregir la interfaz de jugadorDAO, el get solito devuelve una lista de jugadores
         * 2. Llenar los metodos de JugadorDao y funcionando
         * 3. Para el lunes a las 9 am
         * Aguas con las pruevas unitarias*/

        //Completar el DAO, propuesta del DAO para todas las clases.

        JugadorDaoImp jdi = new JugadorDaoImp();
        UsuarioDaoImp udi = new UsuarioDaoImp();
        List<Jugador> list = new ArrayList<>();
        //Jugador j = new Jugador();

        Connection connection;
        String url = "jdbc:postgresql://localhost:5432/torneos?currentSchema=prueva1";
        String user = "postgres";
        String password = "megumin";

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url,user,password);
            jdi.setConnection(connection);
            udi.setConnection(connection);

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

            Usuario u = new Usuario();
            u.setNombre("Andy Gerald");
            u.setApellido1("San Juan");
            u.setApellido2("Martinez");
            u.setFechaNacimiento(formato.parse("15/10/2003"));
            u.setNoCuenta("1234567");
            u.setCorreo("andyFarsa@example.com");
            u.setUserName("farsaXDiana");
            u.setPassword("dianateamo");
            System.out.println(udi.createUsuario(u));


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
