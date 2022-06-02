package DAOImplementations;

import DAO.IntervistaDAO;
import DBEntities.Intervista;

import java.sql.*;
import java.util.List;

public class IntervistaDAOPostgre implements IntervistaDAO {

    private Connection connection;
    private String[] inserisciIntervista;
    private String[] modificaIntervista;

    public IntervistaDAOPostgre(Connection connection) {
        this.connection = connection;
        inserisciIntervista = new String[6];
        modificaIntervista = new String[2];
        inserisciIntervista[0] = "insert into riferimento values (default, ?, ?, ?, ?, ?, cast(? as tipologia));";
        inserisciIntervista[1] = "select currval('riferimento_riferimento_id_seq');";
        inserisciIntervista[2] = "insert into intervista values (?, ?, ?, cast(? as int))";
        inserisciIntervista[3] = "insert into catalogo values (default, ?, ?);";
        inserisciIntervista[4] = "insert into citazione values (default, ?, ?);";
        inserisciIntervista[5] = "insert into tags values (default, ?, ?);";
        modificaIntervista[0] = "update riferimento set titolo=?, autori=?, data_pub=?, descrizione=?, lingua=? where riferimento_id = cast(? as int);";
        modificaIntervista[1] = "update intervista set doi=?, mezzo=?, ospiti=? where riferimento_id = cast(? as int);";
    }

    public String inserisciIntervista(Intervista i) throws SQLException {
        String s = "";
        String codice = "";
        s = componiAutori(i.getAutori());
        String o = "";
        o = componiAutori(i.getOspiti());
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(inserisciIntervista[0]);
            ps.setString(1, i.getTitolo());
            ps.setString(2, s);
            ps.setString(3, i.getData());
            ps.setString(4, i.getDescrizione());
            ps.setString(5, i.getLingua());
            ps.setString(6, "art_rivista");
            ps.executeUpdate();
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(inserisciIntervista[1]);
                if (rs.next()) {
                    codice = rs.getString("currval");
                    ps = connection.prepareStatement(inserisciIntervista[2]);
                    ps.setString(1, i.getDoi());
                    ps.setString(2, i.getMezzo());
                    ps.setString(3, o);
                    ps.setString(4, codice);
                    ps.executeUpdate();
                    connection.commit();
                }
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda istruzione di inserimento di un oggetto intervista fallita:\n" + e);
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima istruzione di inserimento di un oggetto intervista fallita:\n" + e);
            connection.rollback();
        }
        connection.setAutoCommit(true);
        return codice;
    }

    public void modificaIntervista(Intervista i) throws SQLException {
        String s = "";
        String o = "";
        s = componiAutori(i.getAutori());
        o = componiAutori(i.getOspiti());
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(modificaIntervista[0]);
            ps.setString(1, i.getTitolo());
            ps.setString(2, s);
            ps.setString(3, i.getData());
            ps.setString(4, i.getDescrizione());
            ps.setString(5, i.getLingua());
            ps.setString(6, i.getCodice());
            ps.executeUpdate();
            try {
                ps = connection.prepareStatement(modificaIntervista[1]);
                ps.setString(1, i.getDoi());
                ps.setString(2, i.getMezzo());
                ps.setString(3, o);
                ps.setString(4, i.getCodice());
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda istruzione di modifica di un oggetto intervista fallita:\n" + e);
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima istruzione di modifica di un oggetto intervista fallita:\n" + e);
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

