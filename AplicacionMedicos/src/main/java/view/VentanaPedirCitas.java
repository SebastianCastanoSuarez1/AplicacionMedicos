package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

public class VentanaPedirCitas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static String dni;

	Controller controller = new Controller();
	MedicoController medicoController = new MedicoController();

	private JComboBox<String> comboBoxEspecialidades;
	private JComboBox<String> comboBoxCitasDisponibles;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private VentanaPrincipal principal;
	private JLabel lblMensaje;
	private DateFormat formateador;
	private JLabel lblNewLabel;
	private HashMap<Integer, String> posicion_Dni;

	private List<Document> medicosEspecialidad = new ArrayList<>();
	private List<String> citasDisponibles;

	private VentanaPrincipal ventanaPrincipal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPedirCitas frame = new VentanaPedirCitas(dni);
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
	public VentanaPedirCitas(String dni) {
		try {
			VentanaPedirCitas.dni = dni;

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 568, 471);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(255, 255, 255));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);

			// Inicialización del comboBoxEspecialidades
			comboBoxEspecialidades = new JComboBox<String>();
			comboBoxEspecialidades.setModel(new DefaultComboBoxModel<String>(new String[] { "", "Medico Familia",
					"Cirugia", "Traumatologia", "Dermatologia", "Oftalmologia", "Pediatria", "Reumatologia",
					"Neurologia", "Enfermeria", "Fisioterapia", "Gastroenterologia" }));
			comboBoxEspecialidades.setBounds(178, 119, 179, 21);
			contentPane.add(comboBoxEspecialidades);

			comboBoxCitasDisponibles = new JComboBox<String>();
			comboBoxCitasDisponibles.setEnabled(false);
			comboBoxCitasDisponibles.setBounds(178, 219, 179, 21);
			contentPane.add(comboBoxCitasDisponibles);

			JLabel lblNewLabel_1 = new JLabel("Citas disponibles:");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel_1.setBounds(30, 223, 100, 13);
			contentPane.add(lblNewLabel_1);

			btnAceptar = new JButton("Aceptar");
			btnAceptar.setEnabled(false);
			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Optional<Document> paciente = controller.findByDni(dni);
					if (paciente.isPresent()) {
						String citaSeleccionada = (String) comboBoxCitasDisponibles.getSelectedItem();
						int selectedIndex = comboBoxCitasDisponibles.getSelectedIndex();
						String dniMedico = posicion_Dni.get(selectedIndex);

						System.out.println("Selected Index: " + selectedIndex); // Debugging line
						System.out.println("DNI Medico: " + dniMedico); // Debugging line

						Document listaCitas = new Document();
						listaCitas.append("DniMedico", dniMedico).append("Fecha", citaSeleccionada);

						Boolean anadido = controller.addCitasPaciente(paciente, listaCitas);
						if (anadido) {
							medicoController.eliminarCita(medicoController.findByDni(dniMedico), citaSeleccionada);
							lblMensaje.setText("Cita asignada al paciente con éxito");
							lblMensaje.setForeground(Color.GREEN);
							ventanaPrincipal = new VentanaPrincipal(dni);
							ventanaPrincipal.setVisible(true);
							dispose();
						} else {
							lblMensaje.setText("Cita no ha sido asignada al paciente con éxito");
							lblMensaje.setForeground(Color.RED);
						}
					} else {
						lblMensaje.setText("No existe el paciente con el DNI " + dni);
						lblMensaje.setForeground(Color.RED);
					}
				}
			});
			btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnAceptar.setBounds(381, 353, 85, 27);
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
			btnCancelar.setBounds(216, 353, 85, 27);
			contentPane.add(btnCancelar);

			lblMensaje = new JLabel("");
			lblMensaje.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblMensaje.setBounds(200, 406, 296, 21);
			contentPane.add(lblMensaje);

			lblNewLabel = new JLabel("Especialidad:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblNewLabel.setBounds(30, 123, 100, 13);
			contentPane.add(lblNewLabel);

			comboBoxEspecialidades.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String especialidadSeleccionada = (String) comboBoxEspecialidades.getSelectedItem();
					cargarCitasDisponibles(especialidadSeleccionada);
				}
			});

		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(VentanaPedirCitas.this, "El DNI " + dni + " no tiene pacientes a cargo");
		}
	}

	// Método para cargar citas disponibles según la especialidad seleccionada
	private void cargarCitasDisponibles(String especialidad) {
		comboBoxCitasDisponibles.removeAllItems();
		if (especialidad != null && !especialidad.isEmpty()) {
			medicosEspecialidad = medicoController.findbyEspecialidad(especialidad);
			ArrayList<String> citasDisponibles = new ArrayList<>();
			posicion_Dni = new HashMap<>();
			int contador = 0;
			for (Document medico : medicosEspecialidad) {
				String dni = medico.getString("Dni");
				List<String> citasMedico = medico.getList("Citas_Abiertas", String.class);

				if (citasMedico != null) {
					for (String cita : citasMedico) {
						posicion_Dni.put(contador, dni);
						citasDisponibles.add(cita);
						contador++;
					}
				}
			}

			// Limpiar el modelo actual del comboBox
			comboBoxCitasDisponibles.removeAllItems();

			// Agregar cada cita al modelo del comboBox
			for (String cita : citasDisponibles) {
				comboBoxCitasDisponibles.addItem(cita);
			}
			comboBoxCitasDisponibles.setEnabled(true);
		} else {
			comboBoxCitasDisponibles.setEnabled(false);
		}
		btnAceptar.setEnabled(comboBoxCitasDisponibles.getItemCount() > 0);
	}
}
