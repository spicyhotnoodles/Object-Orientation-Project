package DAOImplementations;

import DAO.LibroDAO;
import DBEntities.Libro;

import java.sql.*;
import java.util.List;

public class LibroDAOPostgre implements LibroDAO {

    private Connection connection;
    private String[] inserisciLibro;
    private String[] modificaLibro;

    public LibroDAOPostgre(Connection connection) throws SQLException {
        this.connection = connection;
        inserisciLibro = new String[6];
        modificaLibro = new String[2];
        inserisciLibro[0] = "insert into riferimento values (default, ?, ?, ?, ?, ?, cast(? as tipologia));";
        inserisciLibro[1] = "select currval('riferimento_riferimento_id_seq');";
        inserisciLibro[2] = "insert into libro values (?, ?, ?, ?, ?)";
        inserisciLibro[3] = "insert into catalogo values (default, ?, ?);";
        inserisciLibro[4] = "insert into citazione values (default, ?. ?);";
        inserisciLibro[5] = "insert into tags values (default, ?, ?);";
        modificaLibro[0] = "update riferimento set titolo=?, autori=?, data_pub=?, descrizione=?, lingua=? where riferimento_id = cast(? as int);";
        modificaLibro[1] = "update libro set isbn=?, pagine=?, serie=?, volume=? where riferimento_id = cast(? as int);";
    }

    @Override
    public String inserisciLibro(Libro libro) throws SQLException {
        String s = "";
        String codice = "";
        s = componiAutori(libro.getAutori());
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(inserisciLibro[0]);
            ps.setString(1, libro.getTitolo());
            ps.setString(2, s);
            ps.setString(3, libro.getData());
            ps.setString(4, libro.getDescrizione());
            ps.setString(5, libro.getLingua());
            ps.setString(6, "libro");
            ps.executeUpdate();
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(inserisciLibro[1]);
                if (rs.next()) {
                    codice = rs.getString("currval");
                    ps = connection.prepareStatement(inserisciLibro[2]);
                    ps.setString(1, libro.getIsbn());
                    ps.setString(2, libro.getPagine());
                    ps.setString(3, libro.getSerie());
                    ps.setString(4, libro.getVolume());
                    ps.setInt(5, Integer.parseInt(rs.getString("currval")));
                    ps.executeUpdate();
                    connection.commit();
                }
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda istruzione di inserimento di un oggetto libro fallita:\n" + e);
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima istruzione di inserimento di un oggetto libro fallita:\n" + e);
            connection.rollback();
        }
        connection.setAutoCommit(true);
        return codice;
    }

    public String componiAutori(List<String> autori) {
        String s = "";
        for (String a : autori) {
            s = s + "[" + a + "]" + " ";
        }
        return s;
    }

    /*
    La funzione modificaLibro(Libro libro) consente di modificare un libro all'interno del database.
    Essa prende in ingresso il libro che si intende modificare e sovrascrive tutti i campi del record
    presente nel database il cui codice corrisponde a quello del libro che viene passato alla funzione.
    Questa funzione è anche in grado di modificare le categorie del libro; verifica che il libro abbia
    o meno una nuova lista di categorie:
    1) Se la lista esiste, allora verifica se nel catalogo sono già presenti delle categorie
       con il codice del libro:
        a) Se ci sono, allora sostituisce il codice delle vecchie categorie con quello delle nuove.
        b) Se non ci sono, allora effettua un inserimento in catalogo delle nuove categorie.
    2) Se la lista non esiste, allora verifica se nel catalogo sono già presenti delle categorie
       con il codice del libro. Se le trova, le elimina.
     */


    @Override
    public void modificaLibro(Libro libro) throws SQLException {
        String s = "";
        s = componiAutori(libro.getAutori());
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(modificaLibro[0]);
            ps.setString(1, libro.getTitolo());
            ps.setString(2, s);
            ps.setString(3, libro.getData());
            ps.setString(4, libro.getDescrizione());
            ps.setString(5, libro.getLingua());
            ps.setString(6, libro.getCodice());
            ps.executeUpdate();
            try {
                ps = connection.prepareStatement(modificaLibro[1]);
                ps.setString(1, libro.getIsbn());
                ps.setString(2, libro.getPagine());
                ps.setString(3, libro.getSerie());
                ps.setString(4, libro.getVolume());
                ps.setInt(5, Integer.parseInt(libro.getCodice()));
                ps.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda istruzione di modifica di un oggetto libro fallita:\n" + e);
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima istruzione di modifica di un oggetto libro fallita:\n" + e);
        }
        connection.setAutoCommit(true);
    }
}