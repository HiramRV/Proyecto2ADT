import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;

import javax.swing.ImageIcon;

import javax.swing.JTextField;

import javax.swing.JButton;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.io.File;

import java.util.Vector;

import org.neo4j.graphdb.GraphDatabaseService;

import org.neo4j.graphdb.Node;

import org.neo4j.graphdb.Transaction;

import org.neo4j.graphdb.factory.GraphDatabaseFactory;

 

public class GUI extends JFrame {

      

       private JPanel contentPane;

       private JTextField txtUsuario;

       private JTextField txtRecomendacion;

       private JButton btnSi;

       private JButton btnSalir;

       private JButton btnNo;

       private JButton btnotra;

       private JButton btnComenzar;



       /**

       * Launch the application.

       */

       public static void main(String[] args) {
    	   
           GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();

           File file= new File("C:/Users/AndreaMaybell/Documents/AMPE/deberes/Algoritmos y Estructuras de Datos/Proyecto2/BP3");

           GraphDatabaseService db= dbFactory.newEmbeddedDatabase(file);

           Node usuario= null;
           Node pelicula= null;
           Funciones funciones= new Funciones();
           Vector<Integer> pelisId= funciones.IdsPeliculas(db);
           //Vector<Node> pelisVistas= funciones.actualizacionPerfilUsuario(db,usuario);
           Vector<Node> pelisVistas= new Vector<Node>();
           

             EventQueue.invokeLater(new Runnable() {

                    public void run() {

                          try {
                        	  try (Transaction tx = db.beginTx()) {

                                 GUI frame = new GUI(db, usuario, pelicula, funciones, pelisId, pelisVistas);

                                 frame.setVisible(true);
                                 
                                 tx.success();//se cierra la base de datos

                          }} catch (Exception e) {

                                 e.printStackTrace();

                          }

                          }

             });


       }

 

       /**

       * Create the frame.

       */

       public GUI(GraphDatabaseService db, Node usuario, Node pelicula, Funciones funciones, Vector<Integer> pelisId, Vector<Node> pelisVistas) {

            

      

            

             setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

             setBounds(100, 100, 664, 417);

             contentPane = new JPanel();

             contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

             setContentPane(contentPane);

             contentPane.setLayout(null);

            

             JPanel panel = new JPanel();

             panel.setForeground(new Color(0, 0, 0));

             panel.setBackground(new Color(0, 0, 0));

             panel.setBounds(10, 11, 628, 107);

             contentPane.add(panel);

             panel.setLayout(null);

            

             JLabel lblNewLabel = new JLabel("New label");

             //lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Gladys Herrera\\Desktop\\UVG\\cuarto semestre\\Estructura de Datos\\ADTP2\\Proyecto2\\imagen\\prototipo.PNG"));

             lblNewLabel.setBounds(20, 11, 598, 85);

             lblNewLabel.setForeground(Color.WHITE);

             panel.add(lblNewLabel);

            

             JPanel panel_1 = new JPanel();

             panel_1.setBackground(Color.RED);

             panel_1.setBounds(10, 117, 628, 250);

             contentPane.add(panel_1);

             panel_1.setLayout(null);

            

             JLabel lblNewLabel_1 = new JLabel("Ingrese su nombre de usuario:");

             lblNewLabel_1.setBounds(10, 11, 161, 14);

             panel_1.add(lblNewLabel_1);

            

             JLabel lblNewLabel_2 = new JLabel("Pelicula recomendada:");

             lblNewLabel_2.setBounds(10, 49, 161, 14);

             panel_1.add(lblNewLabel_2);

            

             txtUsuario = new JTextField();

             txtUsuario.setBounds(181, 8, 86, 20);

             panel_1.add(txtUsuario);

             txtUsuario.setColumns(10);

            

             txtRecomendacion = new JTextField();

             txtRecomendacion.setBounds(181, 46, 86, 20);

             panel_1.add(txtRecomendacion);

             txtRecomendacion.setColumns(10);

            

             //try (Transaction tx = db.beginTx()) {

             String nombre= txtUsuario.getText();

             usuario = funciones.ingreso(db, nombre);

             //pelisId= funciones.IdsPeliculas(db);

            

             btnComenzar = new JButton("Comenzar");

             btnComenzar.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                    	try (Transaction tx = db.beginTx()) {

                          Node usuario= funciones.ingreso(db, nombre);

                          Vector<Integer> pelisId= funciones.IdsPeliculas(db);
                          
                          Vector<Node> pelisVistas= funciones.actualizacionPerfilUsuario(db,usuario); 
                          Node pelicula= null;

                          if (pelisVistas.size()>0)
                          {
                        	  pelicula= funciones.recomendacion(db,pelisVistas,pelisId,usuario);
                          }
                          else
                          {
                        	  pelicula= funciones.recomendacionRandom(db, pelisId);
                          }
                          String titulo= (String) pelicula.getProperty("nombre");

                          txtRecomendacion.setText(titulo);

                          tx.success();
                    	}}

                    }

             );

             btnComenzar.setBounds(308, 7, 89, 23);

             panel_1.add(btnComenzar);

            

             JLabel lblleGustoLa = new JLabel("\u00BFLe gusto la recomendacion?");

             lblleGustoLa.setBounds(10, 103, 222, 14);

             panel_1.add(lblleGustoLa);

            

             btnSi = new JButton("SI");

             btnSi.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                    	
                    	try (Transaction tx = db.beginTx()) {
                    		
                    	
                    	  Vector<Integer> pelisId= funciones.IdsPeliculas(db);
                    	  
                          Node usuario= funciones.ingreso(db, nombre);
                          
                          Node pelicula= db.findNode(Etiquetas.Pelicula, "nombre", txtRecomendacion.getText());
                          
                          usuario.createRelationshipTo(pelicula, Relaciones.Vio);

                          Vector<Node> pelisVistas= funciones.actualizacionPerfilUsuario(db,usuario);  

                          pelicula= funciones.recomendacion(db,pelisVistas,pelisId,usuario);

                          String titulo= (String) pelicula.getProperty("nombre");

                          txtRecomendacion.setText(titulo);

                          	tx.success();}
                    }

             });

             btnSi.setBounds(10, 146, 58, 23);

             panel_1.add(btnSi);

            

             btnNo = new JButton("NO :(");

             btnNo.setBounds(78, 146, 62, 23);

             panel_1.add(btnNo);

            

             JLabel lblotraRecomendacion = new JLabel("\u00BFOtra recomendacion?");

             lblotraRecomendacion.setBounds(308, 103, 197, 14);

             panel_1.add(lblotraRecomendacion);

            

             btnotra = new JButton("SI");

             btnotra.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                    	
                    	try (Transaction tx = db.beginTx()) {

                          String nombre= txtUsuario.getText();

                          Node usuario= funciones.ingreso(db, nombre);

                          Vector<Node> pelisVistas= funciones.actualizacionPerfilUsuario(db,usuario);  

                          Vector<Integer> pelisId= funciones.IdsPeliculas(db);
                          
                          Node pelicula = null;

                          pelicula= funciones.recomendacion(db,pelisVistas,pelisId,usuario);

                          String titulo= (String) pelicula.getProperty("nombre");

                          txtRecomendacion.setText(titulo);
                          
                          tx.success();}

                    }

             });

             btnotra.setBounds(335, 146, 48, 23);

             panel_1.add(btnotra);

            

             btnSalir = new JButton("SALIR ");

             btnSalir.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                          setVisible(false);

                          GUIAdios nuevaVentana1 = new GUIAdios();

                          nuevaVentana1.setVisible(true);

                    }

             });

             btnSalir.setBounds(490, 205, 98, 23);

             panel_1.add(btnSalir);

             //tx.success();//cierre database
             //}
             
             
       }
       		
       }



