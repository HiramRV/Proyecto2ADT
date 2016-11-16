import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUIComenzar extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIComenzar frame = new GUIComenzar();
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
	public GUIComenzar() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(0, 0, 434, 76);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Gladys Herrera\\Desktop\\UVG\\cuarto semestre\\Estructura de Datos\\ADTP2\\Proyecto2\\imagen\\prototipo.PNG"));
		lblNewLabel.setBounds(10, 11, 414, 54);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.RED);
		panel_1.setBounds(0, 74, 434, 187);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnUsuario = new JButton("Usuario");
		btnUsuario.setBackground(Color.BLACK);
		btnUsuario.setForeground(Color.WHITE);
		btnUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				GUI nuevaVentana1 = new GUI();
				nuevaVentana1.setVisible(true);
			}
		});
		btnUsuario.setBounds(75, 83, 89, 23);
		panel_1.add(btnUsuario);
		
		JButton btnAdmin = new JButton("Administrador");
		btnAdmin.setForeground(Color.WHITE);
		btnAdmin.setBackground(Color.BLACK);
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				GUIAdmin nuevaVentana1 = new GUIAdmin();
				nuevaVentana1.setVisible(true);
			}
		});
		btnAdmin.setBounds(229, 83, 132, 23);
		panel_1.add(btnAdmin);
	}

}
