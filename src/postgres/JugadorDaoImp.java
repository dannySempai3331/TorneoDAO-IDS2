package postgres;

import dao.JugadorDao;
import dtos.Jugador;
import error.PersistenciaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author José Daniel Pérez Mejia
 * */

public class JugadorDaoImp implements JugadorDao {
    private Connection connection;
    public JugadorDaoImp() {
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Jugador> getAll() {
        //Ya jala
        Statement statement;
        ResultSet rs;
        Jugador j;
        List<Jugador> resultado = new ArrayList<>();

        try {
            statement = this.connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM jugador");

            while (rs.next()){
                j = new Jugador();
                j.setId(rs.getInt("id_jugador"));
                j.setEdad(rs.getInt("edad"));
                j.setNombre(rs.getString("nombre"));
                j.setApellido1(rs.getString("apellido1"));
                j.setApellido2(rs.getString("apellido2"));
                resultado.add(j);
            }
            statement.close();
            connection.close();

            return resultado;

        } catch (SQLException e) {
            throw new PersistenciaException(e);
            //No mandar un mensaje de error con la exepción!!!!
        }
    }
    @Override
    public Jugador createJugador(Jugador jugador) {
        //Ya jala
        PreparedStatement ps;
        String sqlCreate = "INSERT INTO jugador ";
        String chain1 = "(";
        String chain2 = "";
        Map<String,Object> mapCreate;

        try {
            mapCreate = getAtributos(jugador);

            for(Map.Entry<String, Object> map : mapCreate.entrySet()){
                chain1 += map.getKey() + ", ";
            }

            chain1 = chain1.substring(0,chain1.length()-2);
            chain1 = chain1 + ")";
            sqlCreate = sqlCreate + chain1 + " VALUES(";

            for(Map.Entry<String, Object> map : mapCreate.entrySet()){

                Object value = map.getValue();

                if(value instanceof String) {
                    chain2 += "'"+value.toString()+"', ";
                } else {
                    chain2 += value.toString()+ ", ";
                }
            }

            sqlCreate = sqlCreate + chain2.substring(0,chain2.length()-2) + ")";

            ps = this.connection.prepareStatement(sqlCreate);
            ps.executeUpdate();
            ps.close();

            int idAux = getIdCreate(jugador);
            this.connection.close();
            jugador.setId(idAux);

            return jugador;

        } catch (SQLException e) {
            throw new PersistenciaException(e);
        }

    }
    @Override
    public void deleteJugador(Jugador jugador) {
        //Ya jala
        PreparedStatement ps;
        try {
            if(jugador.getId() != 0) {

                ps = this.connection.prepareStatement("DELETE FROM jugador WHERE id_jugador = ?");
                ps.setInt(1, jugador.getId());

                ps.executeUpdate();

                ps.close();
                this.connection.close();
            }

        } catch (SQLException e) {
            throw new PersistenciaException(e);
        }

    }
    @Override
    public Jugador modifyJugador(Jugador jugador) {
        //Ya jala
        PreparedStatement ps;
        String sqlUpdate = "UPDATE jugador SET ";
        String chain1 = "";
        Map<String,Object> mapUpdate;
        Jugador j;

        try {
            if(jugador.getId()!=0) {

                mapUpdate = getAtributos(jugador);

                for (Map.Entry<String, Object> map : mapUpdate.entrySet()) {

                    if (map.getKey() != "id_jugador") {

                        if (map.getValue() instanceof String) {
                            chain1 += map.getKey() + " = '" + map.getValue() + "', ";
                        } else {
                            chain1 += map.getKey() + " = " + map.getValue() + ", ";
                        }
                    }
                }
                sqlUpdate = sqlUpdate + chain1.substring(0, chain1.length() - 2) + " WHERE id_jugador = " + jugador.getId();

                ps = this.connection.prepareStatement(sqlUpdate);
                ps.executeUpdate();

                ps.close();
                j = getbyId(jugador.getId());
                this.connection.close();

                return j;
            }else {
                return null;
            }

        }catch (SQLException e){
            throw new PersistenciaException(e);
        }
    }

    @Override
    public List<Jugador> get(Jugador jugador) {

        Statement stmt;
        String sql, cad;
        ResultSet rs;
        Map<String, Object> atributos;
        List<Jugador> devueltos = new ArrayList<>();;
        Jugador consultado;

        try {

            stmt = this.connection.createStatement();
            sql = "SELECT * FROM jugador WHERE ";
            atributos = this.getAtributos(jugador);

            if(!atributos.isEmpty()) {

                for (Map.Entry<String, Object> coso : atributos.entrySet()) {

                    String key = coso.getKey();
                    Object value = coso.getValue();
                    cad = key+"=";

                    if(value instanceof String) {
                        cad +="'"+value.toString()+"' AND ";
                    } else {
                        cad += value.toString()+ " AND ";
                    }
                    sql = sql + cad;
                }
                sql = sql.substring(0,sql.length()-5);

                rs = stmt.executeQuery(sql);

                while(rs.next()) {
                    consultado = new Jugador();
                    consultado.setId(rs.getInt("id_jugador"));
                    consultado.setNombre(rs.getString("nombre"));
                    consultado.setApellido1(rs.getString("apellido1"));
                    consultado.setApellido2(rs.getString("apellido2"));
                    consultado.setEdad(rs.getInt("edad"));

                    devueltos.add(consultado);
                }

                return devueltos;

            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new PersistenciaException(e);
        }
    }

    private Map<String, Object> getAtributos(Jugador j){

        Map<String, Object> resul = new HashMap<String, Object>();

        String val = j.getNombre();
        int var;

        if(val != null) {
            resul.put("nombre", val);
        }

        val = j.getApellido1();

        if(val != null) {
            resul.put("apellido1", val);
        }

        val = j.getApellido2();

        if(val != null) {
            resul.put("apellido2", val);
        }

        var = j.getEdad();

        if(var > 0) {
            resul.put("edad", Integer.valueOf(var));
        }

        var = j.getId();

        if(var > 0) {
            resul.put("id_jugador", Integer.valueOf(var));
        }

        return resul;
    }

    private int getIdCreate(Jugador jugador){
        //Solo utilizar este método despues de un Create!
        //Regresar una lista

        PreparedStatement ps;
        ResultSet rs;
        List<Integer> list = new ArrayList<>();

        try {
            if(jugador.getApellido2()!=null) {
                ps = this.connection.prepareStatement("SELECT id_jugador FROM jugador WHERE nombre = ? AND apellido1 = ? AND apellido2 = ? AND edad = ?");
                ps.setString(1, jugador.getNombre());
                ps.setString(2, jugador.getApellido1());
                ps.setString(3, jugador.getApellido2());
                ps.setInt(4, jugador.getEdad());

            }else{
                ps = this.connection.prepareStatement("SELECT id_jugador FROM jugador WHERE nombre = ? AND apellido1 = ? AND edad = ?");
                ps.setString(1, jugador.getNombre());
                ps.setString(2, jugador.getApellido1());
                ps.setInt(3, jugador.getEdad());

            }
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            ps.close();

            return list.get(list.size()-1);

        } catch (SQLException e) {
            throw new PersistenciaException(e);
        }

    }

    private Jugador getbyId(int id) {

        PreparedStatement ps;
        ResultSet rs;
        Jugador jugador = new Jugador();
        jugador.setId(id);

        try {
            ps = this.connection.prepareStatement("SELECT nombre, apellido1, apellido2, edad FROM jugador WHERE id_jugador = ?");
            ps.setInt(1,id);

            rs = ps.executeQuery();

            while (rs.next()){
                jugador.setNombre(rs.getString("nombre"));
                jugador.setApellido1(rs.getString("apellido1"));
                jugador.setApellido2(rs.getString("apellido2"));
                jugador.setEdad(rs.getInt("edad"));
            }

            ps.close();
            return jugador;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
