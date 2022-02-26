package DAOImplementations;

import DAO.CatalogoDAO;
import DBEntities.Categoria;
import DBEntities.Riferimento;

import java.sql.Connection;

public class CatalogoDAOPostgre implements CatalogoDAO {

    private Connection connection;
    //Qui vanno definiti gli Statement per le istruzioni SQL che i vari metodi devono eseguire

    public CatalogoDAOPostgre(Connection connection) {
        this.connection=connection;
    }

    @Override
    public int catalogaRiferimento(Riferimento riferimento, Categoria categoria) {
        return 0;
    }
}
