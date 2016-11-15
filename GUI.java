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
		
		Vector<Integer> pelisId= Funciones.IdsPeliculas(db);
		GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
		File file= new File("C:/Users/AndreaMaybell/Documents/AMPE/deberes/Algoritmos y Estructuras de Datos/Proyecto2/Base2");
		GraphDatabaseService db= dbFactory.newEmbeddedDatabase(file);
		Funciones funciones= new Funciones();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		
		Vector<Integer> pelisId= funciones.IdsPeliculas(db);
		Node usuario= funciones.ingreso(db, nombre);
		Vector<Node> pelisVistas= funciones.actualizacionPerfilUsuario(db,usuario);
		
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
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Gladys Herrera\\Desktop\\UVG\\cuarto semestre\\Estructura de Datos\\ADTP2\\Proyecto2\\imagen\\prototipo.PNG"));
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
		
		btnComenzar = new JButton("Comenzar");
		btnComenzar.addActionListener(new MiListener());
		btnComenzar.setBounds(308, 7, 89, 23);
		panel_1.add(btnComenzar);
		
		JLabel lblleGustoLa = new JLabel("\u00BFLe gusto la recomendacion?");
		lblleGustoLa.setBounds(10, 103, 161, 14);
		panel_1.add(lblleGustoLa);
		
		btnSi = new JButton("SI");
		btnSi.addActionListener(new MiListener());
		btnSi.setBounds(10, 146, 43, 23);
		panel_1.add(btnSi);
		
		btnNo = new JButton("NO :(");
		btnNo.addActionListener(new MiListener());
		btnNo.setBounds(78, 146, 62, 23);
		panel_1.add(btnNo);
		
		JLabel lblotraRecomendacion = new JLabel("\u00BFOtra recomendacion?");
		lblotraRecomendacion.setBounds(308, 103, 128, 14);
		panel_1.add(lblotraRecomendacion);
		
		btnotra = new JButton("SI");
		btnotra.addActionListener(new MiListener());
		btnotra.setBounds(335, 146, 48, 23);
		panel_1.add(btnotra);
		
		btnSalir = new JButton("SALIR ");
		btnSalir.addActionListener(new MiListener());
		btnSalir.setBounds(490, 205, 70, 23);
		panel_1.add(btnSalir);
		
	
	}
	
	private class MiListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
		
			if (e.getSource() == btnComenzar){
				Funciones.ingreso(GraphDatabaseService db,String nombre);
				String recomendacion = Funciones.recomendacion(db,pelisVistas,pelisId,usuario);
				txtRecomendacion.setText(recomendacion);
			}
			
			if (e.getSource() == btnSi){
				usuario.createRelationshipTo(pelicula, Relaciones.Vio);
				Funciones.actualizacionPerfilUsuario(db, usuario);	
			}
			
			if (e.getSource() == btnNo){	
				
			}
			
			if (e.getSource() == btnotra){
				String recomendacion = Funciones.recomendacion(db,pelisVistas,pelisId,usuario);
				txtRecomendacion.setText(recomendacion);
			}
			
			if (e.getSource() == btnSalir){	
				GUIAdios nuevaVentana1 = new GUIAdios();
				nuevaVentana1.setVisible(true);
		
			}
		}
	}
	
}

