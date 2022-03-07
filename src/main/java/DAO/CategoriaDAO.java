package DAO;

import DBEntities.Categoria;

import java.sql.SQLException;

public interface CategoriaDAO {

    String creaCategoria(Categoria categoria) throws SQLException;
    int eliminaCategoria(Categoria categoria);

}
