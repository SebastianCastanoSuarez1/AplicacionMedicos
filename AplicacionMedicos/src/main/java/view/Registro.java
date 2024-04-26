package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

public class Registro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registro frame = new Registro();
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
	public Registro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel Title = new JLabel("Crea una cuenta Medica");
		Title.setFont(new Font("Tahoma", Font.BOLD, 17));
		Title.setBounds(10, 10, 211, 62);
		contentPane.add(Title);
		
		JLabel name = new JLabel("Nombre:");
		name.setFont(new Font("Tahoma", Font.PLAIN, 12));
		name.setBounds(288, 90, 56, 21);
		contentPane.add(name);
		
		JLabel surname = new JLabel("Apellidos:");
		surname.setFont(new Font("Tahoma", Font.PLAIN, 12));
		surname.setBounds(100, 127, 56, 31);
		contentPane.add(surname);
		
		JLabel dob = new JLabel("Fecha de nacimiento:");
		dob.setFont(new Font("Tahoma", Font.PLAIN, 12));
		dob.setBounds(100, 181, 145, 21);
		contentPane.add(dob);
		
		JLabel gender = new JLabel("Sexo:");
		gender.setFont(new Font("Tahoma", Font.PLAIN, 12));
		gender.setBounds(100, 238, 45, 13);
		contentPane.add(gender);
		
		JLabel dni = new JLabel("DNI:");
		dni.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dni.setBounds(100, 93, 45, 13);
		contentPane.add(dni);
	}
}
