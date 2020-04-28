package etsisi.ems2020.trabajo3.lineadehorizonte;

/*
 * 
 * @author Juan Manuel
 * Clase para definir un punto sobre el eje
 * cartesiano de coordendas
 */
public class Punto {
	private int x;
    private int y;

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

    /*
    public double distancia (Punto p){
    	double cateto1 = x - p.getX();
    	double cateto2 = y - p.getY();
    	return Math.sqrt(cateto1 * cateto1 + cateto2 * cateto2);
    }
    */

    public Punto actualizarPaux(int yLineaHorizonte){
        return new Punto(this.getX(), this.calcularMaximoIntY(yLineaHorizonte));
    }

    public int calcularMaximoIntY(int entero) {
        return Math.max(this.getY(), entero);
    }

    public boolean esDistintoY (int prev) {
        return this.getY()!=prev;
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
