package DAOImplementations;

import DAO.CatalogoDAO;
import DBEntities.Categoria;
import DBEntities.Riferimento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CatalogoDAOPostgre implements CatalogoDAO {

    private Connection connection;
    private PreparedStatement rimuoviDalCatalogo;
    private PreparedStatement aggiungiAlCatalogo;

    public CatalogoDAOPostgre(Connection connection) {
        this.connection=connection;
    }

    @Override
    public void rimuoviDalCatalogo(String id) throws SQLException {
        rimuoviDalCatalogo = connection.prepareStatement("delete from catalogo where categoria_id = cast(? as int)");
        rimuoviDalCatalogo.setString(1, id);
        rimuoviDalCatalogo.executeUpdate();
    }

    @Override
    public void aggiungiAlCatalogo(String riferimento_id, String categoria_id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select supercategoria_id from categoria where categoria_id = cast(? as int)");
        ps.setString(1, categoria_id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            rs.getString("supercategoria_id");
            if (rs.wasNull()) {
                System.out.println("Non aveva un padre");
                aggiungiAlCatalogo = connection.prepareStatement("insert into catalogo values (default, cast(? as int), cast(? as int))");
                aggiungiAlCatalogo.setString(1, riferimento_id);
                aggiungiAlCatalogo.setString(2, categoria_id);
                aggiungiAlCatalogo.executeUpdate();
            }
            else {
                //Qui la situazione si complica.
            }
        }
    }
}
