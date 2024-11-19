import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FormularioLibros extends JFrame {

    private JTable tablalibros;
    private JButton listarButton;
    private JButton nuevoButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JPanel panelLibros;
    private JTextField texttitulo;
    private JTextField textautor;
    private JTextField text_n;
    private JButton guardarButton;

    private JTextField text_c;
    private JTextField text_u;
    private JTextField text_a;
    private JTextField text_p;
    private JTextField text_ca;
    private JTextField text_e;


    String url="jdbc:mysql://localhost:3306/bd_biblioteca";
    String usuario_bd="root";
    String password_bd="";

    public FormularioLibros() {//Inicio del contructor no usages5
        setContentPane(panelLibros); // Agregarmos el panel principal
        setTitle("Formulario de Libros");//titulo del formulario
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//accion para cerrar la ventana
        setSize(700, 500);//centrado del del forrmilario
        setLocationRelativeTo(null);//centrado del formulario en pantalla
        setResizable(false);//para que el usuario no puede modificar
        setVisible(true);//para visualizar el formulario en la pantalla


        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection PruebaConexion = null;
                PruebaConexion = conexion();
                if(PruebaConexion!=null){
                    System.out.println("Conexion establecida");

                    Statement statement = null;//sirve para instrucciones
                    ResultSet resultado = null;//es para el conjumto de registros

                    String sql = "select * from libros";//seleciona rodos los registro de la tabla producto

                    try {
                        statement = PruebaConexion.createStatement();
                        resultado = statement.executeQuery(sql);

                        /*
                        while(resultado.next()){
                            String titulo = resultado.getString("titulo");
                            String autor = resultado.getString("autor");
                            int n_paginas = resultado.getInt("n_paginas");

                            System.out.println(titulo+"--"+autor+"--"+n_paginas);


                        }*/

                        //creamos un modelos en jttablet
                        DefaultTableModel modelo = new DefaultTableModel();
                        modelo.setColumnIdentifiers(new Object[]{"Id","Título", "Autor", "Números de Paginas","Cantidad","Ubicacion","Año","Editor","Pais","Categoria"});
                        modelo.setRowCount(0);

                        while (resultado.next()) {
                            Object[] fila = new Object[10];
                            fila[0] = resultado.getInt("id");
                            fila[1] = resultado.getString("titulo");
                            fila[2] = resultado.getString("autor");
                            fila[3] = resultado.getInt("n_paginas");
                            fila[4] = resultado.getInt("cantidad");
                            fila[5] = resultado.getString("ubicacion");
                            fila[6] = resultado.getString("anio");
                            fila[7] = resultado.getString("editor");
                            fila[8] = resultado.getString("pais");
                            fila[9] = resultado.getString("categoria");
                            modelo.addRow(fila);
                        }
                        tablalibros.setModel(modelo);

                    }catch (SQLException ex){

                        System.out.println(ex.getErrorCode());

                        System.out.println("Error al acceder a la tabla de libros ");
                    }


                }else {
                    System.out.println("Conexion no establecida");
                    JOptionPane.showMessageDialog(null, "Conexion no establecida");
                }
            }
        });


        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //primero nos conectamos a bd
                Connection Conexion = null;
                Conexion = conexion();
                if (Conexion != null) {
                    System.out.println("Correcto, la BD ha respondido");
                    String titulo = texttitulo.getText();
                    String autor = textautor.getText();
                    int pagina = Integer.parseInt(text_n.getText());

                    int cantidad = Integer.parseInt(text_c.getText());
                    String ubicacion = text_u.getText();
                    String anio = text_a.getText();
                    String editor = text_e.getText();
                    String pais = text_p.getText();
                    String categoria = text_ca.getText();


                    try {
                        String sql ="INSERT INTO libros(titulo,autor,n_paginas,  cantidad,ubicacion,anio,editor,pais,categoria) VALUES (?,?,?,?,?,?,?,?,?)";
                        PreparedStatement statement = Conexion.prepareStatement(sql);
                        statement.setString(1, titulo);
                        statement.setString(2, autor);
                        statement.setInt(3, pagina);

                        statement.setInt(4, cantidad);
                        statement.setString(5, ubicacion);
                        statement.setString(6, anio);
                        statement.setString(7, editor);
                        statement.setString(8, pais);
                        statement.setString(9, categoria);
                        statement.execute();
                        JOptionPane.showMessageDialog(null, "Biblioteca guardada correctamente");


                    }catch (SQLException ex){
                        System.out.println(ex.getMessage());
                        System.out.println("Error al guardar el libro");
                    }


                }else {
                    System.out.println("La BD No ha respondido");

                }
            }
        });//fi de boton de GUARDAR

        //Inicio del boton modificar
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int FilaSeleccionado = tablalibros.getSelectedRow();
                System.out.println("Seleccionado en la tabla de libros "+FilaSeleccionado);
                if(FilaSeleccionado==-1){
                    JOptionPane.showMessageDialog(null, "Seleccione un libro");
                }
                int id = Integer.parseInt(tablalibros.getValueAt(FilaSeleccionado,0).toString());
                String titulo = tablalibros.getValueAt(FilaSeleccionado,1).toString();
                String autor = tablalibros.getValueAt(FilaSeleccionado,2).toString();
                int paginas = Integer.parseInt(tablalibros.getValueAt(FilaSeleccionado,3).toString());
                int cantidad = Integer.parseInt(tablalibros.getValueAt(FilaSeleccionado,4).toString());
                String ubicacion = tablalibros.getValueAt(FilaSeleccionado,5).toString();
                String anio = tablalibros.getValueAt(FilaSeleccionado,6).toString();
                String editor = tablalibros.getValueAt(FilaSeleccionado,7).toString();
                String pais = tablalibros.getValueAt(FilaSeleccionado,8).toString();
                String categoria = tablalibros.getValueAt(FilaSeleccionado,9).toString();

                System.out.println(id+" | "+titulo+" | "+autor+" | "+paginas+" | "+cantidad+" | "+ubicacion+" | "+editor+" | "+pais+" | "+categoria);

                //debemos crear una neva vetana

                Connection Conexion_para_modificar = null;
                Conexion_para_modificar = conexion();

                DialogModifiacr Ventanita =new DialogModifiacr(id,titulo,autor,paginas,cantidad,ubicacion,anio,editor,pais,categoria,Conexion_para_modificar);
                Ventanita.setVisible(true);
            }
        });//fin del boton modificar

        //Inicio de boton eliminar
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection Conexion = null;
                Conexion = conexion();
                if (Conexion != null) {

                    //Mostramos un mensaje para iliminar
                    int confirmacion = JOptionPane.showOptionDialog(null,
                            "Estas seguro de querer eliminar el libro?",
                            "Cuidado!!",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            new Object[]{"Si, Eliminar","NO"},
                            "no");
                    if (confirmacion != JOptionPane.YES_OPTION) {
                        return;
                    }


                    int FilaSeleccionado = tablalibros.getSelectedRow();
                    System.out.println("Seleccionado en la tabla de libros "+FilaSeleccionado);
                    int idlibros = Integer.parseInt(tablalibros.getValueAt(FilaSeleccionado,0).toString());
                    try {
                        String sql ="DELETE FROM libros WHERE id = ?";
                        PreparedStatement statement = Conexion.prepareStatement(sql);
                        statement.setInt(1, idlibros);
                        statement.execute();
                        JOptionPane.showMessageDialog(null, "Libro eliminado correctamente");
                    }catch (Exception ex){
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });//Inicio de boton eliminar


        nuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }//fin del constructor
    public static void main(String[] args) {//Inicio del main
        new FormularioLibros();
    }//fin de main

    public Connection conexion(){
        Connection conex = null;

        try{
            conex= DriverManager.getConnection(url,usuario_bd,password_bd);
        }catch (SQLException e){
            System.out.println("Error al conectar con la base de datos");
        }
        return conex;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
