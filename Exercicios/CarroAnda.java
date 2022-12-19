
import java.awt.*;
public class CarroAnda extends Animacao{
    private Corpo carro;
    public CarroAnda()
    {
        this.carro=new Carro(50,100,0,0);
        this.carro.setVely(Math.random()*6-3);
        this.carro.setVelx(0);
    }
    @Override
    public void animar(Graphics g){
        Graphics2D graf2d=(Graphics2D) g;
        graf2d.setPaint(Color.DARK_GRAY);
        graf2d.setStroke(new BasicStroke(5));
        graf2d.drawLine(0, 520, 790, 520);
        graf2d.drawLine(0, 0, 0, 590);
        graf2d.drawLine(0, 0, 790, 0);
        graf2d.drawLine(790, 0, 790, 590);
        this.carro.desenhar(g);
        if(this.carro.getVely()==0 && this.carro.getVelx()==0)
            this.carro.setVelx(Math.random()*6-3);
        this.carro.moverFisica(0.02, 0.2, 450, 600);
    }
}
