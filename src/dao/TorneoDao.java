package dao;

import dtos.Torneo;

import java.util.List;

public interface TorneoDao {

    List<Torneo> getAll();
    Torneo createTorneo(Torneo torneo);
    Torneo modifyName(Torneo torneo);
    void deleteTorneo(Torneo torneo);
}
