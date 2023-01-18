package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import clases.BaseDatos;
import clases.Evento;
import clases.Utilidades;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class VentanaPrincipal extends JFrame implements Runnable {

	//Elementos de la ventana
	private JPanel contentPane;
	private JFrame ventanaActual, ventanaAnterior, ventanaSiguiente;
	private JCalendar calendario;
	
	//Elementos JList
	private JList<Evento> lista;
	private DefaultListModel<Evento> modeloLista;
	private JScrollPane scrollLista;
	
	//Elementos para actualizar la imagen
	private ImageIcon imagen;
	private boolean f1 = true;
	private ImageIcon frame1 = new ImageIcon("imagenes/imagenventanaInicioSesion.png");;
	private ImageIcon frame2 = new ImageIcon("imagenes/icono_proyecto");
	private Thread t;
	private JPanel panelTaskMan = new JPanel();
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
					//frame.animationThread();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		
		//Elementos base y funcionalidad de la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 560);
		setTitle("Vista calendario");
		setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes/ventana_principal.png"));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		
		//Panel Central: Contenedor para todos los elementos
		JPanel pCentral = new JPanel();
		contentPane.add(pCentral, BorderLayout.CENTER);
		pCentral.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel pCentralIzq = new JPanel();
		pCentral.add(pCentralIzq);
		
		//Panel Izq. Arriba: Imagen de TaskMan
		JPanel pIzqArriba = new JPanel();
		pCentralIzq.add(pIzqArriba);
		//panelTaskMan.setPreferredSize(new Dimension(frame1.getIconWidth(), frame1.getIconHeight()));
		//pCentralIzq.add(panelTaskMan);
		
		ImageIcon frame1 = new ImageIcon("imagenes/imagenventanaInicioSesion.png");
		JLabel pIzqImagen = new JLabel(frame1);
		//panelTaskMan.add(pIzqImagen);
		pIzqArriba.add(pIzqImagen);
		
		//Panel Izq. Abajo: Botones "agenda", "contactos", "cerrar" y "eliminar usuario"
		JPanel pIzqAbajo = new JPanel();
		pCentralIzq.add(pIzqAbajo);
		pIzqAbajo.setLayout(new GridLayout(7, 1, 0, 0));
		
		JButton btnAgenda = new JButton("AGENDA");
		pIzqAbajo.add(btnAgenda);
		
		JLabel lblEspacio1 = new JLabel("");
		pIzqAbajo.add(lblEspacio1);
		
		JButton btnContactos = new JButton("CONTACTOS");
		pIzqAbajo.add(btnContactos);
		
		JLabel lblEspacio2 = new JLabel("");
		pIzqAbajo.add(lblEspacio2);
		
		JButton btnCerrar = new JButton("CERRAR SESIÓN");
		pIzqAbajo.add(btnCerrar);
		
		JLabel lblEspacio3 = new JLabel("");
		pIzqAbajo.add(lblEspacio3);
		
		JButton btnEliminarUsuario = new JButton("ELIMINAR CUENTA");
		pIzqAbajo.add(btnEliminarUsuario);
		
		
		//Panel Drch.: JLabel y JCalendar
		JPanel pCentralDrch = new JPanel();
		pCentral.add(pCentralDrch);
		pCentralDrch.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel pDrchArriba = new JPanel();
		pCentralDrch.add(pDrchArriba);
		
		JLabel lblCalendario = new JLabel("CALENDARIO");
		lblCalendario.setPreferredSize(new Dimension(200, 30));
		lblCalendario.setHorizontalAlignment(JLabel.CENTER);
		pDrchArriba.add(lblCalendario);
		
		JPanel pDrchAbajo = new JPanel(new BorderLayout());
		pCentralDrch.add(pDrchAbajo);
		
		calendario = new JCalendar();
		pDrchAbajo.add(calendario, BorderLayout.CENTER);
		
		modeloLista = new DefaultListModel<>();
		lista = new JList<Evento>(modeloLista);
		scrollLista = new JScrollPane(lista);
		scrollLista.setPreferredSize(new Dimension(new Dimension(300, 200)));
		pDrchArriba.add(scrollLista);
		
		//Eventos
		DefaultListCellRenderer render = (DefaultListCellRenderer)lista.getCellRenderer();
		render.setHorizontalAlignment(SwingConstants .CENTER);
		
		btnCerrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaAnterior = new VentanaInicioSesion();
				ventanaActual = VentanaPrincipal.this;
				JOptionPane.showMessageDialog(null, "Cerrando sesión...", "REGISTRADO", JOptionPane.INFORMATION_MESSAGE);
				ventanaAnterior.setVisible(true);
				ventanaActual.dispose();
			}
		});
		
		
		btnAgenda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaAnterior = VentanaPrincipal.this;
				ventanaActual = new VentanaAgenda();
				ventanaActual.setVisible(true);
				ventanaAnterior.dispose();
			}
		});
		
		
		btnContactos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaAnterior = VentanaPrincipal.this;
				ventanaActual = new VentanaContactos();
				ventanaActual.setVisible(true);
				ventanaAnterior.dispose();
			}
		});
		
		
		btnEliminarUsuario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaSiguiente = new VentanaInicioSesion();
				ventanaActual = VentanaPrincipal.this;
				BaseDatos.eliminarUsuario(BaseDatos.initBD("proyecto.db"), VentanaInicioSesion.nombreUsuario());
				ventanaSiguiente.setVisible(true);
				ventanaActual.dispose();		
			}
		});
		
		calendario.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Date d = calendario.getDate();
				String fecha = Utilidades.sdf.format(d);
				ArrayList<Evento> eventos = BaseDatos.obtenerEventosFechaUsuario(BaseDatos.initBD("proyecto.db"), VentanaInicioSesion.nombreUsuario(), fecha);
				modeloLista.removeAllElements();
				for(Evento e: eventos) {
					modeloLista.addElement(e);
				}
			}
		});

		
	}
	
	public void animationThread() {
		t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run(){
		while(t != null) {
			JLabel pIzqImagen;
			
			try {
				t.sleep(1000);
				if(f1 == true) {
					pIzqImagen = new JLabel(frame2);
					f1 = false;
					panelTaskMan.add(pIzqImagen);
					this.repaint();
					System.out.println("prueba");
					
				} else {
					pIzqImagen = new JLabel(frame1);
					f1 = true;
					System.out.println("false");
					panelTaskMan.add(pIzqImagen);
					this.repaint();
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}