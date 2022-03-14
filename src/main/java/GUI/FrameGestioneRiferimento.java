package GUI;

import DBEntities.Riferimento;

import javax.swing.*;
import DBEntities.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class FrameGestioneRiferimento extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane cateogoriaTabbedPane;
    private JPanel infoPanel;
    private JPanel tagPanel;
    private JPanel rimandiPanel;
    private JLabel titoloLabel;
    private JTextField titoloTextField;
    private JLabel autoriLabel;
    private JTextField autoriTextField;
    private JLabel annoLabel;
    private JTextField annoTextField;
    private JLabel linguaLabel;
    private JTextField linguaTextField;
    private JLabel descrizioneLabel;
    private JTextField descrizioneTextField;
    private JComboBox tipoRiferimentoComboBox;
    private JLabel tipoRiferimentoLabel;
    private JTextField isbnTextField;
    private JLabel isbnLabel;
    private JLabel pagineLabel;
    private JTextField pagineTextField;
    private JLabel volumeLabel;
    private JTextField volumeTextField;
    private JLabel serieLabel;
    private JTextField serieTextField;
    private JButton salvaButton;
    private JPanel categoriaPanel;
    private JButton aggiungiCatRifButton;
    private JList categoriaRifList;
    private JLabel aggiungiCatRifLabel;
    private JButton aggiungiTagRifButton;
    private JList tagRifList;
    private JLabel aggiungiTagRifLabel;
    private JList rimandiRifList;
    private JButton aggiungiRimandiRifButton;
    private JLabel aggiungiRimandiRifLabel;
    private JComboBox aggiungiCategoriaComboBox;
    private JComboBox aggiungiTagComboBox;
    private JComboBox aggiungiRimandiRifComboBox;
    private JButton rimuoviCategoriaRifButton;
    private JButton rimuoviTagRifButton;
    private JButton rimuoviRimandiRifButton;
    private DefaultListModel tagDLM = new DefaultListModel<>();
    private DefaultListModel categoriaDLM = new DefaultListModel<>();
    private DefaultListModel rimandiDLM = new DefaultListModel<>();
    private Controller c;
    ArrayList<Categoria> categorie = new ArrayList<>();
    ArrayList<String> tags = new ArrayList<>();
    ArrayList<Riferimento> riferimenti = new ArrayList<>();

    public FrameGestioneRiferimento(String titolo, Controller c) {
        super(titolo);
        this.c = c;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setBounds(650, 250, 700, 500);
        riferimenti = c.getRiferimenti();
        categorie = c.getCategorie();
        tags = c.getTags();
        for (Riferimento rif: riferimenti)
            aggiungiRimandiRifComboBox.addItem(rif.getTitolo());
        for (Categoria cat: categorie)
            aggiungiCategoriaComboBox.addItem(cat.getNome());
        for (String tag: tags)
            aggiungiTagComboBox.addItem(tag.toString());
        tagRifList.setModel(tagDLM);
        categoriaRifList.setModel(categoriaDLM);
        rimandiRifList.setModel(rimandiDLM);
    }

    public void mostraRiferimento(Riferimento riferimento, FrameTabellaRiferimenti frame) {
        riempiListaCategorieRiferimento(riferimento);
        riempiListaRimandiRiferimento(riferimento);
        riempiListaTagRiferimento(riferimento);
        rimuoviCategoriaRifButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (Categoria cat: categorie) {
                    if (cat.getNome().equals(categoriaRifList.getSelectedValue().toString())) {
                        try {
                            c.rimuoviDalCatalogo(riferimento.getCodice(), cat.getCodice());
                            JOptionPane.showMessageDialog(mainPanel, "Il riferimento è stato rimosso dalla categoria selezionata", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                            categorie.remove(cat);
                            categoriaDLM.removeElement(categoriaRifList.getSelectedValue().toString());

                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        aggiungiCatRifButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (Categoria cat: categorie) {
                    if (cat.getNome().equals(aggiungiCategoriaComboBox.getSelectedItem().toString())) {
                        try {
                            c.aggiungiAlCatalogo(riferimento.getCodice(), cat.getCodice());
                            JOptionPane.showMessageDialog(mainPanel, "Il riferimento è stato assegnato alla categoria selezionata", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                            categoriaDLM.addElement(aggiungiCategoriaComboBox.getSelectedItem().toString());

                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        salvaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String s = autoriTextField.getText();
                ArrayList<String> nuoviAutori = new ArrayList<>(Arrays.asList(s.split(";")));
                //System.out.println(categoriaRifList.getSelectedValue().toString());

                if (tipoRiferimentoComboBox.getSelectedItem().equals("Libro")) {
                    Libro l = (Libro) riferimento;
                    l.setTitolo(titoloTextField.getText());
                    l.setAutori(nuoviAutori);
                    l.setDescrizione(descrizioneTextField.getText());
                    l.setData(annoTextField.getText());
                    l.setLingua(linguaTextField.getText());
                    l.setIsbn(isbnTextField.getText());
                    l.setPagine(pagineTextField.getText());
                    l.setSerie(serieTextField.getText());
                    l.setVolume(volumeTextField.getText());
                    /*try {
                        c.modificaRiferimento(l);
                        JOptionPane.showMessageDialog(mainPanel, "Libro modificato.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                        frame.riempiTabella();
                        dispose();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }*/
                }
            }
        });
        tipoRiferimentoComboBox.setEnabled(false);
        if (riferimento instanceof Libro) {
            //mostraCampiLibro();
            tipoRiferimentoComboBox.setSelectedIndex(0);
            Libro l = (Libro) riferimento;
            titoloTextField.setText(l.getTitolo());
            descrizioneTextField.setText(l.getDescrizione());
            annoTextField.setText(l.getData());
            String autori = "";
            for (String autore: l.getAutori())
                autori = autori + autore + "; ";
            autoriTextField.setText(autori);
            linguaTextField.setText(l.getLingua());
            isbnTextField.setText(l.getIsbn());
            pagineTextField.setText(l.getPagine());
            volumeTextField.setText(l.getVolume());
            serieTextField.setText(l.getSerie());
        }
        if (riferimento instanceof Convegno) {

        }
        if (riferimento instanceof Rivista) {
            tipoRiferimentoComboBox.setSelectedIndex(2);
            Rivista r = (Rivista) riferimento;
            titoloTextField.setText(r.getTitolo());
            descrizioneTextField.setText(r.getDescrizione());
            annoTextField.setText(r.getData());
            String autori = "";
            for (String autore: r.getAutori())
                autori = autori + autore + "; ";
            autoriTextField.setText(autori);
            linguaTextField.setText(r.getLingua());
        }
        if (riferimento instanceof Giornale) {

        }
        if (riferimento instanceof Legge) {

        }
        if (riferimento instanceof Tesi) {

        }
        if (riferimento instanceof Film) {

        }
        if (riferimento instanceof Podcast) {

        }
        if (riferimento instanceof Intervista) {

        }
        if (riferimento instanceof Web) {

        }

    }

    public void creaNuovoRiferimento(FrameTabellaRiferimenti frame) {
        salvaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Libro")) {
                    String s = autoriTextField.getText();
                    ArrayList<String> autori = new ArrayList<>(Arrays.asList(s.split(";")));
                    ArrayList<Categoria> categorie = new ArrayList<>();
                    ArrayList<String> tags = new ArrayList<>();

                    Libro l = new Libro.Builder()
                            .titolo(titoloTextField.getText())
                            .autori(autori)
                            .descrizione(descrizioneTextField.getText())
                            .lingua(linguaTextField.getText())
                            .data(annoTextField.getText())
                            .isbn(isbnTextField.getText())
                            .pagine(pagineTextField.getText())
                            .serie(serieTextField.getText())
                            .volume(volumeTextField.getText())
                            .tipo(tipoRiferimentoComboBox.getSelectedItem().toString())
                            .categorie(categorie)
                            .tags(tags)
                            .build();
                    try {
                        c.creaRiferimento(l);
                        JOptionPane.showMessageDialog(mainPanel, "Libro creato.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                        frame.riempiTabella();
                        dispose();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    public void riempiListaTagRiferimento(Riferimento riferimento) {
        for (String tag : riferimento.getTags())
            tagDLM.addElement(tag);
    }

    public void riempiListaCategorieRiferimento(Riferimento riferimento) {
        for (Categoria cat : riferimento.getCategorie())
            categoriaDLM.addElement(cat.getNome());
    }

    public void riempiListaRimandiRiferimento(Riferimento riferimento) {
        for (Riferimento rif: riferimento.getRimandi())
            rimandiDLM.addElement(rif.getTitolo());
    }
}
