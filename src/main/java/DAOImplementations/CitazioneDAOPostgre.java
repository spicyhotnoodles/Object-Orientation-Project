package DAOImplementations;

import DAO.CitazioneDAO;
import DBEntities.Riferimento;

import java.sql.Connection;
import java.util.ArrayList;

public class CitazioneDAOPostgre implements CitazioneDAO {

    private Connection connection;
    //Qui vanno definiti gli Statement per le istruzioni SQL che i vari metodi devono eseguire

    public CitazioneDAOPostgre(Connection connection) {
        this.connection=connection;
    }

    @Override
    public int associaRiferimenti(Riferimento riferimento, ArrayList<Riferimento> menzioni) {
        return 0;
    }
}
