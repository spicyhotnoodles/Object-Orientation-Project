package DAOImplementations;

import DAO.CategoriaDAO;
import DBEntities.Categoria;

import java.sql.*;

public class CategoriaDAOPostgre implements CategoriaDAO {

    private Connection connection;
    //Qui vanno definiti gli Statement per le istruzioni SQL che i vari metodi devono eseguire
    PreparedStatement creaCategoria;

    public CategoriaDAOPostgre(Connection connection) {
        this.connection=connection;
    }

    @Override
    public String creaCategoria(Categoria categoria) throws SQLException {
        String codice = "";
        Statement st = connection.createStatement();
        try {
            creaCategoria = connection.prepareStatement("insert into categoria values (default, ?)");
            creaCategoria.setString(1, categoria.getNome());
            creaCategoria.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Inserimento fallito! Inserimento di un oggetto categoria fallito:\n" + e);
        }
        try {
            ResultSet rs = st.executeQuery("select currval('categoria_categoria_id_seq');");
            if (rs.next())
                codice = rs.getString("currval");
        } catch (SQLException e) {
            System.out.println("Selezione fallita! Selezione di un oggetto sequence fallita:\n" + e);
        }
        return codice;
    }

    @Override
    public int eliminaCategoria(Categoria categoria) {
        return 0;
    }
}
