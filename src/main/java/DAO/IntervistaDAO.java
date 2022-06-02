package DAO;

import java.sql.SQLException;
import java.util.List;
import DBEntities.Intervista;

public interface IntervistaDAO {

    public String inserisciIntervista(Intervista intervista) throws SQLException;
    public void modificaIntervista(Intervista intervista) throws SQLException;

}
