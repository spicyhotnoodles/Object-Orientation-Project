package DAOImplementations;

import DAO.LeggeDAO;
import DBEntities.Legge;

import java.sql.*;
import java.util.List;

public class LeggeDAOPostgre implements LeggeDAO {

    private Connection connection;
    private String[] inserisciLegge;
    private String[] modificaLegge;
    public LeggeDAOPostgre(Connection connection) {
        this.connection = connection;
        inserisciLegge = new String[6];
        modificaLegge = new String[2];
        inserisciLegge[0] = "insert into riferimento values (default, ?, ?, ?, ?, ?, cast(? as tipologia));";
        inserisciLegge[1] = "select currval('riferimento_riferimento_id_seq');";
        inserisciLegge[2] = "insert into legge values (?, ?, ?, cast(? as int))";
        inserisciLegge[3] = "insert into catalogo values (default, ?, ?);";
        inserisciLegge[4] = "insert into citazione values (default, ?, ?);";
        inserisciLegge[5] = "insert into tags values (default, ?, ?);";
        modificaLegge[0] = "update riferimento set titolo=?, autori=?, data_pub=?, descrizione=?, lingua=? where riferimento_id = cast(? as int);";
        modificaLegge[1] = "update legge set numero=?, tipo_legge=?, codice=? where riferimento_id = cast(? as int);";
    }

    public String inserisciLegge(Legge l) throws SQLException {
        String s = "";
        String codice = "";
        s = componiAutori(l.getAutori());
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(inserisciLegge[0]);
            ps.setString(1, l.getTitolo());
            ps.setString(2, s);
            ps.setString(3, l.getData());
            ps.setString(4, l.getDescrizione());
            ps.setString(5, l.getLingua());
            ps.setString(6, "legge");
            ps.executeUpdate();
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(inserisciLegge[1]);
                if (rs.next()) {
                    codice = rs.getString("currval");
                    ps = connection.prepareStatement(inserisciLegge[2]);
                    ps.setString(1, l.getNumero());
                    ps.setString(2, l.getTipoLegge());
                    ps.setString(3, l.getCodiceLegge());
                    ps.setString(4, codice);
                    ps.executeUpdate();
                    connection.commit();
                }
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda istruzione di inserimento di un oggetto legge fallita:\n" + e);
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima istruzione di inserimento di un oggetto legge fallita:\n" + e);
            connection.rollback();
        }
        connection.setAutoCommit(true);
        return codice;
    }

    public void modificaLegge(Legge l) throws SQLException {
        String s = "";
        s = componiAutori(l.getAutori());
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(modificaLegge[0]);
            ps.setString(1, l.getTitolo());
            ps.setString(2, s);
            ps.setString(3, l.getData());
            ps.setString(4, l.getDescrizione());
            ps.setString(5, l.getLingua());
            ps.setString(6, l.getCodice());
            ps.executeUpdate();
            try {
                ps = connection.prepareStatement(modificaLegge[1]);
                ps.setString(1, l.getNumero());
                ps.setString(2, l.getTipoLegge());
                ps.setString(3, l.getCodiceLegge());
                ps.setString(4, l.getCodice());
                ps.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda istruzione di modifica di un oggetto legge fallita:\n" + e);
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima istruzione di modifica di un oggetto legge fallita:\n" + e);
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
