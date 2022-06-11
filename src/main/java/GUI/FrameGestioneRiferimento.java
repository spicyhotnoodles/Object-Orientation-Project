package GUI;

import DBEntities.Riferimento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DBEntities.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FrameGestioneRiferimento extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane cateogoriaTabbedPane;
    private JPanel infoPanel;
    private JPanel tagPanel;
    private JPanel citazioniPanel;
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
    private JList citazioniRifList;
    private JButton aggiungiCitazioneRifButton;
    private JLabel aggiungiCitazioniRifLabel;
    private JComboBox aggiungiCategoriaComboBox;
    private JComboBox aggiungiTagComboBox;
    private JComboBox aggiungiCitazioniRifComboBox;
    private JButton rimuoviCategoriaRifButton;
    private JButton rimuoviTagRifButton;
    private JButton rimuoviCitazioneRifButton;
    private JLabel doiLabel;
    private JTextField doiTextField;
    private JLabel luogoLabel;
    private JTextField luogoTextField;
    private JTextField isanTextField;
    private JLabel isanLabel;
    private JTextField genereTextField;
    private JLabel genereLabel;
    private JLabel distribuzioneLabel;
    private JTextField distribuzioneTextField;
    private JLabel issnLabel;
    private JTextField issnTextField;
    private JLabel testataLabel;
    private JTextField testataTextField;
    private JTextField sezioneTextField;
    private JLabel sezioneLabel;
    private JLabel fascicoloLabel;
    private JTextField fascicoloTextField;
    private JLabel numeroLabel;
    private JTextField numeroTextField;
    private JLabel tipoLabel;
    private JTextField tipoTextField;
    private JTextField codiceTextField;
    private JLabel ateneoLabel;
    private JLabel codiceLabel;
    private JTextField ateneoTextField;
    private JLabel urlLabel;
    private JTextField urlTextField;
    private JLabel sitoLabel;
    private JTextField sitoTextField;
    private JLabel mezzoLabel;
    private JTextField mezzoTextField;
    private JLabel ospitiLabel;
    private JTextField ospitiTextField;
    private JLabel episodioLabel;
    private JTextField episodioTextField;
    private DefaultListModel tagDLM = new DefaultListModel<>();
    private DefaultListModel categoriaDLM = new DefaultListModel<>();
    private DefaultListModel rimandiDLM = new DefaultListModel<>();
    private Controller c;

    public FrameGestioneRiferimento(String titolo, Controller c) {
        super(titolo);
        this.c = c;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setBounds(650, 250, 700, 500);
        for (Riferimento rif: c.getRiferimenti())
            aggiungiCitazioniRifComboBox.addItem(rif.getTitolo());
        for (Categoria cat: c.getCategorie())
            aggiungiCategoriaComboBox.addItem(cat.getNome());
        for (String tag: c.getTags())
            aggiungiTagComboBox.addItem(tag);
        tagRifList.setModel(tagDLM);
        categoriaRifList.setModel(categoriaDLM);
        citazioniRifList.setModel(rimandiDLM);
        nascondiAttributi();
        tipoRiferimentoComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Libro")) {
                    nascondiAttributi();
                    mostraAttributiLibro();
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Atto di convegno")) {
                    nascondiAttributi();
                    mostraAttributiConvegno();
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Articolo di rivista")) {
                    nascondiAttributi();
                    mostraAttributiRivista();
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Articolo di giornale")) {
                    nascondiAttributi();
                    mostraAttributiGiornale();
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Legge")) {
                    nascondiAttributi();
                    mostraAttributiLegge();
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Tesi")) {
                    nascondiAttributi();
                    mostraAttributiTesi();
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Sito web")) {
                    nascondiAttributi();
                    mostraAttributiWeb();
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Intervista")) {
                    nascondiAttributi();
                    mostraAttributiIntervista();
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Podcast")) {
                    nascondiAttributi();
                    mostraAttributiPodcast();
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Film")) {
                    nascondiAttributi();
                    mostraAttributiFilm();
                }
            }
        });
    }

    public void mostraRiferimento(Riferimento riferimento, DefaultTableModel model, int index) {
        if (riferimento.getTags() != null)
            for (String tag : riferimento.getTags())
                tagDLM.addElement(tag);
        if (riferimento.getCategorie() != null)
            for (Categoria cat : riferimento.getCategorie())
                categoriaDLM.addElement(cat.getNome());
        if (riferimento.getCitazioni() != null)
            for (Riferimento rif: riferimento.getCitazioni())
                rimandiDLM.addElement(rif.getTitolo());
        aggiungiCatRifButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!categoriaDLM.contains(aggiungiCategoriaComboBox.getSelectedItem().toString()))
                    for (Categoria cat: c.getCategorie()) {
                        if (cat.getNome().equals(aggiungiCategoriaComboBox.getSelectedItem().toString())) {
                            try {
                                c.aggiungiAlCatalogo(riferimento.getCodice(), cat.getCodice());
                                JOptionPane.showMessageDialog(mainPanel, "Il riferimento è stato assegnato alla categoria selezionata", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                                riferimento.getCategorie().add(cat);
                                categoriaDLM.addElement(cat.getNome());
                                while (cat.getSupercategoria() != null) {
                                    for (Categoria cat1: c.getCategorie()) {
                                        if (cat1.getCodice().equals(cat.getSupercategoria())) {
                                            if (!categoriaDLM.contains(cat1.getNome())) {
                                                riferimento.getCategorie().add(cat1);
                                                categoriaDLM.addElement(cat1.getNome());
                                            }
                                            cat = cat1;
                                            break;
                                        }
                                    }
                                }
                                for (int i = 0; i < model.getRowCount(); i++) {
                                    if (model.getValueAt(i, 0).equals(riferimento.getTitolo())) {
                                        model.setValueAt(riferimento.getCategorie(), i, 7);
                                        break;
                                    }
                                }
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        }
                    }
                else
                    JOptionPane.showMessageDialog(mainPanel, "Il riferimento è già assegnato a questa categoria!", "Errore!", JOptionPane.ERROR_MESSAGE);
            }
        });
        rimuoviCategoriaRifButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!categoriaRifList.isSelectionEmpty()) {
                    for (Categoria cat : riferimento.getCategorie()) {
                        if (cat.getNome().equals(categoriaRifList.getSelectedValue().toString())) {
                            try {
                                c.rimuoviDalCatalogo(riferimento.getCodice(), cat.getCodice());
                                JOptionPane.showMessageDialog(mainPanel, "Il riferimento è stato rimosso dalla categoria selezionata", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                                //c.getCategorie().remove(cat);
                                riferimento.getCategorie().remove(cat);
                                categoriaDLM.removeElement(categoriaRifList.getSelectedValue().toString());
                                //TODO: rimuovere tutte le sottocategorie di una categoria

                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        }
                    }
                }
                else
                    JOptionPane.showMessageDialog(mainPanel, "Nessuna categoria selezionata!", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
        aggiungiCitazioneRifButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (!rimandiDLM.contains(aggiungiCitazioniRifComboBox.getSelectedItem().toString())) {
                        c.aggiungiCitazione(riferimento.getCodice(), aggiungiCitazioniRifComboBox.getSelectedItem().toString());
                        JOptionPane.showMessageDialog(mainPanel, "Riferimenti associati", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                        for (int i = 0; i < model.getRowCount(); i++) {
                            if (model.getValueAt(i, 0).toString().equals(aggiungiCitazioniRifComboBox.getSelectedItem().toString())) {
                                String s = (model.getValueAt(i, 5)).toString();
                                int value = Integer.parseInt(s);
                                value = value + 1;
                                model.setValueAt(value, i, 5);
                                break;
                            }
                        }
                        rimandiDLM.addElement(aggiungiCitazioniRifComboBox.getSelectedItem().toString());
                        for (Riferimento r: c.getRiferimenti())
                            if (r.getTitolo().equals(aggiungiCitazioniRifComboBox.getSelectedItem().toString())) {
                                riferimento.getCitazioni().add(r);
                                break;
                            }
                    }
                    else {
                        JOptionPane.showMessageDialog(mainPanel, "Il riferimento contiene già questa citazione!", "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        rimuoviCitazioneRifButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!citazioniRifList.isSelectionEmpty()) {
                    try {
                        c.rimuoviCitazione(riferimento.getCodice(), citazioniRifList.getSelectedValue().toString());
                        JOptionPane.showMessageDialog(mainPanel, "Citazione rimossa", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                        for (int i = 0; i < model.getRowCount(); i++) {
                            if (model.getValueAt(i, 0).toString().equals(citazioniRifList.getSelectedValue().toString())) {
                                String s = (model.getValueAt(i, 5)).toString();
                                int value = Integer.parseInt(s);
                                value = value - 1;
                                model.setValueAt(value, i, 5);
                                break;
                            }
                        }
                        for (Riferimento r: c.getRiferimenti())
                            if (r.getTitolo().equals(aggiungiCitazioniRifComboBox.getSelectedItem().toString())) {
                                riferimento.getCitazioni().remove(r);
                                break;
                            }
                        rimandiDLM.removeElement(citazioniRifList.getSelectedValue());
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                    JOptionPane.showMessageDialog(mainPanel, "Nessuna citazione selezionata!", "Errore!", JOptionPane.ERROR_MESSAGE);
            }
        });
        aggiungiTagRifButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (!tagDLM.contains(aggiungiTagComboBox.getSelectedItem().toString())) {
                        c.aggiungiTag(riferimento.getCodice(), aggiungiTagComboBox.getSelectedItem().toString());
                        JOptionPane.showMessageDialog(mainPanel, "Tag associato", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                        tagDLM.addElement(aggiungiTagComboBox.getSelectedItem().toString());
                        riferimento.getTags().add(aggiungiTagComboBox.getSelectedItem().toString());
                    }
                    else
                        JOptionPane.showMessageDialog(mainPanel, "Il riferimento è già associato a questo tag!", "Errore", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        rimuoviTagRifButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!tagRifList.isSelectionEmpty()) {
                    try {
                        c.rimuoviTag(riferimento.getCodice(), tagRifList.getSelectedValue().toString());
                        JOptionPane.showMessageDialog(mainPanel, "Tag rimosso", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                        tagDLM.removeElement(tagRifList.getSelectedValue().toString());
                        riferimento.getTags().remove(aggiungiTagComboBox.getSelectedItem().toString());
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                    JOptionPane.showMessageDialog(mainPanel, "Nessun tag selezionato!", "Errore!", JOptionPane.ERROR_MESSAGE);
            }
        });
        //Il pulsante salva richiama il metodo Controller.modificaRiferimento(Riferimento r)
        salvaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String s = autoriTextField.getText();
                ArrayList<String> nuoviAutori = new ArrayList<>(Arrays.asList(s.split(";")));
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Libro")) {
                    Libro libro = (Libro) riferimento;
                    libro.setTitolo(titoloTextField.getText());
                    libro.setAutori(nuoviAutori);
                    libro.setDescrizione(descrizioneTextField.getText());
                    libro.setData(annoTextField.getText());
                    libro.setLingua(linguaTextField.getText());
                    libro.setIsbn(isbnTextField.getText());
                    libro.setPagine(pagineTextField.getText());
                    libro.setSerie(serieTextField.getText());
                    libro.setVolume(volumeTextField.getText());
                    try {
                        c.modificaRiferimento(libro);
                        JOptionPane.showMessageDialog(mainPanel, "Libro modificato.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Atto di convegno")) {
                    Convegno convegno = (Convegno) riferimento;
                    convegno.setTitolo(titoloTextField.getText());
                    convegno.setAutori(nuoviAutori);
                    convegno.setDescrizione(descrizioneTextField.getText());
                    convegno.setData(annoTextField.getText());
                    convegno.setLingua(linguaTextField.getText());
                    convegno.setDoi(doiTextField.getText());
                    convegno.setLuogo(luogoTextField.getText());
                    try {
                        c.modificaRiferimento(convegno);
                        JOptionPane.showMessageDialog(mainPanel, "Atto di convegno modificato.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Articolo di rivista")){
                    Rivista rivista = (Rivista) riferimento;
                    rivista.setTitolo(titoloTextField.getText());
                    rivista.setAutori(nuoviAutori);
                    rivista.setDescrizione(descrizioneTextField.getText());
                    rivista.setData(annoTextField.getText());
                    rivista.setLingua(linguaTextField.getText());
                    rivista.setIssn(issnTextField.getText());
                    rivista.setFascicolo(fascicoloTextField.getText());
                    rivista.setPagine(pagineTextField.getText());
                    try {
                        c.modificaRiferimento(rivista);
                        JOptionPane.showMessageDialog(mainPanel, "Articolo di rivista modificato.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Articolo di giornale")) {
                    Giornale giornale = (Giornale) riferimento;
                    giornale.setTitolo(titoloTextField.getText());
                    giornale.setAutori(nuoviAutori);
                    giornale.setDescrizione(descrizioneTextField.getText());
                    giornale.setData(annoTextField.getText());
                    giornale.setLingua(linguaTextField.getText());
                    giornale.setIssn(issnTextField.getText());
                    giornale.setSezione(sezioneTextField.getText());
                    giornale.setTestata(testataTextField.getText());
                    try {
                        c.modificaRiferimento(giornale);
                        JOptionPane.showMessageDialog(mainPanel, "Giornale modificato.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Legge")) {
                    Legge legge = (Legge) riferimento;
                    legge.setTitolo(titoloTextField.getText());
                    legge.setAutori(nuoviAutori);
                    legge.setDescrizione(descrizioneTextField.getText());
                    legge.setData(annoTextField.getText());
                    legge.setLingua(linguaTextField.getText());
                    legge.setCodiceLegge(codiceTextField.getText());
                    legge.setNumero(numeroTextField.getText());
                    legge.setTipoLegge(tipoTextField.getText());
                    try {
                        c.modificaRiferimento(legge);
                        JOptionPane.showMessageDialog(mainPanel, "Legge modificata.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Tesi")) {
                    Tesi tesi = (Tesi) riferimento;
                    tesi.setTitolo(titoloTextField.getText());
                    tesi.setAutori(nuoviAutori);
                    tesi.setDescrizione(descrizioneTextField.getText());
                    tesi.setData(annoTextField.getText());
                    tesi.setLingua(linguaTextField.getText());
                    tesi.setDoi(doiTextField.getText());
                    tesi.setTipoTesi(tipoTextField.getText());
                    tesi.setAteneo(ateneoTextField.getText());
                    try {
                        c.modificaRiferimento(tesi);
                        JOptionPane.showMessageDialog(mainPanel, "Tesi modificata.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Sito web")) {
                    Web web = (Web) riferimento;
                    web.setTitolo(titoloTextField.getText());
                    web.setAutori(nuoviAutori);
                    web.setDescrizione(descrizioneTextField.getText());
                    web.setData(annoTextField.getText());
                    web.setLingua(linguaTextField.getText());
                    web.setUrl(urlTextField.getText());
                    web.setSito(sitoTextField.getText());
                    web.setTipoSito(tipoTextField.getText());
                    try {
                        c.modificaRiferimento(web);
                        JOptionPane.showMessageDialog(mainPanel, "Sito web modificato.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Intervista")) {
                    String o = ospitiTextField.getText();
                    ArrayList<String> nuoviOspiti = new ArrayList<>(Arrays.asList(o.split(";")));
                    Intervista intervista = (Intervista) riferimento;
                    intervista.setTitolo(titoloTextField.getText());
                    intervista.setAutori(nuoviAutori);
                    intervista.setDescrizione(descrizioneTextField.getText());
                    intervista.setData(annoTextField.getText());
                    intervista.setLingua(linguaTextField.getText());
                    intervista.setDoi(doiTextField.getText());
                    intervista.setMezzo(mezzoTextField.getText());
                    intervista.setOspiti(nuoviOspiti);
                    try {
                        c.modificaRiferimento(intervista);
                        JOptionPane.showMessageDialog(mainPanel, "Intervista modificata.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Podcast")) {
                    Podcast podcast = (Podcast) riferimento;
                    podcast.setTitolo(titoloTextField.getText());
                    podcast.setAutori(nuoviAutori);
                    podcast.setDescrizione(descrizioneTextField.getText());
                    podcast.setData(annoTextField.getText());
                    podcast.setLingua(linguaTextField.getText());
                    podcast.setDoi(doiTextField.getText());
                    podcast.setEpisodio(episodioTextField.getText());
                    podcast.setSerie(serieTextField.getText());
                    try {
                        c.modificaRiferimento(podcast);
                        JOptionPane.showMessageDialog(mainPanel, "Podcast modificato.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Film")) {
                    Film film = (Film) riferimento;
                    film.setTitolo(titoloTextField.getText());
                    film.setAutori(nuoviAutori);
                    film.setDescrizione(descrizioneTextField.getText());
                    film.setData(annoTextField.getText());
                    film.setLingua(linguaTextField.getText());
                    film.setIsan(isanTextField.getText());
                    film.setDistribuzione(distribuzioneTextField.getText());
                    film.setGenere(genereTextField.getText());
                    try {
                        c.modificaRiferimento(film);
                        JOptionPane.showMessageDialog(mainPanel, "Film modificato.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                // Aggiorna le categorie
                if (riferimento.getCategorie() != null)
                    riferimento.getCategorie().clear();
                for (int i = 0; i < categoriaDLM.getSize(); i++) {
                    for (Categoria c: c.getCategorie()) {
                        if (c.getNome().equals(categoriaDLM.getElementAt(i)))
                            riferimento.getCategorie().add(c);
                    }
                }
                // Aggiorna le citazioni
                if (riferimento.getCitazioni() != null)
                    riferimento.getCitazioni().clear();
                for (int i = 0; i < rimandiDLM.getSize(); i++) {
                    for (Riferimento r: c.getRiferimenti()) {
                        if (r.getTitolo().equals(rimandiDLM.getElementAt(i)))
                            riferimento.getCitazioni().add(r);
                    }
                }
                //Aggiorna i tags
                if (riferimento.getTags() != null)
                    riferimento.getTags().clear();
                for (int i = 0; i < tagDLM.getSize(); i++) {
                    for (String tag: c.getTags()) {
                        if (tag.equals(tagDLM.getElementAt(i)))
                            riferimento.getTags().add(tag);
                    }
                }
                model.setValueAt(titoloTextField.getText(), index, 0);
                model.setValueAt(autoriTextField.getText(), index, 1);
                model.setValueAt(tipoRiferimentoComboBox.getSelectedItem(), index, 2);
                model.setValueAt(annoTextField.getText(), index, 3);
                model.setValueAt(linguaTextField.getText(), index, 4);
                dispose();
            }
        });
        tipoRiferimentoComboBox.setEnabled(false);
        titoloTextField.setText(riferimento.getTitolo());
        descrizioneTextField.setText(riferimento.getDescrizione());
        annoTextField.setText(riferimento.getData());
        String autori = "";
        for (String autore: riferimento.getAutori())
            autori = autori + autore + "; ";
        autoriTextField.setText(autori);
        linguaTextField.setText(riferimento.getLingua());
        if (riferimento instanceof Libro) {
            mostraAttributiLibro();
            tipoRiferimentoComboBox.setSelectedIndex(0);
            Libro libro = (Libro) riferimento;
            isbnTextField.setText(libro.getIsbn());
            pagineTextField.setText(libro.getPagine());
            volumeTextField.setText(libro.getVolume());
            serieTextField.setText(libro.getSerie());
        }
        if (riferimento instanceof Convegno) {
            mostraAttributiConvegno();
            tipoRiferimentoComboBox.setSelectedIndex(1);
            Convegno convegno = (Convegno) riferimento;
            doiTextField.setText(convegno.getDoi());
            luogoTextField.setText(convegno.getLuogo());
        }
        if (riferimento instanceof Rivista) {
            mostraAttributiRivista();
            tipoRiferimentoComboBox.setSelectedIndex(2);
            Rivista rivista = (Rivista) riferimento;
            issnTextField.setText(rivista.getIssn());
            pagineTextField.setText(rivista.getPagine());
            fascicoloTextField.setText(rivista.getFascicolo());
        }
        if (riferimento instanceof Giornale) {
            mostraAttributiGiornale();
            tipoRiferimentoComboBox.setSelectedIndex(3);
            Giornale giornale = (Giornale) riferimento;
            issnTextField.setText(giornale.getIssn());
            testataTextField.setText(giornale.getTestata());
            sezioneTextField.setText(giornale.getSezione());
        }
        if (riferimento instanceof Legge) {
            mostraAttributiLegge();
            tipoRiferimentoComboBox.setSelectedIndex(4);
            Legge legge = (Legge) riferimento;
            numeroTextField.setText(legge.getNumero());
            tipoTextField.setText(legge.getTipoLegge());
            codiceTextField.setText(legge.getCodiceLegge());
        }
        if (riferimento instanceof Tesi) {
            mostraAttributiTesi();
            tipoRiferimentoComboBox.setSelectedIndex(5);
            Tesi tesi = (Tesi) riferimento;
            doiTextField.setText(tesi.getDoi());
            tipoTextField.setText(tesi.getTipoTesi());
            ateneoTextField.setText(tesi.getAteneo());
        }
        if (riferimento instanceof Web) {
            mostraAttributiWeb();
            tipoRiferimentoComboBox.setSelectedIndex(6);
            Web web = (Web) riferimento;
            urlTextField.setText(web.getUrl());
            sitoTextField.setText(web.getSito());
            tipoTextField.setText(web.getTipoSito());
        }
        if (riferimento instanceof Intervista) {
            mostraAttributiIntervista();
            tipoRiferimentoComboBox.setSelectedIndex(7);
            Intervista intervista = (Intervista) riferimento;
            String ospiti = "";
            for (String ospite: intervista.getOspiti())
                ospiti = ospiti + ospite + "; ";
            doiTextField.setText(intervista.getDoi());
            mezzoTextField.setText(intervista.getMezzo());
            ospitiTextField.setText(ospiti);
        }
        if (riferimento instanceof Podcast) {
            mostraAttributiPodcast();
            tipoRiferimentoComboBox.setSelectedIndex(8);
            Podcast podcast = (Podcast) riferimento;
            doiTextField.setText(podcast.getDoi());
            episodioTextField.setText(podcast.getEpisodio());
            serieTextField.setText(podcast.getSerie());
        }
        if (riferimento instanceof Film) {
            mostraAttributiFilm();
            tipoRiferimentoComboBox.setSelectedIndex(9);
            Film film = (Film) riferimento;
            isanTextField.setText(film.getIsan());
            genereTextField.setText(film.getGenere());
            descrizioneTextField.setText(film.getDistribuzione());
        }
        model.setValueAt(titoloTextField.getText(), index, 0);
        model.setValueAt(autoriTextField.getText(), index, 1);
        model.setValueAt(tipoRiferimentoComboBox.getSelectedItem(), index, 2);
        model.setValueAt(annoTextField.getText(), index, 3);
        model.setValueAt(linguaTextField.getText(), index, 4);
        dispose();
    }

    public void creaNuovoRiferimento(DefaultTableModel model) {
        //Il pulsante salva richiama il metodo Controller.creaRiferimento(Riferimento r)
        aggiungiCatRifButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //TODO
                //Se c'è un errore è perché va prima creato un riferimento!
            }
        });
        rimuoviCategoriaRifButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //TODO
            }
        });
        aggiungiTagRifButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        rimuoviTagRifButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //TODO
            }
        });
        aggiungiCitazioneRifButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //TODO
            }
        });
        rimuoviCitazioneRifButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //TODO
            }
        });
        salvaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String s = autoriTextField.getText();
                ArrayList<String> autori = new ArrayList<>(Arrays.asList(s.split(";")));
                ArrayList<String> tags = new ArrayList<>();
                ArrayList<Riferimento> citazioni = new ArrayList<>();
                ArrayList<Categoria> categorie = new ArrayList<>();
                if (!tagDLM.isEmpty()) {
                    for (int i = 0; i < tagDLM.getSize(); i++)
                        tags.add((String) tagDLM.getElementAt(i));
                }
                if (!categoriaDLM.isEmpty()) {
                    for (int i = 0; i < categoriaDLM.getSize(); i++) {
                        for (Categoria c: c.getCategorie()) {
                            if (c.getNome().equals(categoriaDLM.getElementAt(i)))
                                categorie.add(c);
                        }
                    }
                }
                if (!rimandiDLM.isEmpty()) {
                    for (int i = 0; i < rimandiDLM.getSize(); i++) {
                        for (Riferimento r: c.getRiferimenti()) {
                            if (r.getTitolo().equals(rimandiDLM.getElementAt(i)))
                                citazioni.add(r);
                        }
                    }
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Libro")) {
                    Libro libro = new Libro.Builder()
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
                            .tags(tags)
                            .categorie(categorie)
                            .citazioni(citazioni)
                            .build();
                    try {
                        c.creaRiferimento(libro);
                        c.getRiferimenti().add(libro);
                        JOptionPane.showMessageDialog(mainPanel, "Libro creato.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Atto di convegno")) {
                    Convegno convegno = new Convegno.Builder()
                            .titolo(titoloTextField.getText())
                            .autori(autori)
                            .descrizione(descrizioneTextField.getText())
                            .lingua(linguaTextField.getText())
                            .tipo(tipoRiferimentoComboBox.getSelectedItem().toString())
                            .data(annoTextField.getText())
                            .doi(doiTextField.getText())
                            .luogo(luogoTextField.getText())
                            .build();
                    try {
                        c.creaRiferimento(convegno);
                        c.getRiferimenti().add(convegno);
                        JOptionPane.showMessageDialog(mainPanel, "Atto di convegno creato.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Articolo di rivista")) {
                    Rivista rivista = new Rivista.Builder()
                            .titolo(titoloTextField.getText())
                            .autori(autori)
                            .descrizione(descrizioneTextField.getText())
                            .lingua(linguaTextField.getText())
                            .tipo(tipoRiferimentoComboBox.getSelectedItem().toString())
                            .data(annoTextField.getText())
                            .categorie(categorie)
                            .tags(tags)
                            .citazioni(citazioni)
                            .issn(issnTextField.getText())
                            .pagine(pagineTextField.getText())
                            .fascicolo(fascicoloTextField.getText())
                            .build();
                    try {
                        c.creaRiferimento(rivista);
                        c.getRiferimenti().add(rivista);
                        JOptionPane.showMessageDialog(mainPanel, "Articolo di rivista creato.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Articolo di giornale")) {
                    Giornale giornale = new Giornale.Builder()
                            .titolo(titoloTextField.getText())
                            .autori(autori)
                            .descrizione(descrizioneTextField.getText())
                            .lingua(linguaTextField.getText())
                            .tipo(tipoRiferimentoComboBox.getSelectedItem().toString())
                            .data(annoTextField.getText())
                            .categorie(categorie)
                            .tags(tags)
                            .citazioni(citazioni)
                            .issn(issnTextField.getText())
                            .testata(testataTextField.getText())
                            .sezione(sezioneTextField.getText())
                            .build();
                    try {
                        c.creaRiferimento(giornale);
                        c.getRiferimenti().add(giornale);
                        JOptionPane.showMessageDialog(mainPanel, "Articolo di giornale creato.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Legge")) {
                    Legge legge = new Legge.Builder()
                            .titolo(titoloTextField.getText())
                            .autori(autori)
                            .descrizione(descrizioneTextField.getText())
                            .lingua(linguaTextField.getText())
                            .tipo(tipoRiferimentoComboBox.getSelectedItem().toString())
                            .data(annoTextField.getText())
                            .categorie(categorie)
                            .tags(tags)
                            .citazioni(citazioni)
                            .numero(numeroTextField.getText())
                            .tipoLegge(tipoTextField.getText())
                            .codiceLegge(codiceTextField.getText())
                            .build();
                    try {
                        c.creaRiferimento(legge);
                        c.getRiferimenti().add(legge);
                        JOptionPane.showMessageDialog(mainPanel, "Legge creata.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Tesi")) {
                    Tesi tesi = new Tesi.Builder()
                            .titolo(titoloTextField.getText())
                            .autori(autori)
                            .descrizione(descrizioneTextField.getText())
                            .lingua(linguaTextField.getText())
                            .tipo(tipoRiferimentoComboBox.getSelectedItem().toString())
                            .data(annoTextField.getText())
                            .categorie(categorie)
                            .tags(tags)
                            .citazioni(citazioni)
                            .doi(doiTextField.getText())
                            .ateneo(ateneoTextField.getText())
                            .tipoTesi(tipoTextField.getText())
                            .build();
                    try {
                        c.creaRiferimento(tesi);
                        c.getRiferimenti().add(tesi);
                        JOptionPane.showMessageDialog(mainPanel, "Tesi creata.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Sito web")) {
                    Web web = new Web.Builder()
                            .titolo(titoloTextField.getText())
                            .autori(autori)
                            .descrizione(descrizioneTextField.getText())
                            .lingua(linguaTextField.getText())
                            .tipo(tipoRiferimentoComboBox.getSelectedItem().toString())
                            .data(annoTextField.getText())
                            .categorie(categorie)
                            .tags(tags)
                            .citazioni(citazioni)
                            .url(urlTextField.getText())
                            .sito(sitoTextField.getText())
                            .tipoSito(tipoTextField.getText())
                            .build();
                    try {
                        c.creaRiferimento(web);
                        c.getRiferimenti().add(web);
                        JOptionPane.showMessageDialog(mainPanel, "Sito web creato.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Intervista")) {
                    String o = ospitiTextField.getText();
                    ArrayList<String> ospiti = new ArrayList<>(Arrays.asList(o.split(";")));
                    Intervista intervista = new Intervista.Builder()
                            .titolo(titoloTextField.getText())
                            .autori(autori)
                            .descrizione(descrizioneTextField.getText())
                            .lingua(linguaTextField.getText())
                            .tipo(tipoRiferimentoComboBox.getSelectedItem().toString())
                            .data(annoTextField.getText())
                            .categorie(categorie)
                            .tags(tags)
                            .citazioni(citazioni)
                            .doi(doiTextField.getText())
                            .mezzo(mezzoTextField.getText())
                            .ospiti(ospiti)
                            .build();
                    try {
                        c.creaRiferimento(intervista);
                        c.getRiferimenti().add(intervista);
                        JOptionPane.showMessageDialog(mainPanel, "Intervista creata.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Podcast")) {
                    Podcast podcast = new Podcast.Builder()
                            .titolo(titoloTextField.getText())
                            .autori(autori)
                            .descrizione(descrizioneTextField.getText())
                            .lingua(linguaTextField.getText())
                            .tipo(tipoRiferimentoComboBox.getSelectedItem().toString())
                            .data(annoTextField.getText())
                            .categorie(categorie)
                            .tags(tags)
                            .citazioni(citazioni)
                            .doi(doiTextField.getText())
                            .episodio(episodioTextField.getText())
                            .serie(serieTextField.getText())
                            .build();
                    try {
                        c.creaRiferimento(podcast);
                        c.getRiferimenti().add(podcast);
                        JOptionPane.showMessageDialog(mainPanel, "Podcast creato.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Film")) {
                    Film film = new Film.Builder()
                            .titolo(titoloTextField.getText())
                            .autori(autori)
                            .descrizione(descrizioneTextField.getText())
                            .lingua(linguaTextField.getText())
                            .tipo(tipoRiferimentoComboBox.getSelectedItem().toString())
                            .data(annoTextField.getText())
                            .categorie(categorie)
                            .tags(tags)
                            .citazioni(citazioni)
                            .isan(isanTextField.getText())
                            .genere(genereTextField.getText())
                            .distribuzione(distribuzioneTextField.getText())
                            .build();
                    try {
                        c.creaRiferimento(film);
                        c.getRiferimenti().add(film);
                        JOptionPane.showMessageDialog(mainPanel, "Film creato.", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                model.addRow(new Object[]{titoloTextField.getText(), autoriTextField.getText(), tipoRiferimentoComboBox.getSelectedItem(), annoTextField.getText(), linguaTextField.getText(), 0, "", ""});
                dispose();
            }
        });
    }

    public void nascondiAttributi() {
        isbnTextField.setVisible(false);
        isbnLabel.setVisible(false);
        pagineLabel.setVisible(false);
        pagineTextField.setVisible(false);
        volumeLabel.setVisible(false);
        volumeTextField.setVisible(false);
        serieLabel.setVisible(false);
        serieTextField.setVisible(false);
        doiLabel.setVisible(false);
        doiTextField.setVisible(false);
        luogoLabel.setVisible(false);
        luogoTextField.setVisible(false);
        isanTextField.setVisible(false);
        isanLabel.setVisible(false);
        genereTextField.setVisible(false);
        genereLabel.setVisible(false);
        distribuzioneLabel.setVisible(false);
        distribuzioneTextField.setVisible(false);
        issnLabel.setVisible(false);
        issnTextField.setVisible(false);
        testataLabel.setVisible(false);
        testataTextField.setVisible(false);
        sezioneTextField.setVisible(false);
        sezioneLabel.setVisible(false);
        fascicoloLabel.setVisible(false);
        fascicoloTextField.setVisible(false);
        numeroLabel.setVisible(false);
        numeroTextField.setVisible(false);
        tipoLabel.setVisible(false);
        tipoTextField.setVisible(false);
        codiceTextField.setVisible(false);
        ateneoLabel.setVisible(false);
        codiceLabel.setVisible(false);
        ateneoTextField.setVisible(false);
        urlLabel.setVisible(false);
        urlTextField.setVisible(false);
        sitoLabel.setVisible(false);
        sitoTextField.setVisible(false);
        mezzoLabel.setVisible(false);
        mezzoTextField.setVisible(false);
        ospitiLabel.setVisible(false);
        ospitiTextField.setVisible(false);
        episodioLabel.setVisible(false);
        episodioTextField.setVisible(false);
    }

    public void mostraAttributiLibro() {
        isbnTextField.setVisible(true);
        isbnLabel.setVisible(true);
        pagineLabel.setVisible(true);
        pagineTextField.setVisible(true);
        volumeLabel.setVisible(true);
        volumeTextField.setVisible(true);
        serieLabel.setVisible(true);
        serieTextField.setVisible(true);
    }

    public void mostraAttributiConvegno() {
        doiTextField.setVisible(true);
        doiLabel.setVisible(true);
        luogoTextField.setVisible(true);
        luogoLabel.setVisible(true);
    }

    public void mostraAttributiRivista() {
        issnLabel.setVisible(true);
        issnTextField.setVisible(true);
        pagineLabel.setVisible(true);
        pagineTextField.setVisible(true);
        fascicoloLabel.setVisible(true);
        fascicoloTextField.setVisible(true);
    }

    public void mostraAttributiGiornale() {
        issnTextField.setVisible(true);
        issnLabel.setVisible(true);
        testataLabel.setVisible(true);
        testataTextField.setVisible(true);
        sezioneLabel.setVisible(true);
        sezioneTextField.setVisible(true);
    }

    public void mostraAttributiFilm() {
        isanLabel.setVisible(true);
        isanTextField.setVisible(true);
        genereLabel.setVisible(true);
        genereTextField.setVisible(true);
        distribuzioneLabel.setVisible(true);
        distribuzioneTextField.setVisible(true);
    }

    public void mostraAttributiPodcast() {
        doiLabel.setVisible(true);
        doiTextField.setVisible(true);
        episodioTextField.setVisible(true);
        episodioLabel.setVisible(true);
        serieLabel.setVisible(true);
        serieTextField.setVisible(true);
    }

    public void mostraAttributiIntervista() {
        doiLabel.setVisible(true);
        doiTextField.setVisible(true);
        mezzoLabel.setVisible(true);
        mezzoTextField.setVisible(true);
        ospitiLabel.setVisible(true);
        ospitiTextField.setVisible(true);
    }

    public void mostraAttributiTesi() {
        doiLabel.setVisible(true);
        doiTextField.setVisible(true);
        tipoTextField.setVisible(true);
        tipoLabel.setVisible(true);
        ateneoTextField.setVisible(true);
        ateneoLabel.setVisible(true);
    }

    public void mostraAttributiWeb() {
        urlTextField.setVisible(true);
        urlLabel.setVisible(true);
        sitoTextField.setVisible(true);
        sitoLabel.setVisible(true);
        tipoLabel.setVisible(true);
        tipoTextField.setVisible(true);
    }

    public void mostraAttributiLegge() {
        numeroTextField.setVisible(true);
        numeroLabel.setVisible(true);
        tipoTextField.setVisible(true);
        tipoLabel.setVisible(true);
        codiceLabel.setVisible(true);
        codiceTextField.setVisible(true);
    }

    public void hideTabs() {
        cateogoriaTabbedPane.remove(3);
        cateogoriaTabbedPane.remove(2);
        cateogoriaTabbedPane.remove(1);
    }
}
