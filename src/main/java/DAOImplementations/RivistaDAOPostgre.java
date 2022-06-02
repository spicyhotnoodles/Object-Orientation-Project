package DAOImplementations;

import DAO.RivistaDAO;
import DBEntities.Rivista;

import java.sql.*;
import java.util.List;

public class RivistaDAOPostgre implements RivistaDAO {

    private Connection connection;
    private String[] inserisciRivista;
    private String[] modificaRivista;

    public RivistaDAOPostgre(Connection connection) {
        this.connection = connection;
        inserisciRivista = new String[6];
        modificaRivista = new String[2];
        inserisciRivista[0] = "insert into riferimento values (default, ?, ?, ?, ?, ?, cast(? as tipologia));";
        inserisciRivista[1] = "select currval('riferimento_riferimento_id_seq');";
        inserisciRivista[2] = "insert into rivista values (?, ?, ?, cast(? as int));";
        inserisciRivista[3] = "insert into catalogo values (default, ?, ?);";
        inserisciRivista[4] = "insert into citazione values (default, ?, ?);";
        inserisciRivista[5] = "insert into tags values (default, ?, ?);";
        modificaRivista[0] = "update riferimento set titolo=?, autori=?, data_pub=?, descrizione=?, lingua=? where riferimento_id = cast(? as int);";
        modificaRivista[1] = "update rivista set issn=?, pagine=?, fascicolo=? where riferimento_id = cast(? as int);";
    }

    public String inserisciRivista(Rivista r) throws SQLException {
        String s = "";
        String codice = "";
        s = componiAutori(r.getAutori());
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(inserisciRivista[0]);
            ps.setString(1, r.getTitolo());
            ps.setString(2, s);
            ps.setString(3, r.getData());
            ps.setString(4, r.getDescrizione());
            ps.setString(5, r.getLingua());
            ps.setString(6, "art_rivista");
            ps.executeUpdate();
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(inserisciRivista[1]);
                if (rs.next()) {
                    codice = rs.getString("currval");
                    ps = connection.prepareStatement(inserisciRivista[2]);
                    ps.setString(1, r.getIssn());
                    ps.setString(2, r.getPagine());
                    ps.setString(3, r.getFascicolo());
                    ps.setString(4, codice);
                    ps.executeUpdate();
                    connection.commit();
                }
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda istruzione di inserimento di un oggetto rivista fallita:\n" + e);
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima istruzione di inserimento di un oggetto rivista fallita:\n" + e);
            connection.rollback();
        }
        connection.setAutoCommit(true);
        return codice;
    }

    public void modificaRivista(Rivista r) throws SQLException {
        String s = "";
        s = componiAutori(r.getAutori());
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(modificaRivista[0]);
            ps.setString(1, r.getTitolo());
            ps.setString(2, s);
            ps.setString(3, r.getData());
            ps.setString(4, r.getDescrizione());
            ps.setString(5, r.getLingua());
            ps.setString(6, r.getCodice());
            ps.executeUpdate();
            try {
                ps = connection.prepareStatement(modificaRivista[1]);
                ps.setString(1, r.getIssn());
                ps.setString(2, r.getPagine());
                ps.setString(3, r.getFascicolo());
                ps.setString(4, r.getCodice());
                ps.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda istruzione di modifica di un oggetto rivista fallita:\n" + e);
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima istruzione di modifica di un oggetto rivista fallita:\n" + e);
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
