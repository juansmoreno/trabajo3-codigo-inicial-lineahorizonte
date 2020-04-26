package etsisi.ems2020.trabajo3.lineadehorizonte;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;


public class LineaHorizonte {
	
	private ArrayList <Punto> LineaHorizonte ;
	
    /*
     * Constructor sin par�metros
     */
    public LineaHorizonte() {
        LineaHorizonte = new ArrayList <Punto>();
    }
            
    /*
     * m�todo que devuelve un objeto de la clase Punto
     */
    public Punto getPunto(int i) {
        return (Punto)this.LineaHorizonte.get(i);
    }
    
    // A�ado un punto a la l�nea del horizonte
    public void addPunto(Punto p)
    {
        LineaHorizonte.add(p);
    }    
    
    // m�todo que borra un punto de la l�nea del horizonte
    public void borrarPunto(int i)
    {
        LineaHorizonte.remove(i);
    }
    
    public int size()
    {
        return LineaHorizonte.size();
    }

    // m�todo que me dice si la l�nea del horizonte est� o no vac�a
    public boolean isEmpty()
    {
        return LineaHorizonte.isEmpty();
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
    	
    	for(int i=0; i< LineaHorizonte.size(); i++ ){
    		System.out.println(cadena(i));
    	}
    }
    
    public String cadena (int i){
    	Punto p = LineaHorizonte.get(i);
    	return p.toString();
    }

    public LineaHorizonte getLineaHorizonte(Ciudad ciudad)
    {
        // pi y pd, representan los edificios de la izquierda y de la derecha.
        int pi = 0;
        int pd = ciudad.size()-1;
        return crearLineaHorizonte(pi, pd, ciudad);
    }

    public LineaHorizonte crearLineaHorizonte(int pi, int pd, Ciudad ciudad){
        LineaHorizonte linea = new LineaHorizonte();
        java.util.List<Punto> puntos = Arrays.asList(new Punto(), new Punto());
        if(pi==pd){			//Si los edificios de la izquierda y de la derecha son iguales.
            guardarAltura(ciudad.getEdificio(pi), puntos);
            linea.addPunto(puntos.get(0));
            linea.addPunto(puntos.get(1));
        } else {
            int medio=(pi+pd)/2;

            LineaHorizonte s1 = this.crearLineaHorizonte(pi,medio,ciudad);
            LineaHorizonte s2 = this.crearLineaHorizonte(medio+1,pd,ciudad);
            linea = LineaHorizonteFussion(s1,s2);
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
    public LineaHorizonte LineaHorizonteFussion(LineaHorizonte s1,LineaHorizonte s2){

        // en estas variables guardaremos las alturas de los puntos anteriores, en s1y la del s1, en s2y la del s2
        // y en prev guardaremos la previa del segmento anterior introducido
        int s1y=-1, s2y=-1, prev=-1;
        LineaHorizonte salida = new LineaHorizonte(); // LineaHorizonte de salida
        imprimirFusionLineas(s1,s2);	//Imprimios la fusión de las lineas
        Punto paux = new Punto();  // Inicializamos la variable paux

        //Mientras tengamos elementos en s1 y en s2
        while ((!s1.isEmpty()) && (!s2.isEmpty())) {
            Punto p1 = s1.getPunto(0); // guardamos el primer elemento de s1
            Punto p2 = s2.getPunto(0); // guardamos el primer elemento de s2

            if (p2.esMaximoX(p1)) { // si X del s1 es menor que la X del s2
                paux = actualizarPaux(p1,s2y); //La X de paux sera la X de p1 y la Y sera el maximo entre la Y de p1 y s2y

                if (paux.esDistinto(prev)) { // si este maximo es distinto al del segmento anterior
                    prev = anadirPuntoALineaHorizonte(salida, paux);    //se añade paux a la solucion de LineaHorizonte y se guarda su Y en prev
                }
                s1y = actualizarAlturaLineaHorizonte(p1, s1);
            }
            else if (p1.esMaximoX(p2)) { // si X del s1 es mayor que la X del s2
                paux = actualizarPaux(p2,s1y); //La X de paux sera la X de p2 y la Y sera el maximo entre la Y de p2 y s1y

                if (paux.esDistinto(prev)){ // si este maximo es distinto al del segmento anterior
                    prev = anadirPuntoALineaHorizonte(salida, paux);    //se añade paux a la solucion de LineaHorizonte y se guarda su Y en prev
                }
                s2y = actualizarAlturaLineaHorizonte(p2, s2);
            }
            else { // si la X del s1 es igual a la X del s2
                if (p1.esMaximoY(p2) && (p1.esDistinto(prev))) { // guardaremos aquel punto que tenga la altura mas alta
                    prev = anadirPuntoALineaHorizonte(salida, p1);      //se añade paux a la solucion de LineaHorizonte y se guarda su Y en prev
                }
                if ((p2.esMaximoY(p1) || p2.esIgualY(p1)) && (p2.esDistinto(prev))){
                    prev = anadirPuntoALineaHorizonte(salida, p2);      //se añade paux a la solucion de LineaHorizonte y se guarda su Y en prev
                }
                s2y = actualizarAlturaLineaHorizonte(p2, s2);
                s1y = actualizarAlturaLineaHorizonte(p1, s1);
            }
        }

        while ((!s1.isEmpty())) { //si aun nos quedan elementos en el s1
            paux=s1.getPunto(0); // guardamos en paux el primer punto

            if (paux.esDistinto(prev)) { // si paux no tiene la misma altura del segmento previo
                prev = anadirPuntoALineaHorizonte(salida, paux);        //se añade paux a la solucion de LineaHorizonte y se guarda su Y en prev
            }
            s1.borrarPunto(0); // en cualquier caso eliminamos el punto de s1 (tanto si se añade como si no es valido)
        }

        while((!s2.isEmpty())) { //si aun nos quedan elementos en el s2
            paux=s2.getPunto(0); // guardamos en paux el primer punto

            if (paux.esDistinto(prev)) { // si paux no tiene la misma altura del segmento previo
                prev = anadirPuntoALineaHorizonte(salida, paux);        //se añade paux a la solucion de LineaHorizonte y se guarda su Y en prev
            }
            s2.borrarPunto(0); // en cualquier caso eliminamos el punto de s2 (tanto si se añade como si no es valido)
        }

        return salida;
    }

    public int actualizarAlturaLineaHorizonte(Punto p1, LineaHorizonte s1){
        s1.borrarPunto(0); // en cualquier caso eliminamos el punto de s1 (tanto si se añade como si no es valido)
        return p1.getY();   // actualizamos la altura s1y
    }

    public Punto actualizarPaux(Punto p1, int s2y) {
        Punto paux = new Punto(p1.getX(), p1.calcularMaximo(s2y));
        return paux;
    }

    public int anadirPuntoALineaHorizonte(LineaHorizonte salida, Punto paux) {
        salida.addPunto(paux); // añadimos el punto al LineaHorizonte de salida
        return paux.getY();
    }

    public void imprimirFusionLineas(LineaHorizonte s1,LineaHorizonte s2) {
        System.out.println("==== S1 ====");
        s1.imprimir();
        System.out.println("==== S2 ====");
        s2.imprimir();
        System.out.println("\n");
    }

}
