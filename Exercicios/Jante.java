import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.*;

public class Jante extends Roda{
public final double DELTA_THETA = Math.PI/180;
    public Jante(double x, double y, int raio, Paint cor) {
        super(x, y, raio, cor);
    }
    @Override
    public void desenhar(Graphics g) {
        Graphics2D graf2d=(Graphics2D) g.create();
        graf2d.rotate(getRotacao()*DELTA_THETA,getX()+getRaio()/2,getY()+getRaio()/2);
        graf2d.setPaint(getCor());
        graf2d.fill(new Arc2D.Double(getX(),getY(),getRaio(),getRaio(),0,360,Arc2D.PIE));
        graf2d.setPaint(Color.RED);
        graf2d.drawLine((int)(getX()+getRaio()*0.5), (int)(getY()+getRaio()*0.2), (int)(getX()+getRaio()*0.5), (int)(getY()+getRaio()*0.8));
        graf2d.setPaint(Color.RED);
        graf2d.drawLine((int)(getX()+getRaio()*0.35), (int)(getY()+getRaio()*0.5), (int)(getX()+getRaio()*0.65), (int)(getY()+getRaio()*0.5));
        graf2d.dispose();
    }
}
