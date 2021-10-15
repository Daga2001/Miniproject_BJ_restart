package comunes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class DatosBlackJack implements Serializable{
	private String[] idJugadores;
	private String[] apuestaJugadores;
	private ArrayList<Carta> manoJugador1, manoJugador2, manoDealer;
	private int[] valorManos;
	private boolean reiniciar, terminoJuego;
	private Carta carta;
	private String mensaje;
	private String jugador,jugadorEstado;
	
	public boolean getEstadoJuego() {
		return terminoJuego;
	}
	
	public void setEstadoJuego(boolean estado) {
		terminoJuego = estado;
	}
	
	public String getJugador() {
		return jugador;
	}
	public void setJugador(String jugador) {
		this.jugador = jugador;
	}
	
	public String getJugadorEstado() {
		return jugadorEstado;
	}
	public void setJugadorEstado(String jugadorEstado) {
		this.jugadorEstado = jugadorEstado;
	}
		
	public String[] getIdJugadores() {
		return idJugadores;
	}
	public void setIdJugadores(String[] idJugadores) {
		this.idJugadores = idJugadores;
	}
	public String[] getApuestaJugadores() {//------------------------
		return apuestaJugadores;
	}
	public void setApuestaJugadores(String[] apuesta) {//----------------------
		apuestaJugadores = apuesta;
	}
	
	public ArrayList<Carta> getManoJugador1() {
		return manoJugador1;
	}
	public void setManoJugador1(ArrayList<Carta> manoJugador1) {
		this.manoJugador1 = manoJugador1;
	}
	
	public ArrayList<Carta> getManoJugador2() {
		return manoJugador2;
	}
	public void setManoJugador2(ArrayList<Carta> manoJugador2) {
		this.manoJugador2 = manoJugador2;
	}
	
	public ArrayList<Carta> getManoDealer() {
		return manoDealer;
	}
	public void setManoDealer(ArrayList<Carta> manoDealer) {
		this.manoDealer = manoDealer;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public void setValorManos(int[] valorManos) {
		this.valorManos=valorManos;
	}
	public int[] getValorManos() {
		return valorManos;	
	}
	public void setCarta(Carta carta) {
		this.carta=carta;
	}
	public Carta getCarta() {
		return carta;
	}
	public void setReiniciar(boolean reiniciar) {
		this.reiniciar=reiniciar;
	}
	public boolean getReiniciar() {
		return reiniciar;
	}
	
}
