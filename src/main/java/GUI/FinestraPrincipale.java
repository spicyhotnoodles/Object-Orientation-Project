package GUI;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.intellij.uiDesigner.core.GridConstraints;

import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;

public class FinestraPrincipale extends JFrame {

    private Controller theController;
    private JPanel leftPanel;
    private JPanel centerPanel;
    private JPanel rightPanel;
    private JTable refTable;
    private JTabbedPane tabbedPane;
    private JTree categoryTree;
    private JButton createCatButton;
    private JButton deleteCatButton;
    private FontIcon folderIcon; //icona del pulsante per la creazione di una (sotto)categoria
    private FontIcon canIcon; //icona del pulsante per la cancellazione di una (sotto)categoria
    private FontIcon searchIcon; //icona del pulsante per la ricerca dei riferimenti
    private FontIcon addIcon; //icona del pulsante per l'aggiunta dei riferimenti
    private FontIcon deleteIcon; //icona del pulsante per la cancellazione dei riferimenti
    private FontIcon addAuthorIcon; //icona del pulsante per l'aggiunta di un autore
    private FontIcon removeAuthorIcon; //icona del pulsante per la cancellazione di un autore
    private JPanel mainPanel;
    private JButton addRefButton;
    private JButton deleteRefButton;
    private JTextField searchTextField;
    private JButton searchButton;
    private JScrollPane tableScrollPane;
    private JList tagList;
    private JScrollPane tagScrollPane;
    private JLabel tagLabel;
    private JTextField titleTF;
    private JLabel titleLabel;
    private JTextField authorTF;
    private JLabel authorLabel;
    private JComboBox typeComboBox;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField isbnTF;
    private JTextField doiTF;
    private JLabel isbnLabel;
    private JLabel doiLabel;
    private JButton removeAuthorButton;
    private JButton addAuthorButton;
    private JPanel infoPanel;
    // private String[][] rowData;
    // private String[] columnName = {"Titolo"};
    private List<JLabel> jLabelList = new ArrayList<JLabel>();
    private List<JTextField> textFieldList = new ArrayList<JTextField>();
    private int i = 0; //variabile contatore per l'aggiunta o la rimozione degli autori


    public FinestraPrincipale(String title, Controller c) throws SQLException, IOException {

        super(title);
        theController = c;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setBounds(650, 250, 700, 500);
        createRefTable();
        //refTable = new JTable(rowData, columnName);

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
        searchButton.setIcon(searchIcon);
        addAuthorButton.setIcon(addAuthorIcon);
        removeAuthorButton.setIcon(removeAuthorIcon);

        //infoPanel.setLayout(new GridLayout(0, 2));
        isbnLabel.setVisible(false);
        isbnTF.setVisible(false);
        doiLabel.setVisible(false);
        doiTF.setVisible(false);

        //action listners:

        //action listner per il pulsante di creazione di una (sotto)categoria
        createCatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        //action listner per la combobox del tipo di riferimento
        typeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (typeComboBox.getSelectedItem().equals("Libro")) {
                    isbnLabel.setVisible(true);
                    isbnTF.setVisible(true);
                    doiLabel.setVisible(false);
                    doiTF.setVisible(false);
                } else if (typeComboBox.getSelectedItem().equals("Dataset")) {
                    isbnLabel.setVisible(false);
                    isbnTF.setVisible(false);
                    doiLabel.setVisible(true);
                    doiTF.setVisible(true);
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
    }

    private void createRefTable() {
        refTable.setModel(new DefaultTableModel(null, new String[]{"Titolo", "Autori", "Tipologia", "Anno", "Editore"}));
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayoutManager(5, 4, new Insets(10, 10, 10, 10), -1, -1));
        mainPanel.add(leftPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(317, 529), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        leftPanel.add(scrollPane1, new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 700), null, 0, false));
        categoryTree = new JTree();
        scrollPane1.setViewportView(categoryTree);
        createCatButton = new JButton();
        createCatButton.setText("");
        leftPanel.add(createCatButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        leftPanel.add(spacer1, new GridConstraints(0, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        tagScrollPane = new JScrollPane();
        leftPanel.add(tagScrollPane, new GridConstraints(4, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tagList = new JList();
        tagScrollPane.setViewportView(tagList);
        tagLabel = new JLabel();
        Font tagLabelFont = this.$$$getFont$$$(null, -1, 14, tagLabel.getFont());
        if (tagLabelFont != null) tagLabel.setFont(tagLabelFont);
        tagLabel.setText("Parole Chiave");
        leftPanel.add(tagLabel, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        leftPanel.add(spacer2, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        deleteCatButton = new JButton();
        deleteCatButton.setText("");
        leftPanel.add(deleteCatButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayoutManager(2, 5, new Insets(10, 5, 10, 5), -1, -1));
        mainPanel.add(centerPanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        addRefButton = new JButton();
        addRefButton.setText("");
        centerPanel.add(addRefButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(80, 30), null, 0, false));
        deleteRefButton = new JButton();
        deleteRefButton.setText("");
        centerPanel.add(deleteRefButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        centerPanel.add(spacer3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        searchTextField = new JTextField();
        centerPanel.add(searchTextField, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        searchButton = new JButton();
        searchButton.setText("");
        centerPanel.add(searchButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tableScrollPane = new JScrollPane();
        centerPanel.add(tableScrollPane, new GridConstraints(1, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        refTable = new JTable();
        tableScrollPane.setViewportView(refTable);
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1, true, true));
        mainPanel.add(rightPanel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(1);
        tabbedPane.setToolTipText("");
        rightPanel.add(tabbedPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(300, 200), null, 0, false));
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayoutManager(9, 5, new Insets(10, 10, 10, 10), -1, -1));
        infoPanel.setToolTipText("");
        tabbedPane.addTab("Informazioni", infoPanel);
        titleLabel = new JLabel();
        titleLabel.setText("Titolo");
        infoPanel.add(titleLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        infoPanel.add(spacer4, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        infoPanel.add(spacer5, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        titleTF = new JTextField();
        infoPanel.add(titleTF, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        authorLabel = new JLabel();
        authorLabel.setText("Autore");
        infoPanel.add(authorLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        authorTF = new JTextField();
        infoPanel.add(authorTF, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Tipo di oggetto");
        infoPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        typeComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("");
        defaultComboBoxModel1.addElement("Libro");
        defaultComboBoxModel1.addElement("Articolo Scientifico");
        defaultComboBoxModel1.addElement("Dataset");
        typeComboBox.setModel(defaultComboBoxModel1);
        infoPanel.add(typeComboBox, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        infoPanel.add(textField1, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Data");
        infoPanel.add(label2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Lingua");
        infoPanel.add(label3, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField2 = new JTextField();
        infoPanel.add(textField2, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textField3 = new JTextField();
        infoPanel.add(textField3, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("URL");
        infoPanel.add(label4, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        isbnLabel = new JLabel();
        isbnLabel.setText("ISBN");
        infoPanel.add(isbnLabel, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        isbnTF = new JTextField();
        isbnTF.setText("");
        infoPanel.add(isbnTF, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        doiLabel = new JLabel();
        doiLabel.setText("DOI");
        infoPanel.add(doiLabel, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        doiTF = new JTextField();
        infoPanel.add(doiTF, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        removeAuthorButton = new JButton();
        removeAuthorButton.setText("");
        infoPanel.add(removeAuthorButton, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addAuthorButton = new JButton();
        addAuthorButton.setText("");
        infoPanel.add(addAuthorButton, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("Note", panel1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("Tag", panel2);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("Collegamenti", panel3);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
