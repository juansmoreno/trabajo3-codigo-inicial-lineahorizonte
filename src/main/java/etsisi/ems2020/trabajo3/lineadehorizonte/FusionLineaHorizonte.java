package etsisi.ems2020.trabajo3.lineadehorizonte;

public class FusionLineaHorizonte {

    private LineaHorizonte salida;
    private Punto pAux;
    private int yLineaHorizonte1;
    private int yLineaHorizonte2;
    private int prev;
    private LineaHorizonte lineaHorizonte1;
    private LineaHorizonte lineaHorizonte2;

    public FusionLineaHorizonte(LineaHorizonte lineaHorizonte1, LineaHorizonte lineaHorizonte2) {
        this.salida = new LineaHorizonte(); // LineaHorizonte de salida
        this.pAux = new Punto();  // Inicializamos la variable pAux
        this.yLineaHorizonte1 = -1;
        this.yLineaHorizonte2 = -1;
        this.prev = -1;
        this.lineaHorizonte1 = lineaHorizonte1;
        this.lineaHorizonte2 = lineaHorizonte2;

        fusionar();
    }

    public void fusionar(){
        //Mientras tengamos elementos en lineaHorizonte1 y en lineaHorizonte2, fusionamos
        while (!lineaHorizonte1.isEmpty() && !lineaHorizonte2.isEmpty()) {
            FusionPuntos fusionPuntos = new FusionPuntos(lineaHorizonte1.getPunto(0), lineaHorizonte2.getPunto(0)); // guardamos el primer elemento de lineaHorizonte2

            if (fusionPuntos.esMaximoXP2()) { // si X del lineaHorizonte1 es menor que la X del lineaHorizonte2
                pAux = fusionPuntos.getP1().actualizarPaux(yLineaHorizonte2); //La X de pAux sera la X de p1 y la Y sera el maximo entre la Y de p1 y yLineaHorizonte2
                //En cualquier caso eliminamos el punto de lineaHorizonte1 (tanto si se añade como si no es valido) y actualizamos la altura yLineaHorizonte1 a la de p1
                yLineaHorizonte1 = actualizarAlturaLineaHorizonte(fusionPuntos.getP1(), lineaHorizonte1);
            } else if (fusionPuntos.esMaximoXP1()) { // si X del lineaHorizonte1 es mayor que la X del lineaHorizonte2
                pAux = pAux = fusionPuntos.getP2().actualizarPaux(yLineaHorizonte1); //La X de pAux sera la X de p2 y la Y sera el maximo entre la Y de p2 y yLineaHorizonte1
                //En cualquier caso eliminamos el punto de lineaHorizonte2 (tanto si se añade como si no es valido) y actualizamos la altura yLieneaHorizonte2 a la de p2
                yLineaHorizonte2 = actualizarAlturaLineaHorizonte(fusionPuntos.getP2(), lineaHorizonte2);
            }

            if ((fusionPuntos.esMaximoXP2() || fusionPuntos.esMaximoXP1()) && pAux.esDistintoY(prev)) { // si este maximo de Y de pAux es distinto al del segmento anterior
                prev = anadirPuntoALineaHorizonte(salida, pAux);    //se añade pAux a la solucion de LineaHorizonte y se guarda su Y en prev
            }

            // si la X del lineaHorizonte1 es igual a la X del lineaHorizonte2
            if (fusionPuntos.esIgualX() && fusionPuntos.esMaximoYP1() && fusionPuntos.esDistintoYP1(prev)) { // guardaremos aquel punto que tenga la altura mas alta
                prev = anadirPuntoALineaHorizonte(salida, fusionPuntos.getP1());      //se añade pAux a la solucion de LineaHorizonte y se guarda su Y en prev
            } else if (fusionPuntos.esIgualX() && (fusionPuntos.esMaximoYP2() || fusionPuntos.esIgualY()) && fusionPuntos.esDistintoYP2(prev)) {
                prev = anadirPuntoALineaHorizonte(salida, fusionPuntos.getP2());      //se añade pAux a la solucion de LineaHorizonte y se guarda su Y en prev
            }

            if (fusionPuntos.esIgualX()) {
                yLineaHorizonte2 = actualizarAlturaLineaHorizonte(fusionPuntos.getP2(), lineaHorizonte2);
                yLineaHorizonte1 = actualizarAlturaLineaHorizonte(fusionPuntos.getP1(), lineaHorizonte1);
            }
        }
    }

    public int anadirPuntoALineaHorizonte(LineaHorizonte salida, Punto pAux) {
        salida.addPunto(pAux); // añadimos el punto al LineaHorizonte de salida
        return pAux.getY();
    }

    public int actualizarAlturaLineaHorizonte(Punto p1, LineaHorizonte lineaHorizonte){
        lineaHorizonte.borrarPunto(0); // en cualquier caso eliminamos el punto de lineaHorizonte (tanto si se añade como si no es valido)
        return p1.getY();   // actualizamos la altura yLineaHorizonte1
    }

    public LineaHorizonte getSalida(){
        return this.salida;
    }

    public int getPrev(){
        return this.prev;
    }

}




