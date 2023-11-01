package dao;

import dtos.Grupo;

import java.util.List;

public interface GrupoDao {

    List<Grupo> getAllGroups();
    Grupo createGroup(Grupo grupo);
    Grupo modifyGrupo(Grupo grupo);
    void deleteGrupo(Grupo grupo);
    Grupo getByOneGrupo(Grupo grupo);

}
