package matsant.controllers;

public class Calculadora {
    private double nota1, nota2, nota3, nota4;

    public Calculadora(double nota1, double nota2) {
        this.nota1 = nota1;
        this.nota2 = nota2;
    }

    public Calculadora(double nota1, double nota2, double nota3, double nota4) {
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.nota4 = nota4;
    }

    public double getMediaSemestral() {
        return (this.nota1 + this.nota2)/2;
    }

    public double getMediaAnual() {
        return (this.nota1 + this.nota2 + this.nota3 + this.nota4)/4;
    }


}
