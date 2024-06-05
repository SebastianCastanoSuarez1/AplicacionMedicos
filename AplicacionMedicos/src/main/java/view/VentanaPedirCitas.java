package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

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
	private JButton btnAceptar;
	private JButton btnCancelar;
	private VentanaPrincipal principal;
	private JLabel lblMensaje;
	private JLabel lblNewLabel;
	private HashMap<Integer, String> posicion_Dni;

	private List<Document> medicosEspecialidad = new ArrayList<>();

	private VentanaPrincipal ventanaPrincipal;
	private JTable table;
	private DefaultTableModel tableModel;

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
			comboBoxEspecialidades.setBounds(178, 30, 179, 21);
			contentPane.add(comboBoxEspecialidades);

			JLabel lblNewLabel_1 = new JLabel("Citas disponibles:");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel_1.setBounds(30, 326, 100, 13);
			contentPane.add(lblNewLabel_1);

			btnAceptar = new JButton("Aceptar");
			btnAceptar.setEnabled(false);
			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Optional<Document> paciente = controller.findByDni(dni);
					if (paciente.isPresent()) {
						int selectedRow = table.getSelectedRow();
						if (selectedRow != -1) {
							String citaSeleccionada = tableModel.getValueAt(selectedRow, 0).toString();
							String dniMedico = posicion_Dni.get(selectedRow);

							String[] dniPacientes = new String[1];
							dniPacientes[0] = dni;

							Document listaCitas = new Document();
							listaCitas.append("DniMedico", dniMedico).append("Fecha", citaSeleccionada);
							Boolean anadido = controller.addCitasPaciente(paciente, listaCitas);
							if (anadido) {
								medicoController.eliminarCita(medicoController.findByDni(dniMedico), citaSeleccionada);
								medicoController.crearPacientesCargo(dniMedico, dniPacientes);

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
							lblMensaje.setText("Seleccione una cita de la tabla.");
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
			lblNewLabel.setBounds(30, 34, 100, 13);
			contentPane.add(lblNewLabel);

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(49, 89, 447, 224);
			contentPane.add(scrollPane);

			tableModel = new DefaultTableModel(new Object[] { "Fecha" }, 0);
			table = new JTable(tableModel);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			scrollPane.setViewportView(table);

			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {
					btnAceptar.setEnabled(table.getSelectedRow() != -1);
				}
			});

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
		tableModel.setRowCount(0);
		if (especialidad != null && !especialidad.isEmpty()) {
			medicosEspecialidad = medicoController.findbyEspecialidad(especialidad);
			posicion_Dni = new HashMap<>();
			int contador = 0;
			for (Document medico : medicosEspecialidad) {
				String dni = medico.getString("Dni");
				List<String> citasMedico = medico.getList("Citas_Abiertas", String.class);

				if (citasMedico != null) {
					for (String cita : citasMedico) {
						posicion_Dni.put(contador, dni);
						tableModel.addRow(new Object[] { cita });
						contador++;
					}
				}
			}
			btnAceptar.setEnabled(false);
		}
	}
}
