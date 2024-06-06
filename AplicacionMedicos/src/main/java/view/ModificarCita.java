package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.bson.Document;

import controller.Controller;
import controller.MedicoController;

public class ModificarCita extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private Controller controller = new Controller();
	private MedicoController medicoController = new MedicoController();
	private Map<String, String> Dni_Cita;
	private JComboBox<String> comboBoxEspecialidades;
	private ArrayList<String> citas, dniMedicos;
	private JComboBox<String> comboBoxCitas; // Esta es la variable de instancia que se está utilizando
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
				cargarCitasDisponibles(especialidadSeleccionada);
			}
		});
		comboBoxEspecialidades.setBounds(50, 120, 200, 21);
		contentPane.add(comboBoxEspecialidades);

		comboBoxCitas = new JComboBox<>(); // Inicialización correcta
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

	private void cargarCitasDisponibles(String especialidad) {
		if (especialidad != null && !especialidad.isEmpty()) {
			Document medicos;
			citas = controller.findbyCitas(dni);
			dniMedicos = controller.findDniMedicobyCitas(dni);
			Dni_Cita = new HashMap<>();
			for (int i = 0; i < dniMedicos.size(); i++) {
				medicos = medicoController.findByDni(dni).get();
				if (medicos.getString("Especialidad") == especialidad) {
					Dni_Cita.put(dniMedicos.get(i), citas.get(i));
				}
			}
			// Do not change anything above this line
			int contador = 0;
			for (Document medico : medicosEspecialidad) {
				String dni = medico.getString("Dni");
				List<String> citasMedico = medico.getList("Citas_Abiertas", String.class);

				if (citasMedico != null) {
					for (String cita : citasMedico) {
						Dni_Cita.put(contador, dni);
						citasDisponibles.add(cita);
						contador++;
					}
				}
			}

			comboBoxCitas.removeAllItems();
			for (String cita : citasDisponibles) {
				comboBoxCitas.addItem(cita);
			}
			comboBoxCitas.setEnabled(true);
		} else {
			comboBoxCitas.setEnabled(false);
		}

		JButton btnGuardarCambios = (JButton) contentPane.getComponent(4);
		btnGuardarCambios.setEnabled(comboBoxCitas.getItemCount() > 0);
	}

}
