package DAOImplementations;

import DAO.CategoriaDAO;
import DBEntities.Categoria;

import java.sql.Connection;

public class CategoriaDAOPostgre implements CategoriaDAO {

    private Connection connection;
    //Qui vanno definiti gli Statement per le istruzioni SQL che i vari metodi devono eseguire

    public CategoriaDAOPostgre(Connection connection) {
        this.connection=connection;
    }

    @Override
    public int creaNuovaCategoria(Categoria categoria) {
        return 0;
    }

    @Override
    public int eliminaCategoria(Categoria categoria) {
        return 0;
    }
}
