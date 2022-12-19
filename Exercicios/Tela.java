import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.IOException;

public class Tela extends JFrame implements ActionListener
{
    private PainelAnimacao painelAnimacao;
    private FlowLayout layoutPainel;
    private BorderLayout layout;
    private JButton[] botoes;
    private Animacao[] ans;
    private int quant;
    public Tela(String titulo)
    {
        super(titulo);
        quant=0;
        String[] nomes = {"Bolas Saltitantes","Carro Andante","Movimento Cosseno","Areas Cortadas","Sprite Animada","Dupla Tarefa"}; 
        this.botoes = new JButton[10];
        try {
            Animacao[] ans={new BolaSalta(),new CarroAnda(),new MovimentoCosseno(),new AreasCortadas(),new TexturaAnim(),new CataDistorcao()};
            this.ans=ans;
        } catch (IOException e){}
        
        layoutPainel=new FlowLayout();
        layoutPainel.setAlignment(FlowLayout.RIGHT);
        JPanel painelSuperior = new JPanel(layoutPainel);
        for(int i=0;i<nomes.length;i++)
            adicionarExercicio(nomes[i], painelSuperior);
        painelSuperior.setSize(getWidth(),40);
        layout = new BorderLayout();
        setLayout(layout);
        this.painelAnimacao=new PainelAnimacao(ans[0]);
        this.painelAnimacao.setSize(getWidth(), 600);
        add(painelAnimacao,BorderLayout.CENTER);
        add(painelSuperior,BorderLayout.NORTH);
    }
    private void adicionarExercicio(String nome, JPanel comp)
    {
        if(botoes.length>quant){
            JButton jb = new JButton(nome);
            this.botoes[quant++]=jb;
            jb.addActionListener(this);
            comp.add(jb);
        }else JOptionPane.showMessageDialog(null, "O Array de Botões está cheia");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=0;i<botoes.length;i++)
            if(e.getSource()==botoes[i])
                this.painelAnimacao.setAn(ans[i]);
    }
    public static void main(String[] args)
    {
        Tela t = new Tela("Exercicios");
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        t.setSize(800, 600);
        t.setResizable(false);
        t.setLocationRelativeTo(null);
        t.setVisible(true);
    }
    
}