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
    private String[] ottieniRiferimenti;

    public RiferimentoDAOPostgre(Connection connection) throws SQLException {
        this.connection = connection;
        ottieniRiferimenti = new String[10];
        ottieniRiferimenti[0] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, tipo, isbn, pagine, serie, volume from riferimento as r inner join libro as l on r.riferimento_id = l.riferimento_id"; //ottieni libri
        ottieniRiferimenti[1] =  "select rif.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, cast(tipo as varchar), issn, pagine, fascicolo from riferimento as rif inner join rivista as riv on rif.riferimento_id = riv.riferimento_id"; //ottieni riviste
        ottieniRiferimenti[2] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, cast(tipo as varchar), doi, luogo from riferimento as r inner join convegno as c on r.riferimento_id = c.riferimento_id"; //ottieni convegni
        ottieniRiferimenti[3] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, cast(tipo as varchar), isan, genere, distribuzione from riferimento as r inner join film as f on r.riferimento_id = f.riferimento_id"; //ottieni film
        ottieniRiferimenti[4] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, cast(tipo as varchar), issn, testata, sezione from riferimento as r inner join giornale as g on r.riferimento_id = g.riferimento_id"; //ottieni giornali
        ottieniRiferimenti[5] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, cast(tipo as varchar), doi, episodio, serie from riferimento as r inner join podcast as p on r.riferimento_id = p.riferimento_id"; //ottieni podcast
        ottieniRiferimenti[6] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, cast(tipo as varchar), doi, mezzo, ospiti from riferimento as r inner join intervista as i on r.riferimento_id = i.riferimento_id"; //ottieni interviste
        ottieniRiferimenti[7] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, cast(tipo as varchar), doi, tipo, ateneo from riferimento as r inner join tesi as t on r.riferimento_id = t.riferimento_id"; //ottieni tesi
        ottieniRiferimenti[8] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, cast(tipo as varchar), numero, tipo_legge, codice from riferimento as r inner join legge as l on r.riferimento_id = l.riferimento_id"; //ottieni leggi
        ottieniRiferimenti[9] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, note, cast(tipo as varchar), url, sito, tipo_sito from riferimento as r inner join web as w on r.riferimento_id = w.riferimento_id"; //ottieni web
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
                    autori = estraiLista(libri.getString("autori"));
                    rimandi = ottieniRimandiRiferimento(libri.getString("riferimento_id"));
                    tags = ottieniTagsRiferimento(libri.getString("riferimento_id"));
                    Libro l = Libro.builder()
                            .codice(libri.getString("riferimento_id"))
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
            try {
                riviste = st.executeQuery(ottieniRiferimenti[1]);
                while (riviste.next()) {
                    autori = estraiLista(riviste.getString("autori"));
                    rimandi = ottieniRimandiRiferimento(riviste.getString("id_riferimento"));
                    tags = ottieniTagsRiferimento(riviste.getString("id_riferimento"));
                    Rivista r = Rivista.builder()
                            .codice(riviste.getString("id_riferimento"))
                            .titolo(riviste.getString("titolo"))
                            .autori(autori)
                            .data(riviste.getString("data_pub"))
                            .descrizione(riviste.getString("descrizione"))
                            .lingua(riviste.getString("lingua"))
                            .note(riviste.getString("note"))
                            .tipo(riviste.getString("tipo"))
                            .rimandi(rimandi)
                            .tags(tags)
                            .issn(riviste.getString("issn"))
                            .pagine(riviste.getString("pagine"))
                            .fascicolo(riviste.getString("fascicolo"))
                            .build();
                    riferimenti.add(r);
                }
            } catch (SQLException e) {
                System.out.println("Selezione fallita! Selezione di un oggetto riferimento di tipo rivista (inner join) fallita:\n" + e);
            }
            try {
                convegni = st.executeQuery(ottieniRiferimenti[2]);
                while (convegni.next()) {
                    autori = estraiLista(convegni.getString("autori"));
                    rimandi = ottieniRimandiRiferimento(convegni.getString("id_riferimento"));
                    tags = ottieniTagsRiferimento(convegni.getString("id_riferimento"));
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
            try {
                film = st.executeQuery(ottieniRiferimenti[3]);
                while (film.next()) {
                    autori = estraiLista(film.getString("autori"));
                    rimandi = ottieniRimandiRiferimento(film.getString("id_riferimento"));
                    tags = ottieniTagsRiferimento(film.getString("id_riferimento"));
                    Film f = Film.builder()
                            .codice(film.getString("id_riferimento"))
                            .titolo(film.getString("titolo"))
                            .autori(autori)
                            .data(film.getString("data_pub"))
                            .descrizione(film.getString("descrizione"))
                            .lingua(film.getString("lingua"))
                            .note(film.getString("note"))
                            .tipo(film.getString("tipo"))
                            .rimandi(rimandi)
                            .tags(tags)
                            .isan(film.getString("isan"))
                            .genere(film.getString("genere"))
                            .distribuzione(film.getString("distribuzione"))
                            .build();
                    riferimenti.add(f);
                }
            } catch (SQLException e) {
                System.out.println("Selezione fallita! Selezione di un oggetto riferimento di tipo film (inner join) fallita:\n" + e);
            }
            try {
                giornali = st.executeQuery(ottieniRiferimenti[4]);
                while (giornali.next()) {
                    autori = estraiLista(giornali.getString("autori"));
                    rimandi = ottieniRimandiRiferimento(giornali.getString("id_riferimento"));
                    tags = ottieniTagsRiferimento(giornali.getString("id_riferimento"));
                    Giornale g = Giornale.builder()
                            .codice(giornali.getString("id_riferimento"))
                            .titolo(giornali.getString("titolo"))
                            .autori(autori)
                            .data(giornali.getString("data_pub"))
                            .descrizione(giornali.getString("descrizione"))
                            .lingua(giornali.getString("lingua"))
                            .note(giornali.getString("note"))
                            .tipo(giornali.getString("tipo"))
                            .rimandi(rimandi)
                            .tags(tags)
                            .issn(giornali.getString("issn"))
                            .testata(giornali.getString("testata"))
                            .sezione(giornali.getString("sezione"))
                            .build();
                    riferimenti.add(g);
                }
            } catch (SQLException e) {
                System.out.println("Selezione fallita! Selezione di un oggetto riferimento di tipo giornale (inner join) fallita:\n" + e);
            }
            try {
                podcast = st.executeQuery(ottieniRiferimenti[5]);
                while (podcast.next()) {
                    autori = estraiLista(podcast.getString("autori"));
                    rimandi = ottieniRimandiRiferimento(podcast.getString("id_riferimento"));
                    tags = ottieniTagsRiferimento(podcast.getString("id_riferimento"));
                    Podcast p = Podcast.build()
                            .codice(podcast.getString("id_riferimento"))
                            .titolo(podcast.getString("titolo"))
                            .autori(autori)
                            .data(podcast.getString("data_pub"))
                            .descrizione(podcast.getString("descrizione"))
                            .lingua(podcast.getString("lingua"))
                            .note(podcast.getString("note"))
                            .tipo(podcast.getString("tipo"))
                            .rimandi(rimandi)
                            .tags(tags)
                            .doi(podcast.getString("doi"))
                            .episodio(podcast.getString("episodio"))
                            .serie(podcast.getString("serie"))
                            .build();
                    riferimenti.add(p);
                }
            } catch (SQLException e) {
                System.out.println("Selezione fallita! Selezione di un oggetto riferimento di tipo podcast fallita:\n" + e);
            }
            try {
                interviste = st.executeQuery(ottieniRiferimenti[6]);
                ArrayList<String> ospiti = new ArrayList<String>();
                while (interviste.next()) {
                    autori = estraiLista(interviste.getString("autori"));
                    ospiti = estraiLista(interviste.getString("ospiti"));
                    rimandi = ottieniRimandiRiferimento(interviste.getString("id_riferimento"));
                    tags = ottieniTagsRiferimento(interviste.getString("id_riferimento"));
                    Intervista i = Intervista.builder()
                            .codice(interviste.getString("id_riferimento"))
                            .titolo(interviste.getString("titolo"))
                            .autori(autori)
                            .data(interviste.getString("data_pub"))
                            .descrizione(interviste.getString("descrizione"))
                            .lingua(interviste.getString("lingua"))
                            .note(interviste.getString("note"))
                            .tipo(interviste.getString("tipo"))
                            .rimandi(rimandi)
                            .tags(tags)
                            .doi(interviste.getString("doi"))
                            .mezzo(interviste.getString("mezzo"))
                            .ospiti(ospiti)
                            .build();
                    riferimenti.add(i);
                }
            } catch (SQLException e) {
                System.out.println("Selezione fallita! Selezione di un oggetto riferimento di tipo intervista fallita:\n" + e);
            }
            try {
                tesi = st.executeQuery(ottieniRiferimenti[7]);
                while (tesi.next()) {
                    autori = estraiLista(tesi.getString("autori"));
                    rimandi = ottieniRimandiRiferimento(tesi.getString("id_riferimento"));
                    tags = ottieniTagsRiferimento(tesi.getString("id_riferimento"));
                    Tesi t = Tesi.builder()
                            .codice(tesi.getString("id_riferimento"))
                            .titolo(tesi.getString("titolo"))
                            .autori(autori)
                            .data(tesi.getString("data_pub"))
                            .descrizione(tesi.getString("descrizione"))
                            .lingua(tesi.getString("lingua"))
                            .note(tesi.getString("note"))
                            .tipo(tesi.getString("tipo"))
                            .rimandi(rimandi)
                            .tags(tags)
                            .doi(tesi.getString("doi"))
                            .tipo(tesi.getString("tipo_tesi"))
                            .ateneo(tesi.getString("ateneo"))
                            .build();
                    riferimenti.add(t);
                }
            } catch (SQLException e) {
                System.out.println("Selezione fallita! Selezione di un oggetto riferimento di tipo tesi fallita:\n" + e);
            }
            try {
                leggi = st.executeQuery(ottieniRiferimenti[8]);
                while (leggi.next()) {
                    autori = estraiLista(leggi.getString("autori"));
                    rimandi = ottieniRimandiRiferimento(leggi.getString("id_riferimento"));
                    tags = ottieniTagsRiferimento(leggi.getString("id_riferimento"));
                    Legge l = Legge.build()
                            .codice(leggi.getString("id_riferimento"))
                            .titolo(leggi.getString("titolo"))
                            .autori(autori)
                            .data(leggi.getString("data_pub"))
                            .descrizione(leggi.getString("descrizione"))
                            .lingua(leggi.getString("lingua"))
                            .note(leggi.getString("note"))
                            .tipo(leggi.getString("tipo"))
                            .rimandi(rimandi)
                            .tags(tags)
                            .numero(leggi.getString("numero"))
                            .tipo(leggi.getString("tipo_legge"))
                            .codice(leggi.getString("codice"))
                            .build();
                    riferimenti.add(l);
                }
            } catch (SQLException e) {
                System.out.println("Selezione fallita! Selezione di un oggetto riferimento di tipo legge fallita:\n" + e);
            }
            try {
                web = st.executeQuery(ottieniRiferimenti[9]);
                while (web.next()) {
                    autori = estraiLista(web.getString("autori"));
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
                System.out.println("Selezione fallita! Selezione di un oggetto riferimento di tipo web fallita:\n" + e);
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
            ResultSet rs = st.executeQuery("select parola from tag where riferimento_id = " + riferimento_id);
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

    public ArrayList<String> estraiLista(String str) {
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
