package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.text.MaskFormatter;

import controller.Controller;

public class InicioSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPasswordField passwordField;
	private JLabel usernameLabel;
	private MaskFormatter mascara;
	private JFormattedTextField formattedDni;
	private Registro registro;
	private VentanaPrincipal ventanaPrincipal;
	private CambiarContrasena cambiarContrasena;
	private final Controller controller = new Controller();
	private JRadioButton RadioButtonsaveuserpasswd;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InicioSesion frame = new InicioSesion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public InicioSesion() {
		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 456);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		usernameLabel = new JLabel("DNI:");
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		usernameLabel.setBounds(132, 100, 100, 30);
		contentPane.add(usernameLabel);

		JLabel passwordLabel = new JLabel("Contraseña:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordLabel.setBounds(100, 147, 89, 30);
		contentPane.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(200, 150, 200, 30);
		contentPane.add(passwordField);

		JRadioButton showPasswordButton = new JRadioButton("Mostrar contraseña");
		showPasswordButton.setContentAreaFilled(false);
		showPasswordButton.setBounds(418, 149, 121, 30);
		contentPane.add(showPasswordButton);

		showPasswordButton.addActionListener(e -> {
			if (showPasswordButton.isSelected()) {
				passwordField.setEchoChar((char) 0);
			} else {
				passwordField.setEchoChar('\u2022');
			}
		});

		JButton loginButton = new JButton("Iniciar Sesión");
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		loginButton.setBounds(294, 269, 150, 40);
		contentPane.add(loginButton);

		JButton registerButton = new JButton("Registrarse");
		registerButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		registerButton.setBounds(139, 269, 121, 40);
		contentPane.add(registerButton);
		registerButton.addActionListener(e -> {
			registro = new Registro();
			registro.setVisible(true);
			dispose();
		});

		JLabel cambiarContrasenaLabel = new JLabel("Cambiar Contraseña");
		cambiarContrasenaLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cambiarContrasenaLabel.setForeground(Color.BLUE);
		cambiarContrasenaLabel.setBounds(200, 186, 200, 21);
		cambiarContrasenaLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(cambiarContrasenaLabel);

		try {
			mascara = new MaskFormatter("########?");
			mascara.setValidCharacters("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			formattedDni = new JFormattedTextField(mascara);
			formattedDni.setBounds(200, 104, 200, 27);
			contentPane.add(formattedDni);

			RadioButtonsaveuserpasswd = new JRadioButton("Guardar usuario");
			RadioButtonsaveuserpasswd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

				}
			});
			RadioButtonsaveuserpasswd.setContentAreaFilled(false);
			RadioButtonsaveuserpasswd.setBounds(200, 213, 121, 21);
			contentPane.add(RadioButtonsaveuserpasswd);
			
			JLabel lblNewLabel = new JLabel("Pacientes Hospital la Paloma");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblNewLabel.setBounds(100, 47, 268, 21);
			contentPane.add(lblNewLabel);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		cambiarContrasenaLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cambiarContrasena = new CambiarContrasena();
				cambiarContrasena.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				cambiarContrasenaLabel.setText("<html><u>Cambiar Contraseña</u></html>");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				cambiarContrasenaLabel.setText("Cambiar Contraseña");
			}
		});

		loginButton.addActionListener(e -> iniciarSesion());

		KeyAdapter enterKeyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					iniciarSesion();
				}
			}
		};

		formattedDni.addKeyListener(enterKeyListener);
		passwordField.addKeyListener(enterKeyListener);
		loadUserAndPassword();

	}

	private void iniciarSesion() {
		String username = formattedDni.getText();
		String password = new String(passwordField.getPassword());

		if (controller.existdni(username) && controller.authenticateUser(username, password)) {
			if (RadioButtonsaveuserpasswd.isSelected()) {
				saveUserAndPassword(username, password);
			}
			ventanaPrincipal = new VentanaPrincipal(username);
			ventanaPrincipal.setVisible(true);
			dispose();
		} else if (controller.existdni(username)) {
			JOptionPane.showMessageDialog(InicioSesion.this,
					"El usuario " + username + " existe pero la contraseña es incorrecta");
		} else {
			JOptionPane.showMessageDialog(InicioSesion.this, "El usuario " + username + " no existe");
		}
	}

	private void saveUserAndPassword(String username, String password) {
		try (BufferedWriter writer = new BufferedWriter(
				new FileWriter("src/main/resources/user_credentials.txt", false))) {
			writer.write("Usuario: " + username + ", Contraseña: " + password);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadUserAndPassword() {
		File file = new File("src/main/resources/user_credentials.txt");
		if (file.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line = reader.readLine();
				if (line != null && !line.trim().isEmpty()) {
					String[] parts = line.split(", ");
					if (parts.length == 2) {
						String username = parts[0].split(": ")[1];
						String password = parts[1].split(": ")[1];
						formattedDni.setText(username);
						passwordField.setText(password);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
