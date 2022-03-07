package DAO;

import DBEntities.Libro;

import java.sql.SQLException;
import java.util.List;

public interface LibroDAO {

    //public List<Libro> ottieniLibri() throws SQLException;
    public String inserisciLibro(Libro libro) throws SQLException;
    public int modificaLibro(Libro libro) throws SQLException;

}
