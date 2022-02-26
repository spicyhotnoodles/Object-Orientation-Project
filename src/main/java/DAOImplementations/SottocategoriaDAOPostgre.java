package DAOImplementations;

import DAO.SottocategoriaDAO;
import DBEntities.Categoria;

import java.sql.Connection;

public class SottocategoriaDAOPostgre implements SottocategoriaDAO {

    private Connection connection;
    //Qui vanno definiti gli Statement per le istruzioni SQL che i vari metodi devono eseguire

    public SottocategoriaDAOPostgre(Connection connection) {
        this.connection=connection;
    }

    @Override
    public int definisciSupercategoria(Categoria categoria) {
        return 0;
    }
}
