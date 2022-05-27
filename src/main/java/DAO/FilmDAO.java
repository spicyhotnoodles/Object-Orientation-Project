package DAO;

import DBEntities.Film;

import java.sql.SQLException;

public interface FilmDAO {

    public void modificaFilm(Film f) throws SQLException;
    public String inserisciFilm(Film m) throws SQLException;

}
