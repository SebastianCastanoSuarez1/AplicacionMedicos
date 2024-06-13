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

public class ModificarCita extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static String dni;
	private Controller controller = new Controller();
	private MedicoController medicoController = new MedicoController();
	private JComboBox<String> comboBoxCita;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private VentanaPrincipal ventanaPrincipal;
	private JLabel lblMensaje;
	private JLabel lblNewLabel;
	private HashMap<String, String> citaToMedicoMap = new HashMap<>();
	private ArrayList<String> citas;
	private String selectedCita;
	private JTable table;
	private DefaultTableModel tableModel;
	private String[] citasAbiertas;

	/**
	 * Launch the application.
	 */
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
		setResizable(false);
		ModificarCita.dni = dni;
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 568, 471);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		comboBoxCita = new JComboBox<String>();
		citas = controller.findbyCitas(dni);
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement("");
		for (String cita : citas) {
			model.addElement(cita);
		}
		comboBoxCita.setModel(model);
		comboBoxCita.setBounds(178, 30, 210, 21);
		comboBoxCita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedCita = (String) comboBoxCita.getSelectedItem();

				citaToMedicoMap.clear();
				List<String> dnies = controller.findDniMedicobyCitas(dni);
				for (int i = 0; i < citas.size(); i++) {
					citaToMedicoMap.put(citas.get(i), dnies.get(i));
				}

				tableModel.setRowCount(0);
				if (selectedCita != null && !selectedCita.isEmpty() && citaToMedicoMap.containsKey(selectedCita)) {
					String dniMedico = citaToMedicoMap.get(selectedCita);
					citasAbiertas = medicoController.citasAbiertas(dniMedico);
					for (String cita : citasAbiertas) {
						tableModel.addRow(new Object[] { cita });
					}

				}
			}
		});
		contentPane.add(comboBoxCita);

		JLabel lblNewLabel_1 = new JLabel("Citas disponibles:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(30, 66, 100, 13);
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
						String dniMedicoSeleccionado = citaToMedicoMap.get(selectedCita);
						String selectedCita = comboBoxCita.getSelectedItem().toString();

						try {
							Boolean anadido = controller.modificarCita(dni, dniMedicoSeleccionado, selectedCita,
									citaSeleccionada);

							if (anadido) {
								List<String> citaMedicoDevolver = new ArrayList<>();
								citaMedicoDevolver.add(selectedCita);
								medicoController.abrirCitasPaciente(medicoController.findByDni(dniMedicoSeleccionado),
										citaMedicoDevolver);
								lblMensaje.setText("Cita asignada al paciente con éxito");
								lblMensaje.setForeground(Color.GREEN);
								ventanaPrincipal = new VentanaPrincipal(dni);
								ventanaPrincipal.setVisible(true);
								dispose();
							} else {
								lblMensaje.setText("No se pudo asignar la cita");
								lblMensaje.setForeground(Color.RED);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
							lblMensaje.setText("Error al intentar actualizar la cita");
							lblMensaje.setForeground(Color.RED);
						}
					} else {
						lblMensaje.setText("Por favor, selecciona una cita de la tabla.");
						lblMensaje.setForeground(Color.RED);
					}
				} else {
					lblMensaje.setText("Paciente no encontrado.");
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
				ventanaPrincipal = new VentanaPrincipal(dni);
				ventanaPrincipal.setVisible(true);
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

		lblNewLabel = new JLabel("Citas pedidas");
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

		try {
			if (citas.isEmpty()) {
				throw new NullPointerException("El DNI " + dni + " no tiene pacientes a cargo");
			}
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(ModificarCita.this, e.getMessage());
		}
	}
}
