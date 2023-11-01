package dao;

import dtos.Jugador;
import java.util.List;

/**
 * @Author José Daniel Pérez Mejía
 */

public interface JugadorDao {
    List<Jugador> getAll();

    Jugador createJugador(Jugador jugador);

    void deleteJugador(Jugador jugador);

    Jugador modifyJugador(Jugador jugador);

    List<Jugador> get(Jugador jugador);

    //delete update con el ID
}
