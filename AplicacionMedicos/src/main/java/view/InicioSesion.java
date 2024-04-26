package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class InicioSesion extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
    private JPasswordField passwordField;
    private JToggleButton showPasswordButton;
    private JLabel usernameLabel;
    private Registro registro; 
    
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
        contentPane.setLayout(null);
        setContentPane(contentPane);

        usernameLabel = new JLabel("DNI:");
        usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        usernameLabel.setBounds(132, 100, 100, 30);
        contentPane.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        usernameField.setBounds(200, 100, 200, 30);
        contentPane.add(usernameField);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        passwordLabel.setBounds(100, 147, 89, 30);
        contentPane.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 150, 200, 30);
        contentPane.add(passwordField);

        showPasswordButton = new JToggleButton("Mostrar contraseña");
        showPasswordButton.setBounds(411, 150, 121, 30);
        contentPane.add(showPasswordButton);
        
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

        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        loginButton.setBounds(290, 217, 150, 40);
        contentPane.add(loginButton);
        
        JButton registerButton = new JButton("Registrarse");
        registerButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        registerButton.setBounds(134, 217, 121, 40);
        contentPane.add(registerButton);
        registerButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		registro=new Registro();
        		registro.setVisible(true);
        		dispose();        		
        	}
        });
 
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí iría el código para verificar las credenciales y realizar el inicio de sesión
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                // Aquí deberías verificar las credenciales y realizar el inicio de sesión adecuado
                JOptionPane.showMessageDialog(InicioSesion.this, "Usuario: " + username + "\nContraseña: " + password);
            }
        });
    }
}
