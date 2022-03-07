package DAO;

import DBEntities.Web;

import java.sql.SQLException;

public interface WebDAO {

    public void inserisciWeb(Web web) throws SQLException;
    public void modificaWeb(Web web) throws SQLException;

}
