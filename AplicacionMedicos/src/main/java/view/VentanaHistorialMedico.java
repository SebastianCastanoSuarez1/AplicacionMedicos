package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

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

public class VentanaHistorialMedico extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static String dni;
	private Controller controllerPaciente = new Controller();
	private JLabel lblAlergenos, lblMedicamentos, lblEnfermedades, lblHistorialMedico;
	private JTextArea textAreaAlergenos, textAreaMedicamentos, textAreaEnfermedades;
	private JScrollPane scrollPaneAlergenos, scrollPaneMedicamentos, scrollPaneEnfermedades;
	private JButton btnVolver;

	private VerEnfermedades verEnfermedades;
	private VentanaPrincipal principal;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaHistorialMedico frame = new VentanaHistorialMedico(dni);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaHistorialMedico(String dni) {
		setResizable(false);
		VentanaHistorialMedico.dni = dni;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 589, 588);
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
		lblLogo.setBounds(123, 0, 295, 151);
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

		lblHistorialMedico = new JLabel("Historial Médico");
		lblHistorialMedico.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblHistorialMedico.setBounds(203, 150, 192, 21);
		contentPane.add(lblHistorialMedico);

		lblAlergenos = new JLabel("Alérgenos:");
		lblAlergenos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAlergenos.setBounds(123, 200, 85, 22);
		contentPane.add(lblAlergenos);

		scrollPaneAlergenos = new JScrollPane();
		scrollPaneAlergenos.setBounds(257, 200, 180, 70);
		contentPane.add(scrollPaneAlergenos);

		textAreaAlergenos = new JTextArea();
		textAreaAlergenos.setEditable(false);
		scrollPaneAlergenos.setViewportView(textAreaAlergenos);

		lblMedicamentos = new JLabel("Medicamentos:");
		lblMedicamentos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMedicamentos.setBounds(123, 280, 85, 22);
		contentPane.add(lblMedicamentos);

		scrollPaneMedicamentos = new JScrollPane();
		scrollPaneMedicamentos.setBounds(257, 280, 180, 70);
		contentPane.add(scrollPaneMedicamentos);

		textAreaMedicamentos = new JTextArea();
		textAreaMedicamentos.setEditable(false);
		scrollPaneMedicamentos.setViewportView(textAreaMedicamentos);

		lblEnfermedades = new JLabel("Enfermedades:");
		lblEnfermedades.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEnfermedades.setBounds(123, 360, 85, 22);
		contentPane.add(lblEnfermedades);

		scrollPaneEnfermedades = new JScrollPane();
		scrollPaneEnfermedades.setBounds(257, 360, 180, 70);
		contentPane.add(scrollPaneEnfermedades);

		textAreaEnfermedades = new JTextArea();
		textAreaEnfermedades.setEditable(false);
		scrollPaneEnfermedades.setViewportView(textAreaEnfermedades);

		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(e -> {
			principal = new VentanaPrincipal(dni);
			principal.setVisible(true);
			dispose();
		});
		btnVolver.setBackground(new Color(240, 240, 240));
		btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnVolver.setBounds(123, 474, 129, 34);
		contentPane.add(btnVolver);

		JButton btnNewButton = new JButton("Ver enfermedades\r\n");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnNewButton == e.getSource()) {
					verEnfermedades = new VerEnfermedades(dni);
					verEnfermedades.setVisible(true);
					dispose();
				}
			}
		});
		btnNewButton.setBounds(293, 475, 146, 34);
		contentPane.add(btnNewButton);

		cargarDatosPaciente(dni);
	}

	private void cargarDatosPaciente(String dni) {
		try {
			String[] alergenos = controllerPaciente.findAlergenos(dni);
			String[] medicamentos = controllerPaciente.medicamentos(dni);
			List<String> enfermedades = controllerPaciente.findEnfermedad(dni);
			textAreaAlergenos.setText(String.join("\n", alergenos));
			textAreaMedicamentos.setText(String.join("\n", medicamentos));
			textAreaEnfermedades.setText(String.join("\n", enfermedades));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al cargar los datos del paciente.");
		}
	}
}
