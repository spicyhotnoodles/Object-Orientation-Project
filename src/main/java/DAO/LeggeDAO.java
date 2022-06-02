package DAO;

import DBEntities.Legge;

import java.sql.SQLException;

public interface LeggeDAO {

    public String inserisciLegge(Legge legge) throws SQLException;
    public void modificaLegge(Legge legge) throws SQLException;

}
