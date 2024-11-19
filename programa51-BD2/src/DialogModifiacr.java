import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DialogModifiacr extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField text_t;
    private JTextField text_a;
    private JTextField text_n;
    private JTextField text_i;
    private JTextField text_ct;
    private JTextField text_u;
    private JTextField text_an;
    private JTextField text_ed;
    private JTextField text_pa;
    private JTextField text_ca;

    public DialogModifiacr(int id, String titulo, String autor, int paginas, int cantidad,String ubicacion,String anio,String editor,String pais,String categoria, Connection conex) {
        setTitle("MODIFICAR");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(400,600);
        setLocationRelativeTo(null);

        //colocamos los parametros resividos en los cuadros de texto
        text_i.setText(String.valueOf(id));
        text_t.setText(autor);
        text_a.setText(titulo);
        text_n.setText(String.valueOf(paginas));
        text_ct.setText(String.valueOf(cantidad));
        text_u.setText(ubicacion);
        text_an.setText(anio);
        text_ed.setText(editor);
        text_pa.setText(pais);
        text_ca.setText(categoria);


        System.out.println("id de producto recibido : "+id);



        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql="UPDATE libros SET titulo=?,autor=?,n_paginas=?, cantidad=?,ubicacion=?,anio=?,editor=?,pais=?,categoria=? WHERE id=?";
                    PreparedStatement pst=conex.prepareStatement(sql);
                    pst.setInt(1,Integer.parseInt(text_i.getText()));
                    pst.setString(2,text_t.getText());
                    pst.setString(3,text_a.getText());
                    pst.setInt(4,Integer.parseInt(text_n.getText()));
                    pst.setInt(5,Integer.parseInt(text_ct.getText()));
                    pst.setString(6,text_u.getText());
                    pst.setString(7,text_an.getText());
                    pst.setString(8,text_ed.getText());
                    pst.setString(9,text_pa.getText());
                    pst.setString(10,text_ca.getText());
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "MODIFICADO");
                    dispose();

                }catch (SQLException ex) {
                    System.out.println("Error: "+ex);
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /*private void onOK() {
        // add your code here
        dispose();
    }*/

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /*public static void main(String[] args) {
        DialogModifiacr dialog = new DialogModifiacr();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
