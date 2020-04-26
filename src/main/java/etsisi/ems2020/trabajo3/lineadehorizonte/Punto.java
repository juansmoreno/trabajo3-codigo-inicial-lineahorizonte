package etsisi.ems2020.trabajo3.lineadehorizonte;

/*
 * 
 * @author Juan Manuel
 * Clase para definir un punto sobre el eje
 * cartesiano de coordendas
 */
public class Punto {
	int x;
    int y;

    /*
     * Constructor sin par�metros de un punto en concreto
     */
    public Punto() {
        this.x = 0;
        this.y = 0;
    }
    
    /*
     * Constructos con par�metros de un punto
     * @param x
     * @param y
     */
    public Punto(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /*
     * 
     * Get de la coordenada X
     */
    public int getX() {
        return x;
    }
    
    /*
     * 
     * Set de la coordenada X
     */
    public void setX(int x) {
        this.x = x;
    }
    
    /*
 	   Get de la coordenada Y
     */
    public int getY() {
        return y;
    }
    
    /* 
     * Set de la coordenada Y
     */
    public void setY(int y) {
        this.y = y;
    }
    
    public double distancia (Punto b){          //PARECE UN METODO INUNTIL
    	double cateto1 = x - b.getX();
    	double cateto2 = y - b.getY();
    	double hipotenusa = Math.sqrt(cateto1*cateto1 + cateto2*cateto2);
    	return hipotenusa;
    }
    
    public boolean esMaximoX (Punto p2) {
    	return(p2.getX() < this.getX());
    }
    
    public boolean esMaximoY (Punto p2) {
    	return(p2.getY() < this.getY());
    }
    public boolean esDistinto(int prev) {
    	return(this.getY()!=prev);
    }
    public boolean esIgualY(Punto p2){
        return this.getY() == p2.getY();
    }

    public int calcularMaximo(int s2y) {
        return Math.max(this.getY(), s2y);
    }

	@Override
	public String toString() {
		String linea = "Punto [x=";
		linea = linea + x;
		linea = linea + ", y=";
		linea = linea + y;
		linea = linea +  "]";
		return linea;
	}
}
