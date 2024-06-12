package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import controller.Controller;

public class VerEnfermedades extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldEnfermedad;
	private JTextField textFieldFecha;
	private JTextField textFieldTratamiento;
	private JTextField textFieldInforme;
	static String dni;
	private JLabel lblEnfermedad;
	private JLabel lblFecha;
	private JLabel lblTratamiento;
	private JLabel lblDetalles;
	private JLabel lblMedicamentosParaEl;
	private JLabel lblInforme;
	private JLabel lblTitulo;
	private JScrollPane scrollPaneMedicamentosTratamiento;
	private JTextArea textAreaMedicamentosTratamiento;
	private Controller controller = new Controller();
	private ArrayList<String> enfermedad, fecha,fecha_Alta, tratamiento, informe;
	private ArrayList<ArrayList<String>> medicamentos;
	private static int posicionEnfermedad = 0;
	private JButton btnSiguienteEnfermedad;
	private JButton btnAnteriorEnfermedad;
	private JButton btnVolver;
	private VentanaPrincipal principal;
	private JTextField textField_fecha_Alta;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VerEnfermedades frame = new VerEnfermedades(dni);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VerEnfermedades(String dni) {
		setResizable(false);
		VerEnfermedades.dni = dni;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 562, 550);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		enfermedad = controller.findEnfermedad(dni);
		fecha = controller.findFecha(dni);
		fecha_Alta=controller.findFechaAlta(dni);
		tratamiento = controller.findTratamiento(dni);
		medicamentos = controller.findMedicamentosTratamiento(dni);
		informe = controller.findInforme(dni);

		lblEnfermedad = new JLabel("Enfermedad:");
		lblEnfermedad.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEnfermedad.setBounds(121, 86, 97, 22);
		contentPane.add(lblEnfermedad);

		textFieldEnfermedad = new JTextField();
		textFieldEnfermedad.setEditable(false);
		textFieldEnfermedad.setColumns(10);
		textFieldEnfermedad.setBounds(269, 86, 180, 22);
		contentPane.add(textFieldEnfermedad);

		textFieldFecha = new JTextField();
		textFieldFecha.setEditable(false);
		textFieldFecha.setColumns(10);
		textFieldFecha.setBounds(269, 133, 180, 22);
		contentPane.add(textFieldFecha);

		lblFecha = new JLabel("Fecha baja:");
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFecha.setBounds(121, 131, 97, 22);
		contentPane.add(lblFecha);

		lblTratamiento = new JLabel("Tratamiento:");
		lblTratamiento.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTratamiento.setBounds(119, 230, 97, 22);
		contentPane.add(lblTratamiento);

		textFieldTratamiento = new JTextField();
		textFieldTratamiento.setEditable(false);
		textFieldTratamiento.setColumns(10);
		textFieldTratamiento.setBounds(267, 230, 180, 22);
		contentPane.add(textFieldTratamiento);

		lblDetalles = new JLabel("Detalles:");
		lblDetalles.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDetalles.setBounds(121, 198, 85, 22);
		contentPane.add(lblDetalles);

		lblMedicamentosParaEl = new JLabel("Medicamentos");
		lblMedicamentosParaEl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMedicamentosParaEl.setBounds(119, 277, 120, 22);
		contentPane.add(lblMedicamentosParaEl);

		scrollPaneMedicamentosTratamiento = new JScrollPane();
		scrollPaneMedicamentosTratamiento.setBounds(267, 276, 180, 70);
		contentPane.add(scrollPaneMedicamentosTratamiento);

		textAreaMedicamentosTratamiento = new JTextArea();
		textAreaMedicamentosTratamiento.setEditable(false);
		scrollPaneMedicamentosTratamiento.setViewportView(textAreaMedicamentosTratamiento);

		lblInforme = new JLabel("Informe:");
		lblInforme.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblInforme.setBounds(119, 354, 97, 22);
		contentPane.add(lblInforme);

		textFieldInforme = new JTextField();
		textFieldInforme.setEditable(false);
		textFieldInforme.setColumns(10);
		textFieldInforme.setBounds(267, 356, 180, 22);
		contentPane.add(textFieldInforme);

		lblTitulo = new JLabel("Informacion de las enfermedades del paciente");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTitulo.setBounds(150, 22, 328, 32);
		contentPane.add(lblTitulo);

		btnSiguienteEnfermedad = new JButton("Siguiente Enfermedad");
		btnSiguienteEnfermedad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (posicionEnfermedad < enfermedad.size() - 1) {
					posicionEnfermedad++;
					mostrarDatosEnfermedad();
					actualizarEstadoBotones();
				}

			}
		});

		btnSiguienteEnfermedad.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSiguienteEnfermedad.setBounds(297, 429, 179, 32);
		contentPane.add(btnSiguienteEnfermedad);

		btnAnteriorEnfermedad = new JButton("Anterior Enfermedad");
		btnAnteriorEnfermedad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (posicionEnfermedad > 0) {
					posicionEnfermedad--;
					mostrarDatosEnfermedad();
					actualizarEstadoBotones();
				}

			}
		});
		btnAnteriorEnfermedad.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAnteriorEnfermedad.setBounds(86, 429, 153, 32);
		contentPane.add(btnAnteriorEnfermedad);

		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal = new VentanaPrincipal(dni);
				principal.setVisible(true);
				dispose();
			}
		});
		btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnVolver.setBackground(UIManager.getColor("Button.background"));
		btnVolver.setBounds(35, 29, 85, 21);
		contentPane.add(btnVolver);
		
		JLabel lblFechaAlta = new JLabel("Fecha alta:");
		lblFechaAlta.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFechaAlta.setBounds(121, 173, 97, 22);
		contentPane.add(lblFechaAlta);
		
		textField_fecha_Alta = new JTextField();
		textField_fecha_Alta.setEditable(false);
		textField_fecha_Alta.setColumns(10);
		textField_fecha_Alta.setBounds(269, 175, 180, 22);
		contentPane.add(textField_fecha_Alta);

		if (!enfermedad.isEmpty()) {
			mostrarDatosEnfermedad();
		}
		actualizarEstadoBotones();
	}

	private void mostrarDatosEnfermedad() {
		textFieldEnfermedad.setText(enfermedad.get(posicionEnfermedad));
		textFieldFecha.setText(fecha.get(posicionEnfermedad));
		textFieldTratamiento.setText(tratamiento.get(posicionEnfermedad));
		textFieldInforme.setText(informe.get(posicionEnfermedad));
		textAreaMedicamentosTratamiento.setText(String.join("\n", medicamentos.get(posicionEnfermedad)));
		textField_fecha_Alta.setText(fecha_Alta.get(posicionEnfermedad));
	}

	private void actualizarEstadoBotones() {
		btnAnteriorEnfermedad.setEnabled(posicionEnfermedad > 0);
		btnSiguienteEnfermedad.setEnabled(posicionEnfermedad < enfermedad.size() - 1);
	}
}
