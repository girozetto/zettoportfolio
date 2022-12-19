import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.*;
import java.awt.geom.*;
public class Personagem extends Corpo{
    private BufferedImage imagem;
    private int quadro;
    private int maxQuadro;
    public Personagem(double x, double y, String caminhoImg) throws IOException
    {
        super(x,y,0,0,0,0,1);
        this.quadro=0;
        setImagem(ImageIO.read(new File(caminhoImg)));
    }
    
    public void setMaxQuadro(int quadro) {
        this.maxQuadro = quadro;
    }
    public BufferedImage getImagem() {
        return imagem;
    }
    public void setImagem(BufferedImage imagem) {
        setMaxQuadro(imagem.getWidth(null)/imagem.getHeight(null) - 1);
        this.imagem = imagem;
    }
    @Override
    public void desenhar(Graphics g) {
        Graphics2D grafcop=(Graphics2D)g.create();
        grafcop.setPaint(new TexturePaint(this.imagem.getSubimage((this.quadro=(this.quadro+1)%maxQuadro)*this.imagem.getHeight(null), 0, this.imagem.getHeight(null), imagem.getHeight(null)),new Rectangle2D.Double((int)getX(),(int)getY(),getRotacao()*getLargura(),getAltura())));
        grafcop.fillRect((int)getX(),(int)getY(),getLargura(),getAltura());
    }
    @Override
    public void moverFisica(double friccao, double gravidade, int altura, int largura) {
        setVely(getVely()+gravidade-(gravidade+getVely())*(int)((getAltura()+getY())/altura));
        setY(((int)((getAltura()+getY())/altura)>0?altura-getAltura():getY())+getVely());
    }
}
