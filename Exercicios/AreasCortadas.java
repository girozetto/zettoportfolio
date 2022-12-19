import java.awt.*;
import javax.imageio.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
public class AreasCortadas extends Animacao {
    private Personagem personagem;
    private BufferedImage bi;
    public AreasCortadas() throws IOException
    {
        super();
        this.bi = ImageIO.read(new File("imagens/pr.jpg"));
        this.personagem=new Personagem(300,20,"imagens/correrast.png");
        this.personagem.setMaxQuadro(30);
        this.personagem.setVely(1);
        this.personagem.setVelx(0);
        this.personagem.setAltura(100);
        this.personagem.setLargura(100);
        super.setFps(10);
    }
    @Override
    public void movimentar(int x) {
        this.personagem.setRotacao(x);
    }
    
    @Override
    public void animar(Graphics g) {
        Graphics2D graf2d=(Graphics2D) g;
        graf2d.setPaint(Color.BLACK);
        graf2d.fillRect(0, 0, 800, 700);
        graf2d.setPaint(Color.DARK_GRAY);
        graf2d.setStroke(new BasicStroke(5));
        graf2d.drawLine(0, 520, 790, 520);
        graf2d.drawLine(0, 0, 0, 590);
        graf2d.drawLine(0, 0, 790, 0);
        graf2d.drawLine(790, 0, 790, 590);
        Area rect = new Area(new Rectangle2D.Double(20,20,40,80));
        Area c = new Area(new Arc2D.Double(30,50,20,20,0,360,Arc2D.PIE));
        rect.subtract(c);
        graf2d.fill(rect);

        graf2d.setPaint(Color.WHITE);
        Area c1 = new Area(new Arc2D.Double(80,20,100,100,0,360,Arc2D.PIE));
        Area c2 = new Area(new Arc2D.Double(110,30,80,80,0,360,Arc2D.PIE));
        c1.subtract(c2);
        graf2d.fill(c1);
        graf2d.setPaint(Color.YELLOW);
        Area c3 = new Area(new Arc2D.Double(200,20,80,80,0,360,Arc2D.PIE));
        Area c4 = new Area(new Arc2D.Double(230,20,80,80,0,360,Arc2D.PIE));
        c3.intersect(c4);
        graf2d.setPaint(new TexturePaint(this.bi,new Rectangle2D.Double(0,0,80,80)));
        graf2d.fill(c3);
        
        graf2d.setPaint(Color.RED);
        Area c5 = new Area(new Arc2D.Double(400,20,80,80,0,360,Arc2D.PIE));
        Area c6 = new Area(new Arc2D.Double(430,20,80,80,0,360,Arc2D.PIE));
        c5.exclusiveOr(c6);
        graf2d.fill(c5);

        if(this.personagem.getVely()==0)
            this.personagem.setVelx(10);
        this.personagem.moverFisica(0, 0.9, 500, 790);
        this.personagem.desenhar(g);
        
        if(this.personagem.getLargura()+this.personagem.getX()>=780 || this.personagem.getX()<=this.personagem.getLargura())
        {
            this.personagem.setVelx(-this.personagem.getVelx());
            this.personagem.setRotacao(-this.personagem.getRotacao());
            this.personagem.setX(this.personagem.getX()-this.personagem.getLargura()-10);
        }
        this.personagem.setX(this.personagem.getX()+this.personagem.getVelx());
    }
}
