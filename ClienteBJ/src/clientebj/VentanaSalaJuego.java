package clientebj;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import comunes.DatosBlackJack;

public class VentanaSalaJuego extends JInternalFrame {
	    
		private PanelJugador dealer, yo, jugador2;
		private JTextArea areaMensajes;
		private JButton pedir, plantar, reiniciar;
		private JPanel panelYo, panelBotones, yoFull, panelDealer,panelJugador2;
		
		private String yoId, jugador2Id, apuestaYo, apuestaOtro;
		//private DatosBlackJack datosRecibidos;
		private Escucha escucha;
		
		public VentanaSalaJuego(String yoId, String jugador2Id, String apuestaYo, String apuestaOtro) {
			this.yoId = yoId;
			this.jugador2Id = jugador2Id;
			this.apuestaYo = apuestaYo;
			this.apuestaOtro = apuestaOtro;
			//this.datosRecibidos=datosRecibidos;
						
			initGUI();
			
			//default window settings
			this.setTitle("Sala de juego BlackJack - Jugador: "+yoId);
			this.pack();
			this.setLocation((ClienteBlackJack.WIDTH-this.getWidth())/2, 
			         (ClienteBlackJack.HEIGHT-this.getHeight())/2);
			this.setResizable(false);
			this.show();
		}

		private void initGUI() {
			// TODO Auto-generated method stub
			//set up JFrame Container y Layout
	        
			//Create Listeners objects
			escucha = new Escucha();
			//Create Control objects
						
			//Set up JComponents
			panelDealer = new JPanel();
			dealer = new PanelJugador("Dealer");
			reiniciar = new JButton("reiniciar");
			reiniciar.addActionListener(escucha);
			panelDealer.add(dealer);
			panelDealer.add(reiniciar);
			add(panelDealer,BorderLayout.NORTH);		
			
			panelJugador2 = new JPanel();
			jugador2= new PanelJugador(jugador2Id+" apost�: "+apuestaOtro);	
			panelJugador2.add(jugador2);
			add(panelJugador2,BorderLayout.EAST);	
			
			areaMensajes = new JTextArea(8,18);
			JScrollPane scroll = new JScrollPane(areaMensajes);	
			Border blackline;
			blackline = BorderFactory.createLineBorder(Color.black);
			TitledBorder bordes;
			bordes = BorderFactory.createTitledBorder(blackline, "Area de Mensajes");
	        bordes.setTitleJustification(TitledBorder.CENTER);
			scroll.setBorder(bordes);
			areaMensajes.setOpaque(false);
			areaMensajes.setBackground(new Color(0, 0, 0, 0));
			areaMensajes.setEditable(false);

			scroll.getViewport().setOpaque(false);
			scroll.setOpaque(false);
			add(scroll,BorderLayout.CENTER);
			
			panelYo = new JPanel();
			panelYo.setLayout(new BorderLayout());
			yo = new PanelJugador(yoId+" apost�: "+apuestaYo);
			panelYo.add(yo);
				
			pedir = new JButton("Carta");
			pedir.setEnabled(false);
			pedir.addActionListener(escucha);
			plantar = new JButton("Plantar");
			plantar.setEnabled(false);
			plantar.addActionListener(escucha);
			panelBotones = new JPanel();
			panelBotones.add(pedir);
			panelBotones.add(plantar);
			
			yoFull = new JPanel();
			yoFull.setPreferredSize(new Dimension(206,100));
			yoFull.add(panelYo);
			yoFull.add(panelBotones);
			add(yoFull,BorderLayout.WEST);	
		}
		
		public void activarBotones(boolean turno) {
			pedir.setEnabled(turno);
			plantar.setEnabled(turno);
		}
		
		public void pintarCartasInicio(DatosBlackJack datosRecibidos) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(datosRecibidos.getIdJugadores()[0].equals(yoId)) {
						yo.pintarCartasInicio(datosRecibidos.getManoJugador1());
						jugador2.pintarCartasInicio(datosRecibidos.getManoJugador2());
					}else {
						yo.pintarCartasInicio(datosRecibidos.getManoJugador2());
						jugador2.pintarCartasInicio(datosRecibidos.getManoJugador1());
					}
					dealer.pintarCartasInicio(datosRecibidos.getManoDealer());
					
					areaMensajes.append(datosRecibidos.getMensaje()+"\n");
				}
			});
			
		}
		public void pintarCartasReinicio(DatosBlackJack datosRecibidos) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(datosRecibidos.getIdJugadores()[0].equals(yoId)) {
						yo.pintarCartasReinicio(datosRecibidos.getManoJugador1());
						jugador2.pintarCartasReinicio(datosRecibidos.getManoJugador2());
					}else {
						yo.pintarCartasReinicio(datosRecibidos.getManoJugador2());
						jugador2.pintarCartasReinicio(datosRecibidos.getManoJugador1());
					}
					dealer.pintarCartasReinicio(datosRecibidos.getManoDealer());
					
				}
			});
			
		}
		
		public void limpiar() {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					yo.limpiar();
					jugador2.limpiar();
					dealer.limpiar();
				}
				
			});
		}
		
		public void pintarTurno(DatosBlackJack datosRecibidos) {
			areaMensajes.append(datosRecibidos.getMensaje()+"\n");
			ClienteBlackJack cliente = (ClienteBlackJack)this.getTopLevelAncestor();
			
			if(datosRecibidos.getReiniciar() == true) {
				activarBotones(false);
				areaMensajes.setText(datosRecibidos.getMensaje()+"\n");	
				limpiar();
				pintarCartasReinicio(datosRecibidos);	
			}
			
			
			if(datosRecibidos.getJugador().contentEquals(yoId)){
				if(datosRecibidos.getJugadorEstado().equals("iniciar")) {
					activarBotones(true);
				}else {
					if(datosRecibidos.getJugadorEstado().equals("plant�") ){
						cliente.setTurno(false);
					}else {
						yo.pintarLaCarta(datosRecibidos.getCarta());
						if(datosRecibidos.getJugadorEstado().equals("vol�")) {
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									activarBotones(false);
									cliente.setTurno(false);
								}});		
						      }
						}
					} 
			 }else {//movidas de los otros jugadores
					if(datosRecibidos.getJugador().equals(jugador2Id)) {
						//mensaje para PanelJuego jugador2
						if(datosRecibidos.getJugadorEstado().equals("sigue")||
						   datosRecibidos.getJugadorEstado().equals("vol�")) {
							jugador2.pintarLaCarta(datosRecibidos.getCarta());
						}
					}else {
						//mensaje para PanelJuego dealer
						if(datosRecibidos.getJugadorEstado().equals("sigue") ||
						   datosRecibidos.getJugadorEstado().equals("vol�")	||
						   datosRecibidos.getJugadorEstado().equals("plant�")) {
							dealer.pintarLaCarta(datosRecibidos.getCarta());
						}
					}
				}			 	
		}
		
		//Ignorar
		public void partidaTerminada(DatosBlackJack datos) {
			System.out.println("Yes");
			if(datos.getEstadoJuego() == true) {
				//Dealer gan� (ambos jugadores perdieron); Dealer empat� (ambos jugadores empataron); Dealer perdi� (Ambos jugvadores ganaron)
				if(datos.getJugador().contentEquals(yoId)) {
					if(datos.getValorManos()[2] > datos.getValorManos()[0] && datos.getValorManos()[0] < 21) {
						int option = JOptionPane.showConfirmDialog(null, "Has perdido, tu apuesta ser� tomada. �Otra ronda?" , "Fin partida", 
								JOptionPane.YES_NO_OPTION);
						if(option == JOptionPane.YES_OPTION) {
							enviarDatos("reiniciar");
						}
						else {
							System.exit(0);
						}
					}
					if(datos.getValorManos()[2] < datos.getValorManos()[0] || datos.getValorManos()[2] > 21) {
						int valor;
						valor = 0;
						if(datos.getValorManos()[0] == 21 && datos.getManoJugador1().size() == 2) {
							valor = (Integer.valueOf(datos.getApuestaJugadores()[0])/2)*3;
						}
						else {
							valor = Integer.valueOf(datos.getApuestaJugadores()[0]);
						}
						int option = JOptionPane.showConfirmDialog(null, "Has ganado, se te pagar�: $"+valor+". �Otra ronda?" , "Fin partida", 
								JOptionPane.YES_NO_OPTION);
						if(option == JOptionPane.YES_OPTION) {
							enviarDatos("reiniciar");
						}
						else {
							System.exit(0);
						}
					}
					if(datos.getValorManos()[2] == datos.getValorManos()[0] && datos.getValorManos()[0] <= 21) {
						int option = JOptionPane.showConfirmDialog(null, "Has empatado con el dealer, conservar�s tu apuesta de: $"
								+datos.getApuestaJugadores()[0]+". �Otra ronda?" , "Fin partida", JOptionPane.YES_NO_OPTION);
						if(option == JOptionPane.YES_OPTION) {
							enviarDatos("reiniciar");
						}
						else {
							System.exit(0);
						}
					}
				}
				if(datos.getJugador().contentEquals(jugador2Id))  {
					{
						if(datos.getValorManos()[2] > datos.getValorManos()[1] && datos.getValorManos()[1] < 21) {
							int option = JOptionPane.showConfirmDialog(null, "Has perdido, tu apuesta ser� tomada. �Otra ronda?" , "Fin partida", 
									JOptionPane.YES_NO_OPTION);
							if(option == JOptionPane.YES_OPTION) {
								enviarDatos("reiniciar");
							}
							else {
								System.exit(0);
							}
						}
						if(datos.getValorManos()[2] < datos.getValorManos()[1] || datos.getValorManos()[2] > 21) {
							int valor;
							valor = 0;
							if(datos.getValorManos()[1] == 21 && datos.getManoJugador2().size() == 2) {
								valor = (Integer.valueOf(datos.getApuestaJugadores()[1])/2)*3;
							}
							else {
								valor = Integer.valueOf(datos.getApuestaJugadores()[1]);
							}
							int option = JOptionPane.showConfirmDialog(null, "Has ganado, se te pagar�: $"+valor+". �Otra ronda?" , "Fin partida", 
									JOptionPane.YES_NO_OPTION);
							if(option == JOptionPane.YES_OPTION) {
								enviarDatos("reiniciar");
							}
							else {
								System.exit(0);
							}
						}
						if(datos.getValorManos()[2] == datos.getValorManos()[1] && datos.getValorManos()[1] <= 21) {
							int option = JOptionPane.showConfirmDialog(null, "Has empatado con el dealer, conservar�s tu apuesta de: $"
									+datos.getApuestaJugadores()[1]+". �Otra ronda?" , "Fin partida", JOptionPane.YES_NO_OPTION);
							if(option == JOptionPane.YES_OPTION) {
								enviarDatos("reiniciar");
							}
							else {
								System.exit(0);
							}
						}
					}
				}
			}
		}
	   
	   private void enviarDatos(String mensaje) {
			// TODO Auto-generated method stub
		  ClienteBlackJack cliente = (ClienteBlackJack)this.getTopLevelAncestor();
		  cliente.enviarMensajeServidor(mensaje);
		}
		   
	  
	   private class Escucha implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			// TODO Auto-generated method stub
			if(actionEvent.getSource()==pedir) {
				//enviar pedir carta al servidor
				enviarDatos("pedir");				
			}
			if(actionEvent.getSource() == reiniciar) {
				enviarDatos("reiniciar");
			}
			if(actionEvent.getSource() == plantar) {
				//enviar plantar al servidor
				enviarDatos("plantar");
				activarBotones(false);
			}
		}
	   }
}
