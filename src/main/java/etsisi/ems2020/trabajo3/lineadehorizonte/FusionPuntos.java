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
        return this.p1.getX() < this.p2.getX();
    }

    public boolean esMaximoYP2 () {
        return this.p1.getY() < this.p2.getY();
    }

    public boolean esMaximoXP1 () {
        return this.p2.getX() < this.p1.getX();
    }

    public boolean esMaximoYP1 () {
        return this.p2.getY() < this.p1.getY();
    }


    public boolean esDistintoYP1 (int prev) {
        return this.p1.getY()!=prev;
    }

    public boolean esDistintoYP2 (int prev) {
        return this.p2.getY()!=prev;
    }


    public boolean esIgualY(){
        return this.p1.getY() == this.p2.getY();
    }

    public boolean esIgualX(){
        return this.p1.getX() == this.p2.getX();
    }


}
