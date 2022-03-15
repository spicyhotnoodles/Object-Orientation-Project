package GUI;

import DBEntities.Categoria;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class FrameCreaCategoria extends JFrame {
    private JLabel nomeLabel;
    private JTextField nomeTextField;
    private JRadioButton sottoCategoriaRadioButton;
    private JComboBox supercategoriaComboBox;
    private JButton salvaButton;
    private JLabel supercategoriaLabel;
    private JPanel mainPanel;
    private Controller c;

    public FrameCreaCategoria(String titolo, Controller c) throws SQLException {
        super(titolo);
        this.c = c;
        this.setContentPane(mainPanel);
        this.pack();
        this.setBounds(800, 400, 400, 200);
        supercategoriaComboBox.setEnabled(false);
        if (c.getCategorie().isEmpty())
            sottoCategoriaRadioButton.setEnabled(false);
        else {
            for (Categoria cat : c.getCategorie())
                supercategoriaComboBox.addItem(cat.getNome());
        }
    }

    public void creaCategoria(DefaultListModel model, Categoria categoria) {
        sottoCategoriaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (sottoCategoriaRadioButton.isSelected())
                    supercategoriaComboBox.setEnabled(true);
                else
                    supercategoriaComboBox.setEnabled(false);
            }
        });
        salvaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String nome = nomeTextField.getText();
                if (nome.equals(""))
                    JOptionPane.showMessageDialog(mainPanel, "La categoria deve avere un nome!", "Errore", JOptionPane.ERROR_MESSAGE);
                else {
                    if (supercategoriaComboBox.isEnabled())
                        categoria.setSupercategoria((String) supercategoriaComboBox.getSelectedItem());
                    categoria.setNome(nome);
                    try {
                        c.creaCategoria(categoria);
                        JOptionPane.showMessageDialog(mainPanel, "Categoria creata con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                        model.addElement(categoria.getNome());
                        dispose();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(mainPanel, e, "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}
