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
    PreparedStatement rimuoviDalCatalogo;

    public CategoriaDAOPostgre(Connection connection) {
        this.connection=connection;
    }

    @Override
    public void creaCategoria(Categoria categoria) throws SQLException {
        creaCategoria = connection.prepareStatement("insert into categoria values (default, ?, ?)");
        creaCategoria.setString(1, categoria.getNome());
        if (categoria.getPadre().equals(""))
            creaCategoria.setObject(2, null);
        else {
            PreparedStatement ottieniPadre = connection.prepareStatement("select categoria_id from categoria where nome = ?");
            ottieniPadre.setString(1, categoria.getPadre());
            ResultSet padre = ottieniPadre.executeQuery();
            if (padre.next())
                creaCategoria.setInt(2, padre.getInt("categoria_id"));
        }
        creaCategoria.executeUpdate();
    }

    @Override
    public void eliminaCategoria(String id) throws SQLException {
        eliminaCategoria = connection.prepareStatement("delete from categoria where categoria_id = ?");
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
                    .setPadre(categoria.getString("supercategoria_id"))
                    .build();
            categorie.add(c);
        }
        return categorie;
    }

    /*@Override
    public ArrayList<Categoria> ottieniCategorie() throws SQLException {
        ArrayList<Categoria> categorie = new ArrayList<>();
        ottieniCategorie[0] = "select cast(c.categoria_id as varchar), c.nome, cast(s.padre_id as varchar) from categoria as c left outer join sottocategoria as s on c.categoria_id = s.id_sottocategoria"; //ottieni categorie
        ottieniCategorie[1] = "select cast(c.categoria_id as varchar), nome, cast(padre_id as varchar) from categoria as c left outer join sottocategoria as s on c.categoria_id = s.id_sottocategoria where categoria_id = cast(? as int)";
        Statement st = connection.createStatement();
        ResultSet categoria = st.executeQuery(ottieniCategorie[0]);
        while (categoria.next()) {
            categoria.getString("padre_id");
            if (categoria.wasNull()) {
                System.out.println("Non ha un padre");
                Categoria c = new Categoria.Builder()
                        .setCodice(categoria.getString("categoria_id"))
                        .setNome(categoria.getString("nome"))
                        .setPadre("")
                        .build();
                categorie.add(c);
            }
            else {
                System.out.println("Ha un padre");
                String nomeParent = "";
                PreparedStatement anchestor = connection.prepareStatement(ottieniCategorie[1]);
                anchestor.setString(1, categoria.getString("padre_id"));
                ResultSet padri = anchestor.executeQuery();
                while (padri.next()) {
                    nomeParent = padri.getString("nome");
                }
                Categoria c = new Categoria.Builder()
                        .setCodice(categoria.getString("categoria_id"))
                        .setNome(categoria.getString("nome"))
                        .setPadre(nomeParent)
                        .build();
                categorie.add(c);
            }
        }
        return categorie;
    }*/
}
