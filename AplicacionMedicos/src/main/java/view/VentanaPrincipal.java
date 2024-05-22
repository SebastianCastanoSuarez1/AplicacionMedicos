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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VentanaCitas ventanaCitas;
	static String dni;

	private VentanaEditarPerfil vEditarPerfil;
	private CerrarSesion cerrarSesion;
	private VentanaTarjetaMedica ventanaTarjetaMedica;

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

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 161, 600, 22);
		contentPane.add(menuBar);

		JMenu menuPerfil = new JMenu("Perfil");
		menuPerfil.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		menuBar.add(menuPerfil);

		JMenuItem menuItemEditarPerfil = new JMenuItem("Editar perfil");
		menuItemEditarPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vEditarPerfil = new VentanaEditarPerfil(dni);
				vEditarPerfil.setVisible(true);
				dispose();
			}
		});
		menuPerfil.add(menuItemEditarPerfil);

		JMenuItem menuItemConfiguracion = new JMenuItem("Configuración");
		menuItemConfiguracion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(VentanaPrincipal.this, "Opción de Configuración seleccionada");
			}
		});
		menuPerfil.add(menuItemConfiguracion);

		JMenuItem menuItemCerrarSesion = new JMenuItem("Cerrar Sesión");
		menuItemCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cerrarSesion = new CerrarSesion(dni);
				cerrarSesion.setVisible(true);
				dispose();
			}
		});
		menuPerfil.add(menuItemCerrarSesion);

		JMenuItem menuItemDarseDeBaja = new JMenuItem("Darse de baja");
		menuPerfil.add(menuItemDarseDeBaja);

		JMenu menuCita = new JMenu("Cita");
		menuCita.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		menuBar.add(menuCita);

		JMenuItem menuItemPedirCita = new JMenuItem("Pedir Cita");
		menuItemPedirCita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventanaCitas = new VentanaCitas(dni);
				ventanaCitas.setVisible(true);
				dispose();
			}
		});
		menuCita.add(menuItemPedirCita);

		JMenu menuHistorial = new JMenu("Historial");
		menuHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		menuBar.add(menuHistorial);

		JMenuItem menuItemVerHistorial = new JMenuItem("Ver Historial");
		menuHistorial.add(menuItemVerHistorial);

		JMenu tarjetaMedicaJMenu = new JMenu("Tarjeta medica");
		menuBar.add(tarjetaMedicaJMenu);

		JMenuItem medicamentos = new JMenuItem("Ver medicamentos");
		medicamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventanaTarjetaMedica = new VentanaTarjetaMedica(dni);
				ventanaTarjetaMedica.setVisible(true);
				dispose();
			}
		});
		tarjetaMedicaJMenu.add(medicamentos);
	}

	private void volverAVentanaPrincipal() {
		VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(dni);
		ventanaPrincipal.setVisible(true);
		dispose();
	}
}
