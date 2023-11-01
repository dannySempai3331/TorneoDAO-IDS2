package prueva;

import dtos.Jugador;
import postgres.JugadorDaoImp;

import java.sql.Connection;
import java.sql.DriverManager;
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
        List<Jugador> list = new ArrayList<>();
        //Jugador j = new Jugador();

        Connection connection;
        String url = "jdbc:postgresql://localhost:5432/torneos?currentSchema=prueva";
        String user = "postgres";
        String password = "megumin";

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url,user,password);
            jdi.setConnection(connection);

            //getAll()
            /*list = jdi.getAll();

            for (Jugador jugador : list) {
                System.out.println(jugador);
            }*/

            //createJugador()
            Jugador j = new Jugador();
            j.setNombre("Miguela");
            j.setApellido1("de Cervantes");
            j.setApellido2("Pliego");
            j.setEdad(35);

            System.out.println(jdi.createJugador(j));

            /* Delete jugador
            j.setId(8);
            jdi.deleteJugador(j); */

            //Update

            /*Jugador j = new Jugador();
            j.setId(3);
            j.setApellido2("Navarra");

            System.out.println(jdi.modifyJugador(j));*/

            //get solito
            /*Jugador j = new Jugador();
            j.setNombre("Andy Gerald");
            j.setApellido1("San Juan");
            list= jdi.get(j);

            for(Jugador jg : list){
                System.out.println(jg);
            }*/

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
