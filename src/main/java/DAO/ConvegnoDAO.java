package DAO;

import DBEntities.Convegno;

import java.sql.SQLException;

public interface ConvegnoDAO {

    public String inserisciConvegno(Convegno convegno) throws SQLException;
    public void modificaConvegno(Convegno convegno) throws SQLException;

}
