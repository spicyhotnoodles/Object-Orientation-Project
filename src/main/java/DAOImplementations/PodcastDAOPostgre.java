package DAOImplementations;

import DAO.PodcastDAO;
import DBEntities.Podcast;

import java.sql.*;
import java.util.List;

public class PodcastDAOPostgre implements PodcastDAO {

    private Connection connection;
    private String[] inserisciPodcast;
    private String[] modificaPodcast;

    public PodcastDAOPostgre(Connection connection) {
        this.connection = connection;
        inserisciPodcast = new String[6];
        modificaPodcast = new String[2];
        inserisciPodcast[0] = "insert into riferimento values (default, ?, ?, ?, ?, ?, cast(? as tipologia));";
        inserisciPodcast[1] = "select currval('riferimento_riferimento_id_seq');";
        inserisciPodcast[2] = "insert into podcast values (?, ?, ?, cast(? as int));";
        inserisciPodcast[3] = "insert into catalogo values (default, ?, ?);";
        inserisciPodcast[4] = "insert into citazione values (default, ?, ?);";
        inserisciPodcast[5] = "insert into tags values (default, ?, ?);";
        modificaPodcast[0] = "update riferimento set titolo=?, autori=?, data_pub=?, descrizione=?, lingua=? where riferimento_id = cast(? as int);";
        modificaPodcast[1] = "update podcast set doi=?, episodio=?, serie=? where riferimento_id = cast(? as int);";
    }

    public String inserisciPodcast(Podcast p) throws SQLException {
        String s = "";
        String codice = "";
        s = componiAutori(p.getAutori());
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(inserisciPodcast[0]);
            ps.setString(1, p.getTitolo());
            ps.setString(2, s);
            ps.setString(3, p.getData());
            ps.setString(4, p.getDescrizione());
            ps.setString(5, p.getLingua());
            ps.setString(6, "podcast");
            ps.executeUpdate();
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(inserisciPodcast[1]);
                if (rs.next()) {
                    codice = rs.getString("currval");
                    ps = connection.prepareStatement(inserisciPodcast[2]);
                    ps.setString(1, p.getDoi());
                    ps.setString(2, p.getEpisodio());
                    ps.setString(3, p.getSerie());
                    ps.setString(4, codice);
                    ps.executeUpdate();
                    connection.commit();
                }
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda istruzione di inserimento di un oggetto podcast fallita:\n" + e);
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima istruzione di inserimento di un oggetto podcast fallita:\n" + e);
            connection.rollback();
        }
        connection.setAutoCommit(true);
        return codice;
    }

    public void modificaPodcast(Podcast p) throws SQLException {
        String s = "";
        s = componiAutori(p.getAutori());
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(modificaPodcast[0]);
            ps.setString(1, p.getTitolo());
            ps.setString(2, s);
            ps.setString(3, p.getData());
            ps.setString(4, p.getDescrizione());
            ps.setString(5, p.getLingua());
            ps.setString(6, p.getCodice());
            ps.executeUpdate();
            try {
                ps = connection.prepareStatement(modificaPodcast[1]);
                ps.setString(1, p.getDoi());
                ps.setString(2, p.getEpisodio());
                ps.setString(3, p.getSerie());
                ps.setString(4, p.getCodice());
                ps.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda istruzione di modifica di un oggetto podcast fallita:\n" + e);
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima istruzione di modifica di un oggetto podcast fallita:\n" + e);
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
