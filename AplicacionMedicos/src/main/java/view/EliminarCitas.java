package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import controller.Controller;

public class EliminarCitas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static String dni;
	private ArrayList<String> citas, dniMedicos;
	private ArrayList<JCheckBox> checkBoxes;
	private Controller controller = new Controller();
	private JLabel lblEliminarCitasCon;
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private VentanaPrincipal principal;

	public static void main(String[] args) {
		EliminarCitas frame = new EliminarCitas(dni);
		frame.setVisible(true);
	}

	public EliminarCitas(String dni) {
		EliminarCitas.dni = dni;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 719, 521);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblEliminarCitasCon = new JLabel("Eliminar citas \r\n");
		lblEliminarCitasCon.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEliminarCitasCon.setBounds(46, 154, 357, 27);
		contentPane.add(lblEliminarCitasCon);

		lblNewLabel = new JLabel("Marque las citas que quiera eliminar\r\n");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(48, 179, 252, 37);
		contentPane.add(lblNewLabel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(46, 215, 606, 222);
		contentPane.add(scrollPane);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setLayout(null);
		scrollPane.setViewportView(panel);

		checkBoxes = new ArrayList<>();

		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int citasEliminadas = 0;
					citas = controller.findbyCitas(dni);
					for (int i = 0; i < checkBoxes.size(); i++) {
						if (checkBoxes.get(i).isSelected()) {
							String cita = citas.get(i);
							String dniMedico = dniMedicos.get(i);
							if (controller.eliminarCita(dni, dniMedico, cita)) {
								citasEliminadas++;
							}
						}
					}
					JOptionPane.showMessageDialog(EliminarCitas.this, citasEliminadas + " citas eliminadas con éxito.");
					cargarDatos((JPanel) scrollPane.getViewport().getView());
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(EliminarCitas.this, "Error al eliminar citas.");
				}
			}
		});
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAceptar.setBounds(358, 447, 85, 27);
		contentPane.add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal = new VentanaPrincipal(dni);
				principal.setVisible(true);
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancelar.setBounds(213, 447, 85, 27);
		contentPane.add(btnCancelar);

		cargarDatos(panel);

		ImageIcon logo = new ImageIcon("src\\main\\resources\\multimedia\\logo_Mongo.png");
		Image img = logo.getImage();
		Image newImg = img.getScaledInstance(295, 151, Image.SCALE_SMOOTH);
		logo = new ImageIcon(newImg);

		JLabel lblLogo = new JLabel();
		lblLogo.setBackground(new Color(0, 0, 0));
		lblLogo.setIcon(logo);
		lblLogo.setBounds(198, 10, 295, 151);
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
	}

	private void cargarDatos(JPanel panel) {
		try {
			citas = controller.findbyCitas(dni);
			dniMedicos = controller.findDniMedicobyCitas(dni);
			String nombreMedico = "";
			String apellidoMedico = "";
			String especialidadMedico = "";
			StringBuilder todasLasCitas = new StringBuilder();
			panel.removeAll();
			checkBoxes.clear();

			for (int i = 0; i < citas.size(); i++) {
				nombreMedico = controller.findNombreMedicoPorDni(dniMedicos.get(i));
				apellidoMedico = controller.findApellidoMedicoPorDni(dniMedicos.get(i));
				especialidadMedico = controller.findEspecialidadMedicoPorDni(dniMedicos.get(i));
				todasLasCitas.append(citas.get(i)).append(" - ").append(nombreMedico).append(" " + apellidoMedico)
						.append(" - " + especialidadMedico).append("\n");

				JTextArea textArea = new JTextArea(
						citas.get(i) + " - " + nombreMedico + " " + apellidoMedico + " - " + especialidadMedico);
				textArea.setBounds(10, i * 40, 430, 30);
				textArea.setEditable(false);
				panel.add(textArea);
				JCheckBox checkBox = new JCheckBox();
				checkBox.setBounds(450, i * 40, 25, 30);
				checkBox.setBackground(new Color(0, 0, 0));
				checkBoxes.add(checkBox);
				panel.add(checkBox);
			}

			panel.setPreferredSize(new Dimension(400, citas.size() * 40));

		} catch (NullPointerException e1) {
			JOptionPane.showMessageDialog(EliminarCitas.this, "El DNI " + dni + " no tiene citas a cargo");
		} catch (ClassCastException e2) {
			JOptionPane.showMessageDialog(EliminarCitas.this, "El DNI " + dni + " no tiene citas a cargo");
		}
	}
}
