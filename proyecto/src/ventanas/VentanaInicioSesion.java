	package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import clases.BaseDatos;
import clases.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.regex.Pattern;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * Ventana que muestra los paneles para iniciar sesion/registrarse
 */

public class VentanaInicioSesion extends JFrame {

	// Elementos de la ventana
	private JPanel contentPane;
	private JTextField txtNombreUsuario;
	private JPasswordField txtContrasenia;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtMail;
	private JTextField txtCreaNombreUsuario;
	private JPasswordField txtCreaContrasenia;
	private JFrame ventanaActual, ventanaSiguiente;
	static String nombre;

	// Base de datos
	static
	Connection con;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaInicioSesion frame = new VentanaInicioSesion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaInicioSesion() {

		con = BaseDatos.initBD("proyecto.db");
		BaseDatos.crearTablas(con);

		// Elementos base + funcionalidad de la ventana
		setTitle("¡BIENVENIDO!");
		setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes/iconoVentanaInicioSesion.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Guardar la ventana actual y la ventana que se abrirá al iniciar sesión
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		ventanaActual = this;
		ventanaSiguiente = new VentanaPrincipal();

		// Panel Norte: título de inicio sesión + registro
		JPanel pNorte = new JPanel();
		contentPane.add(pNorte, BorderLayout.NORTH);
		pNorte.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lblNorteIzq = new JLabel("INICIO SESIÓN");
		lblNorteIzq.setOpaque(false);
		lblNorteIzq.setFont(new Font("Tahoma", Font.BOLD, 16));
		pNorte.add(lblNorteIzq);

		JLabel lblNorteDrch = new JLabel("RESGISTRO");
		lblNorteDrch.setFont(new Font("Tahoma", Font.BOLD, 16));
		pNorte.add(lblNorteDrch);

		// Panel Sur: botones de inicio sesión + registro
		JPanel pSur = new JPanel();
		contentPane.add(pSur, BorderLayout.SOUTH);

		JButton btnInicioSesion = new JButton("INICIAR SESIÓN");
		btnInicioSesion.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnInicioSesion.setBackground(new Color(123, 173, 226));
		pSur.add(btnInicioSesion);

		btnInicioSesion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				iniciarSesion();
			}
		});

		JButton btnRegistrar = new JButton("REGISTRAR");
		btnRegistrar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRegistrar.setBackground(new Color(123, 173, 226));
		pSur.add(btnRegistrar);

		btnRegistrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				registrarse();
			}
		});

		// Panel Centro Izq.: Sección de inicio de sesión
		JPanel pCentro = new JPanel();
		contentPane.add(pCentro, BorderLayout.CENTER);
		pCentro.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel pCentroIzq = new JPanel();
		pCentro.add(pCentroIzq);
		pCentroIzq.setLayout(new GridLayout(2, 2, 0, 0));

		JLabel lblNombreUsuario = new JLabel("NOMBRE USUARIO");
		pCentroIzq.add(lblNombreUsuario);

		txtNombreUsuario = new JTextField();
		pCentroIzq.add(txtNombreUsuario);
		txtNombreUsuario.setColumns(10);

		JLabel lblContrasenia = new JLabel("CONTRASEÑA");
		pCentroIzq.add(lblContrasenia);

		txtContrasenia = new JPasswordField();
		pCentroIzq.add(txtContrasenia);
		txtContrasenia.setColumns(10);

		// Panel Centro Drch.: Sección registro
		JPanel pCentroDrch = new JPanel();
		pCentro.add(pCentroDrch);
		pCentroDrch.setLayout(new GridLayout(5, 2, 0, 0));

		JLabel lblNombre = new JLabel("NOMBRE");
		pCentroDrch.add(lblNombre);

		txtNombre = new JTextField();
		pCentroDrch.add(txtNombre);
		txtNombre.setColumns(10);

		JLabel lblApellido = new JLabel("APELLIDO");
		pCentroDrch.add(lblApellido);

		txtApellido = new JTextField();
		pCentroDrch.add(txtApellido);
		txtApellido.setColumns(10);

		JLabel lblMail = new JLabel("MAIL");
		pCentroDrch.add(lblMail);

		txtMail = new JTextField();
		pCentroDrch.add(txtMail);
		txtMail.setColumns(10);

		JLabel lblCreaNombreUsuario = new JLabel("NOMBRE USUARIO");
		pCentroDrch.add(lblCreaNombreUsuario);

		txtCreaNombreUsuario = new JTextField();
		pCentroDrch.add(txtCreaNombreUsuario);
		txtCreaNombreUsuario.setColumns(10);

		JLabel lblCreaContrasenia = new JLabel("CONTRASEÑA");
		pCentroDrch.add(lblCreaContrasenia);

		txtCreaContrasenia = new JPasswordField();
		pCentroDrch.add(txtCreaContrasenia);
		txtCreaContrasenia.setColumns(10);
	
		txtContrasenia.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					iniciarSesion();
				}
			}
		});
		
		txtCreaContrasenia.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					registrarse();
				}
			}
		});
	
	}

	/**
	 * Iniciar sesion del usuario con los datos ingresados en la ventana
	 */
	private void iniciarSesion() {
		String nomUsu = txtNombreUsuario.getText();
		String contra = txtContrasenia.getText();
		Usuario u = BaseDatos.obtenerDatosUsuario(con, nomUsu);
		if (u != null) {
			if (u.getContrasenia().equals(contra)) {
				nombre = nomUsu;
				JOptionPane.showMessageDialog(null, "Bienvenido", "SESIÓN INICIADA", JOptionPane.INFORMATION_MESSAGE);
				ventanaSiguiente.setVisible(true);
				ventanaActual.dispose();

			} else {
				JOptionPane.showMessageDialog(null, "La contraseña no es correcta", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "No existe un usuarion con ese nombre", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
		txtNombreUsuario.setText("");
		txtContrasenia.setText("");
		txtNombreUsuario.requestFocus();
	}

	/**
	 * Registrar el usuario con los datos ingresados
	 */
	private void registrarse() {
		String nom = txtNombre.getText();
		String ape = txtApellido.getText();
		String mail = txtMail.getText();
		String comprobarUsuario= "[A-Za-z]{1,}";
		String comprobarMail = "[A-Za-z0-9]{1,}[@]?[a-z]{1,}[.]?[a-z]{2,}";
		String nomUsu = txtCreaNombreUsuario.getText();
		String cont = txtCreaContrasenia.getText();
		boolean bienUsuario = Pattern.matches(comprobarUsuario, nomUsu);
		boolean bienMail = Pattern.matches(comprobarMail, mail);
		if (!bienMail) {
			JOptionPane.showMessageDialog(null, "El mail no tiene el formato correcto", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		} else {
			if (!bienUsuario) {
				JOptionPane.showMessageDialog(null, "El usuario no tiene el formato correcto", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			} else {
				boolean existeUsuario = BaseDatos.buscarUsuario(con, nomUsu);
				if (!existeUsuario) {
					boolean existeMail = BaseDatos.buscarMailUsuario(con, mail);
					if (!existeMail) {
						BaseDatos.insertarUsuario(con, nom, ape, mail, nomUsu, cont);
						JOptionPane.showMessageDialog(null, "Registro realizado con éxito", "REGISTRADO",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese mail", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					}
				
				} else {
					JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese nombre", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		txtNombre.setText("");
		txtApellido.setText("");
		txtMail.setText("");
		txtCreaNombreUsuario.setText("");
		txtCreaContrasenia.setText("");
		txtNombre.requestFocus();
	}

	/**
	 * Guardar el nombre del usuario para poder acceder a el desde otras ventanas
	 * @return nombre	"Nickname" del usuario
	 */
	public static String nombreUsuario() {
		return nombre;
	}

}