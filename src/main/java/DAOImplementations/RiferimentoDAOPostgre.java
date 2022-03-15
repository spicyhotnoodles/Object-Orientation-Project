package DAOImplementations;

import DAO.RiferimentoDAO;
import DBEntities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Stack;

public class RiferimentoDAOPostgre implements RiferimentoDAO {

    private Connection connection;
    private String[] ottieniRiferimenti;
    private PreparedStatement eliminaRiferimento;

    public RiferimentoDAOPostgre(Connection connection) throws SQLException {
        this.connection = connection;
        ottieniRiferimenti = new String[10];
        ottieniRiferimenti[0] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, tipo, isbn, pagine, serie, volume from riferimento as r inner join libro as l on r.riferimento_id = l.riferimento_id"; //ottieni libri
        ottieniRiferimenti[1] =  "select rif.riferimento_id, titolo, autori, data_pub, descrizione, lingua, cast(tipo as varchar), issn, pagine, fascicolo from riferimento as rif inner join rivista as riv on rif.riferimento_id = riv.riferimento_id"; //ottieni riviste
        ottieniRiferimenti[2] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, cast(tipo as varchar), doi, luogo from riferimento as r inner join convegno as c on r.riferimento_id = c.riferimento_id"; //ottieni convegni
        ottieniRiferimenti[3] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, cast(tipo as varchar), isan, genere, distribuzione from riferimento as r inner join film as f on r.riferimento_id = f.riferimento_id"; //ottieni film
        ottieniRiferimenti[4] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, cast(tipo as varchar), issn, testata, sezione from riferimento as r inner join giornale as g on r.riferimento_id = g.riferimento_id"; //ottieni giornali
        ottieniRiferimenti[5] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, cast(tipo as varchar), doi, episodio, serie from riferimento as r inner join podcast as p on r.riferimento_id = p.riferimento_id"; //ottieni podcast
        ottieniRiferimenti[6] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, cast(tipo as varchar), doi, mezzo, ospiti from riferimento as r inner join intervista as i on r.riferimento_id = i.riferimento_id"; //ottieni interviste
        ottieniRiferimenti[7] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, cast(tipo as varchar), doi, tipo, ateneo from riferimento as r inner join tesi as t on r.riferimento_id = t.riferimento_id"; //ottieni tesi
        ottieniRiferimenti[8] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, cast(tipo as varchar), numero, tipo_legge, codice from riferimento as r inner join legge as l on r.riferimento_id = l.riferimento_id"; //ottieni leggi
        ottieniRiferimenti[9] = "select r.riferimento_id, titolo, autori, data_pub, descrizione, lingua, cast(tipo as varchar), url, sito, tipo_sito from riferimento as r inner join web as w on r.riferimento_id = w.riferimento_id"; //ottieni web
    }

    @Override
    public ArrayList<Riferimento> ottieniRiferimenti() {
        ArrayList<Riferimento> riferimenti = new ArrayList<Riferimento>();
        ArrayList<String> autori = new ArrayList<String>();
        ArrayList<Riferimento> citazioni = new ArrayList<Riferimento>();
        ArrayList<String> tags = new ArrayList<String>();
        ArrayList<Categoria> categorie = new ArrayList<>();
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
            PreparedStatement calcolaRimandi = connection.prepareStatement("select count(menzionato_id) from citazione where menzionato_id = cast(? as int) group by menzionato_id");
            ResultSet rimandi;
            int numeroRimandi = 0;

            Statement st = connection.createStatement();
            try {
                libri = st.executeQuery(ottieniRiferimenti[0]);
                while (libri.next()) {
                    calcolaRimandi.setString(1, libri.getString("riferimento_id"));
                    rimandi = calcolaRimandi.executeQuery();
                    if (rimandi.next())
                        numeroRimandi = rimandi.getInt("count");
                    else
                        numeroRimandi = 0;
                    autori = estraiLista(libri.getString("autori"));
                    citazioni = ottieniCitazioniRiferimento(libri.getString("riferimento_id"));
                    tags = ottieniTagsRiferimento(libri.getString("riferimento_id"));
                    categorie = ottieniCategorieRiferimento(libri.getString("riferimento_id"));
                    Libro l = Libro.builder()
                            .codice(libri.getString("riferimento_id"))
                            .titolo(libri.getString("titolo"))
                            .autori(autori)
                            .data(libri.getString("data_pub"))
                            .descrizione(libri.getString("descrizione"))
                            .lingua(libri.getString("lingua"))
                            .tipo("Libro")
                            .citazioni(citazioni)
                            .tags(tags)
                            .categorie(categorie)
                            .isbn(libri.getString("isbn"))
                            .pagine(libri.getString("pagine"))
                            .serie(libri.getString("serie"))
                            .rimandi(numeroRimandi)
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
                    citazioni = ottieniCitazioniRiferimento(riviste.getString("id_riferimento"));
                    tags = ottieniTagsRiferimento(riviste.getString("id_riferimento"));
                    Rivista r = Rivista.builder()
                            .codice(riviste.getString("id_riferimento"))
                            .titolo(riviste.getString("titolo"))
                            .autori(autori)
                            .data(riviste.getString("data_pub"))
                            .descrizione(riviste.getString("descrizione"))
                            .lingua(riviste.getString("lingua"))
                            .tipo(riviste.getString("tipo"))
                            .citazioni(citazioni)
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
                    citazioni = ottieniCitazioniRiferimento(convegni.getString("id_riferimento"));
                    tags = ottieniTagsRiferimento(convegni.getString("id_riferimento"));
                    Convegno c = Convegno.builder()
                            .codice(convegni.getString("id_riferimento"))
                            .titolo(convegni.getString("titolo"))
                            .autori(autori)
                            .data(convegni.getString("data_pub"))
                            .descrizione(convegni.getString("descrizione"))
                            .lingua(convegni.getString("lingua"))
                            .tipo(convegni.getString("tipo"))
                            .citazioni(citazioni)
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
                    citazioni = ottieniCitazioniRiferimento(film.getString("id_riferimento"));
                    tags = ottieniTagsRiferimento(film.getString("id_riferimento"));
                    Film f = Film.builder()
                            .codice(film.getString("id_riferimento"))
                            .titolo(film.getString("titolo"))
                            .autori(autori)
                            .data(film.getString("data_pub"))
                            .descrizione(film.getString("descrizione"))
                            .lingua(film.getString("lingua"))
                            .tipo(film.getString("tipo"))
                            .citazioni(citazioni)
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
                    citazioni = ottieniCitazioniRiferimento(giornali.getString("id_riferimento"));
                    tags = ottieniTagsRiferimento(giornali.getString("id_riferimento"));
                    Giornale g = Giornale.builder()
                            .codice(giornali.getString("id_riferimento"))
                            .titolo(giornali.getString("titolo"))
                            .autori(autori)
                            .data(giornali.getString("data_pub"))
                            .descrizione(giornali.getString("descrizione"))
                            .lingua(giornali.getString("lingua"))
                            .tipo(giornali.getString("tipo"))
                            .citazioni(citazioni)
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
                    citazioni = ottieniCitazioniRiferimento(podcast.getString("id_riferimento"));
                    tags = ottieniTagsRiferimento(podcast.getString("id_riferimento"));
                    Podcast p = Podcast.build()
                            .codice(podcast.getString("id_riferimento"))
                            .titolo(podcast.getString("titolo"))
                            .autori(autori)
                            .data(podcast.getString("data_pub"))
                            .descrizione(podcast.getString("descrizione"))
                            .lingua(podcast.getString("lingua"))
                            .tipo(podcast.getString("tipo"))
                            .citazioni(citazioni)
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
                    citazioni = ottieniCitazioniRiferimento(interviste.getString("id_riferimento"));
                    tags = ottieniTagsRiferimento(interviste.getString("id_riferimento"));
                    Intervista i = Intervista.builder()
                            .codice(interviste.getString("id_riferimento"))
                            .titolo(interviste.getString("titolo"))
                            .autori(autori)
                            .data(interviste.getString("data_pub"))
                            .descrizione(interviste.getString("descrizione"))
                            .lingua(interviste.getString("lingua"))
                            .tipo(interviste.getString("tipo"))
                            .citazioni(citazioni)
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
                    citazioni = ottieniCitazioniRiferimento(tesi.getString("id_riferimento"));
                    tags = ottieniTagsRiferimento(tesi.getString("id_riferimento"));
                    Tesi t = Tesi.builder()
                            .codice(tesi.getString("id_riferimento"))
                            .titolo(tesi.getString("titolo"))
                            .autori(autori)
                            .data(tesi.getString("data_pub"))
                            .descrizione(tesi.getString("descrizione"))
                            .lingua(tesi.getString("lingua"))
                            .tipo(tesi.getString("tipo"))
                            .citazioni(citazioni)
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
                    citazioni = ottieniCitazioniRiferimento(leggi.getString("id_riferimento"));
                    tags = ottieniTagsRiferimento(leggi.getString("id_riferimento"));
                    Legge l = Legge.build()
                            .codice(leggi.getString("id_riferimento"))
                            .titolo(leggi.getString("titolo"))
                            .autori(autori)
                            .data(leggi.getString("data_pub"))
                            .descrizione(leggi.getString("descrizione"))
                            .lingua(leggi.getString("lingua"))
                            .tipo(leggi.getString("tipo"))
                            .citazioni(citazioni)
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
                    citazioni = ottieniCitazioniRiferimento(web.getString("id_riferimento"));
                    tags = ottieniTagsRiferimento(web.getString("id_riferimento"));
                    Web w = Web.builder()
                            .codice(web.getString("id_riferimento"))
                            .titolo(web.getString("titolo"))
                            .autori(autori)
                            .data(web.getString("data_pub"))
                            .descrizione(web.getString("descrizione"))
                            .lingua(web.getString("lingua"))
                            .tipo(web.getString("tipo"))
                            .citazioni(citazioni)
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

    public ArrayList<Riferimento> ottieniCitazioniRiferimento(String riferimento_id) {
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
        return rimandi;
    }

    public ArrayList<Categoria> ottieniCategorieRiferimento(String riferimento_id) throws SQLException {
        ArrayList<Categoria> categorie = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("select * from categoria where categoria_id in (select categoria_id from catalogo where riferimento_id = cast(? as int))");
        ps.setString(1, riferimento_id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Categoria c = new Categoria.Builder()
                    .setCodice(rs.getString("categoria_id"))
                    .setNome(rs.getString("nome"))
                    .setSupercategoria(rs.getString("supercategoria_id"))
                    .build();
            categorie.add(c);
        }
        return categorie;
    }

    public void eliminaRiferimento(String id) throws SQLException {
        eliminaRiferimento = connection.prepareStatement("delete from riferimento where riferimento_id = cast(? as int)");
        eliminaRiferimento.setString(1, id);
        eliminaRiferimento.executeUpdate();
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
