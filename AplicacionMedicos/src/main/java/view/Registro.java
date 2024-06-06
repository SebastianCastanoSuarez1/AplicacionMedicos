package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import org.bson.Document;

import controller.Controller;
import model.Paciente;

public class Registro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField SurnameTxtField;
	private JTextField nameTxtField;
	private JTextField PasswdTxtField;
	private JLabel labelPaswd;
	private MaskFormatter dniMask;
	private MaskFormatter dobMask;
	private JFormattedTextField bodformated;
	private JFormattedTextField dniformated;
	private JComboBox<String> GendercomboBox;
	private JButton btnNewButton;

	private InicioSesion inicioSesion;
	private final Controller controller = new Controller();
	private JLabel finalMesgLbl;
	private Timer timer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registro frame = new Registro();
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
	public Registro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel Title = new JLabel("Crea una cuenta Medica");
		Title.setFont(new Font("Tahoma", Font.BOLD, 17));
		Title.setBounds(10, 10, 211, 62);
		contentPane.add(Title);

		JLabel name = new JLabel("Nombre:");
		name.setFont(new Font("Tahoma", Font.PLAIN, 12));
		name.setBounds(73, 132, 56, 21);
		contentPane.add(name);

		JLabel surname = new JLabel("Apellidos:");
		surname.setFont(new Font("Tahoma", Font.PLAIN, 12));
		surname.setBounds(310, 127, 56, 31);
		contentPane.add(surname);

		JLabel dob = new JLabel("Fecha de nacimiento:");
		dob.setFont(new Font("Tahoma", Font.PLAIN, 12));
		dob.setBounds(7, 180, 145, 21);
		contentPane.add(dob);

		JLabel gender = new JLabel("Sexo:");
		gender.setFont(new Font("Tahoma", Font.PLAIN, 12));
		gender.setBounds(329, 182, 30, 17);
		contentPane.add(gender);

		finalMesgLbl = new JLabel("");
		finalMesgLbl.setBounds(28, 304, 218, 21);
		contentPane.add(finalMesgLbl);

		JLabel dni = new JLabel("DNI:");
		dni.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dni.setBounds(87, 93, 45, 13);
		contentPane.add(dni);

		SurnameTxtField = new JTextField();
		SurnameTxtField.setBounds(376, 134, 135, 19);
		contentPane.add(SurnameTxtField);
		SurnameTxtField.setColumns(10);

		nameTxtField = new JTextField();
		nameTxtField.setBounds(139, 134, 135, 19);
		contentPane.add(nameTxtField);
		nameTxtField.setColumns(10);

		PasswdTxtField = new JTextField();
		PasswdTxtField.setColumns(10);
		PasswdTxtField.setBounds(376, 92, 135, 19);
		contentPane.add(PasswdTxtField);

		labelPaswd = new JLabel("Contraseña:");
		labelPaswd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelPaswd.setBounds(300, 94, 79, 13);
		contentPane.add(labelPaswd);

		GendercomboBox = new JComboBox<>();
		GendercomboBox.setModel(new DefaultComboBoxModel<>(new String[] { "", "Masculino", "Femenino" }));
		GendercomboBox.setBounds(376, 181, 135, 21);
		contentPane.add(GendercomboBox);

		btnNewButton = new JButton("Aceptar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				anadirPaciente(e);
			}

			private void anadirPaciente(ActionEvent e) {
				if (btnNewButton == e.getSource()) {
					Boolean anadido;
					Document paciente = new Paciente().append("Dni", dniformated.getText())
							.append("Nombre", nameTxtField.getText()).append("Apellidos", SurnameTxtField.getText())
							.append("Fecha_Nacimiento", bodformated.getText())
							.append("Sexo", GendercomboBox.getSelectedItem().toString())
							.append("Contraseña", PasswdTxtField.getText());
					Document sinContraseña = controller.findByDni(dniformated.getText()).get();
					if (!controller.existdni(dniformated.getText())) {
						anadido = controller.savePaciente(paciente);
						if (anadido) {
							JOptionPane.showMessageDialog(null, "Se ha creado el usuario correctamente", "Confirmación",
									JOptionPane.INFORMATION_MESSAGE);
							volverIcioSesion();
						} else {
							mostrarMensaje("Tu cuenta no ha sido creada", Color.RED);
						}
					} else if (controller.existdni(dniformated.getText())
							&& sinContraseña.getString("Contraseña") == null) {
						Boolean addPassword = controller.actualizarContraseña(
								controller.findByDni(dniformated.getText()), "Contraseña", PasswdTxtField.getText());
						if (addPassword) {
							JOptionPane.showMessageDialog(null, "Se ha creado el usuario correctamente", "Confirmación",
									JOptionPane.INFORMATION_MESSAGE);
							volverIcioSesion();
						}

					} else {
						mostrarMensaje("Este dni ya existe", Color.RED);
					}
				}
			}

	

			private void mostrarMensaje(String mensaje, Color color) {
				finalMesgLbl.setText(mensaje);
				finalMesgLbl.setForeground(color);
				timer.restart();
			}
		});
		btnNewButton.setEnabled(false);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBounds(391, 295, 120, 44);
		contentPane.add(btnNewButton);

		bodformated = new JFormattedTextField();
		try {
			dobMask = new MaskFormatter("##/##/####");
			dobMask.setValidCharacters("0123456789");
			bodformated = new JFormattedTextField(dobMask);
			bodformated.setBounds(142, 182, 135, 21);
			contentPane.add(bodformated);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		dniformated = new JFormattedTextField();
		try {
			dniMask = new MaskFormatter("########?");
			dniMask.setValidCharacters("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			dniformated = new JFormattedTextField(dniMask);
			dniformated.setBounds(142, 90, 132, 21);
			contentPane.add(dniformated);

			JButton btnNewButtonCancelar = new JButton("Cancelar");
			btnNewButtonCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					volverIcioSesion();

				}
			});
			btnNewButtonCancelar.setBounds(256, 295, 92, 40);
			contentPane.add(btnNewButtonCancelar);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		timer = new Timer(8000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				finalMesgLbl.setText(""); // Limpiar el texto
				timer.stop(); // Detener el temporizador
			}
		});

		// Agregar listeners para verificar cuando los campos estén completos
		SurnameTxtField.addActionListener(new TextFieldListener());
		nameTxtField.addActionListener(new TextFieldListener());
		PasswdTxtField.addActionListener(new TextFieldListener());
		bodformated.addActionListener(new TextFieldListener());
		dniformated.addActionListener(new TextFieldListener());
		GendercomboBox.addActionListener(new TextFieldListener());
	}
	private void volverIcioSesion() {
		dispose();
		inicioSesion = new InicioSesion();
		inicioSesion.setVisible(true);
	}
	// Clase interna para manejar los eventos de cambio de texto en los campos des
	// texto y en el ComboBox
	private class TextFieldListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Verificar si todos los campos están completos
			boolean allFieldsFilled = !SurnameTxtField.getText().isEmpty() && !nameTxtField.getText().isEmpty()
					&& !PasswdTxtField.getText().isEmpty() && !bodformated.getText().isEmpty()
					&& !dniformated.getText().isEmpty() && !GendercomboBox.getSelectedItem().toString().isEmpty();

			// Habilitar o deshabilitar el botón según si todos los campos están completos
			btnNewButton.setEnabled(allFieldsFilled);
		}
	}
}
