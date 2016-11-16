import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class GUIAdmin extends JFrame {

	private JPanel contentPane;
	private JTextField txtTitulo;
	private JTextField txtGenero1;
	private JTextField txtDirector1;
	private JTextField txtAño;
	private JTextField txtLugar;
	private JTextField txtActor1;
	private JTextField txtActor2;
	private JTextField txtActor3;
	private JTextField txtDirector2;
	private JTextField txtGenero2;
	private JTextField txtSaga;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		   GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
           File file= new File("C:/Users/Gladys Herrera/Desktop/UVG/cuarto semestre/Estructura de Datos/ADTP2/BP3");
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
                                 GUIAdmin frame = new GUIAdmin(db, usuario, pelicula, funciones, pelisId, pelisVistas);
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
	public GUIAdmin(GraphDatabaseService db, Node usuario, Node pelicula, Funciones funciones, Vector<Integer> pelisId, Vector<Node> pelisVistas) {
		
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 468, 433);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.RED);
		panel.setBounds(0, 86, 452, 308);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("T\u00EDtulo pel\u00EDcula:");
		lblNewLabel_1.setBounds(10, 11, 104, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_4 = new JLabel("Genero:");
		lblNewLabel_4.setBounds(10, 74, 118, 14);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_6 = new JLabel("Director:");
		lblNewLabel_6.setBounds(10, 112, 46, 14);
		panel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("A\u00F1o:");
		lblNewLabel_7.setBounds(10, 150, 46, 14);
		panel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Saga:");
		lblNewLabel_8.setBounds(10, 186, 46, 14);
		panel.add(lblNewLabel_8);
		
		JLabel lblLugar = new JLabel("Lugar:");
		lblLugar.setBounds(10, 229, 46, 14);
		panel.add(lblLugar);
		
		txtTitulo = new JTextField();
		txtTitulo.setBounds(167, 8, 86, 20);
		panel.add(txtTitulo);
		txtTitulo.setColumns(10);
		
		txtGenero1 = new JTextField();
		txtGenero1.setBounds(167, 71, 86, 20);
		panel.add(txtGenero1);
		txtGenero1.setColumns(10);
		
		txtDirector1 = new JTextField();
		txtDirector1.setBounds(167, 109, 86, 20);
		panel.add(txtDirector1);
		txtDirector1.setColumns(10);
		
		txtAño = new JTextField();
		txtAño.setBounds(167, 147, 86, 20);
		panel.add(txtAño);
		txtAño.setColumns(10);
		
		txtLugar = new JTextField();
		txtLugar.setBounds(167, 226, 86, 20);
		panel.add(txtLugar);
		txtLugar.setColumns(10);
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Transaction tx = db.beginTx()) {
						if (camposVacios()){
							
							if (txtTitulo.getText().equals("")){
							txtTitulo.setBackground(Color.YELLOW);}
							if (txtGenero1.getText().equals("")){
							txtGenero1.setBackground(Color.YELLOW);}
							if (txtDirector1.getText().equals("")){
							txtDirector1.setBackground(Color.YELLOW);}
							if (txtAño.getText().equals("")){
							txtAño.setBackground(Color.YELLOW);}
							if (txtLugar.getText().equals("")){
							txtLugar.setBackground(Color.YELLOW);}
							if (txtActor1.getText().equals("")){
							txtActor1.setBackground(Color.YELLOW);}
							if (txtSaga.getText().equals("")){
							txtSaga.setBackground(Color.YELLOW);}
							
							JOptionPane.showMessageDialog(null, "Verifique que todos los campos resaltados esten llenos", "Verificar informacion inresada", 0);
							
							txtTitulo.setBackground(Color.white);
							txtGenero1.setBackground(Color.white);
							txtDirector1.setBackground(Color.white);
							txtAño.setBackground(Color.white);
							txtLugar.setBackground(Color.white);
							txtActor1.setBackground(Color.white);
							txtSaga.setBackground(Color.white);
							
						}
						else{
						
					
				String tp = txtTitulo.getText();
				String A1 = txtActor1.getText();
				String A2 = txtActor2.getText();
				String A3 = txtActor3.getText();
				Vector<String> nombresA = new Vector<String>();	
				nombresA.addElement(A1);
				nombresA.addElement(A2);
				nombresA.addElement(A3);
				
				String G1 = txtGenero1.getText();
				String G2 = txtGenero2.getText();
				Vector<String> generos = new Vector<String>();
				generos.addElement(G1);
				generos.addElement(G2);
				
				String D1 = txtDirector1.getText();
				String D2 = txtDirector2.getText();
				Vector<String> nombresD = new Vector<String>();
				nombresD.addElement(D1);
				nombresD.addElement(D2);
				
				String an = txtAño.getText();
				String lp = txtLugar.getText();
				String sp = txtSaga.getText();
				String ss= "No";
				
				Node n1= db.findNode(Etiquetas.Pelicula, "nombre", tp);
				if (n1==null)
				{
					n1= db.createNode(Etiquetas.Pelicula);
					n1.setProperty("nombre", tp);
				}
				
				Node n3= db.findNode(Etiquetas.Lugar, "nombreL", lp);
				if (n3==null)
				{
					n3= db.createNode(Etiquetas.Lugar);
					n3.setProperty("nombreL", lp);
				}
				n1.createRelationshipTo(n3, Relaciones.Ocurre_en);
				Node n4= db.findNode(Etiquetas.Año, "fecha", an);
				if (n4==null)
				{
					n4= db.createNode(Etiquetas.Año);
					n4.setProperty("fecha", an);
				}
				n1.createRelationshipTo(n4, Relaciones.Lanzada_en);
				
				Node n5= db.findNode(Etiquetas.Saga, "nombreS", ss);
				if (n5==null)
				{
					n5= db.createNode(Etiquetas.Saga);
					n5.setProperty("nombreS", ss);
				}
				n1.createRelationshipTo(n5, Relaciones.Saga_);
				
				for (int k=0; k<1; k++)
				{
					String a= generos.get(k);
					Node n2= db.findNode(Etiquetas.Genero, "genero", a);
					if (n2==null)
					{
						n2= db.createNode(Etiquetas.Genero);
						n2.setProperty("genero", a);
					}
					n1.createRelationshipTo(n2, Relaciones.Del_genero);
				}
				
				for (int k=0; k<2; k++)
				{
					String a= nombresA.get(k);
					Node n6= db.findNode(Etiquetas.Actor, "nombreA", a);
					if (n6==null)
					{
						n6= db.createNode(Etiquetas.Actor);
						n6.setProperty("nombreA", a);
					}
					n1.createRelationshipTo(n6, Relaciones.Actor_principal);
				}
				for (int l=0; l<1; l++)
				{
					String a= nombresD.get(l);
					Node n7= db.findNode(Etiquetas.Director, "nombreD", a);
					if (n7==null)
					{
						n7= db.createNode(Etiquetas.Director);
						n7.setProperty("nombreD", a);
					}
					n1.createRelationshipTo(n7, Relaciones.Dirigida_por);
				}
				
				
				txtTitulo.setText("");
				txtGenero1.setText("");
				txtDirector1.setText("");
				txtAño.setText("");
				txtLugar.setText("");
				txtActor1.setText("");
				txtActor2.setText("");
				txtActor3.setText("");
				txtDirector2.setText("");
				txtGenero2.setText("");
				txtSaga.setText("");
				

				JOptionPane.showMessageDialog(panel,"Película ingresada con exito!");
				
				tx.success();
				}}
			}
		});
		
		btnCrear.setBackground(Color.BLACK);
		btnCrear.setForeground(Color.WHITE);
		btnCrear.setBounds(10, 272, 89, 23);
		panel.add(btnCrear);
		
		JLabel lblActorwa = new JLabel("Actores: ");
		lblActorwa.setBounds(10, 40, 46, 14);
		panel.add(lblActorwa);
		
		txtActor1 = new JTextField();
		txtActor1.setBounds(167, 37, 86, 20);
		panel.add(txtActor1);
		txtActor1.setColumns(10);
		
		txtActor2 = new JTextField();
		txtActor2.setBounds(263, 37, 86, 20);
		panel.add(txtActor2);
		txtActor2.setColumns(10);
		
		txtActor3 = new JTextField();
		txtActor3.setBounds(359, 37, 86, 20);
		panel.add(txtActor3);
		txtActor3.setColumns(10);
		
		txtDirector2 = new JTextField();
		txtDirector2.setBounds(263, 109, 86, 20);
		panel.add(txtDirector2);
		txtDirector2.setColumns(10);
		
		txtGenero2 = new JTextField();
		txtGenero2.setBounds(263, 71, 86, 20);
		panel.add(txtGenero2);
		txtGenero2.setColumns(10);
		
		txtSaga = new JTextField();
		txtSaga.setBounds(167, 183, 86, 20);
		panel.add(txtSaga);
		txtSaga.setColumns(10);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBackground(Color.BLACK);
		btnSalir.setForeground(Color.WHITE);
		 btnSalir.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                   setVisible(false);
             }
      });
		btnSalir.setBounds(167, 272, 89, 23);
		panel.add(btnSalir);
		
		JLabel lblNewLabel = new JLabel("ADMIN");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 80));
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setBounds(10, 15, 336, 60);
		contentPane.add(lblNewLabel);
        }
	public boolean camposVacios()
	{
		//Determina si los campos estÃ¡n vacÃ­os
		boolean vacios = false;
		
		if (txtTitulo.getText().equals("") || txtActor1.getText().equals("") || txtGenero1.getText().equals("") || txtDirector1.getText().equals("") || txtAño.getText().equals("") || txtLugar.getText().equals("")){
					vacios = true;
		}	
			
			else{
					vacios = false;
					}
		return vacios;
		
	}
	
}

