package clientebj;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaEntrada extends JInternalFrame {
	
	private JLabel bienvenida, labelNombre, apuesta;
	private JPanel ingreso;
	private JTextField nombreJugador, valorApuesta;
	private JButton ingresar;
	private VentanaEspera ventanaEspera;
	private ClienteBlackJack cliente;
	
	private Escucha escucha;
	
	public VentanaEntrada(ClienteBlackJack cliente) {
		this.cliente=cliente;
		initInternalFrame();
		
		this.setTitle("Bienvenido a Black Jack");
		this.pack();
		this.setLocation((ClienteBlackJack.WIDTH-this.getWidth())/2, 
				         (ClienteBlackJack.HEIGHT-this.getHeight())/2);
		this.show();
	}

	private void initInternalFrame() {
		// TODO Auto-generated method stub
		escucha = new Escucha();
		this.getContentPane().setLayout(new BorderLayout());
		bienvenida = new JLabel("Registre su nombre y valor de apuesta para ingresar");
		add(bienvenida, BorderLayout.NORTH);

		ingreso = new JPanel(); 
		labelNombre= new JLabel("Nombre"); 
		nombreJugador =	new JTextField(7); 
		ingresar = new JButton("Ingresar");
		ingresar.addActionListener(escucha);
		
		apuesta = new JLabel("Apuesta($)");
		valorApuesta = new JTextField(7);
		ingreso.add(labelNombre); ingreso.add(nombreJugador); 
		ingreso.add(apuesta); ingreso.add(valorApuesta); ingreso.add(ingresar);
		add(ingreso,BorderLayout.CENTER);
	}
	
	private Container getContainerFrames() {
		return this.getParent();
	}
    
	private void cerrarVentanaEntrada() {
		this.dispose();
	}
	
	private class Escucha implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			//cargar Sala de Espera y cerrar Ventana Entrada
			if(nombreJugador.getText().length()==0 || valorApuesta.getText().length() == 0) {
				//Aqui hacer una comparación para asegurarse que lo que se reciba de apuesta sea un valor numerico.
				try {
					Integer.parseInt(valorApuesta.getText());
				}
				catch(NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "¡Ingrese el valor de la apuesta sólo en números!");
				}
				finally {
					JOptionPane.showMessageDialog(null, "¡Debes ingresar un nombre y un valor de apuesta para comenzar!");
				}
			}
			else {
				cliente.setIdYo(nombreJugador.getText());
				cliente.setApuesta(valorApuesta.getText());
				
				ventanaEspera = new VentanaEspera(nombreJugador.getText()); 
				getContainerFrames().add(ventanaEspera);
				cliente.buscarServidor();
                cerrarVentanaEntrada();
			}	
		}
	}
	

}
