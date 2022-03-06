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

import DBEntities.Libro;
import DBEntities.Riferimento;
import DBEntities.Web;
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
                private JTree categoryTree; //albero che lista tutte le categorie definite dall'utente
                private JButton createCatButton;
                private JButton deleteCatButton;
                private JTextField categorySearchTextField;
                private JButton categorySearchButton;
            private JPanel centerPanel; //pannello centrale che contiene la tabella dei riferimenti presenti nel sistema
                //elementi del pannello centrale:
                private JScrollPane tableScrollPane;
                private JTable refTable; //tabella dei riferimenti
                private JButton addRefButton;
                private JButton deleteRefButton;
                private JTextField searchRefTextField;
                private JButton searchRefButton;
            private JPanel rightPanel; //pannello di destra che contiene il pannello tab
                //elementi del pannello di destra:
                private JTabbedPane tabbedPane; //pannello tab con cui è possibile visualizzare/modificare/aggiungere informazioni ai riferimenti
                    //elementi del pannello tab:
                    private JPanel infoPanel;
                        //elementi del pannello delle info:
                        private JTextField refTitleTextField;
                        private JLabel titleLabel;
                        private JLabel authorLabel;
                        private JComboBox refTypeComboBox;
                        private JTextField dateTextField;
                        private JTextField languageTextField;
                        private JTextField urlTextField;
                        private JTextField isbnTextField;
                        private JTextField doiTextField;
                        private JLabel isbnLabel;
                        private JLabel doiLabel;
                        private JButton removeAuthorButton;
                        private JButton addAuthorButton;
                        private JScrollPane descrptionScrollPane;
                        private JList authorList;
                        private JLabel urlLabel;
                        private JLabel dataLabel;
                        private JLabel languageLabel;
                        private JLabel refTypeLabel;
                        private JTextArea descriptionTextArea;
                        private JLabel descriptionLabel;
                        private JButton refSaveButton;
                        private JTextField bookSeriesTextField;
                        private JTextField pageNumberTextField;
                        private JTextField volumeTextField;
                        private JTextField webPageNameTextField;
                        private JTextField webPageTypeTextField;
                        private JTextField publicationTitleTextField;
                        private JTextField issnTextField;
                        private JTextField dossierNumberTextField;
                        private JTextField sectionTextField;
                        private JTextField conferenceTitleTextField;
                        private JTextField editionPlaceTextField;
                        private JTextField thesisTypeTextField;
                        private JTextField universityTextField;
                        private JTextField tagSearchTextField;
                        private JButton tagSearchButton;
                        private JTextField lawLenderTextField;
                        private JTextField lawNumberTextField;
                        private JTextField lawVolumeCodeTextField;
                        private JTextField legislativeBodyTextField;
                        private JTextField podcastTitleTextField;
                        private JTextField podcastSeriesTitleTextField;
                        private JTextField podcastEpisodeNumberTextField;
                        private JList directorList;
                        private JButton removeDirectorButton;
                        private JButton addDirectorButton;
                        private JList interviewGuestList;
                        private JButton addGuestButton;
                        private JButton removeGuestButton;
                        private JScrollPane categoryScrollPane;
                        private JLabel bookSeriesLabel;
                        private JLabel pageNumberLabel;
                        private JLabel volumeLabel;
                        private JLabel webPageTypeLabel;
                        private JLabel webPageNameLabel;
                        private JLabel publicationTitleLabel;
                        private JLabel issnLabel;
                        private JLabel dossierNumberLabel;
                        private JLabel conferenceTitleLabel;
                        private JLabel editionPlaceLabel;
                        private JLabel sectionLabel;
                        private JLabel thesisTypeLabel;
                        private JLabel universityLabel;
                        private JLabel lawLenderLabel;
                        private JLabel lawNumberLabel;
                        private JLabel lawCodeLabel;
                        private JTextField lawCodeTextField;
                        private JLabel lawVolumeCodeLabel;
                        private JLabel legislativeBodyLabel;
                        private JLabel podcastTitleLabel;
                        private JLabel podcastSeriesTitleLabel;
                        private JLabel podcastEpisodeNumberLabel;
                        private JLabel directorLabel;
                        private JLabel interviewGuestLabel;
                        private JTextField moviePublisherTextField;
                        private JTextField movieGenreTextField;
                        private JTextField movieLengthTextField;
                        private JLabel moviePublisherLabel;
                        private JLabel movieGenreLabel;
                        private JLabel movieLengthLabel;
                        private JList lawLenderList;
                        private JButton addLawLenderButton;
                        private JButton removeLawLenderButton;
                    private JPanel notesPanel;
                        //elementi del pannello delle note
                        private JTextArea notesTextArea;
                        private JButton saveNotesButton;
                        private JButton clearNotesButton;
                    private JPanel linkPanel;
                        //elementi del pannello dei collegamenti
                        private JLabel refLinkLabel;
                        private JButton addRefLinkButton;
                        private JList refLinkList;
                        private JButton deleteRefLinkButton;
                        private JButton changeRefLinkButton;
                    private JPanel tagPanel;
                        private JTextField addTagTextField;
                        private JButton addTagButton;
                        private JList addTagList;
                        private JButton deleteTagButton;
                        private JButton changeTagButton;
                        private JLabel addTagLabel;
    private JTextField bookTitleTextField;
    private JLabel bookTitleLabel;
    private JLabel newsTitleLabel;
    private JTextField newsTitleTextField;
    private JTextField interviewDistributionTextField;
    private JLabel interviewDistributionLabel;
    private JTextField isanTextField;
    private JLabel isanLabel;
    private JTextField thesisTitleTextField;
    private JLabel thesisTitleLabel;
    private JTextField movieTitletextField;
    private JLabel movieTitleLabel;


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
        createRefTable();
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
        createCatButton.setIcon(folderIcon);
        deleteCatButton.setIcon(canIcon);
        addRefButton.setIcon(addIcon);
        deleteRefButton.setIcon(deleteIcon);
        searchRefButton.setIcon(searchIcon);
        addAuthorButton.setIcon(addAuthorIcon);
        removeAuthorButton.setIcon(removeAuthorIcon);
        addDirectorButton.setIcon(addAuthorIcon);
        removeDirectorButton.setIcon(removeAuthorIcon);
        addLawLenderButton.setIcon(addAuthorIcon);
        removeLawLenderButton.setIcon(removeAuthorIcon);
        addGuestButton.setIcon(addAuthorIcon);
        removeGuestButton.setIcon(removeAuthorIcon);
        authorList.setBorder(new LineBorder(Color.GRAY));
        directorList.setBorder(new LineBorder(Color.GRAY));
        interviewGuestList.setBorder(new LineBorder(Color.GRAY));
        lawLenderList.setBorder(new LineBorder(Color.GRAY));
        //oscura tutti gli attributi
        nascondiAttributiDiscriminanti();
        riferimenti = theController.ottieniRiferimenti();
        //infoPanel.setVisible(false);
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
        createCatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        //action listner per la combobox del tipo di riferimento
        refTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (refTypeComboBox.getSelectedItem().equals("Libro")) {
                    nascondiAttributiDiscriminanti();
                    bookTitleLabel.setVisible(true);
                    bookTitleTextField.setVisible(true);
                    addAuthorButton.setVisible(true);
                    removeAuthorButton.setVisible(true);
                    authorList.setVisible(true);
                    authorLabel.setVisible(true);
                    authorLabel.setVisible(true);
                    isbnLabel.setVisible(true);
                    isbnTextField.setVisible(true);
                    volumeLabel.setVisible(true);
                    volumeTextField.setVisible(true);
                    pageNumberLabel.setVisible(true);
                    pageNumberTextField.setVisible(true);
                    bookSeriesLabel.setVisible(true);
                    bookSeriesTextField.setVisible(true);
                } else if (refTypeComboBox.getSelectedItem().equals("Atto di convegno")) {
                    nascondiAttributiDiscriminanti();
                    authorList.setVisible(true);
                    authorLabel.setVisible(true);
                    addAuthorButton.setVisible(true);
                    removeAuthorButton.setVisible(true);
                    doiLabel.setVisible(true);
                    doiTextField.setVisible(true);
                    conferenceTitleLabel.setVisible(true);
                    conferenceTitleTextField.setVisible(true);
                    editionPlaceLabel.setVisible(true);
                    editionPlaceTextField.setVisible(true);
                    isbnLabel.setVisible(true);
                    isbnTextField.setVisible(true);
                } else if (refTypeComboBox.getSelectedItem().equals("Articolo di rivista")) {
                    nascondiAttributiDiscriminanti();
                    authorLabel.setVisible(true);
                    authorList.setVisible(true);
                    addAuthorButton.setVisible(true);
                    removeAuthorButton.setVisible(true);
                    publicationTitleLabel.setVisible(true);
                    publicationTitleTextField.setVisible(true);
                    pageNumberLabel.setVisible(true);
                    pageNumberTextField.setVisible(true);
                    dossierNumberLabel.setVisible(true);
                    dossierNumberTextField.setVisible(true);
                } else if (refTypeComboBox.getSelectedItem().equals("Articolo di giornale")) {
                    nascondiAttributiDiscriminanti();
                    authorLabel.setVisible(true);
                    authorList.setVisible(true);
                    addAuthorButton.setVisible(true);
                    removeAuthorButton.setVisible(true);
                    newsTitleLabel.setVisible(true);
                    newsTitleTextField.setVisible(true);
                    sectionLabel.setVisible(true);
                    sectionTextField.setVisible(true);
                } else if (refTypeComboBox.getSelectedItem().equals("Legge")) {
                    nascondiAttributiDiscriminanti();
                    lawCodeLabel.setVisible(true);
                    lawCodeTextField.setVisible(true);
                    lawNumberLabel.setVisible(true);
                    lawNumberTextField.setVisible(true);
                    lawLenderLabel.setVisible(true);
                    lawLenderList.setVisible(true);
                    addLawLenderButton.setVisible(true);
                    removeLawLenderButton.setVisible(true);
                } else if (refTypeComboBox.getSelectedItem().equals("Tesi")) {
                    nascondiAttributiDiscriminanti();
                    authorList.setVisible(true);
                    authorLabel.setVisible(true);
                    addAuthorButton.setVisible(true);
                    removeAuthorButton.setVisible(true);
                    doiLabel.setVisible(true);
                    doiTextField.setVisible(true);
                    universityLabel.setVisible(true);
                    universityTextField.setVisible(true);
                    thesisTypeLabel.setVisible(true);
                    thesisTypeTextField.setVisible(true);
                    thesisTitleLabel.setVisible(true);
                    thesisTitleTextField.setVisible(true);
                } else if (refTypeComboBox.getSelectedItem().equals("Sito web")) {
                    nascondiAttributiDiscriminanti();
                    authorLabel.setVisible(true);
                    authorList.setVisible(true);
                    addAuthorButton.setVisible(true);
                    removeAuthorButton.setVisible(true);
                    webPageNameLabel.setVisible(true);
                    webPageNameTextField.setVisible(true);
                    webPageTypeLabel.setVisible(true);
                    webPageTypeTextField.setVisible(true);
                }
                else if (refTypeComboBox.getSelectedItem().equals("Intervista")) {
                    nascondiAttributiDiscriminanti();
                    interviewGuestLabel.setVisible(true);
                    interviewGuestList.setVisible(true);
                    addGuestButton.setVisible(true);
                    removeGuestButton.setVisible(true);
                    interviewDistributionLabel.setVisible(true);
                    interviewDistributionTextField.setVisible(true);
                }
                else if (refTypeComboBox.getSelectedItem().equals("Podcast")) {
                    nascondiAttributiDiscriminanti();
                    authorLabel.setVisible(true);
                    authorList.setVisible(true);
                    addAuthorButton.setVisible(true);
                    removeAuthorButton.setVisible(true);
                    doiLabel.setVisible(true);
                    doiTextField.setVisible(true);
                    podcastEpisodeNumberLabel.setVisible(true);
                    podcastEpisodeNumberTextField.setVisible(true);
                    podcastSeriesTitleLabel.setVisible(true);
                    podcastSeriesTitleTextField.setVisible(true);
                }
                else if (refTypeComboBox.getSelectedItem().equals("Film")) {
                    nascondiAttributiDiscriminanti();
                    isanLabel.setVisible(true);
                    isanTextField.setVisible(true);
                    movieGenreLabel.setVisible(true);
                    movieGenreTextField.setVisible(true);
                    moviePublisherLabel.setVisible(true);
                    moviePublisherTextField.setVisible(true);
                    movieTitleLabel.setVisible(true);
                    movieTitletextField.setVisible(true);
                    addDirectorButton.setVisible(true);
                    removeDirectorButton.setVisible(true);
                    directorLabel.setVisible(true);
                    directorList.setVisible(true);
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
        refTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //riferimenti.get(0).toString();
                //Riferimento r = riferimenti.get(refTable.getModel().getValueAt(refTable.getSelectedRow()));
                System.out.println(riferimenti.get(refTable.getSelectedRow()));
                if (riferimenti.get(refTable.getSelectedRow()) instanceof Libro) {
                    Libro l = (Libro) riferimenti.get(refTable.getSelectedRow());
                    l.toString();
                    //titleLabel.setText("LIBROOOOOO");
                }

            }
        });
    }

    private void createRefTable() {
        refTable.setModel(new DefaultTableModel(null, new String[]{"Titolo", "Tipologia", "Anno", "Lingua"}));
    }

    private void nascondiAttributiDiscriminanti() {
        //tipo libro
        bookTitleLabel.setVisible(false);
        bookTitleTextField.setVisible(false);
        authorList.setVisible(false);
        authorLabel.setVisible(false);
        addAuthorButton.setVisible(false);
        removeAuthorButton.setVisible(false);
        isbnLabel.setVisible(false);
        isbnTextField.setVisible(false);
        volumeLabel.setVisible(false);
        volumeTextField.setVisible(false);
        pageNumberLabel.setVisible(false);
        pageNumberTextField.setVisible(false);
        bookSeriesLabel.setVisible(false);
        bookSeriesTextField.setVisible(false);
        //tipo articolo di giornale
        publicationTitleLabel.setVisible(false);
        publicationTitleTextField.setVisible(false);
        sectionLabel.setVisible(false);
        sectionTextField.setVisible(false);
        issnLabel.setVisible(false);
        issnTextField.setVisible(false);
        newsTitleLabel.setVisible(false);
        newsTitleTextField.setVisible(false);
        //tipo articolo di rivista
        publicationTitleLabel.setVisible(false);
        publicationTitleTextField.setVisible(false);
        dossierNumberLabel.setVisible(false);
        dossierNumberTextField.setVisible(false);
        //tipo atto di convengo
        doiLabel.setVisible(false);
        doiTextField.setVisible(false);
        conferenceTitleLabel.setVisible(false);
        conferenceTitleTextField.setVisible(false);
        editionPlaceLabel.setVisible(false);
        editionPlaceTextField.setVisible(false);
        //tipo film
        directorLabel.setVisible(false);
        directorList.setVisible(false);
        addDirectorButton.setVisible(false);
        removeDirectorButton.setVisible(false);
        movieGenreLabel.setVisible(false);
        movieGenreTextField.setVisible(false);
        moviePublisherLabel.setVisible(false);
        moviePublisherTextField.setVisible(false);
        isanLabel.setVisible(false);
        isanTextField.setVisible(false);
        movieTitleLabel.setVisible(false);
        movieTitletextField.setVisible(false);
        //tipo legge
        lawCodeLabel.setVisible(false);
        lawCodeTextField.setVisible(false);
        lawLenderLabel.setVisible(false);
        lawLenderList.setVisible(false);
        addLawLenderButton.setVisible(false);
        removeLawLenderButton.setVisible(false);
        lawNumberLabel.setVisible(false);
        lawNumberTextField.setVisible(false);
        //tipo pagina web
        webPageNameLabel.setVisible(false);
        webPageNameTextField.setVisible(false);
        webPageTypeLabel.setVisible(false);
        webPageTypeTextField.setVisible(false);
        //tipo podcast
        podcastSeriesTitleLabel.setVisible(false);
        podcastSeriesTitleTextField.setVisible(false);
        podcastEpisodeNumberLabel.setVisible(false);
        podcastEpisodeNumberTextField.setVisible(false);
        //tipo tesi
        thesisTypeLabel.setVisible(false);
        thesisTypeTextField.setVisible(false);
        universityLabel.setVisible(false);
        universityTextField.setVisible(false);
        thesisTitleLabel.setVisible(false);
        thesisTitleTextField.setVisible(false);
        //tipo intervista
        interviewGuestLabel.setVisible(false);
        interviewGuestList.setVisible(false);
        addGuestButton.setVisible(false);
        removeGuestButton.setVisible(false);
        interviewDistributionLabel.setVisible(false);
        interviewDistributionTextField.setVisible(false);
    }

    public void riempiTabella(ArrayList<Riferimento> riferimenti) {
        DefaultTableModel model = (DefaultTableModel)refTable.getModel();
        model.setRowCount(0);
        for (Riferimento r:riferimenti) {
            System.out.println(r.getTitolo());
            model.addRow(new Object[]{r.getTitolo(), r.getTipo(), r.getData(), r.getLingua()});
        }
    }

}
