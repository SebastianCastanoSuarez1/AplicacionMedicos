package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModificarCita extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Controller controller;
	private JComboBox<String> comboBoxEspecialidades;
	private ArrayList<String> citas, dniMedicos;

	static String dni;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModificarCita frame = new ModificarCita(dni);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ModificarCita(String dni) {
		ModificarCita.dni = dni;
		controller = new Controller();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 568, 389);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Seleccione la especialidad:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(50, 36, 200, 40);
		contentPane.add(lblNewLabel);

		comboBoxEspecialidades = new JComboBox<>();
		comboBoxEspecialidades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String especialidadSeleccionada = (String) comboBoxEspecialidades.getSelectedItem();

			}
		});
		comboBoxEspecialidades.setBounds(50, 120, 200, 21);
		contentPane.add(comboBoxEspecialidades);

		JComboBox<String> comboBoxCitas = new JComboBox<>();
		comboBoxCitas.setBounds(378, 120, 150, 21);
		contentPane.add(comboBoxCitas);

		JButton btnGuardarCambios = new JButton("Guardar cambios");
		btnGuardarCambios.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnGuardarCambios.setBounds(223, 258, 150, 27);
		contentPane.add(btnGuardarCambios);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancelar.setBounds(379, 258, 85, 27);
		contentPane.add(btnCancelar);

		// Load specialities into the combo box
		cargarEspecialidades();
	}

	private void cargarEspecialidades() {
		try {
			dniMedicos = controller.findDniMedicobyCitas(dni);
			citas = controller.findbyCitas(dni);
			Set<String> especialidades = new HashSet<>();
			for (int i = 0; i < citas.size(); i++) {
				String especialidad = controller.findEspecialidadMedicoPorDni(dniMedicos.get(i));
				especialidades.add(especialidad);
			}
			DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) comboBoxEspecialidades.getModel();
			for (String especialidad : especialidades) {
				if (model.getIndexOf(especialidad) == -1) {
					model.addElement(especialidad);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(ModificarCita.this, "Error al cargar especialidades.");
		}
	}
	
}
