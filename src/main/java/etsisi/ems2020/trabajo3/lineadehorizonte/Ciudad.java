package etsisi.ems2020.trabajo3.lineadehorizonte;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/*
 Clase fundamental.
 Sirve para hacer la lectura del fichero de entrada que contiene los datos de como
 están situados los edificios en el fichero de entrada. xi, xd, h, siendo. Siendo
 xi la coordenada en X origen del edificio iésimo, xd la coordenada final en X, y h la altura del edificio.
 
 */
public class Ciudad {
	
    private ArrayList <Edificio> ciudad;

    public Ciudad(){
    	
    	/*
    	 * Generamos una ciudad de manera aleatoria para hacer 
    	 * pruebas.
    	 */
	ciudad = new ArrayList <Edificio>();
	this.metodoRandom(5);
	        
	ciudad = new ArrayList <Edificio>();
    }
    
        
    public Edificio getEdificio(int i) {
        return (Edificio)this.ciudad.get(i);
    }
    
       
    public void addEdificio (Edificio e)
    {
        ciudad.add(e);
    }
    public void removeEdificio(int i)
    {
        ciudad.remove(i);
    }
    
    public int size()
    {
        return ciudad.size();
    }
    
    public LineaHorizonte getLineaHorizonte()
    {
    	// pi y pd, representan los edificios de la izquierda y de la derecha.
        int pi = 0;                       
        int pd = ciudad.size()-1;      
        return crearLineaHorizonte(pi, pd);  
    }
    
    public LineaHorizonte crearLineaHorizonte(int pi, int pd){
        LineaHorizonte linea = new LineaHorizonte();
        java.util.List<Punto> puntos = Arrays.asList(new Punto(), new Punto());
        if(pi==pd){			//Si los edificios de la izquierda y de la derecha son iguales.
            guardarAltura(this.getEdificio(pi), puntos);
            linea.addPunto(puntos.get(0));
            linea.addPunto(puntos.get(1));
        } else {
            		int medio=(pi+pd)/2;
            	
            		LineaHorizonte s1 = this.crearLineaHorizonte(pi,medio);  
            		LineaHorizonte s2 = this.crearLineaHorizonte(medio+1,pd);
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
        
        Punto p1 = new Punto();         // punto donde guardaremos el primer punto del LineaHorizonte s1
        Punto p2 = new Punto();         // punto donde guardaremos el primer punto del LineaHorizonte s2
        
        imprimirFusionLineas(s1,s2);	//Imprimios la fusión de las lineas
        
        Punto paux = new Punto();  // Inicializamos la variable paux
        
        //Mientras tengamos elementos en s1 y en s2
        while ((!s1.isEmpty()) && (!s2.isEmpty())) {
        	paux = new Punto();
        	
            p1 = s1.getPunto(0); // guardamos el primer elemento de s1
            p2 = s2.getPunto(0); // guardamos el primer elemento de s2

            if (p1.getX() < p2.getX()) { // si X del s1 es menor que la X del s2
            
                paux.setX(p1.getX());                // guardamos en paux la X de p1
                paux.setY(p1.calcularMaximo(s2y)); // y hacemos que el maximo entre la Y del s1 y la altura previa del s2 sea la altura Y de paux
                
                if (paux.esDistinto(prev)) { // si este maximo no es igual al del segmento anterior
                
                	prev = anadirPuntoALineaHorizonte(salida, paux);
                }
                s1y = p1.getY();   // actualizamos la altura s1y
                s1.borrarPunto(0); // en cualquier caso eliminamos el punto de s1 (tanto si se añade como si no es valido)
            }
            else if (p1.getX() > p2.getX()) { // si X del s1 es mayor que la X del s2
            	
                paux.setX(p2.getX());                // guardamos en paux la X de p2
                paux.setY(Math.max(p2.getY(), s1y)); // y hacemos que el maximo entre la Y del s2 y la altura previa del s1 sea la altura Y de paux

                if (paux.esDistinto(prev)) // si este maximo no es igual al del segmento anterior
                {
                	prev = anadirPuntoALineaHorizonte(salida, paux);
                }
                s2y = p2.getY();   // actualizamos la altura s2y
                s2.borrarPunto(0); // en cualquier caso eliminamos el punto de s2 (tanto si se añade como si no es valido)
            }
            else { // si la X del s1 es igual a la X del s2
                if ((p1.getY() > p2.getY()) && (p1.esDistinto(prev))) { // guardaremos aquel punto que tenga la altura mas alta
                
                	prev = anadirPuntoALineaHorizonte(salida, p1);
                }
                if ((p1.getY() <= p2.getY()) && (p2.esDistinto(prev))){
                	
                	prev = anadirPuntoALineaHorizonte(salida, p2);
                }
                
                s1y = p1.getY();   // actualizamos la s1y e s2y
                s2y = p2.getY();
                s1.borrarPunto(0); // eliminamos el punto del s1 y del s2
                s2.borrarPunto(0);
            }
        }
        
        
        while ((!s1.isEmpty())) { //si aun nos quedan elementos en el s1
        
            paux=s1.getPunto(0); // guardamos en paux el primer punto
            
            if (paux.esDistinto(prev)) { // si paux no tiene la misma altura del segmento previo
            
            	prev = anadirPuntoALineaHorizonte(salida, paux);
            }
            s1.borrarPunto(0); // en cualquier caso eliminamos el punto de s1 (tanto si se añade como si no es valido)
        }
        
        
        while((!s2.isEmpty())) { //si aun nos quedan elementos en el s2
        
            paux=s2.getPunto(0); // guardamos en paux el primer punto
           
            if (paux.esDistinto(prev)) { // si paux no tiene la misma altura del segmento previo
            
            	prev = anadirPuntoALineaHorizonte(salida, paux);
            }
            s2.borrarPunto(0); // en cualquier caso eliminamos el punto de s2 (tanto si se añade como si no es valido)
        }
        
        return salida;
    }
    /*
     Método que carga los edificios que me pasan en el
     archivo cuyo nombre se encuentra en "fichero".
     El formato del fichero nos lo ha dado el profesor en la clase del 9/3/2020,
     pocos días antes del estado de alarma.
     */

    public void cargarEdificios (String fichero) {
//    	int n = 6;
//    	int i=0;
//        int xi,y,xd;
//        for(i=0;i<n;i++)
//        {
//            xi=(int)(Math.random()*100);
//            y=(int)(Math.random()*100);
//            xd=(int)(xi+(Math.random()*100));
//            this.addEdificio(new Edificio(xi,y,xd));
//        }
    	
        try {
        	
            int xi, y, xd;
            Scanner sr = new Scanner(new File(fichero));

            while(sr.hasNext()) {
                xi = sr.nextInt();
                xd = sr.nextInt();
                y = sr.nextInt();

                this.addEdificio(new Edificio(Arrays.asList(xi,xd,y)));
            }
        } catch(Exception e){
        	System.out.println(e);
        }
           
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

    
    public void metodoRandom(int n)
    {
        int i=0;
        int xi,y,xd;
        for(i=0;i<n;i++)
        {
            xi=(int)(Math.random()*100);
            y=(int)(Math.random()*100);
            xd=(int)(xi+(Math.random()*100));
            this.addEdificio(new Edificio(Arrays.asList(xi,xd,y)));
        }
        //HOLA MUNDO
    }
}
