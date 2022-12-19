import java.awt.Graphics;
import java.awt.*;
import java.awt.geom.*;

public class Helice extends Corpo{

    public Helice(double x, double y, double velx, double rotacao) {
        super(x, y, velx, 0, 0, 0, rotacao);
    }
    @Override
    public void desenhar(Graphics g) {
        Graphics2D grafcop=(Graphics2D)g.create();
        grafcop.rotate(Math.toRadians(getRotacao()),getX()+getLargura()/2,getY()+getAltura()/2);
        Shape hel = new Arc2D.Double(getX(),getY(),getLargura(),getAltura(),0,15,Arc2D.PIE);
        grafcop.fill(hel);
    }
    @Override
    public void moverFisica(double friccao, double gravidade, int altura, int largura) {
        setVelx(getVelx()+getAceleracao());
        setRotacao(getRotacao()+getVelx());
    }
    
}
