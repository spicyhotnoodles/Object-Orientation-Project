package DAOImplementations;

import DAO.ConvegnoDAO;
import DBEntities.Convegno;

import java.sql.*;
import java.util.List;

public class ConvegnoDAOPostgre implements ConvegnoDAO {

    private Connection connection;

    private String[] inserisciConvegno;

    private String[] modificaConvegno;

    public ConvegnoDAOPostgre(Connection connection) {
        this.connection = connection;
        inserisciConvegno = new String[6];
        modificaConvegno = new String[2];
        inserisciConvegno[0] = "insert into riferimento values (default, ?, ?, ?, ?, ?, cast(? as tipologia));";
        inserisciConvegno[1] = "select currval('riferimento_riferimento_id_seq');";
        inserisciConvegno[2] = "insert into convegno values (?, ?, ?);";
        inserisciConvegno[3] = "insert into catalogo values (default, ?, ?);";
        inserisciConvegno[4] = "insert into citazione values (default, ?, ?);";
        inserisciConvegno[5] = "insert into tags values (default, ?, ?);";
        modificaConvegno[0] = "update riferimento set titolo=?, autori=?, data_pub=?, descrizione=?, lingua=? where riferimento_id = cast(? as int);";
        modificaConvegno[1] = "update convegno set doi=?, luogo=? where riferimento_id = cast(? as int);";
    }


    @Override
    public String inserisciConvegno(Convegno convegno) throws SQLException {
        String s = "";
        String codice = "";
        s = componiAutori(convegno.getAutori());
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(inserisciConvegno[0]);
            ps.setString(1, convegno.getTitolo());
            ps.setString(2, s);
            ps.setString(3, convegno.getData());
            ps.setString(4, convegno.getDescrizione());
            ps.setString(5, convegno.getLingua());
            ps.setString(6, "art_convegno");
            ps.executeUpdate();
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(inserisciConvegno[1]);
                if (rs.next()) {
                    codice = rs.getString("currval");
                    ps = connection.prepareStatement(inserisciConvegno[2]);
                    ps.setString(1, convegno.getDoi());
                    ps.setString(2, convegno.getLuogo());
                    ps.setInt(3, Integer.parseInt(codice));
                    ps.executeUpdate();
                    connection.commit();
                }
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda istruzione di inserimento di un oggetto di tipo convegno fallita:\n" + e);
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima istruzione di inserimento di un oggetto di tipo convegno fallita:\n" + e);
            connection.rollback();
        }
        connection.setAutoCommit(true);
        return codice;
    }

    @Override
    public void modificaConvegno(Convegno convegno) throws SQLException {
        String s = "";
        s = componiAutori(convegno.getAutori());
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(modificaConvegno[0]);
            ps.setString(1, convegno.getTitolo());
            ps.setString(2, s);
            ps.setString(3, convegno.getData());
            ps.setString(4, convegno.getDescrizione());
            ps.setString(5, convegno.getLingua());
            ps.setString(6, convegno.getCodice());
            ps.executeUpdate();
            try {
                ps = connection.prepareStatement(modificaConvegno[1]);
                ps.setString(1, convegno.getDoi());
                ps.setString(2, convegno.getLuogo());
                ps.setInt(3, Integer.parseInt(convegno.getCodice()));
                ps.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda istruzione di modifica di un oggetto di tipo convegno fallita:\n" + e);
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima istruzione di modifica di un oggetto di tipo convegno fallita:\n" + e);
            connection.rollback();
        }
    }

    public String componiAutori(List<String> autori) {
        String s = "";
        for (String a : autori) {
            s = s + "[" + a + "]" + " ";
        }
        return s;
    }
}
