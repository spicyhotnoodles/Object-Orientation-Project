package DAOImplementations;

import DAO.WebDAO;
import DBEntities.Web;

import java.sql.*;
import java.util.List;

public class WebDAOPostgre implements WebDAO {

    private Connection connection;
    private String[] inserisciWeb;

    public WebDAOPostgre(Connection connection) throws SQLException {
        this.connection = connection;
        inserisciWeb = new String[3];
        inserisciWeb[0] = "insert into riferimento values (default, ?, ?, ?, ?, ?, ?, cast(? as tipologia));";
        inserisciWeb[1] = "select currval('riferimento_riferimento_id_seq');";
        inserisciWeb[2] = "insert into web values (?, ?, ?, ?)";
    }

    @Override
    public String inserisciWeb(Web web) throws SQLException {
        String codice = "";
        String s = "";
        s = componiAutori(web.getAutori());
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(inserisciWeb[0]);
            ps.setString(1, web.getTitolo());
            ps.setString(2, s);
            ps.setString(3, web.getData());
            ps.setString(4, web.getDescrizione());
            ps.setString(5, web.getLingua());
            ps.setString(6, web.getTipo());
            ps.executeUpdate();
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(inserisciWeb[1]);
                if (rs.next()) {
                    codice = rs.getString("currval");
                    ps = connection.prepareStatement(inserisciWeb[2]);
                    ps.setString(1, web.getUrl());
                    ps.setString(2, web.getSito());
                    ps.setString(3, web.getTipoSito());
                    ps.setInt(4, Integer.parseInt(rs.getString("currval")));
                    ps.executeUpdate();
                    connection.commit();
                }
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda istruzione di inserimento di un oggetto web fallita:\n" + e);
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima istruzione di inserimento di un oggetto web fallita:\n" + e);
            connection.rollback();
        }
        return codice;
    }

    @Override
    public void modificaWeb(Web web) throws SQLException {

    }

    public String componiAutori(List<String> autori) {
        String s = "";
        for (String a : autori) {
            s = s + "[" + a + "]" + " ";
        }
        return s;
    }
}
