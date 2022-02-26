package GUI;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.*;
/*
import java.sql.Connection;
import java.sql.SQLException;
 */
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
    private FinestraPrincipale fp;


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
            sottocategoriaDAO = new SottocategoriaDAOPostgre(connection);
        }
        catch (SQLException e) { //catch (SQLException | ConnectionException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

}
