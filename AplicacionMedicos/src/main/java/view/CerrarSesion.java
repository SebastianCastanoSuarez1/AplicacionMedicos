package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import org.bson.Document;

import controller.Controller;

public class CerrarSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private JPasswordField passwordField;
	private JRadioButton showPasswordButton;
	private JPasswordField passwordField_1;
	static String dni;
	
	private VentanaPrincipal ventanaPrincipal;

	private final Controller controller = new Controller();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CerrarSesion dialog = new CerrarSesion(dni);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CerrarSesion(String dni) {
		CerrarSesion.dni = dni;
		setBounds(100, 100, 624, 451);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		ImageIcon logo = new ImageIcon("src\\main\\resources\\multimedia\\logo_Mongo.png");
		Image img = logo.getImage();
		Image newImg = img.getScaledInstance(295, 151, Image.SCALE_SMOOTH);
		logo = new ImageIcon(newImg);

		JLabel lblLogo = new JLabel();
		lblLogo.setIcon(logo);
		lblLogo.setBounds(130, 0, 295, 151);
		contentPanel.add(lblLogo);

		JLabel titulo = new JLabel("Estas seguro que quieres darte de baja?");
		titulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		titulo.setForeground(new Color(128, 0, 0));
		titulo.setBounds(40, 144, 302, 51);
		contentPanel.add(titulo);

		JLabel passwordLbl = new JLabel("Contraseña:");
		passwordLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		passwordLbl.setBounds(40, 205, 82, 35);
		contentPanel.add(passwordLbl);
		passwordField = new JPasswordField();
		passwordField.setBounds(215, 209, 200, 30);
		contentPanel.add(passwordField);

		showPasswordButton = new JRadioButton("Mostrar contraseña");
		showPasswordButton.setContentAreaFilled(false);
		showPasswordButton.setBounds(423, 208, 121, 30);
		contentPanel.add(showPasswordButton);

		JLabel lblConfirmarContrasea = new JLabel("Confirmar contraseña:");
		lblConfirmarContrasea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblConfirmarContrasea.setBounds(40, 250, 160, 35);
		contentPanel.add(lblConfirmarContrasea);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(215, 249, 200, 30);
		contentPanel.add(passwordField_1);

		JButton saveBtn = new JButton("Siguiente");
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Optional<Document> documento = controller.findByDni(dni);
				String password = new String(passwordField.getPassword());
				String confirmarPassword = new String(passwordField_1.getPassword());
				Document paciente = documento.get();
				if (password.equals(paciente.getString("Contraseña"))
						&& confirmarPassword.equals(paciente.getString("Contraseña"))) {
					int response = JOptionPane.showConfirmDialog(saveBtn,
							"¿Estás seguro que quieres darte de baja? Se perderan todos tus datos", "Confirmación",
							JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						JOptionPane.showMessageDialog(contentPanel, "Has sido dado de baja.");
						controller.eliminarPaciente(dni);
						dispose();
					} else if (response == JOptionPane.NO_OPTION) {
						JOptionPane.showMessageDialog(contentPanel, "No se ha dado de baja.");
						ventanaPrincipal = new VentanaPrincipal(dni);
					}
				} else {
					JOptionPane.showMessageDialog(contentPanel, "Las contraseñas no coinciden o son incorrectas.");
				}
			}
		});
		saveBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		saveBtn.setBounds(459, 350, 85, 30);
		contentPanel.add(saveBtn);

		JButton btnNewButton_1 = new JButton("Cancelar\r\n");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_1.setBounds(351, 350, 98, 30);
		contentPanel.add(btnNewButton_1);

		showPasswordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (showPasswordButton.isSelected()) {
					passwordField.setEchoChar((char) 0);
				} else {
					passwordField.setEchoChar('\u2022');
				}
			}
		});
	}
}
