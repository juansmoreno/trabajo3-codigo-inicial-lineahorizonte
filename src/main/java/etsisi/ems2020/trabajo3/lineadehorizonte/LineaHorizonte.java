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

        imprimirFusionLineas(lineaHorizonte1, lineaHorizonte2);    //Imprimios la fusión de las lineas

        FusionLineaHorizonte fusionLineaHorizonte = new FusionLineaHorizonte(lineaHorizonte1,lineaHorizonte2);

        LineaHorizonte salida = fusionLineaHorizonte.getSalida();

        int prev = fusionLineaHorizonte.getPrev();

        finalizarFusionLineasHorizonte(salida, lineaHorizonte1, prev); //si aun nos quedan elementos en el lineaHorizonte1
        finalizarFusionLineasHorizonte(salida, lineaHorizonte2, prev); //si aun nos quedan elementos en el lineaHorizonte2

        return salida;
    }


    public int anadirPuntoALineaHorizonte(LineaHorizonte salida, Punto pAux) {
        salida.addPunto(pAux); // añadimos el punto al LineaHorizonte de salida
        return pAux.getY();
    }

    public void finalizarFusionLineasHorizonte(LineaHorizonte salida, LineaHorizonte lineaHorizonte, int prev){

        while (!lineaHorizonte.isEmpty()) {
            Punto pAux=lineaHorizonte.getPunto(0); // guardamos en pAux el primer punto

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
