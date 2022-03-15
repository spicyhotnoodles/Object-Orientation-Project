package DAOImplementations;

import DAO.CategoriaDAO;
import DBEntities.Categoria;

import java.sql.*;
import java.util.ArrayList;

public class CategoriaDAOPostgre implements CategoriaDAO {

    private Connection connection;
    //Qui vanno definiti gli Statement per le istruzioni SQL che i vari metodi devono eseguire
    PreparedStatement creaCategoria;
    Statement ottieniCategorie;
    PreparedStatement eliminaCategoria;

    public CategoriaDAOPostgre(Connection connection) {
        this.connection=connection;
    }

    @Override
    public String creaCategoria(Categoria categoria) throws SQLException {
        String id = "";
        creaCategoria = connection.prepareStatement("insert into categoria values (default, ?, ?)");
        creaCategoria.setString(1, categoria.getNome());
        if (categoria.getSupercategoria().equals(""))
            creaCategoria.setObject(2, null);
        else {
            PreparedStatement ottieniPadre = connection.prepareStatement("select categoria_id from categoria where nome = ?");
            ottieniPadre.setString(1, categoria.getSupercategoria());
            ResultSet padre = ottieniPadre.executeQuery();
            if (padre.next())
                creaCategoria.setInt(2, padre.getInt("categoria_id"));
        }
        creaCategoria.executeUpdate();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("select currval('categoria_id_categoria_seq')");
        if (rs.next())
            id = rs.getString("currval");
        return id;
    }

    @Override
    public void eliminaCategoria(String id) throws SQLException {
        eliminaCategoria = connection.prepareStatement("delete from categoria where categoria_id = cast(? as int)");
        eliminaCategoria.setString(1, id);
        eliminaCategoria.executeUpdate();
    }

    @Override
    public ArrayList<Categoria> ottieniCategorie() throws SQLException {
        ArrayList<Categoria> categorie = new ArrayList<>();
        ottieniCategorie = connection.createStatement();
        ResultSet categoria = ottieniCategorie.executeQuery("select * from categoria");
        while (categoria.next()) {
            Categoria c = new Categoria.Builder()
                    .setCodice(categoria.getString("categoria_id"))
                    .setNome(categoria.getString("nome"))
                    .setSupercategoria(categoria.getString("supercategoria_id"))
                    .build();
            categorie.add(c);
        }
        return categorie;
    }
}
