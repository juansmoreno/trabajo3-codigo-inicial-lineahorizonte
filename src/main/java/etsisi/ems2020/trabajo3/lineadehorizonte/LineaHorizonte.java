package etsisi.ems2020.trabajo3.lineadehorizonte;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;


public class LineaHorizonte {
	
	private ArrayList <Punto> lineaHorizonte ;
	
    /*
     * Constructor sin par�metros
     */
    public LineaHorizonte() {
        this.lineaHorizonte = new ArrayList <Punto>();
    }
            
    /*
     * m�todo que devuelve un objeto de la clase Punto
     */
    public Punto getPunto(int i) {
        return (Punto)this.lineaHorizonte.get(i);
    }
    
    // A�ado un punto a la l�nea del horizonte
    public void addPunto(Punto punto)
    {
        lineaHorizonte.add(punto);
    }    
    
    // m�todo que borra un punto de la l�nea del horizonte
    public void borrarPunto(int i)
    {
        lineaHorizonte.remove(i);
    }
    
    public int size()
    {
        return lineaHorizonte.size();
    }

    // m�todo que me dice si la l�nea del horizonte est� o no vac�a
    public boolean isEmpty()
    {
        return lineaHorizonte.isEmpty();
    }
   
    /*
      M�todo al que le pasamos una serie de par�metros para poder guardar 
      la linea del horizonte resultante despu�s de haber resuelto el ejercicio
      mediante la t�cnica de divide y vencer�s.
     */
    
    public void guardaLineaHorizonte (String fichero){
        try{
            FileWriter fileWriter = new FileWriter(fichero);
            PrintWriter out = new PrintWriter (fileWriter);
     
            for(int i=0; i<this.size(); i++){
                
                out.println(cadena(i));
            }
            out.close();
        } catch(Exception e){
        	System.out.println(e);
        }
    }
 
    
    public void imprimir (){
    	
    	for(int i=0; i< lineaHorizonte.size(); i++ ){
    		System.out.println(cadena(i));
    	}
    }
    
    public String cadena (int i){
    	Punto punto = lineaHorizonte.get(i);
    	return punto.toString();
    }

    public LineaHorizonte getLineaHorizonte(Ciudad ciudad)
    {
        //representan los edificios de la izquierda y de la derecha.
        int numEdificiosIzq = 0;
        int numEdificiosDer = ciudad.size()-1;
        return crearLineaHorizonte(numEdificiosIzq, numEdificiosDer, ciudad);
    }

    public LineaHorizonte crearLineaHorizonte(int numEdificiosIzq, int numEdificiosDer, Ciudad ciudad){
        LineaHorizonte linea = new LineaHorizonte();
        java.util.List<Punto> puntos = Arrays.asList(new Punto(), new Punto());
        if(numEdificiosIzq == numEdificiosDer){			//Si los edificios de la izquierda y de la derecha son iguales.
            guardarAltura(ciudad.getEdificio(numEdificiosIzq), puntos);
            linea.addPunto(puntos.get(0));
            linea.addPunto(puntos.get(1));
        } else {
            int medio=(numEdificiosIzq + numEdificiosDer)/2;

            LineaHorizonte lineaHorizonte1 = this.crearLineaHorizonte(numEdificiosIzq,medio,ciudad);
            LineaHorizonte lineaHorizonte2 = this.crearLineaHorizonte(medio+1,numEdificiosDer,ciudad);
            linea = lineaHorizonteFussion(lineaHorizonte1,lineaHorizonte2);
        }
        return linea;
    }

    public void guardarAltura(Edificio edificio, java.util.List<Punto> list){
        list.get(0).setX(edificio.getXi());
        list.get(0).setY(edificio.getY());
        list.get(1).setX(edificio.getXd());
        list.get(1).setY(0);
    }

    /**
     * Función encargada de fusionar los dos LineaHorizonte obtenidos por la técnica divide y
     * vencerás. Es una función muy compleja ya que es la encargada de decidir si un
     * edificio solapa a otro, si hay edificios contiguos, etc. y solucionar dichos
     * problemas para que el LineaHorizonte calculado sea el correcto.
     */

    public LineaHorizonte lineaHorizonteFussion(LineaHorizonte lineaHorizonte1,LineaHorizonte lineaHorizonte2){
        LineaHorizonte salida = new LineaHorizonte(); // LineaHorizonte de salida
        imprimirFusionLineas(lineaHorizonte1,lineaHorizonte2);	//Imprimios la fusión de las lineas
        Punto paux = new Punto();  // Inicializamos la variable paux
        int s1y=-1;
        int s2y=-1;
        int prev=-1;
        // en estas variables guardaremos las alturas de los puntos p1 y p2 declarados mas abajo. En s1y la del lineaHorizonte1, en s2y la del lineaHorizonte2
        // y en prev guardaremos la previa del segmento anterior introducido

        //Mientras tengamos elementos en lineaHorizonte1 y en lineaHorizonte2
        while (!lineaHorizonte1.isEmpty() && !lineaHorizonte2.isEmpty()) {
            Punto p1 = lineaHorizonte1.getPunto(0); // guardamos el primer elemento de lineaHorizonte1
            Punto p2 = lineaHorizonte2.getPunto(0); // guardamos el primer elemento de lineaHorizonte2

            if (p2.esMaximoX(p1)) { // si X del lineaHorizonte1 es menor que la X del lineaHorizonte2
                paux = actualizarPaux(p1,s2y); //La X de paux sera la X de p1 y la Y sera el maximo entre la Y de p1 y s2y
                s1y = actualizarAlturaLineaHorizonte(p1, lineaHorizonte1);
            }

            else if (p1.esMaximoX(p2)) { // si X del lineaHorizonte1 es mayor que la X del lineaHorizonte2
                paux = actualizarPaux(p2,s1y); //La X de paux sera la X de p2 y la Y sera el maximo entre la Y de p2 y s1y
                s2y = actualizarAlturaLineaHorizonte(p2, lineaHorizonte2);
            }

            if (paux.esDistintoY(prev)) { // si este maximo es distinto al del segmento anterior
                prev = anadirPuntoALineaHorizonte(salida, paux);    //se añade paux a la solucion de LineaHorizonte y se guarda su Y en prev
            }

            if(p1.esIgualX(p2)){ // si la X del lineaHorizonte1 es igual a la X del lineaHorizonte2
                if (p1.esMaximoY(p2) && p1.esDistintoY(prev)) { // guardaremos aquel punto que tenga la altura mas alta
                    prev = anadirPuntoALineaHorizonte(salida, p1);      //se añade paux a la solucion de LineaHorizonte y se guarda su Y en prev
                }
                if ((p2.esMaximoY(p1) || p2.esIgualY(p1)) && p2.esDistintoY(prev)){
                    prev = anadirPuntoALineaHorizonte(salida, p2);      //se añade paux a la solucion de LineaHorizonte y se guarda su Y en prev
                }
                s2y = actualizarAlturaLineaHorizonte(p2, lineaHorizonte2);
                s1y = actualizarAlturaLineaHorizonte(p1, lineaHorizonte1);
            }
        }

        while (!lineaHorizonte1.isEmpty()) { //si aun nos quedan elementos en el lineaHorizonte1
            paux=lineaHorizonte1.getPunto(0); // guardamos en paux el primer punto

            if (paux.esDistintoY(prev)) { // si paux no tiene la misma altura del segmento previo
                prev = anadirPuntoALineaHorizonte(salida, paux);        //se añade paux a la solucion de LineaHorizonte y se guarda su Y en prev
            }
            lineaHorizonte1.borrarPunto(0); // en cualquier caso eliminamos el punto de lineaHorizonte1 (tanto si se añade como si no es valido)
        }

        while(!lineaHorizonte2.isEmpty()) { //si aun nos quedan elementos en el lineaHorizonte2
            paux=lineaHorizonte2.getPunto(0); // guardamos en paux el primer punto

            if (paux.esDistintoY(prev)) { // si paux no tiene la misma altura del segmento previo
                prev = anadirPuntoALineaHorizonte(salida, paux);        //se añade paux a la solucion de LineaHorizonte y se guarda su Y en prev
            }
            lineaHorizonte2.borrarPunto(0); // en cualquier caso eliminamos el punto de lineaHorizonte2 (tanto si se añade como si no es valido)
        }

        return salida;
    }

    public int actualizarAlturaLineaHorizonte(Punto p1, LineaHorizonte lineaHorizonte1){
        lineaHorizonte1.borrarPunto(0); // en cualquier caso eliminamos el punto de lineaHorizonte1 (tanto si se añade como si no es valido)
        return p1.getY();   // actualizamos la altura s1y
    }

    public Punto actualizarPaux(Punto p1, int s2y) {
        return new Punto(p1.getX(), p1.calcularMaximo(s2y));
    }

    public int anadirPuntoALineaHorizonte(LineaHorizonte salida, Punto paux) {
        salida.addPunto(paux); // añadimos el punto al LineaHorizonte de salida
        return paux.getY();
    }

    public void imprimirFusionLineas(LineaHorizonte lineaHorizonte1,LineaHorizonte lineaHorizonte2) {
        System.out.println("==== S1 ====");
        lineaHorizonte1.imprimir();
        System.out.println("==== S2 ====");
        lineaHorizonte2.imprimir();
        System.out.println("\n");
    }

}
