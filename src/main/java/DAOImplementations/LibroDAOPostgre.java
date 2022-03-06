package DAOImplementations;

import DAO.LibroDAO;
import DBEntities.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LibroDAOPostgre implements LibroDAO {

    private ArrayList<Libro> libri = new ArrayList<Libro>();
    private Connection connection;
    private Statement ottieniLibri;
    private Statement inserisciLibro;
    private PreparedStatement inserisciRiferimento;
    private PreparedStatement modificaLibro;

    public LibroDAOPostgre(Connection connection) throws SQLException {
        this.connection = connection;
        ottieniLibri = connection.createStatement();
        //inserisciRiferimento = connection.prepareStatement(
                //"insert into riferimento values (default, ?, ?, ?, ?, ?, ?, ?)");
        //inserisciLibro = connection.prepareStatement(
                //"insert into libro values (?, ?, default, ?, ?)");
        /*inserisciLibro = connection.prepareStatement("begin;\n" +
                "do\n" +
                "$$\n" +
                "declare\n" +
                "var int;\n" +
                "begin\n" +
                "insert into riferimento values (default, ?, ?, ?, ?, ?, ?, ?);\n" +
                "select currval('riferimento_id_riferimento_seq') into var;\n" +
                "insert into libro values (?, ?, var, ?, ?);\n" +
                "end;\n" +
                "$$;");*/
        modificaLibro = connection.prepareStatement("");
    }


    @Override
    public List<Libro> ottieniLibri() throws SQLException {
        libri.clear();
        ResultSet rs = ottieniLibri.executeQuery("" +
                "select r.riferimento_id as id, r.titolo, r.autori, r.data_pub, r.descrizione, r.lingua, r.note," +
                " r.tipo, l.isbn, l.num_pagine as pagine, l.serie, l.volume \n" +
                "from riferimento as r inner join libro as l on r.riferimento_id = l.riferimento_id");
        while(rs.next()) {
            String s = rs.getString("autore");
            Libro libro = Libro.builder()
                    .titolo(rs.getString("titolo"))
                    .autori(estraiAutori(s))
                    .descrizione(rs.getString("descrizione"))
                    .data(rs.getString("data"))
                    .lingua(rs.getString("lingua"))
                    .tipo(rs.getString("tipo"))
                    .note(rs.getString("note"))
                    .isbn(rs.getString("isbn"))
                    .pagine(rs.getString("pagine"))
                    .serie(rs.getString("serie"))
                    .volume(rs.getString("volume"))
                    .build();
            libri.add(libro);
        }
        return libri;
    }

    public ArrayList<String> estraiAutori(String str) {
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

    @Override
    public void inserisciLibro(Libro libro) throws SQLException {
        String s = "";
        s = componiAutori(libro.getAutori());
        /*inserisciLibro.setString(1, libro.getTitolo());
        inserisciLibro.setString(2, s);
        inserisciLibro.setString(3, libro.getData());
        inserisciLibro.setString(4, libro.getDescrizione());
        inserisciLibro.setString(5, libro.getDescrizione());
        inserisciLibro.setString(6, libro.getNote());
        inserisciLibro.setString(7, libro.getTipo());
        inserisciLibro.setString(8, libro.getIsbn());
        inserisciLibro.setString(9, libro.getPagine());
        inserisciLibro.setString(10, libro.getSerie());
        inserisciLibro.setString(11, libro.getVolume());*/
        inserisciLibro.executeQuery("begin;" +
                "do" +
                "$$" +
                "declare" +
                "var int;" +
                "begin" +
                "insert into riferimento values (default, " + libro.getTitolo() +
                ", " + s + ", " + libro.getData() + ", " + libro.getDescrizione() + ", "
                + libro.getLingua() + ", " + libro.getNote() + ", " + ", " + libro.getTipo()
                + ");\n" + "select currval('riferimento_id_riferimento_seq') into var;\n" +
                "insert into libro values (" + libro.getIsbn() + ", " + libro.getPagine() +
                ", var " + libro.getSerie() + ", " + libro.getVolume() + ");\n" + "end;\n" + "$$;\n" + "commit;");
    }

    public String componiAutori(List<String> autori) {
        String s = "";
        for (String a : autori) {
            s = s + "[" + a + "]" + " ";
        }
        return s;
    }

    @Override
    public int modificaLibro(String id) {
        return 0;
    }



}
