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

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


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
    private LibroDAOPostgre libroPostgre;
    private ConvegnoDAOPostgre convegnoPostgre;
    private FilmDAOPostgre filmPostgre;
    private GiornaleDAOPostgre giornalePostgre;
    private IntervistaDAOPostgre intervistaPostgre;
    private LeggeDAOPostgre leggePostgre;
    private PodcastDAOPostgre podcastPostgre;
    private RivistaDAOPostgre rivistaPostgre;
    private TesiDAOPostgre tesiPostgre;
    private WebDAOPostgre webPostgre;
    private FrameTabellaRiferimenti finestraTabella;
    private FrameCreaCategoria finestraCategoria;
    private ArrayList<Riferimento> riferimenti = new ArrayList<Riferimento>();
    private ArrayList<String> tags = new ArrayList<>();
    private ArrayList<Categoria> categorie = new ArrayList<>();


    public Controller() throws SQLException, IOException {
        startConnection();
        categorie = categoriaDAO.ottieniCategorie();
        tags = tagDAO.ottieniTags();
        riferimenti = riferimentoDAO.ottieniRiferimenti();
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
            libroPostgre = new LibroDAOPostgre(connection);
            convegnoPostgre = new ConvegnoDAOPostgre(connection);
            filmPostgre = new FilmDAOPostgre(connection);
            giornalePostgre = new GiornaleDAOPostgre(connection);
            intervistaPostgre = new IntervistaDAOPostgre(connection);
            leggePostgre = new LeggeDAOPostgre(connection);
            podcastPostgre = new PodcastDAOPostgre(connection);
            rivistaPostgre = new RivistaDAOPostgre(connection);
            tesiPostgre = new TesiDAOPostgre(connection);
            webPostgre = new WebDAOPostgre(connection);
            tagDAO = new TagDAOPostgre(connection);
        }
        catch (SQLException e) { //catch (SQLException | ConnectionException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public void creaRiferimento(Riferimento riferimento) throws SQLException {
        if (riferimento instanceof Libro) {
            Libro l = (Libro) riferimento;
            l.setCodice(libroPostgre.inserisciLibro(l));
            riferimenti.add(l);
        }
        if (riferimento instanceof Convegno) {
            Convegno c = (Convegno) riferimento;
            c.setCodice(convegnoPostgre.inserisciConvegno(c));
        }
        if (riferimento instanceof Film) {
            Film f = (Film) riferimento;
            f.setCodice(filmPostgre.inserisciFilm(f));
        }
        if (riferimento instanceof Giornale) {
            Giornale g = (Giornale) riferimento;
            g.setCodice(giornalePostgre.inserisciGiornale(g));
        }
        if (riferimento instanceof Intervista) {
            Intervista i = (Intervista) riferimento;
            i.setCodice(intervistaPostgre.inserisciRivista(i));
        }
        if (riferimento instanceof Legge) {
            Legge l = (Legge) riferimento;
            l.setCodice(leggePostgre.inserisciLegge(l));
        }
        if (riferimento instanceof Podcast) {
            Podcast p = (Podcast) riferimento;
            p.setCodice(podcastPostgre.inserisciPodcast(p));
        }
        if (riferimento instanceof Rivista) {
            Rivista r = (Rivista) riferimento;
            r.setCodice(rivistaPostgre.inserisciRivista(r));
        }
        if (riferimento instanceof Tesi) {
            Tesi t = (Tesi) riferimento;
            t.setCodice(tesiPostgre.inserisciTesi(t));
        }
        if (riferimento instanceof Web) {
            Web w = (Web) riferimento;
            w.setCodice(webPostgre.inserisciWeb(w));
        }
    }

    public void modificaRiferimento(Riferimento riferimento) throws SQLException {
        if (riferimento instanceof  Libro) {
            Libro l = (Libro) riferimento;
            libroPostgre.modificaLibro(l);
        }
        if (riferimento instanceof Convegno) {
            Convegno c = (Convegno) riferimento;
            convegnoPostgre.modificaConvegno(c);
        }
        if (riferimento instanceof Film) {
            Film f = (Film) riferimento;
            filmPostgre.modificaFilm(f);
        }
        if (riferimento instanceof Giornale) {
            Giornale g = (Giornale) riferimento;
            giornalePostgre.modificaGiornale(g);
        }
        if (riferimento instanceof Intervista) {
            Intervista i = (Intervista) riferimento;
            intervistaPostgre.modificaIntervista(i);
        }
        if (riferimento instanceof Legge) {
            Legge l = (Legge) riferimento;
            leggePostgre.modificaLegge(l);
        }
        if (riferimento instanceof Podcast) {
            Podcast p = (Podcast) riferimento;
            podcastPostgre.modificaPodcast(p);
        }
        if (riferimento instanceof Rivista) {
            Rivista r = (Rivista) riferimento;
            rivistaPostgre.modificaRivista(r);
        }
        if (riferimento instanceof Tesi) {
            Tesi t = (Tesi) riferimento;
            tesiPostgre.modificaTesi(t);
        }
        if (riferimento instanceof Web) {
            Web w = (Web) riferimento;
            webPostgre.modificaWeb(w);
        }
    }

    public void eliminaRiferimento(String id) throws SQLException {
        riferimentoDAO.eliminaRiferimento(id);
        for (Riferimento r: riferimenti)
            if (r.getCodice().equals(id)) {
                riferimenti.remove(r);
                break;
            }
    }

    public void aggiungiAlCatalogo(String riferimento_id, String categoria_id) throws SQLException {
        catalogoDAO.aggiungiAlCatalogo(riferimento_id, categoria_id);
    }

    public void rimuoviDalCatalogo(String riferimento_id, String categoria_id) throws SQLException {
        catalogoDAO.rimuoviDalCatalogo(riferimento_id, categoria_id);
    }

    public void aggiungiCitazione(String riferimento_id, String menzionato) throws SQLException {
        for (Riferimento riferimento: riferimenti)
            if (riferimento.getTitolo().equals(menzionato))
                citazioneDAO.associaRiferimento(riferimento_id, riferimento.getCodice());
    }

    public void rimuoviCitazione(String riferimento_id, String menzionato) throws SQLException {
        for (Riferimento riferimento: riferimenti)
            if (riferimento.getTitolo().equals(menzionato))
                citazioneDAO.disassociaRiferimento(riferimento_id, riferimento.getCodice());
    }

    public void aggiungiTag(String riferimento_id, String tag) throws SQLException {
        tagDAO.legaRiferimento(riferimento_id, tag);
    }

    public void rimuoviTag(String riferimento_id, String tag) throws SQLException {
        tagDAO.slegaRiferimento(riferimento_id, tag);
    }

    public void creaCategoria(Categoria categoria) throws SQLException {
        categoria.setCodice(categoriaDAO.creaCategoria(categoria));
        categorie.add(categoria);
    }

    public void eliminaCategoria(String id) throws SQLException {
        categoriaDAO.eliminaCategoria(id);
        for (Categoria cat: categorie)
            if (cat.getCodice().equals(id))
                categorie.remove(cat);
    }

    public void creaTag(String tag) throws SQLException {
        tagDAO.creaTag(tag);
        tags.add(tag);
    }

    public void eliminaTag(String tag) throws SQLException {
        tagDAO.eliminaTag(tag);
        tags.remove(tag);
    }

    public void mostraInformazioniRiferimento(Riferimento riferimento, DefaultTableModel model, int index) {
        FrameGestioneRiferimento finestraRiferimento = new FrameGestioneRiferimento("Modifica", this);
        finestraRiferimento.mostraRiferimento(riferimento, model, index);
        finestraRiferimento.setVisible(true); //Apparentemente se non viene eseguita dopo, il frame non viene visualizzato. Non ho idea del perché.
    }

    public void mostraCreazioneRiferimento(DefaultTableModel model) {
        FrameGestioneRiferimento finestraRiferimento = new FrameGestioneRiferimento("Crea", this);
        finestraRiferimento.setVisible(true);
        finestraRiferimento.creaNuovoRiferimento(model);
    }

    public void mostraCreazioneCategoria(DefaultListModel model) throws SQLException {
        Categoria categoria = new Categoria();
        finestraCategoria = new FrameCreaCategoria("Nuova Categoria", this);
        finestraCategoria.setVisible(true);
        finestraCategoria.creaCategoria(model, categoria);
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
