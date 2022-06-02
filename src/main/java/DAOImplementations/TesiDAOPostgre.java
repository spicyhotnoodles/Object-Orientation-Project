package DAOImplementations;

import DBEntities.Tesi;

import java.sql.*;
import java.util.List;

public class TesiDAOPostgre {

    private Connection connection;
    private String[] inserisciTesi;
    private String[] modificaTesi;

    public TesiDAOPostgre(Connection connection) {
        this.connection = connection;
        inserisciTesi = new String[6];
        modificaTesi = new String[2];
        inserisciTesi[0] = "insert into riferimento values (default, ?, ?, ?, ?, ?, cast(? as tipologia));";
        inserisciTesi[1] = "select currval('riferimento_riferimento_id_seq');";
        inserisciTesi[2] = "insert into tesi values (?, ?, ?, cast(? as int));";
        inserisciTesi[3] = "insert into catalogo values (default, ?, ?);";
        inserisciTesi[4] = "insert into citazione values (default, ?, ?);";
        inserisciTesi[5] = "insert into tags values (default, ?, ?);";
        modificaTesi[0] = "update riferimento set titolo=?, autori=?, data_pub=?, descrizione=?, lingua=? where riferimento_id = cast(? as int);";
        modificaTesi[1] = "update tesi set doi=?, tipo_tesi=?, ateneo=? where riferimento_id = cast(? as int);";
    }

    public String inserisciTesi(Tesi t) throws SQLException {
        String s = "";
        String codice = "";
        s = componiAutori(t.getAutori());
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(inserisciTesi[0]);
            ps.setString(1, t.getTitolo());
            ps.setString(2, s);
            ps.setString(3, t.getData());
            ps.setString(4, t.getDescrizione());
            ps.setString(5, t.getLingua());
            ps.setString(6, "tesi");
            ps.executeUpdate();
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(inserisciTesi[1]);
                if (rs.next()) {
                    codice = rs.getString("currval");
                    ps = connection.prepareStatement(inserisciTesi[2]);
                    ps.setString(1, t.getDoi());
                    ps.setString(2, t.getTipoTesi());
                    ps.setString(3, t.getAteneo());
                    ps.setString(4, codice);
                    ps.executeUpdate();
                    connection.commit();
                }
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda istruzione di inserimento di un oggetto tesi fallita:\n" + e);
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima istruzione di inserimento di un oggetto tesi fallita:\n" + e);
            connection.rollback();
        }
        connection.setAutoCommit(true);
        return s;
    }

    public void modificaTesi(Tesi t) throws SQLException {
        String s = "";
        s = componiAutori(t.getAutori());
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(modificaTesi[0]);
            ps.setString(1, t.getTitolo());
            ps.setString(2, s);
            ps.setString(3, t.getData());
            ps.setString(4, t.getDescrizione());
            ps.setString(5, t.getLingua());
            ps.setString(6, t.getCodice());
            ps.executeUpdate();
            try {
                ps = connection.prepareStatement(modificaTesi[1]);
                ps.setString(1, t.getDoi());
                ps.setString(2, t.getTipoTesi());
                ps.setString(3, t.getAteneo());
                ps.setString(4, t.getCodice());
                ps.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda istruzione di modifica di un oggetto tesi fallita:\n" + e);
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima istruzione di modifica di un oggetto tesi fallita:\n" + e);
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
