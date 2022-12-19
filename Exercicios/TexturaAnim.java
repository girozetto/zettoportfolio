import java.awt.Graphics;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
public class TexturaAnim extends Animacao {
    private Personagem personagem;
    private BufferedImage fundo;
    private BufferedImage chao;
    private BufferedImage ceu;
    private double xMov;
    private double xFun;
    private double xCeu;
    private String[] acoes;
    public TexturaAnim() throws IOException
    {
        super();
        this.personagem=new Personagem(10,300,"imagens/parado.png");
        this.personagem.setVely(1);
        this.personagem.setAltura(200);
        this.personagem.setLargura(200);
        this.acoes = new String[4];
        this.fundo = ImageIO.read(new File("imagens/fundo.png"));
        this.chao = ImageIO.read(new File("imagens/chao.png"));
        this.ceu = ImageIO.read(new File("imagens/ceu.png"));
        this.acoes[0] = "imagens/parado.png";
        this.acoes[1] = "imagens/andar.png";
        this.acoes[2] = "imagens/correr.png";
        this.acoes[3] = "imagens/saltar.png";
        this.xMov=0;
        this.xFun=0;
        this.xCeu=0;
        this.personagem.setVelx(0);
        super.setFps(10);
    }
    
    @Override
    public void animar(Graphics g) {
        Graphics2D graf2d=(Graphics2D) g;

        this.personagem.setVelx(this.personagem.getVelx()+0.1);
        double porc = Math.abs((int)this.personagem.getVelx()/6);
        //Movimentacao e Desenho do Ceu
        int sentido = -2;
        this.xCeu=(this.xCeu+sentido*porc)%(this.ceu.getWidth(null)-1);
        graf2d.drawImage(this.ceu.getSubimage(Math.abs((int)this.xCeu), 0, this.ceu.getWidth(null)-Math.abs((int)this.xCeu)%(this.ceu.getWidth(null)-1), this.ceu.getHeight(null)), 0, 0, this.ceu.getWidth(null)-Math.abs((int)this.xCeu)%(this.ceu.getWidth(null)-1), this.ceu.getHeight(null), null, null);
        graf2d.drawImage(this.ceu.getSubimage(0, 0, Math.abs((int)this.xCeu)%(this.ceu.getWidth(null)-1)+1, this.ceu.getHeight(null)), this.ceu.getWidth(null)-Math.abs((int)this.xCeu)%(this.ceu.getWidth(null)-1), 0, Math.abs((int)this.xCeu)%(this.ceu.getWidth(null)-1)+1, this.ceu.getHeight(null), null, null);
        graf2d.setPaint(Color.white);
        //A Lua no Ar
        Area c1 = new Area(new Arc2D.Double(80,40,100,100,0,360,Arc2D.PIE));
        Area c2 = new Area(new Arc2D.Double(110,50,80,80,0,360,Arc2D.PIE));
        c1.subtract(c2);
        graf2d.fill(c1);
        //Movimentacao e Desenho das Arvores
        sentido = -3; 
        this.xMov=(this.xMov+sentido*porc)%(this.fundo.getWidth(null)-1);
        graf2d.drawImage(this.fundo.getSubimage(Math.abs((int)this.xMov), 0, this.fundo.getWidth(null)-Math.abs((int)this.xMov)%(this.fundo.getWidth(null)-1), this.fundo.getHeight(null)), 0, 0, this.fundo.getWidth(null)-Math.abs((int)this.xMov)%(this.fundo.getWidth(null)-1), this.fundo.getHeight(null), null, null);
        graf2d.drawImage(this.fundo.getSubimage(0, 0, Math.abs((int)this.xMov)%(this.fundo.getWidth(null)-1)+1, this.fundo.getHeight(null)), this.fundo.getWidth(null)-Math.abs((int)this.xMov)%(this.fundo.getWidth(null)-1), 0, Math.abs((int)this.xMov)%(this.fundo.getWidth(null)-1)+1, 600, null, null);
        
        //Movimentacao e Desenho do Personagem
        this.personagem.moverFisica(0.01, 0.9, 500, 790);
        this.personagem.desenhar(g);

        //Movimentacao e Desenho da Relva
        sentido = -6; 
        this.xFun=(this.xFun+sentido*porc)%(this.chao.getWidth(null)-1);
        graf2d.drawImage(this.chao.getSubimage(Math.abs((int)this.xFun), 0, this.chao.getWidth(null)-Math.abs((int)this.xFun)%(this.chao.getWidth(null)-1), this.chao.getHeight(null)), 0, 0, this.chao.getWidth(null)-Math.abs((int)this.xFun)%(this.chao.getWidth(null)-1), this.chao.getHeight(null)-70, null, null);
        graf2d.drawImage(this.chao.getSubimage(0, 0, Math.abs((int)this.xFun)%(this.chao.getWidth(null)-1)+1, this.chao.getHeight(null)), this.chao.getWidth(null)-Math.abs((int)this.xFun)%(this.chao.getWidth(null)-1), 0, Math.abs((int)this.xFun)%(this.chao.getWidth(null)-1)+1, this.chao.getHeight(null)-70, null, null);
        
        //Fases de animacao do Personagem
        try {
            this.personagem.setImagem(ImageIO.read(new File(acoes[(int)(Math.abs(this.personagem.getVelx()/6))])));
        } catch (IOException e) {}
        
        //Loop de Velocidade
        this.personagem.setVelx((-2*((int)this.personagem.getVelx()/22)+1)*this.personagem.getVelx());     
    }
}

