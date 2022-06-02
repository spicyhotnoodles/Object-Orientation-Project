package DAOImplementations;

import DAO.GiornaleDAO;
import DBEntities.Giornale;

import java.sql.*;
import java.util.List;

public class GiornaleDAOPostgre implements GiornaleDAO {

    private Connection connection;
    private String[] inserisciGiornale;
    private String[] modificaGiornale;

    public GiornaleDAOPostgre(Connection connection) {
        this.connection = connection;
        inserisciGiornale = new String[6];
        modificaGiornale = new String[6];
        inserisciGiornale[0] = "insert into riferimento values (default, ?, ?, ?, ?, ?, cast(? as tipologia));";
        inserisciGiornale[1] = "select currval('riferimento_riferimento_id_seq');";
        inserisciGiornale[2] = "insert into giornale values (?, ?, ?, cast(? as int));";
        inserisciGiornale[3] = "insert into catalogo values (default, ?, ?);";
        inserisciGiornale[4] = "insert into citazione values (default, ?, ?);";
        inserisciGiornale[5] = "insert into tags values (default, ?, ?);";
        modificaGiornale[0] = "update riferimento set titolo=?, autori=?, data_pub=?, descrizione=?, lingua=? where riferimento_id = cast(? as int);";
        modificaGiornale[1] = "update giornale set issn=?, testata=?, sezione=? where riferimento_id = cast(? as int);";

    }

    public String inserisciGiornale(Giornale g) throws SQLException {
        String s = "";
        String codice = "";
        s = componiAutori(g.getAutori());
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(inserisciGiornale[0]);
            ps.setString(1, g.getTitolo());
            ps.setString(2, s);
            ps.setString(3, g.getData());
            ps.setString(4, g.getDescrizione());
            ps.setString(5, g.getLingua());
            ps.setString(6, "art_giornale");
            ps.executeUpdate();
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(inserisciGiornale[1]);
                if (rs.next()) {
                    codice = rs.getString("currval");
                    ps = connection.prepareStatement(inserisciGiornale[2]);
                    ps.setString(1, g.getIssn());
                    ps.setString(2, g.getTestata());
                    ps.setString(3, g.getSezione());
                    ps.setString(4, codice);
                    ps.executeUpdate();
                    connection.commit();
                }
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda istruzione di inserimento di un oggetto giornale fallita:\n" + e);
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima istruzione di inserimento di un oggetto giornale fallita:\n" + e);
        }
        connection.setAutoCommit(true);
        return codice;
    }

    public void modificaGiornale(Giornale g) throws SQLException {
        String s = "";
        s = componiAutori(g.getAutori());
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(modificaGiornale[0]);
            ps.setString(1, g.getTitolo());
            ps.setString(2, s);
            ps.setString(3, g.getData());
            ps.setString(4, g.getDescrizione());
            ps.setString(5, g.getLingua());
            ps.setString(6, g.getCodice());
            ps.executeUpdate();
            try {
                ps = connection.prepareStatement(modificaGiornale[1]);
                ps.setString(1, g.getIssn());
                ps.setString(2, g.getTestata());
                ps.setString(3, g.getSezione());
                ps.setString(4, g.getCodice());
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda istruzione di modifica di un oggetto giornale fallita:\n" + e);
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima istruzione di modifica di un oggetto giornale fallita:\n" + e);
            connection.rollback();
        }
        connection.setAutoCommit(true);
    }

    public String componiAutori(List<String> autori) {
        String s = "";
        for (String a : autori) {
            s = s + "[" + a + "]" + " ";
        }
        return s;
    }
}
