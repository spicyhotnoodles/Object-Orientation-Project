package DAOImplementations;

import DAO.FilmDAO;
import DBEntities.Film;

import java.sql.Connection;
import java.sql.SQLException;

public class FilmDAOPostgre implements FilmDAO {

    private Connection connection;

    private String[] inserisciFilm;

    private String[] modificaFilm;

    public FilmDAOPostgre(Connection connection) throws SQLException {
        this.connection = connection;
        inserisciFilm = new String[6];
        modificaFilm =  new String[2];
        inserisciFilm[0] = "insert into riferimento values (default, ?, ?, ?, ?, ?, cast(? as tipologia));";
        inserisciFilm[1] = "select currval('riferimento_riferimento_id_seq') as id;";
    }

    @Override
    public void modificaFilm(Film f) throws SQLException {

    }

    @Override
    public String inserisciFilm(Film f) throws SQLException
    {
        return null;
    }
}
