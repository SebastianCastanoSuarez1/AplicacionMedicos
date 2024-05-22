package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.bson.Document;

import controller.Controller;
import model.Paciente;

public class VentanaEditarPerfil extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VentanaCitas ventanaCitas;
	static String dni;
	private JTextField textFieldNombre;
	private JTextField textFieldDni;
	private JTextField textFieldApellidos;
	private JTextField textFieldFechaNacimiento;
	private JTextField textFieldAltura;
	private JTextField textFieldPeso;
	private JComboBox<String> GendercomboBox;
	private JLabel lblUpdateStatus;

	private VentanaPrincipal ventanaPrincipal;
	private VentanaEditarPerfil vEditarPerfil;

	private final Controller controller = new Controller();
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JButton cancelButton;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaEditarPerfil frame = new VentanaEditarPerfil(dni);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaEditarPerfil(String dni) {
		VentanaEditarPerfil.dni = dni;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 673, 557);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ImageIcon logo = new ImageIcon("src\\main\\resources\\multimedia\\logo_Mongo.png");
		Image img = logo.getImage();
		Image newImg = img.getScaledInstance(295, 151, Image.SCALE_SMOOTH);
		logo = new ImageIcon(newImg);

		JLabel lblLogo = new JLabel();
		lblLogo.setIcon(logo);
		lblLogo.setBounds(144, 0, 295, 151);
		contentPane.add(lblLogo);

		lblLogo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				volverAVentanaPrincipal();
			}
		});

		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(133, 207, 150, 20);
		contentPane.add(textFieldNombre);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(32, 206, 70, 20);
		contentPane.add(lblNombre);

		textFieldDni = new JTextField();
		textFieldDni.setEditable(false);
		textFieldDni.setBounds(479, 207, 150, 20);
		contentPane.add(textFieldDni);

		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(340, 206, 70, 20);
		contentPane.add(lblDni);

		textFieldApellidos = new JTextField();
		textFieldApellidos.setBounds(133, 260, 150, 20);
		contentPane.add(textFieldApellidos);

		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setBounds(32, 259, 70, 20);
		contentPane.add(lblApellidos);

		textFieldFechaNacimiento = new JTextField();
		textFieldFechaNacimiento.setBounds(479, 260, 150, 20);
		contentPane.add(textFieldFechaNacimiento);

		JLabel lblFechaNacimiento = new JLabel("Fecha Nacimiento:");
		lblFechaNacimiento.setBounds(340, 259, 128, 20);
		contentPane.add(lblFechaNacimiento);

		textFieldAltura = new JTextField();
		textFieldAltura.setBounds(133, 307, 150, 20);
		contentPane.add(textFieldAltura);
		textFieldAltura.setColumns(10);
		// Limitar a 3 caracteres y solo números
		textFieldAltura.setDocument(new JTextFieldLimit(3, true));

		textFieldPeso = new JTextField();
		textFieldPeso.setBounds(479, 307, 150, 20);
		contentPane.add(textFieldPeso);
		textFieldPeso.setColumns(10);
		// Limitar a 3 caracteres y solo números
		textFieldPeso.setDocument(new JTextFieldLimit(3, true));

		JButton saveButton = new JButton("Guardar Datos");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = new String(passwordField.getPassword());

				Document paciente = new Paciente().append("Dni", textFieldDni.getText())
						.append("Nombre", textFieldNombre.getText()).append("Apellidos", textFieldApellidos.getText())
						.append("Fecha_Nacimiento", textFieldFechaNacimiento.getText())
						.append("Sexo", GendercomboBox.getSelectedItem().toString()).append("Contraseña", password)
						.append("Altura", Integer.parseInt(textFieldAltura.getText()))
						.append("Peso", Integer.parseInt(textFieldPeso.getText()));
				if (controller.updateData(dni, paciente)) {
					ventanaPrincipal = new VentanaPrincipal(dni);
					ventanaPrincipal.setVisible(true);
					dispose();
				} else {
					lblUpdateStatus.setText("¡Error al actualizar!");
				}
			}
		});
		saveButton.setFont(new Font("Tahoma", Font.BOLD, 17));
		saveButton.setBackground(new Color(240, 240, 240));
		saveButton.setBounds(483, 464, 155, 28);
		contentPane.add(saveButton);

		passwordField = new JPasswordField();
		passwordField.setBounds(133, 370, 150, 19);
		contentPane.add(passwordField);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(479, 370, 150, 19);
		contentPane.add(passwordField_1);

		JLabel lblNewLabel = new JLabel("Contraseña: ");
		lblNewLabel.setBounds(32, 370, 70, 16);
		contentPane.add(lblNewLabel);

		JLabel lblConfirmarContrasea = new JLabel("Confirmar contraseña: ");
		lblConfirmarContrasea.setBounds(340, 373, 128, 16);
		contentPane.add(lblConfirmarContrasea);

		lblUpdateStatus = new JLabel(" ");
		lblUpdateStatus.setBounds(32, 464, 191, 20);
		contentPane.add(lblUpdateStatus);

		JRadioButton showPassword = new JRadioButton("Mostrar Contraseña");
		showPassword.setContentAreaFilled(false);
		showPassword.setBounds(32, 407, 172, 21);
		contentPane.add(showPassword);
		showPassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (showPassword.isSelected()) {
					passwordField.setEchoChar((char) 0);
				} else {
					passwordField.setEchoChar('\u2022');
				}
			}
		});

		JLabel lblSexo = new JLabel("Sexo:");
		lblSexo.setBounds(32, 341, 70, 13);
		contentPane.add(lblSexo);

		GendercomboBox = new JComboBox<String>();
		GendercomboBox.setModel(new DefaultComboBoxModel<>(new String[] { "", "Masculino", "Femenino" }));
		GendercomboBox.setBounds(132, 337, 135, 21);
		contentPane.add(GendercomboBox);

		cancelButton = new JButton("Cancelar");
		cancelButton.setFont(new Font("Tahoma", Font.BOLD, 17));
		cancelButton.setBounds(324, 464, 115, 28);
		contentPane.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volverAVentanaPrincipal();
			}
		});

		JLabel lblNewLabel_1 = new JLabel("Altura");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_1.setBounds(32, 309, 45, 13);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Peso");
		lblNewLabel_2.setBounds(340, 310, 45, 13);
		contentPane.add(lblNewLabel_2);

		cargarDatosDocumento();
	}

	private void cargarDatosDocumento() {
		Optional<Document> documento = controller.findByDni(dni);
		documento.ifPresent(usuarioDatos -> {
			textFieldNombre.setText(usuarioDatos.getString("Nombre"));
			textFieldDni.setText(usuarioDatos.getString("Dni"));
			textFieldApellidos.setText(usuarioDatos.getString("Apellidos"));
			textFieldFechaNacimiento.setText(usuarioDatos.getString("Fecha_Nacimiento"));
			textFieldAltura.setText(String.valueOf(usuarioDatos.getInteger("Altura")));
			textFieldPeso.setText(String.valueOf(usuarioDatos.getInteger("Peso")));
			passwordField.setText(usuarioDatos.getString("Contraseña"));
			passwordField_1.setText(usuarioDatos.getString("Contraseña"));
			GendercomboBox.setSelectedItem(usuarioDatos.getString("Sexo"));
		});
	}

	private void volverAVentanaPrincipal() {
		ventanaPrincipal = new VentanaPrincipal(dni);
		ventanaPrincipal.setVisible(true);
		dispose();
	}

	class JTextFieldLimit extends PlainDocument {
		private static final long serialVersionUID = 1L;
		private int limit;
		private boolean onlyDigits;

		JTextFieldLimit(int limit, boolean onlyDigits) {
			super();
			this.limit = limit;
			this.onlyDigits = onlyDigits;
		}

		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
				return;

			if ((getLength() + str.length()) <= limit) {
				if (onlyDigits && !str.matches("\\d+")) {
					return;
				}
				super.insertString(offset, str, attr);
			}
		}
	}
}
