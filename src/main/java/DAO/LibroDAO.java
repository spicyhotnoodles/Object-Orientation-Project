package DAO;

import DBEntities.Libro;

import java.sql.SQLException;

public interface LibroDAO {
    public String inserisciLibro(Libro libro) throws SQLException;
    public void modificaLibro(Libro libro) throws SQLException;
}
