package etsisi.ems2020.trabajo3.lineadehorizonte;

public class FusionPuntos {
    private Punto p1;
    private Punto p2;

    public FusionPuntos(Punto p1, Punto p2) {
        this.setP1(p1);
        this.setP2(p2);
    }

    public Punto getP1() {
        return p1;
    }

    public Punto getP2() {
        return p2;
    }

    public void setP1(Punto p1) {
        this.p1 = p1;
    }

    public void setP2(Punto p2) {
        this.p2 = p2;
    }

    public boolean esMaximoXP2 () {
        return this.p2.esMaximoX(p1);
    }

    public boolean esMaximoYP2 () {
        return this.p2.esMaximoY(p1);
    }

    public boolean esMaximoXP1 () {
        return this.p1.esMaximoX(p2);
    }

    public boolean esMaximoYP1 () {
        return this.p1.esMaximoY(p2);
    }


    public boolean esDistintoYP1 (int prev) {
        return this.p1.esDistintoY(prev);
    }

    public boolean esDistintoYP2 (int prev) {
        return this.p2.esDistintoY(prev);
    }


    public boolean esIgualY(){
        return this.p1.esIgualY(this.p2);
    }

    public boolean esIgualX(){
        return this.p1.esIgualX(this.p2);
    }


}
