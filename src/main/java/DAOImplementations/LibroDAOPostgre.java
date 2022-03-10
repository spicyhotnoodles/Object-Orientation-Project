package DAOImplementations;

import DAO.LibroDAO;
import DBEntities.Categoria;
import DBEntities.Libro;
import GUI.FinestraPrincipale;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LibroDAOPostgre implements LibroDAO {

    private Connection connection;
    private String[] inserisciLibro;
    private String[] modificaLibro;
    private String[] eliminaLibro;

    public LibroDAOPostgre(Connection connection) throws SQLException {
        this.connection = connection;
        inserisciLibro = new String[6];
        modificaLibro = new String[2];
        eliminaLibro = new String[2];
        inserisciLibro[0] = "insert into riferimento values (default, ?, ?, ?, ?, ?, ?, cast(? as tipologia));";
        inserisciLibro[1] = "select currval('riferimento_id_riferimento_seq');";
        inserisciLibro[2] = "insert into libro values (?, ?, ?, ?, ?)";
        inserisciLibro[3] = "insert into catalogo values (default, ?, ?);";
        inserisciLibro[4] = "insert into citazione values (default, ?. ?);";
        inserisciLibro[5] = "insert into tags values (default, ?, ?);";
        modificaLibro[0] = "update riferimento set titolo=?, autori=?, data_pub=?, descrizione=?, lingua=?, note=? where riferimento_id = cast(? as int);";
        modificaLibro[1] = "update libro set isbn=?, num_pagine=?, serie=?, volume=? where riferimento_id = cast(? as int);";
        eliminaLibro[0] = "delete from riferimento where riferimento_id = ?";
        eliminaLibro[1] = "delete from libro where riferimento_id = ?";
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
            ps.setString(6, libro.getNote());
            ps.setString(7, libro.getTipo());
            ps.executeUpdate();
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(inserisciLibro[1]);
                if (rs.next()) {
                    codice = rs.getString("currval");
                    ps = connection.prepareStatement(inserisciLibro[2]);
                    ps.setString(1, libro.getIsbn());
                    ps.setString(2, libro.getPagine());
                    ps.setInt(3, Integer.parseInt(rs.getString("currval")));
                    ps.setString(4, libro.getSerie());
                    ps.setString(5, libro.getVolume());
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
        if (!libro.getCategorie().isEmpty())
            try {
                PreparedStatement ps = connection.prepareStatement(inserisciLibro[3]);
                for (Categoria c : libro.getCategorie()) {
                    ps.setInt(1, Integer.parseInt(codice));
                    ps.setInt(2, Integer.parseInt(c.getCodice()));
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                System.out.println("Inserimento fallito! Inserimento di un oggetto catalogo (libro) fallito:\n" + e);
            }
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
    public int modificaLibro(Libro libro) throws SQLException {
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
            ps.setString(6, libro.getNote());
            ps.setString(7, libro.getCodice());
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
        //Se il nuovo libro contiene una (nuova) lista di categorie
        if (!libro.getCategorie().isEmpty()) {
            Statement st = connection.createStatement();
            try {
                ResultSet rs = st.executeQuery("select * from catalogo where riferimento_id = " + libro.getCodice());
                PreparedStatement ps;
                if (rs.next()) {
                    try {
                        ps = connection.prepareStatement("update catalogo set categoria_id = cast(? as int) where riferimento_id = cast(? as int)");
                        for (Categoria c : libro.getCategorie()) {
                            ps.setString(1, c.getCodice());
                            ps.setString(2, libro.getCodice());
                            ps.executeUpdate();
                        }
                    } catch(SQLException e) {
                        System.out.println("Modifica fallita! Aggiornamento di un oggetto catalogo (libro) fallito:\n" + e);
                    }
                } else {
                    try {
                        ps = connection.prepareStatement("insert into catalogo values (cast(? as int), cast(? as int));");
                        for (Categoria c : libro.getCategorie()) {
                            ps.setString(1, c.getCodice());
                            ps.setString(2, libro.getCodice());
                            ps.executeUpdate();
                        }
                    } catch (SQLException e) {
                        System.out.println("Modifica fallita! Inserimento di un oggetto catalogo (libro) fallito:\n" + e);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Modifica fallita! Selezione di un oggetto catalogo (libro) fallito:\n" + e);
            }
        }
        //Se non ci sono nuove categorie
        else {
            Statement st = connection.createStatement();
            try {
                ResultSet rs = st.executeQuery("select * from catalogo where riferimento_id = " + libro.getCodice());
                if (rs.next()) {
                    PreparedStatement ps;
                    try {
                        ps = connection.prepareStatement("delete from catalogo where riferimento_id = cast(? as int)");
                        ps.setString(1, libro.getCodice());
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println("Modifica fallita! Eliminazione di un oggetto catalogo (libro) fallito:\n" + e);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Modifica fallita! Selezione di un oggetto catalogo (libro) fallito:\n" + e);
            }
        }
        return 0;
    }

    @Override
    public void eliminaLibro(String id) throws SQLException {
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(eliminaLibro[0]);
            ps.setString(1, id);
            ps.executeUpdate();
            try {
                ps = connection.prepareStatement(eliminaLibro[1]);
                ps.setString(1, id);
                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Transazione interrotta! Seconda operazione di eliminazione fallita:\n" + e);
            }
        } catch (SQLException e) {
            System.out.println("Transazione interrotta! Prima operazione di eliminazione fallita:\n" + e);
        }
    }

}
