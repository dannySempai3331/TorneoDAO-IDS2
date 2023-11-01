package dao;

import dtos.Equipo;
import dtos.Jugador;

import java.util.List;

public interface EquipoDao {

    List<Equipo> getAllEquipos();
    Equipo createEquipo(Equipo equipo);
    Equipo modifyEquipo(Equipo equipo);
    void addIntegrante(Jugador jugador, Equipo equipo);
    void deleteEquipo(Equipo equipo);
    Equipo getByOneEquipo(Equipo equipo);
    int noIntegrantes(Equipo equipo);
    List<Jugador> integrantesPorEquipo(Equipo equipo);
    void deleteIntegrante(Jugador jugador,Equipo equipo);

}
