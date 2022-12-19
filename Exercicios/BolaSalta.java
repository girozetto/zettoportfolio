
import java.awt.*;

public class BolaSalta extends Animacao{
    private Corpo[] bolas = new Corpo[3];
    public BolaSalta()
    {
        Roda bola = new Roda(250,100,30,Color.RED);
        bola.setVelx(0);
        bola.setVely(Math.random()*6-3);
        bola.setElasticidade(0.20);
        bola.setAceleracao(1);
        Roda bola2 = new Roda(200,70,20,Color.BLUE);
        bola2.setVelx(Math.random()*8-4);
        bola2.setVely(Math.random()*8-4);
        bola2.setElasticidade(0.20);
        bola2.setAceleracao(1.5);
        Roda bola3 = new Roda(100,50,10,Color.LIGHT_GRAY);
        bola3.setVelx(Math.random()*8-4);
        bola3.setVely(Math.random()*8-4);
        bola3.setElasticidade(0.20);
        bola3.setAceleracao(3);
        bolas[0]=bola;
        bolas[1]=bola2;
        bolas[2]=bola3;
    }
    
    @Override
    public void animar(Graphics g){
        Graphics2D graf2d=(Graphics2D) g;
        graf2d.setPaint(Color.DARK_GRAY);
        graf2d.setStroke(new BasicStroke(5));
        graf2d.drawLine(0, 500, 790, 500);
        graf2d.drawLine(0, 0, 0, 500);
        graf2d.drawLine(0, 0, 790, 0);
        graf2d.drawLine(790, 0, 790, 500);
        for (Corpo bola : bolas) {
            bola.desenhar(g);
            bola.moverFisica(0.02, 0.1, 500, 790);
        }
    }
}
