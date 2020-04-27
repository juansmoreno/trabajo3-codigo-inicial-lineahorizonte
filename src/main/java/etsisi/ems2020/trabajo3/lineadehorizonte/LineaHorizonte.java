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
        } catch(Exception exception){
        	System.out.println(exception);
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

        int prev = fusionarLineasHorizonte(salida, lineaHorizonte1, lineaHorizonte2);

        finalizarFusionLineasHorizonte(salida, lineaHorizonte1, prev);

        finalizarFusionLineasHorizonte(salida, lineaHorizonte2, prev);

        return salida;
    }

    public int actualizarAlturaLineaHorizonte(Punto p1, LineaHorizonte lineaHorizonte1){
        lineaHorizonte1.borrarPunto(0); // en cualquier caso eliminamos el punto de lineaHorizonte1 (tanto si se añade como si no es valido)
        return p1.getY();   // actualizamos la altura yLieneaHorizonte1
    }

    public Punto actualizarPaux(Punto p1, int yLineaHorizonte2) {
        return new Punto(p1.getX(), p1.calcularMaximo(yLineaHorizonte2));
    }

    public int anadirPuntoALineaHorizonte(LineaHorizonte salida, Punto pAux) {
        salida.addPunto(pAux); // añadimos el punto al LineaHorizonte de salida
        return pAux.getY();
    }

    public int fusionarLineasHorizonte(LineaHorizonte salida, LineaHorizonte lineaHorizonte1, LineaHorizonte lineaHorizonte2){
        Punto pAux = new Punto();  // Inicializamos la variable pAux
        int yLieneaHorizonte1=-1;
        int yLineaHorizonte2=-1;
        int prev = -1;

        // en estas variables guardaremos las alturas de los puntos p1 y p2 declarados mas abajo. En yLieneaHorizonte1 la del lineaHorizonte1, en yLineaHorizonte2 la del lineaHorizonte2
        // y en prev guardaremos la previa del segmento anterior introducido

        //Mientras tengamos elementos en lineaHorizonte1 y en lineaHorizonte2
        while (!lineaHorizonte1.isEmpty() && !lineaHorizonte2.isEmpty()) {
            Punto p1 = lineaHorizonte1.getPunto(0); // guardamos el primer elemento de lineaHorizonte1
            Punto p2 = lineaHorizonte2.getPunto(0); // guardamos el primer elemento de lineaHorizonte2

            if (p2.esMaximoX(p1)) { // si X del lineaHorizonte1 es menor que la X del lineaHorizonte2
                pAux = actualizarPaux(p1,yLineaHorizonte2); //La X de pAux sera la X de p1 y la Y sera el maximo entre la Y de p1 y yLineaHorizonte2
                yLieneaHorizonte1 = actualizarAlturaLineaHorizonte(p1, lineaHorizonte1);
            }

            else if (p1.esMaximoX(p2)) { // si X del lineaHorizonte1 es mayor que la X del lineaHorizonte2
                pAux = actualizarPaux(p2,yLieneaHorizonte1); //La X de pAux sera la X de p2 y la Y sera el maximo entre la Y de p2 y yLieneaHorizonte1
                yLineaHorizonte2 = actualizarAlturaLineaHorizonte(p2, lineaHorizonte2);
            }

            if ((p1.esMaximoX(p2) || p2.esMaximoX(p1)) && pAux.esDistintoY(prev)) { // si este maximo es distinto al del segmento anterior
                prev = anadirPuntoALineaHorizonte(salida, pAux);    //se añade pAux a la solucion de LineaHorizonte y se guarda su Y en prev
            }

            // si la X del lineaHorizonte1 es igual a la X del lineaHorizonte2
            if (p1.esIgualX(p2) && p1.esMaximoY(p2) && p1.esDistintoY(prev)) { // guardaremos aquel punto que tenga la altura mas alta
                prev = anadirPuntoALineaHorizonte(salida, p1);      //se añade pAux a la solucion de LineaHorizonte y se guarda su Y en prev
            }

            else if (p1.esIgualX(p2) && (p2.esMaximoY(p1) || p2.esIgualY(p1)) && p2.esDistintoY(prev)) {
                prev = anadirPuntoALineaHorizonte(salida, p2);      //se añade pAux a la solucion de LineaHorizonte y se guarda su Y en prev
            }

            if(p1.esIgualX(p2)) {
                yLineaHorizonte2 = actualizarAlturaLineaHorizonte(p2, lineaHorizonte2);
                yLieneaHorizonte1 = actualizarAlturaLineaHorizonte(p1, lineaHorizonte1);
            }
        }

        return prev;
    }

    public void finalizarFusionLineasHorizonte(LineaHorizonte salida, LineaHorizonte lineaHorizonte, int prev){
        Punto pAux = new Punto();  // Inicializamos la variable pAux
        while (!lineaHorizonte.isEmpty()) { //si aun nos quedan elementos en el lineaHorizonte1
            pAux=lineaHorizonte.getPunto(0); // guardamos en pAux el primer punto

            if (pAux.esDistintoY(prev)) { // si pAux no tiene la misma altura del segmento previo
                prev = anadirPuntoALineaHorizonte(salida, pAux);        //se añade pAux a la solucion de LineaHorizonte y se guarda su Y en prev
            }
            lineaHorizonte.borrarPunto(0); // en cualquier caso eliminamos el punto de lineaHorizonte1 (tanto si se añade como si no es valido)
        }
    }

    public void imprimirFusionLineas(LineaHorizonte lineaHorizonte1,LineaHorizonte lineaHorizonte2) {
        System.out.println("==== S1 ====");
        lineaHorizonte1.imprimir();
        System.out.println("==== S2 ====");
        lineaHorizonte2.imprimir();
        System.out.println("\n");
    }

}
