package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;

public class VentanaCitas extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static String dni;
	String[] dniPaciente;
	JComboBox<String> comboBoxDniPacientes;
	String selectedDni;
	JLabel lblTitulo;
	JDateChooser dateChooserFecha;
	private JTextField textFieldFechaSeleccionada;
	JLabel lblFechaSeleccionada;
	JRadioButton rdbtnSeleccionarFecha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCitas frame = new VentanaCitas(dni);
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
	public VentanaCitas(String dni) {
		VentanaCitas.dni = dni;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 518, 388);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblTitulo = new JLabel("Citas con los pacientes a cargo");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTitulo.setBounds(22, 40, 233, 27);
		contentPane.add(lblTitulo);

		dateChooserFecha = new JDateChooser();
		dateChooserFecha.setBounds(22, 101, 233, 19);
		contentPane.add(dateChooserFecha);

		rdbtnSeleccionarFecha = new JRadioButton("Seleccionar fecha");
		rdbtnSeleccionarFecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnSeleccionarFecha.isSelected()) {

					SimpleDateFormat formateador = new SimpleDateFormat("EEEE, d 'de' MMMM 'de' yyyy");
					String fechaFormateada = formateador.format(dateChooserFecha.getDate());
					textFieldFechaSeleccionada.setText(fechaFormateada);
				}
			}
		});
		rdbtnSeleccionarFecha.setBackground(new Color(230, 230, 250));
		rdbtnSeleccionarFecha.setBounds(315, 101, 179, 21);
		contentPane.add(rdbtnSeleccionarFecha);

		lblFechaSeleccionada = new JLabel("Fecha seleccionada");
		lblFechaSeleccionada.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaSeleccionada.setBounds(54, 164, 155, 27);
		contentPane.add(lblFechaSeleccionada);

		textFieldFechaSeleccionada = new JTextField();
		textFieldFechaSeleccionada.setBounds(219, 170, 203, 19);
		contentPane.add(textFieldFechaSeleccionada);
		textFieldFechaSeleccionada.setColumns(10);

	}

}
