package DAOImplementations;

import DAO.RiferimentoDAO;
import DBEntities.Categoria;
import DBEntities.Riferimento;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class RiferimentoDAOPostgre implements RiferimentoDAO {

    private Connection connection;

    public RiferimentoDAOPostgre(Connection connection) throws SQLException {
        this.connection=connection;
    }

    @Override
    public ArrayList<Riferimento> ottieniRiferimentoPerParoleChiave(ArrayList<String> tags) {
        return null;
    }

    @Override
    public int inserisciNuovoRiferimento(Riferimento riferimento) {
        return 0;
    }

    @Override
    public int modificaRiferimento(Riferimento riferimento) {
        return 0;
    }

    @Override
    public ArrayList<Riferimento> ottieniRiferimentiPerCategoria(Categoria categoria) {
        return null;
    }
}
