import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class PainelAnimacao extends JPanel implements Runnable, KeyListener{
    private Animacao an;
    private boolean animar;
    public PainelAnimacao(Animacao an)
    {
        new Thread(this).start();
        this.animar=true;
        this.an=an;
        addKeyListener(this);
    }
    public Animacao getAn() {
        return an;
    }
    public void setAn(Animacao an) {
        this.an = an;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        an.animar(g);
    }
    private boolean rodarFrames()
    {
        this.repaint();
        try {Thread.currentThread().sleep(1000/an.getFps());} catch (InterruptedException e) {
            JOptionPane.showMessageDialog(this.getParent(), "Houve um erro de interrupção da Thread");
        }
        return this.animar;
    }
    @Override
    public void run() {
        while(rodarFrames());
    }
    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("Teclado "+KeyEvent.getKeyText(e.getKeyCode()));
    }
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Tecla Premida "+KeyEvent.getKeyText(e.getKeyCode()));
        getAn().movimentar(-1);
    }
    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Tecla Solta "+KeyEvent.getKeyText(e.getKeyCode()));
        getAn().movimentar(1);
    }
}
