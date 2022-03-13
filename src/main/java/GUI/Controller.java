package GUI;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
/*
import java.sql.Connection;
import java.sql.SQLException;
 */
import DBEntities.*;
import DAOImplementations.*;
import DBConfig.DBConnection;


public class Controller {

    //Attributi della connessione al database
    private DBConnection dbconn = null;
    private Connection connection = null;
    //Attributi dei DAO implementati per postgre
    private TagDAOPostgre tagDAO;
    private CatalogoDAOPostgre catalogoDAO;
    private CategoriaDAOPostgre categoriaDAO;
    private CitazioneDAOPostgre citazioneDAO;
    private RiferimentoDAOPostgre riferimentoDAO;
    private SottocategoriaDAOPostgre sottocategoriaDAO;
    private LibroDAOPostgre libroDAO;
    private FinestraPrincipale fp;
    private FrameTabellaRiferimenti finestraTabella;
    private ArrayList<Riferimento> riferimenti = new ArrayList<Riferimento>();
    private ArrayList<String> tags = new ArrayList<>();
    private ArrayList<Categoria> categorie = new ArrayList<>();


    public Controller() throws SQLException, IOException {
        startConnection();
        riferimentoDAO = new RiferimentoDAOPostgre(connection);
        finestraTabella = new FrameTabellaRiferimenti("Riferimenti", this);
        finestraTabella.setVisible(true);
    }

    public static void main(String[] args) throws SQLException, IOException {
        Controller c = new Controller();
    }

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
            tagDAO = new TagDAOPostgre(connection);
        }
        catch (SQLException e) { //catch (SQLException | ConnectionException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public ArrayList<Riferimento> ottieniRiferimenti() {
        riferimenti = riferimentoDAO.ottieniRiferimenti();
        return riferimenti;
    }

    public ArrayList<Categoria> ottieniCategorie() throws SQLException {
        categorie = categoriaDAO.ottieniCategorie();
        return categorie;
    }

    public void eliminaRiferimento(String id) throws SQLException {
        riferimentoDAO.eliminaRiferimento(id);
    }

    public void eliminaCategoria(String id) throws SQLException {
        categoriaDAO.eliminaCategoria(id);
    }

    public void aggiungiAlCatalogo(String riferimento_id, String categoria_id) throws SQLException {
        catalogoDAO.aggiungiAlCatalogo(riferimento_id, categoria_id);
    }

    public void rimuoviDalCatalogo(String id) throws SQLException {
        catalogoDAO.rimuoviDalCatalogo(id);
    }

    public void creaCategoria(Categoria categoria) throws SQLException {
        categoriaDAO.creaCategoria(categoria);
    }

    public void creaRiferimento(Riferimento riferimento) throws SQLException {
        if (riferimento instanceof Libro) {
            Libro l = (Libro) riferimento;
            l.setCodice(libroDAO.inserisciLibro(l));
        }
    }

    public void modificaRiferimento(Riferimento riferimento) throws SQLException {
        if (riferimento instanceof  Libro) {
            Libro l = (Libro) riferimento;
            libroDAO.modificaLibro(l);
        }
    }

    public void mostraInformazioniRiferimento(Riferimento riferimento) {
        FrameGestioneRiferimento finestraRiferimento = new FrameGestioneRiferimento("Modifica", this);
        finestraRiferimento.setVisible(true);
        finestraRiferimento.mostraRiferimento(riferimento, finestraTabella);
    }

    public void mostraCreazioneRiferimento() {
        FrameGestioneRiferimento finestraRiferimento = new FrameGestioneRiferimento("Crea", this);
        finestraRiferimento.setVisible(true);
        finestraRiferimento.creaNuovoRiferimento(finestraTabella);
    }

    public ArrayList<String> ottieniTags() throws SQLException {
        tags = tagDAO.ottieniTags();
        return tags;
    }

    public ArrayList<Riferimento> getRiferimenti() {
        return riferimenti;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public ArrayList<Categoria> getCategorie() {
        return categorie;
    }
}
