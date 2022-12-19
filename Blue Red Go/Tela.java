import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Tela extends JPanel implements KeyListener, Runnable
{
    private Jogo jogo;
    Set<Integer> teclasPremidas = new HashSet<>();
    boolean bugs=false;
    public Tela()
    {
        this.jogo = new Jogo();
        new Thread(this).start();
        addKeyListener(this);
    }
    private boolean rodarFrames()
    {
        this.repaint();
        try {Thread.currentThread().sleep(1000/60);} 
        catch (InterruptedException e) {JOptionPane.showMessageDialog(this.getParent(), "Houve um erro de interrupção da Thread");}
        return true;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D gr = (Graphics2D) g;
        long inicio= System.nanoTime();
        this.setBackground(Color.WHITE);
        jogo.dim = new int[]{getWidth(),getHeight()};
        jogo.desenhar(gr);
        long fim= System.nanoTime();
        gr.setColor(Color.BLACK);
        if(bugs){
            gr.drawString("Tempo de Desenho: "+(fim-inicio)/1000000000.0+" segundos",0,0);
            System.out.println("Tempo de Desenho: "+(fim-inicio)/1000000000.0+" segundos");
        }
    }

    @Override
    public void run() {
        while(rodarFrames());
    }

    public static void main(String[] args)
    {
        JFrame jogo = new JFrame("Blue Red Go");
        Tela t = new Tela();
        jogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jogo.add(t);
        jogo.addKeyListener(t);
        jogo.setSize(800, 600);
        jogo.setMinimumSize(jogo.getSize());
        jogo.setLocationRelativeTo(null);
        jogo.setVisible(true);
    }
    private void setarControle()
    {
        jogo.setControle(new Controle());
        for(Integer t: teclasPremidas)
        {
            jogo.getControle().setAcelerar(jogo.getControle().isAcelerar() || KeyEvent.VK_A==t);
            jogo.getControle().setRecuar(jogo.getControle().isRecuar() || KeyEvent.VK_Z==t);
            jogo.getControle().setDireita(jogo.getControle().isDireita() || KeyEvent.VK_RIGHT==t);
            jogo.getControle().setEsquerda(jogo.getControle().isEsquerda() || KeyEvent.VK_LEFT==t);
            jogo.getControle().setEspecial(jogo.getControle().isEspecial() || KeyEvent.VK_SPACE==t);
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        teclasPremidas.add(e.getKeyCode());
        setarControle();
        bugs = e.getKeyCode()==KeyEvent.VK_T?!bugs:bugs;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        teclasPremidas.remove(e.getKeyCode());
        setarControle();
    }
}