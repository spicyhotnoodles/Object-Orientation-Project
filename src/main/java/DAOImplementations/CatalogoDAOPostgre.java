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
    String queryRicorsiva = "with recursive ottieniPadri as (select * from categoria c where categoria_id = ?" +
            "union all select c.* from categoria c join ottieniPadri on ottieniPadri.supercategoria_id = c.categoria_id)" +
            "select * from ottieniPadri order by categoria_id";
    PreparedStatement ottieniPadri;

    public CatalogoDAOPostgre(Connection connection) {
        this.connection=connection;
    }

    @Override
    public void rimuoviDalCatalogo(String riferimento_id, String categoria_id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select supercategoria_id from categoria where categoria_id = cast(? as int)");
        ps.setString(1, categoria_id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            rs.getString("supercategoria_id");
            if (rs.wasNull()) {
                rimuoviDalCatalogo = connection.prepareStatement("delete from catalogo where categoria_id = cast(? as int) and riferimento_id = cast(? as int)");
                rimuoviDalCatalogo.setString(1, riferimento_id);
                rimuoviDalCatalogo.setString(2, riferimento_id);
                rimuoviDalCatalogo.executeUpdate();
            }
            else {
                //Qui la situazione si complica tantissimo
                ottieniPadri = connection.prepareStatement(queryRicorsiva + " desc");
                ottieniPadri.setInt(1, Integer.parseInt(categoria_id));
                ResultSet anchestors = ottieniPadri.executeQuery();
                while (anchestors.next()) {
                    System.out.println("Entro Padre");
                    PreparedStatement ottieniFigliDelPadre = connection.prepareStatement("select * from categoria where supercategoria_id = ?");
                    ottieniFigliDelPadre.setInt(1, anchestors.getInt("categoria_id"));
                    ResultSet figli = ottieniFigliDelPadre.executeQuery();
                    while (figli.next()) {
                        System.out.println("Entro figlio");
                        PreparedStatement verificaCategoriaFratello = connection.prepareStatement("select * from catalogo where categoria_id = ? and riferimento_id = ?");
                        verificaCategoriaFratello.setInt(1, figli.getInt("categoria_id"));
                        verificaCategoriaFratello.setInt(2, Integer.parseInt(riferimento_id));
                        ResultSet check = verificaCategoriaFratello.executeQuery();
                        if (check.next()) {
                            System.out.println("SKippo padre");
                            break;//skippa padre
                        }
                        else {
                            System.out.println("Elimino padre");
                            PreparedStatement eliminaPadre = connection.prepareStatement("delete from catalogo where categoria_id = ?");
                            eliminaPadre.setInt(1, anchestors.getInt("categoria_id"));
                        }
                    }
                }
                PreparedStatement pst = connection.prepareStatement("delete from catalogo where categoria_id = cast(? as int)");
                pst.setString(1, categoria_id);
                pst.executeUpdate();
            }
        }
    }

    @Override
    public void aggiungiAlCatalogo(String riferimento_id, String categoria_id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select supercategoria_id from categoria where categoria_id = cast(? as int)");
        String s = "insert into catalogo values (default, cast(? as int), cast(? as int)) on conflict (categoria_id) do nothing"; //poiché è impossibile associare lo stesso riferimento più volte alla stessa categoria (unique), se viene violato il vincolo, l'inserimento viene ignorato
        ps.setString(1, categoria_id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            rs.getString("supercategoria_id");
            if (rs.wasNull()) {
                aggiungiAlCatalogo = connection.prepareStatement(s);
                aggiungiAlCatalogo.setString(1, riferimento_id);
                aggiungiAlCatalogo.setString(2, categoria_id);
                aggiungiAlCatalogo.executeUpdate();
            }
            else {
                //Qui la situazione si complica.
                ottieniPadri = connection.prepareStatement(queryRicorsiva);
                ottieniPadri.setInt(1, Integer.parseInt(categoria_id));
                ResultSet anchestors = ottieniPadri.executeQuery();
                //il riferimento viene legato alla categoria scelta e a tutti i suoi padri
                while (anchestors.next()) {
                    aggiungiAlCatalogo = connection.prepareStatement(s);
                    aggiungiAlCatalogo.setInt(1, Integer.parseInt(riferimento_id));
                    aggiungiAlCatalogo.setInt(2, anchestors.getInt("categoria_id"));
                    aggiungiAlCatalogo.executeUpdate();
                }
            }
        }
    }
}
