package GUI;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/*
import java.sql.Connection;
import java.sql.SQLException;
 */
import DBEntities.*;
import Exceptions.ConnectionException;
import DAOImplementations.*;
import DBConfig.DBConnection;


public class Controller {

    //Attributi della connessione al database
    private DBConnection dbconn = null;
    private Connection connection = null;
    //Attributi dei DAO implementati per postgre
    private CatalogoDAOPostgre catalogoDAO;
    private CategoriaDAOPostgre categoriaDAO;
    private CitazioneDAOPostgre citazioneDAO;
    private RiferimentoDAOPostgre riferimentoDAO;
    private SottocategoriaDAOPostgre sottocategoriaDAO;
    private LibroDAOPostgre libroDAO;
    private FinestraPrincipale fp;
    public ArrayList<Riferimento> riferimenti = new ArrayList<Riferimento>();


    public Controller() throws SQLException, IOException {
        /*Il costruttore inizializza tutti i frame dell'applicativo.
        In particolare, ci sarà un istruzione del tipo <<framePrincipale = new Main("nomeFinestra",this)>>
        per passare un oggetto di tipo controllore alla finestra principale dell'applicativo e
        consentirgli di interagire con i metodi della classe Controller.
         */
        fp = new FinestraPrincipale("Titolo",this);
        fp.setVisible(true);
    }

    public static void main(String[] args) throws SQLException, IOException {
        Controller c = new Controller();
        c.startConnection();
        //c.libroDAO.inserisciLibro(l);
        /*if (c.libroDAO.inserisciLibro(l))
            System.out.println("Libro inserito con successo!");
        else
            System.out.println("Errore!");*/
        /*List<String> autori = new ArrayList<String>();
        autori.add("J.R.R. Tolkien");
        Libro l = Libro.builder()
                .titolo("Il Signore degli Anelli")
                .descrizione("Il miglior fantasy di sempre.")
                .data("1955")
                .lingua("Inglese")
                .tipo("Libro")
                .note("...")
                .autori(autori)
                .pagine("1395")
                .isbn("86824972019012")
                .serie("Il Signore degli Anelli")
                .volume("La Compagnia dell'Anello")
                .build();
        System.out.println(l.toString());*/
    }

    //startConnection() crea una connessione al database

    public void startConnection() throws SQLException {
        try {
            //inizializzo la connessione
            dbconn = DBConnection.getInstance("progetto");
            connection = dbconn.getConnection();
            //inizializzo i costruttori *DAOPostgre
            catalogoDAO = new CatalogoDAOPostgre(connection);
            categoriaDAO = new CategoriaDAOPostgre(connection);
            citazioneDAO = new CitazioneDAOPostgre(connection);
            riferimentoDAO = new RiferimentoDAOPostgre(connection);
            libroDAO = new LibroDAOPostgre(connection);
            sottocategoriaDAO = new SottocategoriaDAOPostgre(connection);
        }
        catch (SQLException e) { //catch (SQLException | ConnectionException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public ArrayList<Riferimento> ottieniRiferimenti() {
        ArrayList<Riferimento> riferimenti = new ArrayList<Riferimento>();
        List<String> autori = new ArrayList<String>();
        List<String> autori2 = new ArrayList<String>();
        autori.add("George R.R. Martin");
        autori2.add("Autore di prova");
        Libro l = Libro.builder()
                .titolo("Il Trono di Spade. Un gioco di troni")
                .descrizione("Valar Morghulis.")
                .data("2019")
                .lingua("Inglese")
                .tipo("libro")
                .note("Questa è una mia nota personale circa il riferimento")
                .autori(autori)
                .pagine("829")
                .isbn("978-8804711957")
                .serie("Cronache del Ghiaccio e del Fuoco")
                .volume("Primo")
                .build();
        Web w = Web.builder()
                .titolo("Titolo di prova")
                .descrizione("Descrizione di prova")
                .data("2022")
                .lingua("Italiano")
                .tipo("web")
                .note("Questa è una mia nota personale circa il riferimento")
                .autori(autori2)
                .sito("Social Network di prova")
                .url("www.socialnetworkdiprova.it")
                .tipoSito("Social Network")
                .build();
        riferimenti.add(l);
        riferimenti.add(w);
        return riferimenti;
    }
}
