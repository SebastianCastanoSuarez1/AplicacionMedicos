package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
	private ArrayList<String> citas, dniMedico;
	private Controller controller = new Controller();
	private JLabel lblVerCitasCon;
	private JTextArea textAreaMostrar;
	private JScrollPane scrollPane;
	private JButton btnCancelar;
	private VentanaPrincipal principal;

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
		setResizable(false);
		VerCitas.dni = dni;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 644, 586);
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
		lblLogo.setBounds(160, 16, 295, 151);
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

		lblVerCitasCon = new JLabel("Ver citas concertadas");
		lblVerCitasCon.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblVerCitasCon.setBounds(44, 177, 233, 27);
		contentPane.add(lblVerCitasCon);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 214, 547, 246);
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
		btnCancelar.setBounds(262, 470, 85, 27);
		contentPane.add(btnCancelar);

		cargarDatos();
	}

	private void cargarDatos() {
		try {
			citas = controller.findbyCitas(dni);
			dniMedico = controller.findDniMedicobyCitas(dni);
			String nombreMedico = "";
			String apellidoMedico = "";
			String especialidadMedico = "";
			StringBuilder todasLasCitas = new StringBuilder();
			for (int i = 0; i < citas.size(); i++) {
				nombreMedico = controller.findNombreMedicoPorDni(dniMedico.get(i));
				apellidoMedico = controller.findApellidoMedicoPorDni(dniMedico.get(i));
				especialidadMedico = controller.findEspecialidadMedicoPorDni(dniMedico.get(i));
				todasLasCitas.append(citas.get(i)).append(" - ").append(nombreMedico).append(" " + apellidoMedico)
						.append(" - " + especialidadMedico).append("\n");
			}
			textAreaMostrar.setText(todasLasCitas.toString());

		} catch (NullPointerException e1) {
			JOptionPane.showMessageDialog(VerCitas.this, "El DNI " + dni + " no tiene citas a cargo");
		} catch (ClassCastException e2) {
			JOptionPane.showMessageDialog(VerCitas.this, "El DNI " + dni + " no tiene citas a cargo");
		}
	}
}
