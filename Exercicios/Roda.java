import java.awt.*;

public class Roda extends Corpo{
    private int raio;
    private Paint cor;
    public Roda(double x, double y,int raio,Paint cor) {
        super(x, y,0,0,0,0,0);
        this.cor = cor;
        this.raio=raio;
    }
    public int getRaio() {
        return raio;
    }
    public void setRaio(int raio) {
        this.raio = raio;
    }
    public Paint getCor() {
        return cor;
    }
    public void setCor(Paint cor) {
        this.cor = cor;
    }
    @Override
    public void desenhar(Graphics g) {
        Graphics2D graf2d=(Graphics2D) g;
        graf2d.setPaint(cor);
        graf2d.fillArc((int)getX(), (int)getY(), this.raio, this.raio, 0, 360);
    }
    
    @Override
    public void moverFisica(double friccao, double gravidade, int altura, int largura) {
        
        setVely(getVely()+gravidade);
        if(getY()+getRaio()>=altura ){
            setVely(-getVely());
            setY(getY()-(getY()+getRaio()-altura));
            setVely(getVely()-getVely()*getElasticidade());
        }
        else if(getY()<=getRaio()){
            setVely(-getVely());
            setY(getY()+(getRaio()-getY()));
        }

        if(getX() <= 0 || getX()+getRaio()>=largura)
            setVelx(-getVelx());
        
        if(Math.abs(getVely())<=1 && (int)(getY()+getRaio())==altura){
            setVely(0);
            setVelx(getVelx()-getVelx()*friccao);
        }
        setX(getX()+getVelx());
        setY(getY()+getVely());

    }
}
