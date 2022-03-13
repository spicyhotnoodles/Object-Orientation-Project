package GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import DBEntities.*;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;

public class FinestraPrincipale extends JFrame {

    private Controller theController; //controllore
    private JPanel mainPanel; //pannello principale che contiene tutto il frame
    private JPanel leftPanel; //pannello di sinistra che contiene l'albero delle categorie e la lista delle parole chiave
    //elementi del pannello di sinsitra:
    private JScrollPane tagScrollPane; //JScrollPane che contiene la lista delle parole chiave
    //elementi del pannello tag:
    private JList tagList; //lista delle parole chiave definite dall'utente
    private JLabel tagLabel; //JLabel "Parole chiave"
    private JButton creaCategoriaButton;
    private JButton eliminaCategoriaButton;
    private JTextField cercaCategoriaTextField;
    private JButton cercaCategoriaButton;
    private JPanel centerPanel; //pannello centrale che contiene la tabella dei riferimenti presenti nel sistema
    //elementi del pannello centrale:
    private JScrollPane tabellaScrollPane;
    private JTable riferimentiTable; //tabella dei riferimenti
    private JButton aggiungiRiferimentoButton;
    private JButton eliminaRiferimentoButton;
    private JTextField cercaRiferimentoTextField;
    private JButton cercaRiferimentoButton;
    private JPanel rightPanel; //pannello di destra che contiene il pannello tab
    //elementi del pannello di destra:
    private JTabbedPane tabbedPane; //pannello tab con cui è possibile visualizzare/modificare/aggiungere informazioni ai riferimenti
    //elementi del pannello tab:
    private JPanel infoPanel;
    //elementi del pannello delle info:
    private JTextField titoloRiferimentoTextField;
    private JLabel titoloRiferimentoLabel;
    private JLabel autoreLabel;
    private JComboBox tipoRiferimentoComboBox;
    private JTextField dataTextField;
    private JTextField linguaTextField;
    private JTextField urlTextField;
    private JTextField isbnTextField;
    private JTextField doiTextField;
    private JLabel isbnLabel;
    private JLabel doiLabel;
    private JButton rimuoviAutoreButton;
    private JButton aggiungiAutoreButton;
    private JScrollPane descrizioneRiferimentoScrollPane;
    private JList autoreList;
    private JLabel urlLabel;
    private JLabel dataLabel;
    private JLabel linguaLabel;
    private JLabel tipoRiferimentoLabel;
    private JTextArea descrizioneRiferimentoTextArea;
    private JLabel descrizioneRiferimentoLabel;
    private JButton salvaRiferimentoButton;
    private JTextField serieTextField;
    private JTextField numeroPagineTextField;
    private JTextField volumeTextField;
    private JTextField nomeSitoWebTextField;
    private JTextField tipoSitoWebTextField;
    private JTextField issnTextField;
    private JTextField numeroFascicoloTextField;
    private JTextField sezioneTextField;
    private JTextField luogoConvegnoTextField;
    private JTextField tipoTesiTextField;
    private JTextField nomeUniversitàTextField;
    private JTextField cercaTagTextField;
    private JButton cercaTagButton;
    private JTextField lawLenderTextField;
    private JTextField numeroLeggeTextField;
    private JTextField lawVolumeCodeTextField;
    private JTextField legislativeBodyTextField;
    private JTextField podcastTitleTextField;
    private JTextField numeroEpisodioPodcastTextField;
    private JList registaList;
    private JButton rimuoviRegistaButton;
    private JButton aggiungiRegistaButton;
    private JList ospitiIntervistaList;
    private JButton aggiungiOspiteButton;
    private JButton rimuoviOspiteButton;
    private JScrollPane categoryScrollPane;
    private JLabel serieLabel;
    private JLabel numeroPagineLabel;
    private JLabel volumeLabel;
    private JLabel tipoSitoWebLabel;
    private JLabel nomeSitoWebLabel;
    private JLabel issnLabel;
    private JLabel numeroFascicoloLabel;
    private JLabel luogoConvegnoLabel;
    private JLabel sezioneLabel;
    private JLabel tipoTesiLabel;
    private JLabel nomeUniversitàLabel;
    private JLabel contributoreLeggeLabel;
    private JLabel numeroLeggeLabel;
    private JLabel codiceLeggeLabel;
    private JTextField codiceLeggeTextField;
    private JLabel lawVolumeCodeLabel;
    private JLabel legislativeBodyLabel;
    private JLabel podcastTitleLabel;
    private JLabel numeroEpisodioPodcastLabel;
    private JLabel registaLabel;
    private JLabel ospitiIntervistaLabel;
    private JTextField distribuzioneFilmTextField;
    private JTextField genereFilmTextField;
    private JTextField movieLengthTextField;
    private JLabel distribuzioneFilmLabel;
    private JLabel genereFilmLabel;
    private JLabel movieLengthLabel;
    private JList contributoreLeggeList;
    private JButton aggiungiContributoreLeggeButton;
    private JButton rimuoviContributoreLeggeButton;
    private JPanel notePanel;
    //elementi del pannello delle note
    private JTextArea notesTextArea;
    private JButton clearNotesButton;
    private JPanel collegamentiPanel;
    //elementi del pannello dei collegamenti
    private JLabel refLinkLabel;
    private JButton addRefLinkButton;
    private JList rimandiList;
    private JButton deleteRefLinkButton;
    private JButton changeRefLinkButton;
    private JPanel tagPanel;
    private JTextField addTagTextField;
    private JButton addTagButton;
    private JList tagRiferimentoList;
    private JButton eliminaTagButton;
    private JButton modificaTagButton;
    private JLabel addTagLabel;
    private JTextField mezzoDistribuzioneIntervistaTextField;
    private JLabel mezzoDistribuzioneIntervistaLabel;
    private JTextField isanTextField;
    private JLabel isanLabel;
    private JTextField testataGiornaletextField;
    private JLabel testataGiornaleLabel;
    private JLabel tipoLeggeLabel;
    private JComboBox tipoLeggeComboBox;
    private JButton chiudiButton;
    private JTree categoriaTree;
    private DefaultListModel listaAutoriDLModel = new DefaultListModel<String>();
    private DefaultListModel listaTagDLModel = new DefaultListModel<String>();
    private DefaultListModel listaRimandiDLModel = new DefaultListModel<String>();
    private DefaultListModel listaOspitiDLModel = new DefaultListModel<String>();
    private DefaultMutableTreeNode root;

    //categoryScrollPane.setViewportView(categoriaTree);


    //Lista icone per i pulsanti:

    private FontIcon folderIcon; //icona del pulsante per la creazione di una (sotto)categoria
    private FontIcon canIcon; //icona del pulsante per la cancellazione di una (sotto)categoria
    private FontIcon searchIcon; //icona del pulsante per la ricerca dei riferimenti
    private FontIcon addIcon; //icona del pulsante per l'aggiunta dei riferimenti
    private FontIcon deleteIcon; //icona del pulsante per la cancellazione dei riferimenti
    private FontIcon addAuthorIcon; //icona del pulsante per l'aggiunta di un autore
    private FontIcon removeAuthorIcon; //icona del pulsante per la cancellazione di un autore

    private ArrayList<Riferimento> riferimenti = new ArrayList<Riferimento>();


    public FinestraPrincipale(String title, Controller c) throws SQLException, IOException {

        super(title);
        theController = c;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setBounds(650, 250, 700, 500);
        creaTabellaRiferimenti();
        folderIcon = FontIcon.of(MaterialDesign.MDI_FOLDER);
        canIcon = FontIcon.of(MaterialDesign.MDI_DELETE);
        searchIcon = FontIcon.of(MaterialDesign.MDI_MAGNIFY);
        addIcon = FontIcon.of(MaterialDesign.MDI_PLUS_CIRCLE);
        deleteIcon = FontIcon.of(MaterialDesign.MDI_DELETE);
        addAuthorIcon = FontIcon.of(MaterialDesign.MDI_PLUS);
        removeAuthorIcon = FontIcon.of(MaterialDesign.MDI_MINUS);
        folderIcon.setIconSize(15);
        canIcon.setIconSize(15);
        searchIcon.setIconSize(15);
        addIcon.setIconSize(15);
        deleteIcon.setIconSize(15);
        addAuthorIcon.setIconSize(15);
        removeAuthorIcon.setIconSize(15);
        creaCategoriaButton.setIcon(folderIcon);
        eliminaCategoriaButton.setIcon(canIcon);
        aggiungiRiferimentoButton.setIcon(addIcon);
        eliminaRiferimentoButton.setIcon(deleteIcon);
        cercaRiferimentoButton.setIcon(searchIcon);
        aggiungiAutoreButton.setIcon(addAuthorIcon);
        rimuoviAutoreButton.setIcon(removeAuthorIcon);
        aggiungiRegistaButton.setIcon(addAuthorIcon);
        rimuoviRegistaButton.setIcon(removeAuthorIcon);
        aggiungiContributoreLeggeButton.setIcon(addAuthorIcon);
        rimuoviContributoreLeggeButton.setIcon(removeAuthorIcon);
        aggiungiOspiteButton.setIcon(addAuthorIcon);
        rimuoviOspiteButton.setIcon(removeAuthorIcon);
        autoreList.setBorder(new LineBorder(Color.GRAY));
        registaList.setBorder(new LineBorder(Color.GRAY));
        ospitiIntervistaList.setBorder(new LineBorder(Color.GRAY));
        contributoreLeggeList.setBorder(new LineBorder(Color.GRAY));
        autoreList.setModel(listaAutoriDLModel);
        tagRiferimentoList.setModel(listaTagDLModel);
        rimandiList.setModel(listaRimandiDLModel);
        root = new DefaultMutableTreeNode("Categorie");
        categoriaTree = new JTree(root);
        categoryScrollPane.setViewportView(categoriaTree);
        aggiungiCategorie(root);
        //oscura tutti gli attributi
        tabbedPane.setVisible(false);
        riferimenti = theController.ottieniRiferimenti();
        riempiTabella(riferimenti);
        //action listners:
        //action listner per la combobox del tipo di riferimento
        tipoRiferimentoComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Libro")) {
                    nascondiAttributiDiscriminanti();
                    mostraCampiLibro();
                } else if (tipoRiferimentoComboBox.getSelectedItem().equals("Atto di convegno")) {
                    nascondiAttributiDiscriminanti();
                    mostraCampiConvegno();
                } else if (tipoRiferimentoComboBox.getSelectedItem().equals("Articolo di rivista")) {
                    nascondiAttributiDiscriminanti();
                    mostraCampiRivista();
                } else if (tipoRiferimentoComboBox.getSelectedItem().equals("Articolo di giornale")) {
                    nascondiAttributiDiscriminanti();
                    mostraCampiGiornale();
                } else if (tipoRiferimentoComboBox.getSelectedItem().equals("Legge")) {
                    nascondiAttributiDiscriminanti();
                    mostraCampiLegge();
                } else if (tipoRiferimentoComboBox.getSelectedItem().equals("Tesi")) {
                    nascondiAttributiDiscriminanti();
                    mostraCampiTesi();
                } else if (tipoRiferimentoComboBox.getSelectedItem().equals("Sito web")) {
                    nascondiAttributiDiscriminanti();
                    mostraCampiWeb();
                } else if (tipoRiferimentoComboBox.getSelectedItem().equals("Intervista")) {
                    nascondiAttributiDiscriminanti();
                    mostraCampiIntervista();
                } else if (tipoRiferimentoComboBox.getSelectedItem().equals("Podcast")) {
                    nascondiAttributiDiscriminanti();
                    mostraCampiPodcast();
                } else if (tipoRiferimentoComboBox.getSelectedItem().equals("Film")) {
                    nascondiAttributiDiscriminanti();
                    mostraCampiFilm();
                }
            }
        });

        // Volevo implementare una funzione che modificasse l'interfaccia grafica inserendo una nuova label "Autore" e un relativo nuovo textfield per ogni pressione del bottone 'addAuthorButton'
        // sfortunatamente, l'aggiunta di un nuovo componente al pannello causa la sovrapposizione degli stessi rompendo la disposizione degli elementi. Ho optato per una semplice JList scorrevole
        /*addAuthorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JLabel label = new JLabel("Autore");
                jLabelList.add(label);
                //infoPanel.setLayout(new GridLayout(10,1));
                JTextField textField = new JTextField();
                textFieldList.add(textField);
                //infoPanel.add(jLabelList.get(i), new GridConstraints());
                infoPanel.add(jLabelList.get(i), new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
                infoPanel.add(textFieldList.get(i), new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));

                //infoPanel.add(textFieldList.get(i), new GridConstraints());
                infoPanel.validate();
                infoPanel.repaint();
                i++;
            }
        });*/
        riferimentiTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                tabbedPane.setVisible(true);
                if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Libro) {
                    listaAutoriDLModel.clear();
                    listaRimandiDLModel.clear();
                    listaTagDLModel.clear();
                    Libro libro = (Libro) riferimenti.get(riferimentiTable.getSelectedRow());
                    mostraCampiLibro();
                    tipoRiferimentoComboBox.setSelectedIndex(0);
                    titoloRiferimentoTextField.setText(libro.getTitolo());
                    for (String a: libro.getAutori())
                        listaAutoriDLModel.addElement(a.toString());
                    for (String t: libro.getTags())
                        listaTagDLModel.addElement(t.toString());
                    for (Riferimento r: libro.getRimandi())
                        listaRimandiDLModel.addElement(r.getTitolo());
                    descrizioneRiferimentoTextArea.setText(libro.getDescrizione());
                    dataTextField.setText(libro.getData());
                    linguaTextField.setText(libro.getLingua());
                    isbnTextField.setText(libro.getIsbn());
                    serieTextField.setText(libro.getSerie());
                    numeroPagineTextField.setText(libro.getPagine());
                    volumeTextField.setText(libro.getVolume());
                }
                if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Rivista) {
                    Rivista rivista = (Rivista) riferimenti.get(riferimentiTable.getSelectedRow());
                    listaAutoriDLModel.clear();
                    listaRimandiDLModel.clear();
                    listaTagDLModel.clear();
                    mostraCampiRivista();
                    tipoRiferimentoComboBox.setSelectedIndex(2);
                    titoloRiferimentoTextField.setText(rivista.getTitolo());
                    for (String a: rivista.getAutori())
                        listaAutoriDLModel.addElement(a.toString());
                    for (String t: rivista.getTags())
                        listaTagDLModel.addElement(t.toString());
                    for (Riferimento r: rivista.getRimandi())
                        listaRimandiDLModel.addElement(r.getTitolo());
                    descrizioneRiferimentoTextArea.setText(rivista.getDescrizione());
                    dataTextField.setText(rivista.getData());
                    linguaTextField.setText(rivista.getLingua());
                    issnTextField.setText(rivista.getIssn());
                    numeroPagineTextField.setText(rivista.getPagine());
                    numeroFascicoloTextField.setText(rivista.getFascicolo());
                }
                if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Convegno) {
                    Convegno convegno = (Convegno) riferimenti.get(riferimentiTable.getSelectedRow());
                    listaAutoriDLModel.clear();
                    listaRimandiDLModel.clear();
                    listaTagDLModel.clear();
                    mostraCampiConvegno();
                    tipoRiferimentoComboBox.setSelectedIndex(1);
                    titoloRiferimentoTextField.setText(convegno.getTitolo());
                    for (String a: convegno.getAutori())
                        listaAutoriDLModel.addElement(a.toString());
                    for (String t: convegno.getTags())
                        listaTagDLModel.addElement(t.toString());
                    for (Riferimento r: convegno.getRimandi())
                        listaRimandiDLModel.addElement(r.getTitolo());
                    descrizioneRiferimentoTextArea.setText(convegno.getDescrizione());
                    dataTextField.setText(convegno.getData());
                    linguaTextField.setText(convegno.getLingua());
                    doiTextField.setText(convegno.getDoi());
                    luogoConvegnoTextField.setText(convegno.getLuogo());
                }
                if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Giornale) {
                    Giornale giornale = (Giornale) riferimenti.get(riferimentiTable.getSelectedRow());
                    listaAutoriDLModel.clear();
                    listaRimandiDLModel.clear();
                    listaTagDLModel.clear();
                    mostraCampiGiornale();
                    tipoRiferimentoComboBox.setSelectedIndex(3);
                    titoloRiferimentoTextField.setText(giornale.getTitolo());
                    for (String a: giornale.getAutori())
                        listaAutoriDLModel.addElement(a.toString());
                    for (String t: giornale.getTags())
                        listaTagDLModel.addElement(t.toString());
                    for (Riferimento r: giornale.getRimandi())
                        listaRimandiDLModel.addElement(r.getTitolo());
                    descrizioneRiferimentoTextArea.setText(giornale.getDescrizione());
                    dataTextField.setText(giornale.getData());
                    linguaTextField.setText(giornale.getLingua());
                    issnTextField.setText(giornale.getIssn());
                    testataGiornaletextField.setText(giornale.getTestata());
                    sezioneTextField.setText(giornale.getSezione());
                }
                if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Tesi) {
                    Tesi tesi = (Tesi) riferimenti.get(riferimentiTable.getSelectedRow());
                    listaAutoriDLModel.clear();
                    listaTagDLModel.clear();
                    listaRimandiDLModel.clear();
                    mostraCampiTesi();
                    tipoRiferimentoComboBox.setSelectedIndex(5);
                    titoloRiferimentoTextField.setText(tesi.getTitolo());
                    for (String a: tesi.getAutori())
                        listaAutoriDLModel.addElement(a.toString());
                    for (String t: tesi.getTags())
                        listaTagDLModel.addElement(t.toString());
                    for (Riferimento r: tesi.getRimandi())
                        listaRimandiDLModel.addElement(r.getTitolo());
                    descrizioneRiferimentoTextArea.setText(tesi.getDescrizione());
                    dataTextField.setText(tesi.getData());
                    linguaTextField.setText(tesi.getLingua());
                    doiTextField.setText(tesi.getDoi());
                    tipoTesiTextField.setText(tesi.getTipo());
                    nomeUniversitàTextField.setText(tesi.getAteneo());
                }
                if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Web) {
                    Web web = (Web) riferimenti.get(riferimentiTable.getSelectedRow());
                    listaAutoriDLModel.clear();
                    listaRimandiDLModel.clear();
                    listaTagDLModel.clear();
                    mostraCampiWeb();
                    tipoRiferimentoComboBox.setSelectedIndex(6);
                    titoloRiferimentoTextField.setText(web.getTitolo());
                    for (String a: web.getAutori())
                        listaAutoriDLModel.addElement(a.toString());
                    for (String t: web.getTags())
                        listaTagDLModel.addElement(t.toString());
                    for (Riferimento r: web.getRimandi())
                        listaRimandiDLModel.addElement(r.getTitolo());
                    descrizioneRiferimentoTextArea.setText(web.getDescrizione());
                    dataTextField.setText(web.getData());
                    linguaTextField.setText(web.getLingua());
                    urlTextField.setText(web.getUrl());
                    nomeSitoWebTextField.setText(web.getSito());
                    tipoSitoWebTextField.setText(web.getTipoSito());
                }
                if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Film) {
                    Film film = (Film) riferimenti.get(riferimentiTable.getSelectedRow());
                    listaAutoriDLModel.clear();
                    listaTagDLModel.clear();
                    listaRimandiDLModel.clear();
                    mostraCampiFilm();
                    tipoRiferimentoComboBox.setSelectedIndex(9);
                    titoloRiferimentoTextField.setText(film.getTitolo());
                    for (String a: film.getAutori())
                        listaAutoriDLModel.addElement(a.toString());
                    for (String t: film.getTags())
                        listaTagDLModel.addElement(t.toString());
                    for (Riferimento r: film.getRimandi())
                        listaRimandiDLModel.addElement(r.getTitolo());
                    descrizioneRiferimentoTextArea.setText(film.getDescrizione());
                    dataTextField.setText(film.getData());
                    linguaTextField.setText(film.getLingua());
                    isanTextField.setText(film.getIsan());
                    genereFilmTextField.setText(film.getGenere());
                    distribuzioneFilmTextField.setText(film.getDistribuzione());
                }
                if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Intervista) {
                    Intervista intervista = (Intervista) riferimenti.get(riferimentiTable.getSelectedRow());
                    listaAutoriDLModel.clear();
                    listaRimandiDLModel.clear();
                    listaTagDLModel.clear();
                    listaOspitiDLModel.clear();
                    mostraCampiIntervista();
                    titoloRiferimentoTextField.setText(intervista.getTitolo());
                    for (String a: intervista.getAutori())
                        listaAutoriDLModel.addElement(a.toString());
                    for (String t: intervista.getTags())
                        listaTagDLModel.addElement(t.toString());
                    for (Riferimento r: intervista.getRimandi())
                        listaRimandiDLModel.addElement(r.getTitolo());
                    for (String o: intervista.getOspiti())
                    descrizioneRiferimentoTextArea.setText(intervista.getDescrizione());
                    dataTextField.setText(intervista.getData());
                    linguaTextField.setText(intervista.getLingua());
                    doiTextField.setText(intervista.getDoi());
                    mezzoDistribuzioneIntervistaTextField.setText(intervista.getMezzo());
                }
                if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Legge) {
                    Legge legge = (Legge) riferimenti.get(riferimentiTable.getSelectedRow());
                    listaAutoriDLModel.clear();
                    listaRimandiDLModel.clear();
                    listaTagDLModel.clear();
                    mostraCampiLegge();
                    titoloRiferimentoTextField.setText(legge.getTitolo());
                    for (String a: legge.getAutori())
                        listaAutoriDLModel.addElement(a.toString());
                    for (String t: legge.getTags())
                        listaTagDLModel.addElement(t.toString());
                    for (Riferimento r: legge.getRimandi())
                        listaRimandiDLModel.addElement(r.getTitolo());
                    dataTextField.setText(legge.getData());
                    linguaTextField.setText(legge.getLingua());
                    numeroLeggeTextField.setText(legge.getNumero());
                    codiceLeggeTextField.setText(legge.getCodice());
                    if (legge.getTipo().equalsIgnoreCase("Legge"))
                        tipoLeggeComboBox.setSelectedIndex(1);
                    if (legge.getTipo().equalsIgnoreCase("Decreto"))
                        tipoLeggeComboBox.setSelectedIndex(2);
                    if (legge.getTipo().equalsIgnoreCase("Altro"))
                        tipoLeggeComboBox.setSelectedIndex(3);
                }
                if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Podcast) {
                    Podcast podcast = (Podcast) riferimenti.get(riferimentiTable.getSelectedRow());
                    listaAutoriDLModel.clear();
                    listaRimandiDLModel.clear();
                    listaTagDLModel.clear();
                    mostraCampiPodcast();
                    titoloRiferimentoTextField.setText(podcast.getTitolo());
                    for (String a: podcast.getAutori())
                        listaAutoriDLModel.addElement(a.toString());
                    for (String t: podcast.getTags())
                        listaTagDLModel.addElement(t.toString());
                    for (Riferimento r: podcast.getRimandi())
                        listaRimandiDLModel.addElement(r.getTitolo());
                    dataTextField.setText(podcast.getData());
                    linguaTextField.setText(podcast.getLingua());
                    numeroEpisodioPodcastTextField.setText(podcast.getEpisodio());
                    doiTextField.setText(podcast.getDoi());
                    serieTextField.setText(podcast.getSerie());
                }
            }
        });
        chiudiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pulisciCampi();
            }
        });
        aggiungiRiferimentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String tipo[] = {"Libro", "Atto di convegno", "Articolo di rivista", "Articolo di giornale", "Legge", "Tesi", "Sito web", "Intervista", "Podcast", "Film"};
                JComboBox cb = new JComboBox(tipo);
                int input;
                input = JOptionPane.showConfirmDialog(mainPanel, cb, "Seleziona il tipo di riferimento", JOptionPane.DEFAULT_OPTION);
                if (input == JOptionPane.OK_OPTION) {
                    if (cb.getSelectedItem().equals("Libro")) {
                        nascondiAttributiDiscriminanti();
                        tabbedPane.setVisible(true);
                        tipoRiferimentoComboBox.setSelectedIndex(0);
                        mostraCampiLibro();
                    }
                    if (cb.getSelectedItem().equals("Atto di convegno")) {
                        nascondiAttributiDiscriminanti();
                        tabbedPane.setVisible(true);
                        tipoRiferimentoComboBox.setSelectedIndex(1);
                        mostraCampiConvegno();
                    }
                    if (cb.getSelectedItem().equals("Articolo di rivista")) {
                        nascondiAttributiDiscriminanti();
                        tabbedPane.setVisible(true);
                        tipoRiferimentoComboBox.setSelectedIndex(2);
                        mostraCampiRivista();
                    }
                    if (cb.getSelectedItem().equals("Articolo di giornale")) {
                        nascondiAttributiDiscriminanti();
                        tabbedPane.setVisible(true);
                        tipoRiferimentoComboBox.setSelectedIndex(3);
                        mostraCampiGiornale();
                    }
                    if (cb.getSelectedItem().equals("Legge")) {
                        nascondiAttributiDiscriminanti();
                        tabbedPane.setVisible(true);
                        tipoRiferimentoComboBox.setSelectedIndex(4);
                        mostraCampiLegge();
                    }
                    if (cb.getSelectedItem().equals("Tesi")) {
                        nascondiAttributiDiscriminanti();
                        tabbedPane.setVisible(true);
                        tipoRiferimentoComboBox.setSelectedIndex(5);
                        mostraCampiTesi();
                    }
                    if (cb.getSelectedItem().equals("Sito web")) {
                        nascondiAttributiDiscriminanti();
                        tabbedPane.setVisible(true);
                        tipoRiferimentoComboBox.setSelectedIndex(6);
                        mostraCampiWeb();
                    }
                    if (cb.getSelectedItem().equals("Intervista")) {
                        nascondiAttributiDiscriminanti();
                        tabbedPane.setVisible(true);
                        tipoRiferimentoComboBox.setSelectedIndex(7);
                        mostraCampiIntervista();
                    }
                    if (cb.getSelectedItem().equals("Podcast")) {
                        nascondiAttributiDiscriminanti();
                        tabbedPane.setVisible(true);
                        tipoRiferimentoComboBox.setSelectedIndex(8);
                        mostraCampiPodcast();
                    }
                    if (cb.getSelectedItem().equals("Film")) {
                        nascondiAttributiDiscriminanti();
                        tabbedPane.setVisible(true);
                        tipoRiferimentoComboBox.setSelectedIndex(9);
                        mostraCampiFilm();
                    }
                }
            }
        });
        eliminaRiferimentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (riferimentiTable.getSelectedRow() == -1)
                    JOptionPane.showMessageDialog(mainPanel, "Selezionare il valore da modificare!", "Errore!", JOptionPane.ERROR_MESSAGE);
                else {
                    int input;
                    input = JOptionPane.showConfirmDialog(mainPanel, "Sei sicuro di voler eliminare il riferimento \"" + riferimenti.get(riferimentiTable.getSelectedRow()).getTitolo() + "\"?");
                    if (input == JOptionPane.YES_OPTION)
                        if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Libro) {
                            try {
                                c.eliminaRiferimento(riferimenti.get(riferimentiTable.getSelectedRow()).getCodice());
                                riferimenti.remove(riferimentiTable.getSelectedRow());
                                ((DefaultTableModel) riferimentiTable.getModel()).removeRow(riferimentiTable.getSelectedRow());
                                pulisciCampi();
                                JOptionPane.showMessageDialog(mainPanel, "Riferimento eliminato", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(mainPanel, "Eliminazione fallita! Info:\n" + e, "Errore!", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                }
            }
        });
        creaCategoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String s = JOptionPane.showInputDialog("Inserisci il nome della categoria");
                if (s.equals(""))
                    JOptionPane.showMessageDialog(mainPanel, "Specificare un nome per la categoria!", "Errore!", JOptionPane.ERROR_MESSAGE);
                else {
                    try {
                        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) categoriaTree.getSelectionPath().getLastPathComponent();
                        creaNuovaCategoria(s, nodo);
                    } catch (NullPointerException e) {
                        JOptionPane.showMessageDialog(mainPanel, "Per inserire una nuova categoria, selezionare prima un elemento dalla lista", "Errore!", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void creaTabellaRiferimenti() {
        riferimentiTable.setModel(new DefaultTableModel(null, new String[]{"Titolo", "Tipologia", "Anno", "Lingua"}));
    }

    private void nascondiAttributiDiscriminanti() {
        autoreList.setVisible(false);
        autoreLabel.setVisible(false);
        aggiungiAutoreButton.setVisible(false);
        rimuoviAutoreButton.setVisible(false);
        isbnLabel.setVisible(false);
        isbnTextField.setVisible(false);
        volumeLabel.setVisible(false);
        volumeTextField.setVisible(false);
        numeroPagineLabel.setVisible(false);
        numeroPagineTextField.setVisible(false);
        serieLabel.setVisible(false);
        serieTextField.setVisible(false);
        sezioneLabel.setVisible(false);
        sezioneTextField.setVisible(false);
        issnLabel.setVisible(false);
        issnTextField.setVisible(false);
        testataGiornaleLabel.setVisible(false);
        testataGiornaletextField.setVisible(false);
        numeroFascicoloLabel.setVisible(false);
        numeroFascicoloTextField.setVisible(false);
        doiLabel.setVisible(false);
        doiTextField.setVisible(false);
        luogoConvegnoLabel.setVisible(false);
        luogoConvegnoTextField.setVisible(false);
        registaLabel.setVisible(false);
        registaList.setVisible(false);
        aggiungiRegistaButton.setVisible(false);
        rimuoviRegistaButton.setVisible(false);
        genereFilmLabel.setVisible(false);
        genereFilmTextField.setVisible(false);
        distribuzioneFilmLabel.setVisible(false);
        distribuzioneFilmTextField.setVisible(false);
        isanLabel.setVisible(false);
        isanTextField.setVisible(false);
        codiceLeggeLabel.setVisible(false);
        codiceLeggeTextField.setVisible(false);
        contributoreLeggeLabel.setVisible(false);
        contributoreLeggeList.setVisible(false);
        aggiungiContributoreLeggeButton.setVisible(false);
        rimuoviContributoreLeggeButton.setVisible(false);
        numeroLeggeLabel.setVisible(false);
        numeroLeggeTextField.setVisible(false);
        tipoLeggeComboBox.setVisible(false);
        tipoLeggeLabel.setVisible(false);
        urlTextField.setVisible(false);
        urlLabel.setVisible(false);
        nomeSitoWebLabel.setVisible(false);
        nomeSitoWebTextField.setVisible(false);
        tipoSitoWebLabel.setVisible(false);
        tipoSitoWebTextField.setVisible(false);
        numeroEpisodioPodcastLabel.setVisible(false);
        numeroEpisodioPodcastTextField.setVisible(false);
        tipoTesiLabel.setVisible(false);
        tipoTesiTextField.setVisible(false);
        nomeUniversitàLabel.setVisible(false);
        nomeUniversitàTextField.setVisible(false);
        ospitiIntervistaLabel.setVisible(false);
        ospitiIntervistaList.setVisible(false);
        aggiungiOspiteButton.setVisible(false);
        rimuoviOspiteButton.setVisible(false);
        mezzoDistribuzioneIntervistaLabel.setVisible(false);
        mezzoDistribuzioneIntervistaTextField.setVisible(false);
    }

    public void mostraCampiLibro() {
        nascondiAttributiDiscriminanti();
        aggiungiAutoreButton.setVisible(true);
        rimuoviAutoreButton.setVisible(true);
        autoreList.setVisible(true);
        autoreLabel.setVisible(true);
        autoreLabel.setVisible(true);
        isbnLabel.setVisible(true);
        isbnTextField.setVisible(true);
        volumeLabel.setVisible(true);
        volumeTextField.setVisible(true);
        numeroPagineLabel.setVisible(true);
        numeroPagineTextField.setVisible(true);
        serieLabel.setVisible(true);
        serieTextField.setVisible(true);
    }

    public void mostraCampiRivista() {
        issnLabel.setVisible(true);
        issnTextField.setVisible(true);
        autoreLabel.setVisible(true);
        autoreList.setVisible(true);
        aggiungiAutoreButton.setVisible(true);
        rimuoviAutoreButton.setVisible(true);
        numeroPagineLabel.setVisible(true);
        numeroPagineTextField.setVisible(true);
        numeroFascicoloLabel.setVisible(true);
        numeroFascicoloTextField.setVisible(true);
    }

    public void mostraCampiGiornale() {
        issnLabel.setVisible(true);
        issnTextField.setVisible(true);
        autoreLabel.setVisible(true);
        autoreList.setVisible(true);
        aggiungiAutoreButton.setVisible(true);
        rimuoviAutoreButton.setVisible(true);
        sezioneLabel.setVisible(true);
        sezioneTextField.setVisible(true);
        testataGiornaleLabel.setVisible(true);
        testataGiornaletextField.setVisible(true);
    }

    public void mostraCampiConvegno() {
        autoreList.setVisible(true);
        autoreLabel.setVisible(true);
        aggiungiAutoreButton.setVisible(true);
        rimuoviAutoreButton.setVisible(true);
        doiLabel.setVisible(true);
        doiTextField.setVisible(true);
        luogoConvegnoLabel.setVisible(true);
        luogoConvegnoTextField.setVisible(true);
    }

    public void mostraCampiFilm() {
        isanLabel.setVisible(true);
        isanTextField.setVisible(true);
        genereFilmLabel.setVisible(true);
        genereFilmTextField.setVisible(true);
        distribuzioneFilmLabel.setVisible(true);
        distribuzioneFilmTextField.setVisible(true);
        aggiungiRegistaButton.setVisible(true);
        rimuoviRegistaButton.setVisible(true);
        registaLabel.setVisible(true);
        registaList.setVisible(true);
    }

    public void mostraCampiPodcast() {
        autoreLabel.setVisible(true);
        autoreList.setVisible(true);
        serieLabel.setVisible(true);
        serieTextField.setVisible(true);
        aggiungiAutoreButton.setVisible(true);
        rimuoviAutoreButton.setVisible(true);
        doiLabel.setVisible(true);
        doiTextField.setVisible(true);
        numeroEpisodioPodcastLabel.setVisible(true);
        numeroEpisodioPodcastTextField.setVisible(true);
    }

    public void mostraCampiIntervista() {
        autoreLabel.setVisible(true);
        autoreList.setVisible(true);
        doiLabel.setVisible(true);
        doiTextField.setVisible(true);
        aggiungiAutoreButton.setVisible(true);
        rimuoviAutoreButton.setVisible(true);
        ospitiIntervistaLabel.setVisible(true);
        ospitiIntervistaList.setVisible(true);
        aggiungiOspiteButton.setVisible(true);
        rimuoviOspiteButton.setVisible(true);
        mezzoDistribuzioneIntervistaLabel.setVisible(true);
        mezzoDistribuzioneIntervistaTextField.setVisible(true);
    }

    public void mostraCampiLegge() {
        codiceLeggeLabel.setVisible(true);
        codiceLeggeTextField.setVisible(true);
        numeroLeggeLabel.setVisible(true);
        numeroLeggeTextField.setVisible(true);
        contributoreLeggeLabel.setVisible(true);
        contributoreLeggeList.setVisible(true);
        aggiungiContributoreLeggeButton.setVisible(true);
        rimuoviContributoreLeggeButton.setVisible(true);
        tipoLeggeComboBox.setVisible(true);
        tipoLeggeLabel.setVisible(true);
    }

    public void mostraCampiWeb() {
        autoreLabel.setVisible(true);
        autoreList.setVisible(true);
        aggiungiAutoreButton.setVisible(true);
        rimuoviAutoreButton.setVisible(true);
        nomeSitoWebLabel.setVisible(true);
        nomeSitoWebTextField.setVisible(true);
        tipoSitoWebLabel.setVisible(true);
        tipoSitoWebTextField.setVisible(true);
    }

    public void mostraCampiTesi() {
        autoreList.setVisible(true);
        autoreLabel.setVisible(true);
        aggiungiAutoreButton.setVisible(true);
        rimuoviAutoreButton.setVisible(true);
        doiLabel.setVisible(true);
        doiTextField.setVisible(true);
        nomeUniversitàLabel.setVisible(true);
        nomeUniversitàTextField.setVisible(true);
        tipoTesiLabel.setVisible(true);
        tipoTesiTextField.setVisible(true);
    }

    public void riempiTabella(ArrayList<Riferimento> riferimenti) {
        DefaultTableModel model = (DefaultTableModel) riferimentiTable.getModel();
        model.setRowCount(0);
        riferimentiTable.setDefaultEditor(Object.class, null); //rende la tabella non editabile
        for (Riferimento r : riferimenti) {
            model.addRow(new Object[]{r.getTitolo(), r.getTipo(), r.getData(), r.getLingua()});
        }
    }

    public void pulisciCampi() {
        tabbedPane.setVisible(false);
        titoloRiferimentoTextField.setText("");
        descrizioneRiferimentoTextArea.setText("");
        listaAutoriDLModel.clear();
        dataTextField.setText("");
        linguaTextField.setText("");
        issnTextField.setText("");
        issnTextField.setText("");
        doiTextField.setText("");
        isanTextField.setText("");
        numeroLeggeTextField.setText("");
        serieTextField.setText("");
        numeroPagineTextField.setText("");
        volumeTextField.setText("");
        nomeSitoWebTextField.setText("");
        tipoLeggeComboBox.setSelectedIndex(0);
        tipoSitoWebTextField.setText("");
        numeroFascicoloTextField.setText("");
        luogoConvegnoTextField.setText("");
        testataGiornaletextField.setText("");
        sezioneTextField.setText("");
        tipoTesiTextField.setText("");
        nomeUniversitàTextField.setText("");
        codiceLeggeTextField.setText("");
        numeroEpisodioPodcastTextField.setText("");
        distribuzioneFilmTextField.setText("");
        mezzoDistribuzioneIntervistaTextField.setText("");
        genereFilmTextField.setText("");
        urlTextField.setText("");
        notesTextArea.setText("");
        listaTagDLModel.clear();
        listaRimandiDLModel.clear();
    }

    public void creaNuovaCategoria(String s, DefaultMutableTreeNode n) throws SQLException {
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(s);
        n.add(nodo);
        Categoria categoria = new Categoria.Builder()
                .setNome(s)
                .setPadre(n.toString())
                .build();
        //theController.creaCategoria(categoria);
        ((DefaultTreeModel) categoriaTree.getModel()).reload();
    }

    public void aggiungiCategorie(DefaultMutableTreeNode root) throws SQLException {
        ArrayList<Categoria> categorie = theController.ottieniCategorie();
        for (Categoria cat: categorie) {
            if (cat.getPadre().equals("")) {
                DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(cat.getNome());
                root.add(nodo);
            }
        }
        for (Categoria cat: categorie) {
            if (!cat.getPadre().equals("")){
                DefaultMutableTreeNode padre = trovaNodo(root, cat.getPadre());
                DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(cat.getNome());
                padre.add(nodo);
            }
        }
    }

    public DefaultMutableTreeNode trovaNodo(DefaultMutableTreeNode root, String s) {
        @SuppressWarnings("unchecked")
        Enumeration<TreeNode> e = root.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            if (node.toString().equalsIgnoreCase(s)) {
                return (node);
            }
        }
        return null;
    }

}
