package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private VentanaPedirCitas ventanaCitas;
    static String dni;

    private VentanaEditarPerfil vEditarPerfil;
    private VentanaDarseBaja cerrarSesion;
    private VentanaTarjetaMedica ventanaTarjetaMedica;
    private VentanaHistorialMedico ventanaHistorialMedico;
    private VerInformes verInformes;
    private VerCitas verCitas;
    private EliminarCitas eliminarCitas;
    private ModificarCita modificarCita;
    private InicioSesion inicioSesion;

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
    	setResizable(false);
        VentanaPrincipal.dni = dni;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 504, 426);
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
        lblLogo.setBounds(84, 0, 295, 151);
        contentPane.add(lblLogo);

        lblLogo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                volverAVentanaPrincipal();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBorder(new LineBorder(new Color(58, 58, 58)));
        menuBar.setBounds(0, 161, 620, 22); // Ajuste la anchura a 620 para que llegue casi hasta el fondo
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.X_AXIS));
        contentPane.add(menuBar);

        menuBar.add(Box.createHorizontalStrut(10)); // Espacio al inicio para mover el menú de Perfil

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

        JMenuItem menuItemCerrarSesion = new JMenuItem("Cerrar Sesión");
        menuItemCerrarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inicioSesion = new InicioSesion();
                inicioSesion.setVisible(true);
                dispose();
            }
        });
        menuPerfil.add(menuItemCerrarSesion);

        JMenuItem menuItemDarseDeBaja = new JMenuItem("Darse de baja");
        menuItemDarseDeBaja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrarSesion = new VentanaDarseBaja(dni);
                cerrarSesion.setVisible(true);
                dispose();
            }
        });
        menuPerfil.add(menuItemDarseDeBaja);

        menuBar.add(Box.createHorizontalStrut(30)); // Espacio entre menús

        JMenu menuCita = new JMenu("Cita");
        menuCita.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        menuBar.add(menuCita);

        JMenuItem menuItemPedirCita = new JMenuItem("Pedir Cita");
        menuItemPedirCita.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventanaCitas = new VentanaPedirCitas(dni);
                ventanaCitas.setVisible(true);
                dispose();
            }
        });
        menuCita.add(menuItemPedirCita);

        JMenuItem mntmNewMenuVerCita = new JMenuItem("Ver citas\r\n");
        mntmNewMenuVerCita.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                verCitas = new VerCitas(dni);
                verCitas.setVisible(true);
                dispose();
            }
        });
        menuCita.add(mntmNewMenuVerCita);

        JMenuItem mntmNewMenuModificarCitas = new JMenuItem("Modificar citas\r\n");
        mntmNewMenuModificarCitas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificarCita = new ModificarCita(dni);
                modificarCita.setVisible(true);
                dispose();
            }
        });
        menuCita.add(mntmNewMenuModificarCitas);

        JMenuItem mntmNewMenuEliminarCita = new JMenuItem("Eliminar Cita");
        mntmNewMenuEliminarCita.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarCitas = new EliminarCitas(dni);
                eliminarCitas.setVisible(true);
                dispose();
            }
        });
        menuCita.add(mntmNewMenuEliminarCita);

        menuBar.add(Box.createHorizontalStrut(30)); // Espacio entre menús

        JMenu menuHistorial = new JMenu("Historial");
        menuHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        menuBar.add(menuHistorial);

        JMenuItem menuItemVerHistorial = new JMenuItem("Ver Historial");
        menuItemVerHistorial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventanaHistorialMedico = new VentanaHistorialMedico(dni);
                ventanaHistorialMedico.setVisible(true);
                dispose();
            }
        });
        menuHistorial.add(menuItemVerHistorial);

        menuBar.add(Box.createHorizontalStrut(30)); // Espacio entre menús

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

        menuBar.add(Box.createHorizontalStrut(30)); // Espacio entre menús

        JMenu MenuInformes = new JMenu("Informes\r\n");
        menuBar.add(MenuInformes);

        JMenuItem mntmNewMenuItem = new JMenuItem("Ver informes\r\n");
        mntmNewMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                verInformes = new VerInformes(dni);
                verInformes.setVisible(true);
                dispose();
            }
        });
        MenuInformes.add(mntmNewMenuItem);

        // Añadir espacio horizontal para que llegue casi hasta el fondo
        menuBar.add(Box.createHorizontalGlue());
    }

    private void volverAVentanaPrincipal() {
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(dni);
        ventanaPrincipal.setVisible(true);
        dispose();
    }
}
