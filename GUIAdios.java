import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class GUIAdios extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIAdios frame = new GUIAdios();
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
	public GUIAdios() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 299, 142);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblGraciasPorUsar = new JLabel("GRACIAS POR USAR ");
		lblGraciasPorUsar.setBounds(88, 11, 102, 14);
		contentPane.add(lblGraciasPorUsar);
		
		JLabel lblMoviest = new JLabel("MOVIEST");
		lblMoviest.setBounds(118, 36, 46, 14);
		contentPane.add(lblMoviest);
		
		JLabel lblVuelvaPronto = new JLabel("VUELVA PRONTO");
		lblVuelvaPronto.setBounds(99, 61, 102, 14);
		contentPane.add(lblVuelvaPronto);
	}

}
