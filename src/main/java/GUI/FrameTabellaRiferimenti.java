package GUI;

import DBEntities.Categoria;
import DBEntities.Riferimento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class FrameTabellaRiferimenti extends JFrame {
    private JPanel mainPanel;
    private JTable tableRiferimenti;
    private JTextField ricercaTextField;
    private JLabel ricercaLabel;
    private JScrollPane tableScrollPane;
    private JButton nuovoRiferimentoButton;
    private JButton eliminaRiferimentoButton;
    private JButton nuovaCatButton;
    private JButton eliminaCatButton;
    private JPanel ricercaPanel;
    private JList categoriaList;
    private JList tagList;
    private JButton nuovoTagButton;
    private JButton eliminaTagButton;
    private JLabel categoriaLabel;
    private JLabel tagLabel;
    private Controller c;
    private ArrayList<Riferimento> riferimenti = new ArrayList<>();
    private ArrayList<Categoria> categorie = new ArrayList<>();
    private ArrayList<String> tags = new ArrayList<>();
    private DefaultListModel categoriaLM = new DefaultListModel<>();
    private DefaultListModel tagLM = new DefaultListModel<>();


    public FrameTabellaRiferimenti(String titolo, Controller c) throws SQLException {
        super(titolo);
        this.c = c;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setBounds(650, 250, 700, 500);
        creaTabellaRiferimenti();
        categoriaList.setModel(categoriaLM);
        tagList.setModel(tagLM);
        riempiListaCategorie();
        riempiListaTags();
        riempiTabella();
        tableRiferimenti.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getClickCount() >= 2)
                    c.mostraInformazioniRiferimento(riferimenti.get(tableRiferimenti.getSelectedRow()));
            }
        });
        nuovaCatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    c.mostraCreazioneCategoria();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        ricercaTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String filtro = ricercaTextField.getText();
                TableRowSorter tr = new TableRowSorter(tableRiferimenti.getModel());
                tableRiferimenti.setRowSorter(tr);
                tr.setRowFilter(RowFilter.regexFilter("(?i)" + filtro));
            }
        });
        categoriaList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TableRowSorter tr = new TableRowSorter(tableRiferimenti.getModel());
                if (categoriaList.getSelectedIndex() != -1) {
                    tableRiferimenti.setRowSorter(tr);
                    tr.setRowFilter(RowFilter.regexFilter("(?i)" + categoriaList.getSelectedValue().toString()));
                }
                else {
                    tableRiferimenti.setRowSorter(tr);
                    tr.setRowFilter(RowFilter.regexFilter(""));
                }
            }
        });
        tagList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TableRowSorter tr = new TableRowSorter(tableRiferimenti.getModel());
                if (tagList.getSelectedIndex() != -1) {
                    tableRiferimenti.setRowSorter(tr);
                    tr.setRowFilter(RowFilter.regexFilter("(?i)" + tagList.getSelectedValue().toString()));
                }
                else {
                    tableRiferimenti.setRowSorter(tr);
                    tr.setRowFilter(RowFilter.regexFilter(""));
                }
            }
        });
        eliminaCatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (categoriaList.getSelectedIndex() == -1)
                    JOptionPane.showMessageDialog(mainPanel, "Selezionare l'oggetto da eliminare!", "Errore!", JOptionPane.ERROR_MESSAGE);
                else {
                    try {
                        c.eliminaCategoria(categorie.get(categoriaList.getSelectedIndex()).getCodice());
                        JOptionPane.showMessageDialog(mainPanel, "Categoria eliminata", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                        riempiListaCategorie();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        nuovoRiferimentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                c.mostraCreazioneRiferimento();
            }
        });
        eliminaRiferimentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (tableRiferimenti.getSelectedRow() == -1)
                    JOptionPane.showMessageDialog(mainPanel, "Selezionare l'oggetto da eliminare!", "Errore!", JOptionPane.ERROR_MESSAGE);
                else {
                    try {
                        c.eliminaRiferimento(riferimenti.get(tableRiferimenti.getSelectedRow()).getCodice());
                        JOptionPane.showMessageDialog(mainPanel, "Riferimento eliminato", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                        riempiTabella();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        nuovoTagButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String tag = JOptionPane.showInputDialog(mainPanel, "Inserisci un nuovo tag");
                try {
                    c.creaTag(tag);
                    JOptionPane.showMessageDialog(mainPanel, "Tag creato", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    riempiListaTags();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        eliminaTagButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    c.eliminaTag(tagList.getSelectedValue().toString());
                    JOptionPane.showMessageDialog(mainPanel, "Tag eliminato", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    riempiListaTags();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void creaTabellaRiferimenti() {
        tableRiferimenti.setModel(new DefaultTableModel(null, new String[]{"Titolo", "Autori", "Tipologia", "Anno", "Lingua", "Tags", "Categorie"}));
        TableColumnModel tcm = tableRiferimenti.getColumnModel();
        tcm.removeColumn(tcm.getColumn(5));
        tcm.removeColumn(tcm.getColumn(5));
    }

    public void riempiTabella() {
        riferimenti.clear();
        riferimenti = c.ottieniRiferimenti();
        DefaultTableModel model = (DefaultTableModel) tableRiferimenti.getModel();
        model.setRowCount(0);
        tableRiferimenti.setDefaultEditor(Object.class, null); //rende la tabella non editabile
        for (Riferimento r : riferimenti) {
            String autori = "";
            String tags = "";
            String categorie = "";
            for (String autore: r.getAutori())
                autori = autori + autore.toString() + "; ";
            for (Categoria categoria: r.getCategorie())
                categorie = categorie + categoria.getNome() + "; ";
            for (String tag: r.getTags())
                tags = tags + r.getTags() + "; ";
            model.addRow(new Object[]{r.getTitolo(), autori, r.getTipo(), r.getData(), r.getLingua(), tags, categorie});
        }
    }

    public void riempiListaCategorie() throws SQLException {
        categoriaLM.clear();
        categorie.clear();
        categorie = c.ottieniCategorie();
        for (Categoria cat: categorie)
            categoriaLM.addElement(cat.getNome());
    }

    public void riempiListaTags() throws SQLException {
        tagLM.clear();
        tags.clear();
        tags = c.ottieniTags();
        for (String tag: tags) {
            tagLM.addElement(tag);
        }
    }
}
