package etsisi.ems2020.trabajo3.lineadehorizonte;

public class Main {

	public static void main(String[] args) {
		// Variables que necesitar� para posteriomente 
		// llamar a los distintos m�todos que he ido creando 
		// en las clases .
		
		/*
		 Empezamos a ejecutar el c�digo para intentar hacer el ejercicio
		 que nos piden, calcular la l�nea del horizonte de una ciudad.
		 */
        Ciudad ciudad = new Ciudad();
        ciudad.cargarEdificios("ciudad1.txt");
        
        // Creamos l�nea del horizonte
        LineaHorizonte linea = new LineaHorizonte();
        linea.getLineaHorizonte(ciudad);

        //Guardamos la linea del horizonte
        linea.guardaLineaHorizonte("salida.txt");
        System.out.println("-- Proceso finalizado Correctamente --");
        Punto  p2 = new Punto(5,6);
        System.out.println(p2);
	}

}
