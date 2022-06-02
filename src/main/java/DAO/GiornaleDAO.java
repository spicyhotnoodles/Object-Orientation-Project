package DAO;

import DBEntities.Giornale;

import java.sql.SQLException;

public interface GiornaleDAO {

    String inserisciGiornale(Giornale g) throws SQLException;

    void modificaGiornale(Giornale g) throws SQLException;

}
