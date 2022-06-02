package DAO;

import DBEntities.Tesi;

import java.sql.SQLException;

public interface TesiDAO {

    public String inserisciTesi(Tesi tesi) throws SQLException;
    public void modificaTesi(Tesi tesi) throws SQLException;

}
