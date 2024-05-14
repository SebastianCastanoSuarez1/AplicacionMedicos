package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VentanaCitas ventanaCitas;
	static String dni;
	private VentanaEditarPerfil vEditarPerfil;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal(dni);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaPrincipal(String dni) {
		VentanaPrincipal.dni = dni;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 621, 460);
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
	}

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
				JOptionPane.showMessageDialog(VentanaPrincipal.this, "Opción de Configuración seleccionada");
			}
		});
		menu.add(menuItemConfiguracion);

		JMenuItem menuItemCerrarSesion = new JMenuItem("Cerrar Sesión");
		menuItemCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(VentanaPrincipal.this, "Sesión cerrada");
			}
		});
		menu.add(menuItemCerrarSesion);

		menu.show(contentPane, 50, 252);
	}

	// Método para volver a la ventana principal
	private void volverAVentanaPrincipal() {
		VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(dni); // Crea una nueva instancia de VentanaPrincipal
		ventanaPrincipal.setVisible(true);
		dispose();
	}
}
