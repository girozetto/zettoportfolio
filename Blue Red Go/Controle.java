public class Controle {
    public Controle() {
        this.acelerar=false;
        this.direita=false;
        this.esquerda=false;
        this.recuar = false;
        this.especial = false;
    }
    //Acelerar o carro
    private boolean acelerar;
    public boolean isAcelerar() {
        return acelerar;
    }
    public void setAcelerar(boolean acelerar) {
        this.acelerar = acelerar;
    }
    //Recuar o carro
    private boolean recuar;
    public boolean isRecuar() {
        return recuar;
    }
    public void setRecuar(boolean recuar) {
        this.recuar = recuar;
    }
    //Virar o carro a direita
    private boolean direita;
    public boolean isDireita() {
        return direita;
    }
    public void setDireita(boolean direita) {
        this.direita = direita;
    }
    //Virar o carro a esquerda
    private boolean esquerda;
    public boolean isEsquerda() {
        return esquerda;
    }
    public void setEsquerda(boolean esquerda) {
        this.esquerda = esquerda;
    }
    //Acionar um ataque especial
    private boolean especial;
    public boolean isEspecial() {
        return especial;
    }
    public void setEspecial(boolean especial) {
        this.especial = especial;
    }
    @Override
    public String toString() {
        return "[ Esquerda = "+isEsquerda()+", Direita = "+isDireita()+", Acelerar = "+isAcelerar()+", Recuar = "+isRecuar()+", Especial = "+isEspecial()+"]";
    }
}
