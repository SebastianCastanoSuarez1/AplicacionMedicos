package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

public class VentanaDarseBaja extends JFrame {

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
			VentanaDarseBaja dialog = new VentanaDarseBaja(dni);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaDarseBaja(String dni) {
		VentanaDarseBaja.dni = dni;
		setBounds(100, 100, 643, 502);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel titulo = new JLabel("Estas seguro que quieres darte de baja?");
		titulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		titulo.setForeground(new Color(128, 0, 0));
		titulo.setBounds(54, 181, 302, 51);
		contentPanel.add(titulo);
		ImageIcon logo = new ImageIcon("src\\main\\resources\\multimedia\\logo_Mongo.png");
		Image img = logo.getImage();
		Image newImg = img.getScaledInstance(295, 151, Image.SCALE_SMOOTH);
		logo = new ImageIcon(newImg);

		JLabel lblLogo = new JLabel();
		lblLogo.setBackground(new Color(0, 0, 0));
		lblLogo.setIcon(logo);
		lblLogo.setBounds(131, 10, 295, 151);
		contentPanel.add(lblLogo);

		lblLogo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(dni);
				ventanaPrincipal.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

		JLabel passwordLbl = new JLabel("Contraseña:");
		passwordLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		passwordLbl.setBounds(54, 242, 82, 35);
		contentPanel.add(passwordLbl);
		passwordField = new JPasswordField();
		passwordField.setBounds(229, 246, 200, 30);
		contentPanel.add(passwordField);

		showPasswordButton = new JRadioButton("Mostrar contraseña");
		showPasswordButton.setContentAreaFilled(false);
		showPasswordButton.setBounds(437, 245, 121, 30);
		contentPanel.add(showPasswordButton);

		JLabel lblConfirmarContrasea = new JLabel("Confirmar contraseña:");
		lblConfirmarContrasea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblConfirmarContrasea.setBounds(54, 287, 160, 35);
		contentPanel.add(lblConfirmarContrasea);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(229, 286, 200, 30);
		contentPanel.add(passwordField_1);

		JButton saveBtn = new JButton("Aceptar");
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
						irAlMenuPrincipal(dni);
					}
				} else {
					JOptionPane.showMessageDialog(contentPanel, "Las contraseñas no coinciden o son incorrectas.");
				}
			}

		});
		saveBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		saveBtn.setBounds(473, 387, 85, 30);
		contentPanel.add(saveBtn);

		JButton btnNewButton_1 = new JButton("Cancelar\r\n");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				irAlMenuPrincipal(dni);

			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_1.setBounds(365, 387, 98, 30);
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

	private void irAlMenuPrincipal(String dni) {
		ventanaPrincipal = new VentanaPrincipal(dni);
		ventanaPrincipal.setVisible(true);
		dispose();
	}
}
