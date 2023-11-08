package postgres;

import dao.UsuarioDao;
import dtos.Jugador;
import dtos.Usuario;
import error.PersistenciaException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UsuarioDaoImp implements UsuarioDao {
    private Connection connection;

    public UsuarioDaoImp() {
    }

    public void setConnection(Connection connection){
        this.connection = connection;
    }

    @Override
    public Usuario createUsuario(Usuario usuario) {
        PreparedStatement ps;
        String sqlCreate = "INSERT INTO usuarios ";
        String chain1 = "(";
        String chain2 = "";
        Map<String,Object> mapCreate;

        try {
            mapCreate = getAtributos(usuario);

            for(Map.Entry<String, Object> map : mapCreate.entrySet()){
                chain1 += map.getKey() + ", ";
            }

            chain1 = chain1.substring(0,chain1.length()-2);
            chain1 = chain1 + ")";

            System.out.println(chain1);
            sqlCreate = sqlCreate + chain1 + " VALUES(";

            System.out.println(sqlCreate);

            for(Map.Entry<String, Object> map : mapCreate.entrySet()){

                Object value = map.getValue();

                if(value instanceof String) {
                    chain2 += "'"+value.toString()+"', ";

                }
                else if (value instanceof Date){
                    java.sql.Date fechaSQL = new java.sql.Date(((Date) value).getTime());
                    chain2 += "'"+fechaSQL.toString()+ "', ";
                }
                else {
                    chain2 += value.toString()+ ", ";
                }
            }

            sqlCreate = sqlCreate + chain2.substring(0,chain2.length()-2) + ")";
            System.out.println(sqlCreate);

            ps = this.connection.prepareStatement(sqlCreate);
            ps.executeUpdate();
            ps.close();

            //int idAux = getIdCreate(jugador);
            this.connection.close();
            //jugador.setId(idAux);

            return usuario;

        } catch (SQLException e) {
            throw new PersistenciaException(e);
        }
    }

    private Map<String, Object> getAtributos(Usuario u){

        Map<String, Object> resul = new HashMap<String, Object>();

        String val = u.getNombre();
        Date d = u.getFechaNacimiento();
        int var;

        if(val != null) {
            resul.put("nombre", u.getNombre());
        }

        val = u.getApellido1();

        if(val != null) {
            resul.put("apellido1", val);
        }

        val = u.getApellido2();

        if(val != null) {
            resul.put("apellido2", val);
        }

        if(d != null) {
            resul.put("fechaNacimiento", d);
        }

        val = u.getNoCuenta();

        if (val != null){
            resul.put("noCuenta",val);
        }

        val = u.getCorreo();

        if(val != null){
            resul.put("email",val);
        }

        val = u.getUserName();

        if (val != null){
            resul.put("userName",val);
        }

        val = u.getPassword();

        if (val != null){
            resul.put("password",val);
        }

        return resul;
    }
}
