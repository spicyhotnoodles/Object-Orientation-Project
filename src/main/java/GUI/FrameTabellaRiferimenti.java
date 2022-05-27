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
        for (Categoria cat: c.getCategorie())
            categoriaLM.addElement(cat.getNome());
        for (String tag: c.getTags())
            tagLM.addElement(tag);
        riempiTabella();
        tableRiferimenti.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getClickCount() >= 2) {
                    for (Riferimento r : c.getRiferimenti()) {
                        if (tableRiferimenti.getValueAt(tableRiferimenti.getSelectedRow(), 0).equals(r.getTitolo())) {
                            c.mostraInformazioniRiferimento(r, (DefaultTableModel) tableRiferimenti.getModel(), tableRiferimenti.getSelectedRow());
                        }
                    }
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
        nuovaCatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    c.mostraCreazioneCategoria(categoriaLM);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        eliminaCatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (categoriaList.getSelectedIndex() == -1)
                    JOptionPane.showMessageDialog(mainPanel, "Selezionare l'oggetto da eliminare!", "Errore!", JOptionPane.ERROR_MESSAGE);
                else {
                    if (JOptionPane.showConfirmDialog(mainPanel, "Eliminando la seguente categoria, verrano (eventulmente) eliminate tutte le rispettive sottocategorie," +
                            "\ninoltre tutti i riferimenti categorizzati non apparterrano più ad essa. Procedere? ") == JOptionPane.YES_OPTION) {
                        try {
                            c.eliminaCategoria(c.getCategorie().get(categoriaList.getSelectedIndex()).getCodice());
                            JOptionPane.showMessageDialog(mainPanel, "Categoria eliminata", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                            categoriaLM.remove(categoriaList.getSelectedIndex());
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
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
        nuovoRiferimentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                c.mostraCreazioneRiferimento((DefaultTableModel) tableRiferimenti.getModel());
            }
        });
        eliminaRiferimentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (tableRiferimenti.getSelectedRow() == -1)
                    JOptionPane.showMessageDialog(mainPanel, "Selezionare l'oggetto da eliminare!", "Errore!", JOptionPane.ERROR_MESSAGE);
                else {
                    if (JOptionPane.showConfirmDialog(mainPanel, "Eliminare il riferimento selezionato?") == JOptionPane.YES_OPTION) {
                        try {
                            for (Riferimento r : c.getRiferimenti()) {
                                if (tableRiferimenti.getValueAt(tableRiferimenti.getSelectedRow(), 0).equals(r.getTitolo())) {
                                    c.eliminaRiferimento(r.getCodice());
                                    JOptionPane.showMessageDialog(mainPanel, "Riferimento eliminato", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                                    DefaultTableModel model = (DefaultTableModel) tableRiferimenti.getModel();
                                    model.removeRow(tableRiferimenti.getSelectedRow());
                                    break;
                                }
                            }

                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                        }
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
                    tagLM.addElement(tag);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        eliminaTagButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (JOptionPane.showConfirmDialog(mainPanel, "Eliminare il tag selezionato?") == JOptionPane.YES_OPTION) {
                    try {
                        c.eliminaTag(tagList.getSelectedValue().toString());
                        JOptionPane.showMessageDialog(mainPanel, "Tag eliminato", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                        tagLM.remove(tagList.getSelectedIndex());
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void creaTabellaRiferimenti() {
        tableRiferimenti.setModel(new DefaultTableModel(null, new String[]{"Titolo", "Autori", "Tipologia", "Anno", "Lingua", "Rimandi", "Tags", "Categorie"}));
        tableRiferimenti.setAutoCreateRowSorter(true);
        TableColumnModel tcm = tableRiferimenti.getColumnModel();
        tcm.removeColumn(tcm.getColumn(7));
        tcm.removeColumn(tcm.getColumn(6));
    }

    public void riempiTabella() {
        DefaultTableModel model = (DefaultTableModel) tableRiferimenti.getModel();
        model.setRowCount(0);
        tableRiferimenti.setDefaultEditor(Object.class, null); //rende la tabella non editabile
        for (Riferimento r : c.getRiferimenti()) {
            String autori = "";
            String tags = "";
            String categorie = "";
            for (String autore: r.getAutori())
                autori = autori + autore + "; ";
            if (r.getCategorie() != null)
                for (Categoria categoria: r.getCategorie())
                    categorie = categorie + categoria.getNome() + "; ";
            if (r.getTags() != null)
                for (String tag: r.getTags())
                    tags = tags + r.getTags() + "; ";
            model.addRow(new Object[]{r.getTitolo(), autori, r.getTipo(), r.getData(), r.getLingua(), r.getRimandi(), tags, categorie});
        }
    }
}
