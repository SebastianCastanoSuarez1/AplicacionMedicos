package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import controller.Controller;

public class InicioSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel usernameLabel;

	private Registro registro;
	private VentanaPrincipal ventanaPrincipal;
	private CambiarContrasena cambiarContrasena;

	private final Controller controller = new Controller();

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

		usernameField = new JTextField();
		usernameField.setBounds(200, 100, 200, 30);
		contentPane.add(usernameField);

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

		loginButton.addActionListener(e -> {
			String username = usernameField.getText();
			String password = new String(passwordField.getPassword());

			if (controller.existdni(username) && controller.authenticateUser(username, password)) {
				ventanaPrincipal = new VentanaPrincipal(username);
				ventanaPrincipal.setVisible(true);
				dispose();
			} else if (controller.existdni(username)) {
				JOptionPane.showMessageDialog(InicioSesion.this,
						"El usuario " + username + " existe pero la contraseña es incorrecta");
			} else {
				JOptionPane.showMessageDialog(InicioSesion.this, "El usuario " + username + " no existe");
			}
		});
	}
}
