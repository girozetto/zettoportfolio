import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.File;
import java.io.IOException;

public class CataDistorcao extends Animacao{
    
    private Corpo helice;
    private BufferedImage bi;
    public CataDistorcao() throws IOException
    {
        this.bi = ImageIO.read(new File("imagens/pr.jpg"));
        this.helice = new Helice(0,0,0.1,0);
        this.helice.setAltura(150);
        this.helice.setLargura(150);
        this.helice.setX(-this.helice.getLargura());
        this.helice.setAceleracao(0.002);
    }
    @Override
    public void animar(Graphics g) {
        Graphics2D graf2d=(Graphics2D) g;
        for(int i=0;i<this.bi.getWidth(null)/2;i++)
            graf2d.drawImage(this.bi.getSubimage(i, 0, 1, this.bi.getHeight(null)),i, (int)(150 + 20*(Math.cos((i+this.helice.getX())*(Math.PI/180)))),1,this.bi.getHeight(null)/2, null);
        graf2d.setPaint(Color.RED);
        graf2d.setStroke(new BasicStroke(10));
        graf2d.drawLine((int)(this.helice.getX()+this.helice.getLargura()/2),(int)(this.helice.getY()+this.helice.getAltura()/2),(int)(this.helice.getX()+this.helice.getLargura()/2),(int)(600-this.helice.getY()));
        this.helice.moverFisica(0.1, 1, 600, 800);
        graf2d.setPaint(Color.BLACK);
        for(int i=0;i<4;i++){
            this.helice.setRotacao(this.helice.getRotacao()+90);
            this.helice.desenhar(g);
        }
        this.helice.setX((this.helice.getX()+this.helice.getVelx())%780);
    }
}
