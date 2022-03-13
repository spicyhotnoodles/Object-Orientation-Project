package DAO;

import DBEntities.Categoria;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoriaDAO {

    void creaCategoria(Categoria categoria) throws SQLException;
    void eliminaCategoria(String nome) throws SQLException;
    ArrayList<Categoria> ottieniCategorie() throws SQLException;

}
