package DAO;

import DBEntities.Categoria;

public interface CategoriaDAO {

    int creaNuovaCategoria(Categoria categoria);
    int eliminaCategoria(Categoria categoria);

}
