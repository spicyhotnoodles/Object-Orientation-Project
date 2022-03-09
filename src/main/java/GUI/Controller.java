package GUI;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
        startConnection();
        riferimentoDAO = new RiferimentoDAOPostgre(connection);
        fp = new FinestraPrincipale("Titolo",this);
        fp.setVisible(true);
    }

    public static void main(String[] args) throws SQLException, IOException {
        Controller c = new Controller();
        /*Categoria cat = new Categoria("Fantasy");
        String codCategoria = c.categoriaDAO.creaCategoria(cat);
        cat.setCodice(codCategoria);
        List<Categoria> categorie = new ArrayList<Categoria>();
        categorie.add(cat);
        List<String> autori = new ArrayList<String>();
        autori.add("J.R.R. Tolkien");
        Libro l = Libro.builder()
                .titolo("Il Signore degli Anelli")
                .autori(autori)
                .descrizione("Il miglior fantasy di sempre.")
                .data("1955")
                .lingua("Inglese")
                .tipo("libro")
                .note("...")
                .categorie(categorie)
                .pagine("1395")
                .isbn("978-8830105263")
                .serie("Il Signore degli Anelli")
                .volume("La Compagnia dell'Anello")
                .build();
        String codice = c.libroDAO.inserisciLibro(l);
        l.setCodice(codice);
        System.out.println(l.toString());
        categorie.remove(0);
        l.setTitolo("Il Signore degli Anelli - La compagnia dell'anello");
        l.setCategorie(categorie);
        c.libroDAO.modificaLibro(l);*/
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
        riferimenti = riferimentoDAO.ottieniRiferimenti();
        return riferimenti;
    }
}
