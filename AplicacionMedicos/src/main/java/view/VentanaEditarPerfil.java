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
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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

	private VentanaPrincipal ventanaPrincipal;
	private VentanaEditarPerfil vEditarPerfil;

	private final Controller controller = new Controller();
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

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
		setBounds(100, 100, 710, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnPedirCita = new JButton("Citas");
		btnPedirCita.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnPedirCita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventanaCitas = new VentanaCitas(dni);
				ventanaCitas.setVisible(true);
				dispose();
			}
		});
		btnPedirCita.setBounds(233, 161, 100, 36);
		contentPane.add(btnPedirCita);

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

		JMenuItem btnMenu = new JMenuItem("Menú");
		btnMenu.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarMenu();
			}
		});
		btnMenu.setBounds(123, 161, 100, 36);
		contentPane.add(btnMenu);

		// Crear los textfields y etiquetas
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

		JLabel lblAltura = new JLabel("Altura:");
		lblAltura.setBounds(32, 306, 70, 20);
		contentPane.add(lblAltura);

		textFieldPeso = new JTextField();
		textFieldPeso.setBounds(479, 307, 150, 20);
		contentPane.add(textFieldPeso);

		JLabel lblPeso = new JLabel("Peso:");
		lblPeso.setBounds(340, 306, 70, 20);
		contentPane.add(lblPeso);

		JButton saveButton = new JButton("Guardar Datos");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Document paciente = new Paciente().append("Dni", textFieldDni.getText())
						.append("Nombre", textFieldNombre.getText()).append("Apellidos", textFieldApellidos.getText())
						.append("Fecha_Nacimiento", textFieldFechaNacimiento.getText())
						.append("Sexo", GendercomboBox.getSelectedItem().toString())
						.append("Contraseña", passwordField.getPassword());
				controller.updateData(dni, paciente);
			}
		});
		saveButton.setFont(new Font("Tahoma", Font.BOLD, 17));
		saveButton.setBackground(new Color(135, 206, 250));
		saveButton.setBorderPainted(false);
		saveButton.setBounds(475, 456, 163, 36);
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

		JLabel lblUpdateStatus = new JLabel("New label");
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

		cargarDatosDocumento();
	}

	private void cargarDatosDocumento() {

		Optional<Document> documento = controller.findByDni(dni);
		Document usuarioDatos = documento.get();

		textFieldNombre.setText(usuarioDatos.getString("Nombre"));
		textFieldDni.setText(usuarioDatos.getString("Dni"));
		textFieldApellidos.setText(usuarioDatos.getString("Apellidos"));
		textFieldFechaNacimiento.setText(usuarioDatos.getString("Fecha_Nacimiento"));
		textFieldAltura.setText(String.valueOf(usuarioDatos.getDouble("Altura")));
		textFieldPeso.setText(String.valueOf(usuarioDatos.getDouble("Peso")));
		passwordField.setText(usuarioDatos.getString("Contraseña"));
		passwordField_1.setText(usuarioDatos.getString("Contraseña"));
		GendercomboBox.setSelectedItem(usuarioDatos.getString("Sexo"));

	}

	// Método para mostrar el menú contextual
	private void mostrarMenu() {
		JPopupMenu menu = new JPopupMenu();

		JMenuItem menuItemEditarPerfil = new JMenuItem("Editar perfil");
		menuItemEditarPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vEditarPerfil = new VentanaEditarPerfil(dni);
				vEditarPerfil.setVisible(true);
				dispose();
			}
		});
		menu.add(menuItemEditarPerfil);

		JMenuItem menuItemConfiguracion = new JMenuItem("Configuración");
		menuItemConfiguracion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(VentanaEditarPerfil.this, "Opción de Configuración seleccionada");
			}
		});
		menu.add(menuItemConfiguracion);

		JMenuItem menuItemCerrarSesion = new JMenuItem("Cerrar Sesión");
		menuItemCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(VentanaEditarPerfil.this, "Sesión cerrada");
			}
		});
		menu.add(menuItemCerrarSesion);
		menu.show(contentPane, 50, 252);
	}

	// Método para volver a la ventana principal
	private void volverAVentanaPrincipal() {
		ventanaPrincipal = new VentanaPrincipal(dni);
		ventanaPrincipal.setVisible(true);
		dispose();
	}

	@SuppressWarnings("unused")
	private void comprobarTextField(JTextField textField) {
		if (textField.getText() == "null") {
			textField.setText("");
		}
	}
}
