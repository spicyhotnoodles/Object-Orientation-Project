package DAOImplementations;

import DAO.RiferimentoDAO;
import DBEntities.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Stack;

public class RiferimentoDAOPostgre implements RiferimentoDAO {

    private Connection connection;
    //Qui vanno definiti gli Statement per le istruzioni SQL che i vari metodi devono eseguire
    private Statement ottieniLibri;
    private Statement ottieniRiviste;
    private Statement ottieniConvegni;
    private Statement ottieniFilm;
    private Statement ottieniGiornali;
    private Statement ottieniPodcast;
    private Statement ottieniInterviste;
    private Statement ottieniTesi;
    private Statement ottieniLeggi;
    private Statement ottieniWeb;
    private String[] ottieniRiferimenti;

    public RiferimentoDAOPostgre(Connection connection) throws SQLException {
        this.connection = connection;
        ottieniRiferimenti = new String[10];
        ottieniRiferimenti[0] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, tipo, isbn, pagine, serie, volume" +
                "from riferimento as r inner join libro as l on r.riferimento_id = l.riferimento_id"; //ottieni libri
        ottieniRiferimenti[1] =  "select rif.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, cast(tipo as varchar), issn, pagine, fascicolo" +
                "from riferimento as rif inner join rivista as riv on rif.riferimento_id = riv.riferimento_id"; //ottieni riviste
        ottieniRiferimenti[2] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, cast(tipo as varchar), doi, luogo" +
                "from riferimento as r inner join convegno as c on r.riferimento_id = c.riferimento_id"; //ottieni convegni
        ottieniRiferimenti[3] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, cast(tipo as varchar), isan, genere, distribuzione" +
                "from riferimento as r inner join film as f on r.riferimento_id = f.riferimento_id"; //ottieni film
        ottieniRiferimenti[4] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, cast(tipo as varchar), issn, testata, sezione" +
                "from riferimento as r inner join giornale as g on r.riferimento_id = g.riferimento_id"; //ottieni giornali
        ottieniRiferimenti[5] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, cast(tipo as varchar), doi, episodio, serie" +
                "from riferimento as r inner join podcast as p on r.riferimento_id = p.riferimento_id"; //ottieni podcast
        ottieniRiferimenti[6] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, cast(tipo as varchar), doi, mezzo, ospiti" +
                "from riferimento as r inner join intervista as i on r.riferimento_id = i.riferimento_id"; //ottieni interviste
        ottieniRiferimenti[7] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, cast(tipo as varchar), doi, tipo, ateneo" +
                "from riferimento as r inner join tesi as t on r.riferimento_id = t.riferimento_id"; //ottieni tesi
        ottieniRiferimenti[8] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, cast(tipo as varchar), numero, tipo_legge, codice" +
                "from riferimento as r inner join leggi as l on r.riferimento_id = l.riferimento_id"; //ottieni leggi
        ottieniRiferimenti[9] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, cast(tipo as varchar), url, sito, tipo_sito" +
                "from riferimento as r inner join web as w on r.riferimento_id = w.riferimento_id"; //ottieni web
    }

    @Override
    public ArrayList<Riferimento> ottieniRiferimenti() {
        ArrayList<Riferimento> riferimenti = new ArrayList<Riferimento>();
        ArrayList<String> autori = new ArrayList<String>();
        ArrayList<Riferimento> rimandi = new ArrayList<Riferimento>();
        ArrayList<String> tags = new ArrayList<String>();
        try {
            ResultSet libri;
            ResultSet riviste;
            ResultSet convegni;
            ResultSet film;
            ResultSet giornali;
            ResultSet podcast;
            ResultSet interviste;
            ResultSet tesi;
            ResultSet leggi;
            ResultSet web;
            Statement st = connection.createStatement();
            try {
                libri = st.executeQuery(ottieniRiferimenti[0]);
                while (libri.next()) {
                    autori = estraiElenco(libri.getString("autori"));
                    rimandi = ottieniRimandiRiferimento(libri.getString("id_riferimento"));
                    tags = ottieniTagsRiferimento(libri.getString("id_riferimento"));
                    Libro l = Libro.builder()
                            .codice(libri.getString("id_riferimento"))
                            .titolo(libri.getString("titolo"))
                            .autori(autori)
                            .data(libri.getString("data_pub"))
                            .descrizione(libri.getString("descrizione"))
                            .lingua(libri.getString("lingua"))
                            .note(libri.getString("note"))
                            .tipo(libri.getString("tipo"))
                            .rimandi(rimandi)
                            .tags(tags)
                            .isbn(libri.getString("isbn"))
                            .pagine(libri.getString("pagine"))
                            .serie(libri.getString("serie"))
                            .volume(libri.getString("volume"))
                            .build();
                    riferimenti.add(l);
                }
            } catch (SQLException e) {
                System.out.println("Selezione fallita! Selezione di un oggetto riferimento di tipo libro (inner join) fallita:\n" + e);
            }
            autori.clear();
            try {
                riviste = st.executeQuery(ottieniRiferimenti[1]);
                while (riviste.next()) {
                    autori = estraiElenco(riviste.getString("autori"));
                    Rivista r = new Rivista();
                    riferimenti.add(r);
                }
            } catch (SQLException e) {
                System.out.println("Selezione fallita! Selezione di un oggetto riferimento di tipo rivista (inner join) fallita:\n" + e);
            }
            autori.clear();
            try {
                convegni = st.executeQuery(ottieniRiferimenti[2]);
                while (convegni.next()) {
                    autori = estraiElenco(convegni.getString("autori"));
                    Convegno c = Convegno.builder()
                            .codice(convegni.getString("id_riferimento"))
                            .titolo(convegni.getString("titolo"))
                            .autori(autori)
                            .data(convegni.getString("data_pub"))
                            .descrizione(convegni.getString("descrizione"))
                            .lingua(convegni.getString("lingua"))
                            .note(convegni.getString("note"))
                            .tipo(convegni.getString("tipo"))
                            .rimandi(rimandi)
                            .tags(tags)
                            .doi(convegni.getString("doi"))
                            .luogo(convegni.getString("luogo"))
                            .build();
                    riferimenti.add(c);
                }
            } catch (SQLException e) {
                System.out.println("Selezione fallita! Selezione di un oggetto riferimento di tipo convegno (inner join) fallita:\n" + e);
            }
            autori.clear();
            try {
                film = st.executeQuery(ottieniRiferimenti[3]);
                while (film.next()) {
                    autori = estraiElenco(film.getString("autori"));
                    Film f = new Film();
                    riferimenti.add(f);
                }
                giornali = st.executeQuery(ottieniRiferimenti[4]);
            } catch (SQLException e) {
                System.out.println("Selezione fallita! Selezione di un oggetto riferimento di tipo film (inner join) fallita:\n" + e);
            }
            autori.clear();
            try {
                while (giornali.next()) {
                    autori = estraiElenco(giornali.getString("autori"));
                    Giornale g = new Giornale();
                    riferimenti.add(g);
                }
                podcast = st.executeQuery(ottieniRiferimenti[5]);
            } catch (SQLException e) {
                System.out.println("Selezione fallita! Selezione di un oggetto riferimento di tipo giornale (inner join) fallita:\n" + e);
            }
            autori.clear();
            while (podcast.next()) {
                autori = estraiElenco(podcast.getString("autori"));
                Podcast p = new Podcast();
                riferimenti.add(p);
            }
            interviste = st.executeQuery(ottieniRiferimenti[6]);
            autori.clear();
            while (interviste.next()) {
                autori = estraiElenco(interviste.getString("autori"));
                Intervista i = new Intervista();
                riferimenti.add(i);
            }
            autori.clear();
            try {
                tesi = st.executeQuery(ottieniRiferimenti[7]);
                while (tesi.next()) {
                    autori = estraiElenco(tesi.getString("autori"));
                    Tesi t = new Tesi();
                    riferimenti.add(t);
                }
            } catch (SQLException e) {
                System.out.println("Selezione fallita! Selezione di un oggetto riferimento di tipo tesi fallita:\n" + e);
            }
            autori.clear();
            try {
                leggi = st.executeQuery(ottieniRiferimenti[8]);
                while (leggi.next()) {
                    autori = estraiElenco(leggi.getString("autori"));
                    Legge l = new Legge();
                    riferimenti.add(l);
                }
            } catch (SQLException e) {
                System.out.println("Selezione fallita! Selezione di un oggetto riferimento di tipo legge fallita:\n" + e);
            }
            autori.clear();
            web = st.executeQuery(ottieniRiferimenti[9]);
            while (web.next()) {
                autori = estraiElenco(web.getString("autori"));
                rimandi = ottieniRimandiRiferimento(web.getString("id_riferimento"));
                tags = ottieniTagsRiferimento(web.getString("id_riferimento"));
                Web w = Web.builder()
                        .codice(web.getString("id_riferimento"))
                        .titolo(web.getString("titolo"))
                        .autori(autori)
                        .data(web.getString("data_pub"))
                        .descrizione(web.getString("descrizione"))
                        .lingua(web.getString("lingua"))
                        .note(web.getString("note"))
                        .tipo(web.getString("tipo"))
                        .rimandi(rimandi)
                        .tags(tags)
                        .url(web.getString("url"))
                        .sito(web.getString("sito"))
                        .tipoSito(web.getString("tipo_sito"))
                        .build();
                riferimenti.add(w);
            }
        } catch (SQLException e) {
            System.out.println("Una o più selezioni fallite! Selezione di un oggetto di tipo riferimento (inner join) fallita:\n" + e);
        }
        return riferimenti;
    }

    public ArrayList<String> ottieniTagsRiferimento(String riferimento_id) {
        ArrayList<String> tags = new ArrayList<String>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select parola from tag where riferimento_id = " + riferimento_id + ");");
            while (rs.next()) {
                tags.add(rs.getString("parola"));
            }
        } catch (SQLException e) {
            System.out.println("Selezione fallita! Selezione di un oggetto di tipo tag fallita:\n" + e);
        }
        return tags;
    }

    public ArrayList<Riferimento> ottieniRimandiRiferimento(String riferimento_id) {
        ArrayList<Riferimento> rimandi = new ArrayList<Riferimento>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select r.riferimento_id, r.titolo from riferimento as r" +
                    " where r.riferimento_id = " +
                    "(select c.menzionato_id from citazione as c where c.riferimento_id = " + riferimento_id + ");");
            while (rs.next()) {
                Riferimento r = Riferimento.builder()
                        .codice(rs.getString("riferimento_id"))
                        .titolo(rs.getString("titolo"))
                        .build();
                rimandi.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Selezione fallita! Selezione di un oggetto di tipo riferimento (citazione) fallita:\n" + e);
        }
        /*
        Seleziona tutti i rimandi che matchano il riferimento_id passato come parametro
        Chiama la funzione estraiElenco per ottenere una lista di stringhe
        Ritorna la lista
        LA STESSA COSA VA FATTA PER I TAG
         */
        return rimandi;
    }

    public ArrayList<String> estraiElenco(String str) {
        Stack<Integer> dels = new Stack<Integer>();
        ArrayList<String> autori = new ArrayList<String>();
        for(int i = 0; i < str.length(); i++)
        {
            if (str.charAt(i) == '[')
                dels.add(i);
            else if (str.charAt(i) == ']' &&
                    !dels.isEmpty())
            {
                int pos = dels.peek();
                dels.pop();
                int len = i - 1 - pos;
                String ans = str.substring(
                        pos + 1, pos + 1 + len);
                autori.add(ans);
            }
        }
        return autori;
    }

}
