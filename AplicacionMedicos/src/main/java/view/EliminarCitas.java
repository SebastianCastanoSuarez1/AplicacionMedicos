package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	ArrayList<String> citas, dniMedicos;
	ArrayList<JCheckBox> checkBoxes;
	Controller controller = new Controller();
	JLabel lblEliminarCitasCon;
	JLabel lblNewLabel;
	JScrollPane scrollPane;
	private JButton btnAceptar;
	private JButton btnCancelar;
	VentanaPrincipal principal;

	public static void main(String[] args) {
		EliminarCitas frame = new EliminarCitas(dni);
		frame.setVisible(true);
	}

	public EliminarCitas(String dni) {
		EliminarCitas.dni = dni;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 685, 482);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblEliminarCitasCon = new JLabel("Eliminar citas \r\n");
		lblEliminarCitasCon.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEliminarCitasCon.setBounds(31, 38, 357, 27);
		contentPane.add(lblEliminarCitasCon);

		lblNewLabel = new JLabel("Marque las citas que quiera eliminar\r\n");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(31, 59, 252, 37);
		contentPane.add(lblNewLabel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 150, 606, 222);
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
		btnAceptar.setBounds(343, 382, 85, 27);
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
		btnCancelar.setBounds(198, 382, 85, 27);
		contentPane.add(btnCancelar);

		cargarDatos(panel);
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
				checkBox.setBounds(450, i * 40, 25, 30); // Mueve el CheckBox más a la derecha
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
