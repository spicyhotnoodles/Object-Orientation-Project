package DAO;

import DBEntities.Rivista;

import java.sql.SQLException;

public interface RivistaDAO {

    public String inserisciRivista(Rivista rivista) throws SQLException;
    public void modificaRivista(Rivista rivista) throws SQLException;

}
