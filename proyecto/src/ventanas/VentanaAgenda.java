package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import clases.BaseDatos;
import clases.Evento;
import clases.TipoEvento;
import clases.TipoEventos;

public class VentanaAgenda extends JFrame {

	private JPanel contentPane;
	private JFrame ventanaActual, ventanaAnterior;
	
	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JScrollPane scrollTabla;
	
	private int filaSeleccionada = -1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAgenda frame = new VentanaAgenda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	public VentanaAgenda() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 560);
		setTitle("Agenda");
		setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes/ventana_principal.png"));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);
		
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		//Panel Central: Contenedor para todos los elementos
		JPanel pCentral = new JPanel();
		contentPane.add(pCentral, BorderLayout.CENTER);
		pCentral.setLayout(new GridLayout());

		//Panel CentralIzq: JTable
		JPanel pCentralIzq = new JPanel();
		pCentral.add(pCentralIzq);
		
				
		modeloTabla = new DefaultTableModel();
		tabla = new JTable(modeloTabla);
		scrollTabla = new JScrollPane(tabla);
		String [] titulos = {"C??DIGO","NOMBRE","FECHA","DURACI??N","TIPO", "COMPLETO"};
		modeloTabla.setColumnIdentifiers(titulos);
		cargarModelo();
		pCentralIzq.add(scrollTabla);
		
		JPanel pCentralDch = new JPanel();
		pCentral.add(pCentralDch);
		
		//Panel Dch. Arriba: Imagen TaskMan
		JPanel pDchArriba = new JPanel();
		pCentralDch.add(pDchArriba);
		ImageIcon imagen = new ImageIcon("imagenes/imagenventanaInicioSesion.png");
		JLabel pDchImagen = new JLabel(imagen);
		pDchArriba.add(pDchImagen);
		
		//Panel Dch. Abajo: Botones "a??adir", "eliminar", "editar" y "cerrar"
		JPanel pDchAbajo = new JPanel();
		pCentralDch.add(pDchAbajo);
		pDchAbajo.setLayout(new GridLayout(9, 1 , 0, 0));
		
		JButton btnAnyadirEvento = new JButton("A??ADIR EVENTO");
		pDchAbajo.add(btnAnyadirEvento);
		
		JLabel lblEspacio1 = new JLabel("");
		pDchAbajo.add(lblEspacio1);
		
		JButton btnEliminarEvento = new JButton("ELIMINAR EVENTO");
		pDchAbajo.add(btnEliminarEvento);
		
		JLabel lblEspacio2 = new JLabel("");
		pDchAbajo.add(lblEspacio2);
		
		JButton btnEditarEvento = new JButton("EDITAR EVENTO");
		pDchAbajo.add(btnEditarEvento);
		
		JLabel lblEspacio3 = new JLabel();
		pDchAbajo.add(lblEspacio3);
		
		JButton btnAniadirTipoEvento = new JButton("A??ADIR TIPO DE EVENTO");
		pDchAbajo.add(btnAniadirTipoEvento);
		
		JLabel lblEspacio4 = new JLabel();
		pDchAbajo.add(lblEspacio4);
		
		JButton btnCerrar = new JButton("CERRAR");
		pDchAbajo.add(btnCerrar);
		
		//Panel Sur: Bot??n "Eliminar todos los eventos"
		
		JPanel pSur = new JPanel();
		contentPane.add(pSur, BorderLayout.SOUTH);
		
		JButton btnEliminarTodosEventos = new JButton("ELIMINAR TODOS LOS EVENTOS");
		pSur.add(btnEliminarTodosEventos);
		
		//Eventos
		
		btnAniadirTipoEvento.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String nuevoTipo = JOptionPane.showInputDialog("Introduce el nuevo tipo de evento: ");
				nuevoTipo = nuevoTipo.toUpperCase();
				if(TipoEventos.getTipoEventos().esta(nuevoTipo)==-1) {
					TipoEventos.getTipoEventos().aniadirTipo(nuevoTipo);
					BaseDatos.insertarTipoEvento(VentanaInicioSesion.con, nuevoTipo);
					
				}else {
					JOptionPane.showMessageDialog(null, "Ese tipo de evento ya existe", "TIPO REPETIDO", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnAnyadirEvento.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String fecha = JOptionPane.showInputDialog("Introduce el la fecha del evento, siguiendo el formato yyyy/MM/dd: ", JOptionPane.QUESTION_MESSAGE);
				String nombre = JOptionPane.showInputDialog("Introduce el nombre del nuevo evento: ", JOptionPane.QUESTION_MESSAGE);
				Object tipo = JOptionPane.showInputDialog(null, "Elija el tipo de evento", "Nuevo evento", JOptionPane.QUESTION_MESSAGE,
						null, TipoEventos.getTipoEventos().getTipos(), TipoEventos.getTipoEventos().getTipos()[0]);
				String duracion = JOptionPane.showInputDialog("Introduce la duraci??n del evento: ", JOptionPane.QUESTION_MESSAGE);
				if(fecha!=null && nombre!=null && tipo!=null && duracion!=null) {
					BaseDatos.insertarEvento(VentanaInicioSesion.con, VentanaInicioSesion.nombre, fecha, nombre, String.valueOf(tipo), Integer.parseInt(duracion), "false");
					JOptionPane.showMessageDialog(null, "El evento se ha creado correctamente", "??Bien hecho!", JOptionPane.PLAIN_MESSAGE);
					while(modeloTabla.getRowCount()>0) {
						modeloTabla.removeRow(0);
					}
					cargarModelo();
				}
			}
		});
		
		
		btnEliminarEvento.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(filaSeleccionada!=-1) {
					Integer codigo = (Integer)modeloTabla.getValueAt(filaSeleccionada, 0);
					modeloTabla.removeRow(filaSeleccionada);
					BaseDatos.eliminarEvento(VentanaInicioSesion.con, codigo);
				}
			}
		});
	
		btnEditarEvento.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String fecha = JOptionPane.showInputDialog("Introduce la nueva fecha del evento, siguiendo el formato yyyy/MM/dd: ", JOptionPane.QUESTION_MESSAGE);
				String nombre = JOptionPane.showInputDialog("Introduce el nombre del nuevo evento: ", JOptionPane.QUESTION_MESSAGE);
				Object tipo = JOptionPane.showInputDialog(null, "Elija el tipo de evento", "Nuevo evento", JOptionPane.QUESTION_MESSAGE,
						null, TipoEventos.getTipoEventos().getTipos(), TipoEventos.getTipoEventos().getTipos()[0]);
				String duracion = JOptionPane.showInputDialog("Introduce la duraci??n del evento: ", JOptionPane.QUESTION_MESSAGE);
				int row = tabla.getSelectedRow();
				Integer codigo = (Integer )modeloTabla.getValueAt(row, 0);
				BaseDatos.updateEvento(VentanaInicioSesion.con, String.valueOf(codigo), fecha, nombre, String.valueOf(tipo), Integer.parseInt(duracion));
				JOptionPane.showMessageDialog(null, "El evento se ha actualizado correctamente", "??Bien hecho!", JOptionPane.PLAIN_MESSAGE);
				while(modeloTabla.getRowCount()>0) {
					modeloTabla.removeRow(0);
				}
				cargarModelo();
			}
		});
		
		btnCerrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaAnterior = new VentanaPrincipal();
				ventanaActual = VentanaAgenda.this;
				ventanaAnterior.setVisible(true);
				ventanaActual.dispose();
			}
		});
		
		btnEliminarTodosEventos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirmacion = JOptionPane.showConfirmDialog(btnEliminarTodosEventos, "??Est?? seguro de que quiere eliminar todos los datos?");
				if(confirmacion==0) {
					while(modeloTabla.getRowCount()>0) {
						modeloTabla.removeRow(0);
					}
				}
			}
		});
		
		tabla.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				int fila = e.getFirstRow();
				int col = e.getColumn();
				if(col == 5) {
					int cod = ((Integer)modeloTabla.getValueAt(fila, 0));
					boolean valor = (Boolean) modeloTabla.getValueAt(fila, 5);
					BaseDatos.updateCompleto(VentanaInicioSesion.con, cod, valor);
				}
			}
		});
		
		tabla.setDefaultRenderer(Object.class, (table,value,isSelected,hasFocus,row,column)->{
			JLabel label = new JLabel(value.toString());
			boolean valor = (Boolean) modeloTabla.getValueAt(row, 5);
			if(valor==true) {
				label.setOpaque(true);
				label.setBackground(new Color(112, 219, 147));
			}else {
				label.setOpaque(true);
				label.setBackground(new Color(255, 64, 64));
			}
			return label;
			
		});
		
		tabla.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int col = tabla.getSelectedColumn();
				filaSeleccionada = tabla.getSelectedRow();
				if(col==5) {
					tabla.repaint();
				}
				
			}
		});
		
	}
	
	private void addCheckBox(int col, JTable t) {
		TableColumn tc = t.getColumnModel().getColumn(col);
		tc.setCellEditor(t.getDefaultEditor(Boolean.class));
		tc.setCellRenderer(t.getDefaultRenderer(Boolean.class));
	}
	
	private void cargarModelo() {
		ArrayList<Evento> a = BaseDatos.obtenerEventosUsuario(VentanaInicioSesion.con, VentanaInicioSesion.nombre);
		int f = 0;
		for(Evento e: a) {
			Object [] fila = {e.getCodigo(),e.getNombre(), e.getFecha(),e.getDuracion(), e.getTipo(), e.getUsuario()};
			modeloTabla.addRow(fila);
			addCheckBox(5, tabla);
			modeloTabla.setValueAt(e.isCompleto(), f, 5);
			f++;
		}
	}
}