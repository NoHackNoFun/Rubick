package Rubik;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

public class Nodo implements Comparable<Nodo>{

	private Nodo padre = null;
	private Estado estado;
	private int id;
	private int costo;
	private String accion;
	private double d;
	private double h;
	private double f;
	
	public Nodo(Estado estado, int costo, String accion, double d, Nodo padre, double f, int id) {
		this.id = id;
		this.padre = padre;
		this.estado = estado;
		this.costo = costo;
		this.accion = accion;
		this.d = d;
		this.f = f;
	}
	
	public Nodo(Estado estado, int costo, String accion, double d, Nodo padre, double f, int id, double h) {
		this.id = id;
		this.padre = padre;
		this.estado = estado;
		this.costo = costo;
		this.accion = accion;
		this.d = d;
		this.f = f;
		this.h = h;
	}
	
	@Override
	public int compareTo(Nodo nodo) {
        if(this.getF() > nodo.getF()) 
            return 1;
        else if (this.getF() < nodo.getF()) 
            return -1;
        else {
            return (this.getId() > nodo.getId())? 1 : -1;
        }
    }
	
    public boolean equals(Nodo o) {
        return Objects.equals(estado, o.estado);
    }

    public int hashCode() {
        return Objects.hash(estado);
    }

	public Nodo getPadre() {
		return padre;
	}

	public void setPadre(Nodo padre) {
		this.padre = padre;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	public double getF() {
		return f;
	}

	public void setF(double f) {
		this.f = f;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "[" + id + "]" + "([" + accion + "]" + estado.getCube().getMd5() + ",c=" + costo + ",p=" + d + ",f=" + ((double)Math.round(Math.abs(f)*100))/100;
	}
	
	public String toString2() {
		return "[" + id + "]" + "([" + accion + "]" + estado.getCube().getMd5() + ",c=" + costo + ",p=" + d + ",f=" + ((double)Math.round(Math.abs(f)*100))/100 + ",h=" + ((double)Math.round(Math.abs(h)*100))/100;
	}
	
	public String toString3() {
		if (padre != null)
			return "[" + id + "]" +  "[" + padre.getEstado().getCube().getMd5() + "]" +"([" + accion + "]" + estado.getCube().getMd5() + ",c=" + costo + ",p=" + d + ",f=" + f + ",h=" + h;
		else
			return "[" + id + "]" +  "[ NULL ]" +"([" + accion + "]" + estado.getCube().getMd5() + ",c=" + costo + ",p=" + d + ",f=" + f + ",h=" + h;
	}
}
