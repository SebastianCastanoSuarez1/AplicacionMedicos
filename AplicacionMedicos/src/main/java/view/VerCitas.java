package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import controller.Controller;

public class VerCitas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static String dni;
	ArrayList<String> citas, dniMedico;
	Controller controller = new Controller();
	JLabel lblVerCitasCon;
	JLabel lblNewLabel;
	JTextArea textAreaMostrar;
	JScrollPane scrollPane;
	private JButton btnCancelar;
	VentanaPrincipal principal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VerCitas frame = new VerCitas(dni);
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
	public VerCitas(String dni) {
		VerCitas.dni = dni;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 502, 376);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblVerCitasCon = new JLabel("Ver citas con los pacientes a cargo");
		lblVerCitasCon.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblVerCitasCon.setBounds(21, 32, 233, 27);
		contentPane.add(lblVerCitasCon);

		lblNewLabel = new JLabel("Citas con los pacientes");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(31, 113, 157, 37);
		contentPane.add(lblNewLabel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(194, 119, 274, 121);
		contentPane.add(scrollPane);

		textAreaMostrar = new JTextArea();
		textAreaMostrar.setEditable(false);
		scrollPane.setViewportView(textAreaMostrar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal = new VentanaPrincipal(dni);
				principal.setVisible(true);
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancelar.setBounds(186, 282, 85, 27);
		contentPane.add(btnCancelar);

		cargarDatos();
	}

	private void cargarDatos() {
		try {
			citas = controller.findbyCitas(dni);
			dniMedico = controller.findDniMedicobyCitas(dni);
			String nombreMedico = "";
			String apellidoMedico = "";
			StringBuilder todasLasCitas = new StringBuilder();
			for (int i = 0; i < citas.size(); i++) {
				nombreMedico = controller.findNombreMedicoPorDni(dniMedico.get(i));
				apellidoMedico = controller.findApellidoMedicoPorDni(dniMedico.get(i));
				todasLasCitas.append(citas.get(i)).append(" - ").append(nombreMedico).append(" " + apellidoMedico)
						.append("\n");
			}
			textAreaMostrar.setText(todasLasCitas.toString());

		} catch (NullPointerException e1) {
			JOptionPane.showMessageDialog(VerCitas.this, "El DNI " + dni + " no tiene citas a cargo");
		} catch (ClassCastException e2) {
			JOptionPane.showMessageDialog(VerCitas.this, "El DNI " + dni + " no tiene citas a cargo");
		}
	}
}
