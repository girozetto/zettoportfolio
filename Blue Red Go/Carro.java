import java.awt.*;
public class Carro extends Corpo{
    private double maxVel;
    private Recurso[] imagem;
    private int quadro;
    
    private int[] posTela;
    public Carro(double x, double y, double rotacao, double maxVel, Recurso ...img) {
        super(x, y, 0, 0, 0, 0, rotacao);
        posTela = new int[]{0,0};
        this.imagem=img;
        setMaxVel(maxVel);
        quadro = 0;
    }
    @Override
    public void mover(double friccao) {
        setVely(getVely()+(getControle().isAcelerar()?getAceleracao():0)+(getControle().isRecuar()?-getAceleracao():0));
        setVely((Math.abs(getVely())<=2*friccao?0:1)*getVely()*(1-friccao));
        if(getVely() > this.maxVel) setVely(this.maxVel);
        if(getVely() < -this.maxVel/2) setVely(-this.maxVel/2);
        if(Math.abs(getVely())>1){
            int troca = getVely()>0?1:-1;
            setRotacao(getRotacao() +((getControle().isDireita()?-3*troca:0)+(getControle().isEsquerda()?3*troca:0)));
        }
        setY(getY()-getVely()*Math.cos(Math.toRadians(getRotacao())));
        setX(getX()-getVely()*Math.sin(Math.toRadians(getRotacao())));
        quadro=(quadro+1)%(imagem.length*20-1);
    }
    @Override
    public void desenhar(Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.rotate(Math.toRadians(-getRotacao()), this.posTela[0], this.posTela[1]);
        g2.drawImage(this.imagem[quadro/20].getImagem(),(this.posTela[0]-getLargura()/2),(this.posTela[1]-getAltura()/2),null);
        g2.dispose();
    }
    public int getQuadro() {
        return quadro;
    }
    public void setQuadro(int quadro) {
        this.quadro = quadro;
    }
    public int[] getPosTela() {
        return posTela;
    }
    public void setPosTela(int[] posTela) {
        this.posTela = posTela;
    }
    public double getMaxVel() {
        return maxVel;
    }
    public void setMaxVel(double maxVel) {
        this.maxVel = maxVel;
    }
    
    public Recurso[] getImagem() {

        return imagem;
    }

    public void setImagem(Recurso ...imagem) {
        this.imagem = imagem;
    }
}
