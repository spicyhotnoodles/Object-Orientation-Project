package DAOImplementations;

import DAO.FilmDAO;
import DBEntities.Film;

import java.sql.*;
import java.util.List;

public class FilmDAOPostgre implements FilmDAO {

    private Connection connection;

    private String[] inserisciFilm;

    private String[] modificaFilm;

    public FilmDAOPostgre(Connection connection) throws SQLException {
        this.connection = connection;
        inserisciFilm = new String[6];
        modificaFilm =  new String[2];
        inserisciFilm[0] = "insert into riferimento values (default, ?, ?, ?, ?, ?, cast(? as tipologia));";
        inserisciFilm[1] = "select currval('riferimento_riferimento_id_seq');";
        inserisciFilm[2] = "insert into film values (?, ?, ?, ?)";
        inserisciFilm[3] = "insert into catalogo values (default, ?, ?);";
        inserisciFilm[4] = "insert into citazione values (default, ?, ?);";
        inserisciFilm[5] = "insert into tags values (default, ?, ?);";
        modificaFilm[0] = "update riferimento set titolo=?, autori=?, data_pub=?, descrizione=?, lingua=? where riferimento_id = cast(? as int);";
        modificaFilm[1] = "update film set isan=?, genere=?, distribuzione=? where riferimento_id = cast(? as int);";
    }

    @Override
    public void modificaFilm(Film f) throws SQLException {
        String s = "";
        s = componiAutori(f.getAutori());
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(modificaFilm[0]);
            ps.setString(1, f.getTitolo());
            ps.setString(2, s);
            ps.setString(3, f.getData());
            ps.setString(4, f.getDescrizione());
            ps.setString(5, f.getLingua());
            ps.setString(6, f.getCodice());
            ps.executeUpdate();
            try {
                ps = connection.prepareStatement(modificaFilm[1]);
                ps.setString(1, f.getIsan());
                ps.setString(2, f.getGenere());
                ps.setString(3, f.getDistribuzione());
                ps.setString(4, f.getCodice());
                ps.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda istruzione di modifica di un oggetto di tipo film fallita:\n" + e);
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima istruzione di modifica di un oggetto di tipo film fallita:\n" + e);
        }
    }

    public String componiAutori(List<String> autori) {
        String s = "";
        for (String a : autori) {
            s = s + "[" + a + "]" + " ";
        }
        return s;
    }

    @Override
    public String inserisciFilm(Film f) throws SQLException {
        String s = "";
        String codice = "";
        s = componiAutori(f.getAutori());
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(inserisciFilm[0]);
            ps.setString(1, f.getTitolo());
            ps.setString(2, s);
            ps.setString(3, f.getData());
            ps.setString(4, f.getDescrizione());
            ps.setString(5, f.getLingua());
            ps.setString(6, "film");
            ps.executeUpdate();
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(inserisciFilm[1]);
                if (rs.next()) {
                    codice = rs.getString("currval");
                    ps = connection.prepareStatement(inserisciFilm[2]);
                    ps.setString(1, f.getIsan());
                    ps.setString(2, f.getGenere());
                    ps.setString(3, f.getDistribuzione());
                    ps.setInt(4, Integer.parseInt(codice));
                    ps.executeUpdate();
                    connection.commit();
                }
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda istruzione di inserimento di un oggetto di tipo film fallita:\n" + e);
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima istruzione di inserimento di un oggetto di tipo film fallita:\n" + e);
        }
        connection.setAutoCommit(true);
        return codice;
    }
}
