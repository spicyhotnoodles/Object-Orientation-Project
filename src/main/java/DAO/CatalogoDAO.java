package DAO;

import DBEntities.Categoria;
import DBEntities.Riferimento;

import java.sql.SQLException;

public interface CatalogoDAO {
    public void rimuoviDalCatalogo(String riferimento_id, String categoria_id) throws SQLException;
    public void aggiungiAlCatalogo(String riferimento_id, String categoria_id) throws SQLException;
}
