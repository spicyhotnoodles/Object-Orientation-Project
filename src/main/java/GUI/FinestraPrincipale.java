package GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
    private JTree categoriaTree; //albero che lista tutte le categorie definite dall'utente
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
    private JButton saveNotesButton;
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
    private JButton deleteTagButton;
    private JButton changeTagButton;
    private JLabel addTagLabel;
    private JTextField mezzoDistribuzioneIntervistaTextField;
    private JLabel mezzoDistribuzioneIntervistaLabel;
    private JTextField isanTextField;
    private JLabel isanLabel;
    private JTextField testataGiornaletextField;
    private JLabel testataGiornaleLabel;
    private JLabel tipoLeggeLabel;
    private JComboBox tipoLeggeComboBox;
    private DefaultListModel listaAutoriDLModel = new DefaultListModel<String>();
    private DefaultListModel listaTagDLModel = new DefaultListModel<String>();
    private DefaultListModel listaRimandiDLModel = new DefaultListModel<String>();


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
        //oscura tutti gli attributi
        nascondiAttributiDiscriminanti();
        riferimenti = theController.ottieniRiferimenti();
        riempiTabella(riferimenti);
        //action listners:
        /*for (Riferimento i: riferimenti)
            System.out.println(i);
        Riferimento r = riferimenti.get(0);
        if (r instanceof Libro) {
            Libro mylibro = (Libro) r;
            mylibro.toString();
        }
        if (r instanceof Web) {
            Web myWeb = (Web) r;
            myWeb.toString();
        }*/
        //action listner per il pulsante di creazione di una (sotto)categoria
        creaCategoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        //action listner per la combobox del tipo di riferimento
        tipoRiferimentoComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (tipoRiferimentoComboBox.getSelectedItem().equals("Libro")) {
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
                } else if (tipoRiferimentoComboBox.getSelectedItem().equals("Atto di convegno")) {
                    nascondiAttributiDiscriminanti();
                    autoreList.setVisible(true);
                    autoreLabel.setVisible(true);
                    aggiungiAutoreButton.setVisible(true);
                    rimuoviAutoreButton.setVisible(true);
                    doiLabel.setVisible(true);
                    doiTextField.setVisible(true);
                    luogoConvegnoLabel.setVisible(true);
                    luogoConvegnoTextField.setVisible(true);
                } else if (tipoRiferimentoComboBox.getSelectedItem().equals("Articolo di rivista")) {
                    nascondiAttributiDiscriminanti();
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
                } else if (tipoRiferimentoComboBox.getSelectedItem().equals("Articolo di giornale")) {
                    nascondiAttributiDiscriminanti();
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
                } else if (tipoRiferimentoComboBox.getSelectedItem().equals("Legge")) {
                    nascondiAttributiDiscriminanti();
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
                } else if (tipoRiferimentoComboBox.getSelectedItem().equals("Tesi")) {
                    nascondiAttributiDiscriminanti();
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
                } else if (tipoRiferimentoComboBox.getSelectedItem().equals("Sito web")) {
                    nascondiAttributiDiscriminanti();
                    autoreLabel.setVisible(true);
                    autoreList.setVisible(true);
                    aggiungiAutoreButton.setVisible(true);
                    rimuoviAutoreButton.setVisible(true);
                    nomeSitoWebLabel.setVisible(true);
                    nomeSitoWebTextField.setVisible(true);
                    tipoSitoWebLabel.setVisible(true);
                    tipoSitoWebTextField.setVisible(true);
                } else if (tipoRiferimentoComboBox.getSelectedItem().equals("Intervista")) {
                    nascondiAttributiDiscriminanti();
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
                } else if (tipoRiferimentoComboBox.getSelectedItem().equals("Podcast")) {
                    nascondiAttributiDiscriminanti();
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
                } else if (tipoRiferimentoComboBox.getSelectedItem().equals("Film")) {
                    nascondiAttributiDiscriminanti();
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
                    notesTextArea.setText(libro.getNote());
                }
                if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Rivista) {
                    Rivista rivista = (Rivista) riferimenti.get(riferimentiTable.getSelectedRow());
                    //imposta la gui
                }
                if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Convegno) {
                    Convegno convegno = (Convegno) riferimenti.get(riferimentiTable.getSelectedRow());
                    //imposta la gui
                }
                if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Giornale) {
                    Giornale giornale = (Giornale) riferimenti.get(riferimentiTable.getSelectedRow());
                    //imposta la gui
                }
                if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Tesi) {
                    Tesi tesi = (Tesi) riferimenti.get(riferimentiTable.getSelectedRow());
                    //imposta la gui
                }
                if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Web) {
                    Web web = (Web) riferimenti.get(riferimentiTable.getSelectedRow());
                    //imposta la gui
                }
                if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Film) {
                    Film film = (Film) riferimenti.get(riferimentiTable.getSelectedRow());
                    //imposta la gui
                }
                if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Intervista) {
                    Intervista intervista = (Intervista) riferimenti.get(riferimentiTable.getSelectedRow());
                    //imposta la gui
                }
                if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Legge) {
                    Legge legge = (Legge) riferimenti.get(riferimentiTable.getSelectedRow());
                    //imposta la gui
                }
                if (riferimenti.get(riferimentiTable.getSelectedRow()) instanceof Podcast) {
                    Podcast podcast = (Podcast) riferimenti.get(riferimentiTable.getSelectedRow());
                    //imposta la gui
                }
            }
        });
    }

    private void creaTabellaRiferimenti() {
        riferimentiTable.setModel(new DefaultTableModel(null, new String[]{"Titolo", "Tipologia", "Anno", "Lingua"}));
    }

    private void nascondiAttributiDiscriminanti() {
        //tipo libro
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
        //tipo articolo di giornale
        sezioneLabel.setVisible(false);
        sezioneTextField.setVisible(false);
        issnLabel.setVisible(false);
        issnTextField.setVisible(false);
        testataGiornaleLabel.setVisible(false);
        testataGiornaletextField.setVisible(false);
        //tipo articolo di rivista
        numeroFascicoloLabel.setVisible(false);
        numeroFascicoloTextField.setVisible(false);
        //tipo atto di convengo
        doiLabel.setVisible(false);
        doiTextField.setVisible(false);
        luogoConvegnoLabel.setVisible(false);
        luogoConvegnoTextField.setVisible(false);
        //tipo film
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
        //tipo legge
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
        //tipo pagina web
        nomeSitoWebLabel.setVisible(false);
        nomeSitoWebTextField.setVisible(false);
        tipoSitoWebLabel.setVisible(false);
        tipoSitoWebTextField.setVisible(false);
        //tipo podcast
        numeroEpisodioPodcastLabel.setVisible(false);
        numeroEpisodioPodcastTextField.setVisible(false);
        //tipo tesi
        tipoTesiLabel.setVisible(false);
        tipoTesiTextField.setVisible(false);
        nomeUniversitàLabel.setVisible(false);
        nomeUniversitàTextField.setVisible(false);
        //tipo intervista
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

    public void riempiTabella(ArrayList<Riferimento> riferimenti) {
        DefaultTableModel model = (DefaultTableModel) riferimentiTable.getModel();
        model.setRowCount(0);
        riferimentiTable.setDefaultEditor(Object.class, null); //rende la tabella non editabile
        for (Riferimento r : riferimenti) {
            model.addRow(new Object[]{r.getTitolo(), r.getTipo(), r.getData(), r.getLingua()});
        }
    }

}
