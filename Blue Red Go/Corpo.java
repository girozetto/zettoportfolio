import java.awt.*;
public class Corpo {
    private double x;
    private double y;
    private double velx;
    private double vely;
    private int altura;
    private int largura;
    private double aceleracao;
    private double elasticidade;
    private double rotacao;
    private Controle controle;
    public Corpo(double x, double y, double velx, double vely, double aceleracao, double elasticidade, double rotacao) {
        this.x = x;
        this.y = y;
        this.velx = velx;
        this.vely = vely;
        this.aceleracao = aceleracao;
        this.elasticidade = elasticidade;
        this.rotacao = rotacao;
    }
    public void mover(double friccao)
    {}
    public void desenhar(Graphics2D g){}
    public boolean colisao(Corpo c)
    {
        return true;
    }
    public Controle getControle() {
        return controle;
    }
    public void setControle(Controle controle) {
        this.controle = controle;
    }
    public int getLargura() {
        return largura;
    }
    public void setLargura(int largura) {
        this.largura = largura;
    }
    public int getAltura() {
        return altura;
    }
    public void setAltura(int altura) {
        this.altura = altura;
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public double getVelx() {
        return velx;
    }
    public void setVelx(double velx) {
        this.velx = velx;
    }
    public double getVely() {
        return vely;
    }
    public void setVely(double vely) {
        this.vely = vely;
    }
    public double getAceleracao() {
        return aceleracao;
    }
    public void setAceleracao(double aceleracao) {
        this.aceleracao = aceleracao;
    }
    public double getElasticidade() {
        return elasticidade;
    }
    public void setElasticidade(double elasticidade) {
        this.elasticidade = elasticidade;
    }
    public double getRotacao() {
        return rotacao;
    }
    public void setRotacao(double rotacao) {
        this.rotacao = rotacao;
    }
}
