package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
		VentanaTarjetaMedica.dni = dni;
		this.medicamentos = controller.medicamentos(dni);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNewLabel = new JLabel("Medicamentos disponibles en tu tarjeta médica\r\n");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(24, 20, 311, 37);
		contentPane.add(lblNewLabel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 60, 540, 200);
		contentPane.add(scrollPane);

		// Dynamically add JLabels for medications
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
		btnCancelar.setBounds(250, 300, 85, 27);
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
