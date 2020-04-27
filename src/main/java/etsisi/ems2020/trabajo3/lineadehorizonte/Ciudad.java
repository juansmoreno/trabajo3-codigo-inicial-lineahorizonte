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
        this.ciudad = new ArrayList <Edificio>();

	    /*
	    for(int i=0;i<5;i++) {
	    	int xi=(int)(Math.random()*100);
	    	int y=(int)(Math.random()*100);
	    	int xd=(int)(xi+(Math.random()*100));
	        ciudad.add(new Edificio(Arrays.asList(xi,xd,y)));
	    }*/
	    
    }
        
    public Edificio getEdificio(int i) {
        return (Edificio)this.ciudad.get(i);
    }
       
    public void addEdificio (Edificio edificio) {
        ciudad.add(edificio);
    }

    public void removeEdificio(int i) {
        ciudad.remove(i);
    }

    public int size() {
        return ciudad.size();
    }

    /*
     Método que carga los edificios que me pasan en el
     archivo cuyo nombre se encuentra en "fichero".
     El formato del fichero nos lo ha dado el profesor en la clase del 9/3/2020,
     pocos días antes del estado de alarma.
     */

    public void cargarEdificios (String fichero) {
        try {
            Scanner scanner = new Scanner(new File(fichero));

            while(scanner.hasNext()) {
                int xi = scanner.nextInt();
                int xd = scanner.nextInt();
                int y = scanner.nextInt();

                this.addEdificio(new Edificio(Arrays.asList(xi,xd,y)));
            }
        } catch(Exception exception){
        	System.out.println(exception);
        }
    }

}
