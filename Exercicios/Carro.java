import java.awt.Color;
import java.awt.*;
import java.awt.geom.*;
public class Carro extends Corpo{
    private int altura;
    private int largura;
    private Roda roda;
    private Jante jante;
    private double motorista;
    private double posMotorista;
    
    public Carro(double x, double y, int largura,int altura) {
        super(x, y, 0, 0, 0, 0, 0);
        this.altura=altura;
        this.largura=largura;
        this.roda = new Roda(0, 0, 40,Color.red);
        this.jante = new Jante(0,0,30,Color.BLACK);
        this.motorista = 0.2;
        this.posMotorista = 0;
    }

    public int getAltura() {
        return altura;
    }
    public void setAltura(int altura) {
        this.altura = altura;
    }
    public int getLargura() {
        return largura;
    }
    public void setLargura(int largura) {
        this.largura = largura;
    }
    public Roda getRoda() {
        return roda;
    }
    public void setRoda(Roda roda) {
        this.roda = roda;
    }
    @Override
    public void desenhar(Graphics g) {
        Graphics2D graf2d=(Graphics2D) g;
        graf2d.setPaint(Color.DARK_GRAY);
        graf2d.fill(new RoundRectangle2D.Double(getX(),getY(),180,40,5,20));
        graf2d.setPaint(Color.DARK_GRAY);
        graf2d.fill(new Arc2D.Double(getX(),getY()-70,150,150,0,180,Arc2D.CHORD));
        graf2d.setPaint(Color.CYAN);
        graf2d.fill(new Arc2D.Double(getX()+15,getY()-55,100,100,180,-90,Arc2D.PIE));
        graf2d.setPaint(Color.WHITE);
        graf2d.fill(new Arc2D.Double(getX()+35,getY()-55,100,100,0,90,Arc2D.PIE));
        //Cabeça do motorista
        
        graf2d.setPaint(Color.BLACK);
        graf2d.fillArc((int)(getX() + this.posMotorista)+90, (int)getY()-45,25, 30, 0, 360);
        //Olhos do motorista
        graf2d.setPaint(Color.WHITE);
        graf2d.fillArc((int)(getX() + this.posMotorista)+105, (int)getY()-35, 8, 4, 0, 360);
        graf2d.setPaint(Color.CYAN);
        graf2d.fill(new Arc2D.Double(getX()+35,getY()-20,100,30,0,90,Arc2D.PIE));
        graf2d.setPaint(Color.WHITE);
        graf2d.fillArc((int)getX()+10, (int)getY()+25, 45, 45, 0, 360);
        graf2d.setPaint(Color.WHITE);
        graf2d.fillArc((int)getX()+125, (int)getY()+25, 45, 45, 0, 360);
        graf2d.setPaint(Color.ORANGE);
        graf2d.fill(new RoundRectangle2D.Double(getX()+150,getY()+10,20,10,5,20));
        this.roda.setX(getX()+10);
        this.roda.setY(getY()+30);
        this.roda.desenhar(g);
        this.roda.setX(getX()+130);
        this.roda.setY(getY()+30);
        this.roda.desenhar(g);
        this.jante.setX(getX()+15);
        this.jante.setY(getY()+35);
        this.jante.desenhar(g);
        this.jante.setX(getX()+135);
        this.jante.setY(getY()+35);
        this.jante.desenhar(g);
        
    }
    @Override
    public void moverFisica(double friccao, double gravidade, int altura, int largura) {
        this.posMotorista += this.motorista;
        if(this.posMotorista>=15 || this.posMotorista<=0)
            this.motorista=-motorista;

        if(getAltura()+getY()<altura)
        {
            setVely(getVely()+gravidade);
        }
        else{
            setY(getY()-(getAltura()+getY()-altura));
            setVely(0);
        }
        
        if(getLargura()+getX()>=largura || getX()<=getLargura())
            setVelx(-getVelx());
        
        this.jante.setRotacao(this.jante.getRotacao()+getVelx());
        setX(getX()+getVelx());
        setY(getY()+getVely());
    }
}
