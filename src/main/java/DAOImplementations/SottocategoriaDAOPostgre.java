package DAOImplementations;

import DAO.SottocategoriaDAO;
import DBEntities.Categoria;

import java.sql.*;

public class SottocategoriaDAOPostgre implements SottocategoriaDAO {

    private Connection connection;
    //Qui vanno definiti gli Statement per le istruzioni SQL che i vari metodi devono eseguire
    private PreparedStatement creaSottocategoria;

    public SottocategoriaDAOPostgre(Connection connection) throws SQLException {
        this.connection=connection;
        creaSottocategoria = connection.prepareStatement("insert into sottocategoria values (default, ?. ?)");
    }

    @Override
    public String creaSottocategoria(Categoria sottocategoria, Categoria supercategoria) {
        String codice = "";
        try {
            creaSottocategoria.setString(1, sottocategoria.getNome());
            creaSottocategoria.setString(2, supercategoria.getCodice());
            creaSottocategoria.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Inserimento fallito! Inserimento di un oggetto sottocategoria fallito:\n" + e);
        }
        Statement st;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery("select currval('')");
            if (rs.next())
                codice = rs.getString("currval");
        } catch (SQLException e) {
            System.out.println("Selezione fallita! Selezione di un oggetto currval (sottocategoria) fallita:\n" + e);
        }
        return codice;
    }
}
