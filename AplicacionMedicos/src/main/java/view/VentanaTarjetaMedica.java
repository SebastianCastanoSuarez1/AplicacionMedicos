package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import controller.Controller;

public class VentanaTarjetaMedica extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	private JButton btnCancelar;
	private VentanaPrincipal principal;

	private String[] medicamentos;
	static String dni;

	private Controller controller = new Controller();

	public VentanaTarjetaMedica(String dni) {
		setResizable(false);
		VentanaTarjetaMedica.dni = dni;
		
		// Manejo de excepciones al cargar los datos
		try {
			this.medicamentos = controller.medicamentos(dni);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Fallo al cargar datos", "Error", JOptionPane.ERROR_MESSAGE);
			this.medicamentos = new String[0]; // Para evitar NullPointerException más adelante
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 696, 525);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ImageIcon logo = new ImageIcon("src\\main\\resources\\multimedia\\logo_Mongo.png");
		Image img = logo.getImage();
		Image newImg = img.getScaledInstance(295, 151, Image.SCALE_SMOOTH);
		logo = new ImageIcon(newImg);

		JLabel lblLogo = new JLabel();
		lblLogo.setBackground(new Color(0, 0, 0));
		lblLogo.setIcon(logo);
		lblLogo.setBounds(192, 10, 295, 151);
		contentPane.add(lblLogo);

		lblLogo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(dni);
				ventanaPrincipal.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

		lblNewLabel = new JLabel("Medicamentos disponibles en tu tarjeta médica\r\n");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(73, 152, 311, 37);
		contentPane.add(lblNewLabel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(73, 199, 540, 200);
		contentPane.add(scrollPane);

		// Agregar JLabels dinámicamente para los medicamentos
		JPanel dynamicPanel = new JPanel();
		dynamicPanel.setLayout(null);
		scrollPane.setViewportView(dynamicPanel);

		int yPosition = 10;
		for (String medicamento : medicamentos) {
			JLabel label = new JLabel(medicamento);
			label.setBounds(10, yPosition, 520, 25);
			dynamicPanel.add(label);

			yPosition += 35;
		}
		dynamicPanel.setPreferredSize(new Dimension(520, yPosition));

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancelar.setBounds(299, 439, 85, 27);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal = new VentanaPrincipal(dni);
				principal.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnCancelar);
	}

	public static void main(String[] args) {
		VentanaTarjetaMedica frame = new VentanaTarjetaMedica(dni);
		frame.setVisible(true);
	}
}
