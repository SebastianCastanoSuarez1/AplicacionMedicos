package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

public class Registro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_4;
	private JTextField textField_5;
	private JLabel labelPaswd;
	private MaskFormatter mask;

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
		name.setBounds(73, 132, 56, 21);
		contentPane.add(name);
		
		JLabel surname = new JLabel("Apellidos:");
		surname.setFont(new Font("Tahoma", Font.PLAIN, 12));
		surname.setBounds(310, 127, 56, 31);
		contentPane.add(surname);
		
		JLabel dob = new JLabel("Fecha de nacimiento:");
		dob.setFont(new Font("Tahoma", Font.PLAIN, 12));
		dob.setBounds(7, 180, 145, 21);
		contentPane.add(dob);
		
		JLabel gender = new JLabel("Sexo:");
		gender.setFont(new Font("Tahoma", Font.PLAIN, 12));
		gender.setBounds(329, 182, 30, 17);
		contentPane.add(gender);
		
		JLabel dni = new JLabel("DNI:");
		dni.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dni.setBounds(87, 93, 45, 13);
		contentPane.add(dni);
		
		textField = new JTextField();
		textField.setBounds(139, 92, 135, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(376, 134, 135, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(139, 134, 135, 19);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(376, 92, 135, 19);
		contentPane.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(376, 182, 135, 19);
		contentPane.add(textField_5);
		
		labelPaswd = new JLabel("Contraseña:");
		labelPaswd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelPaswd.setBounds(300, 94, 79, 13);
		contentPane.add(labelPaswd);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBounds(391, 295, 120, 44);
		contentPane.add(btnNewButton);
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(142, 182, 135, 21);
		contentPane.add(formattedTextField);
		try {
			mask = new MaskFormatter("##/##/####");
			mask.setValidCharacters("0123456789");
			formattedTextField= new JFormattedTextField(mask);
			
		}catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
