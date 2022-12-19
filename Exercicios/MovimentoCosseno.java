import java.awt.*;
public class MovimentoCosseno extends Animacao
{
    private Roda circulo;
    public MovimentoCosseno()
    {
        this.circulo = new Roda(30, 20, 20, Color.BLUE);
        this.circulo.setVelx(2);
    }
    @Override
    public void animar(Graphics g) {
        Graphics2D graf2d=(Graphics2D) g;
        graf2d.setPaint(Color.DARK_GRAY);
        graf2d.setStroke(new BasicStroke(5));
        graf2d.drawLine(0, 500, 790, 500);
        graf2d.drawLine(0, 0, 0, 500);
        graf2d.drawLine(0, 0, 790, 0);
        graf2d.drawLine(790, 0, 790, 500);
        this.circulo.moverCosseno(300,60,790);
        this.circulo.desenhar(g);
    }
}