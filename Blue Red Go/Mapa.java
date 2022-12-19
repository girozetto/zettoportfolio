import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
public class Mapa {
    private ArrayList<Recurso> recursos;
    private int escala = 160;
    private int maxColVis;
    private int maxLinVis;
    private int[][] mapa;
    private Jogo tela;
    public Mapa(Jogo tela)
    {
        this.tela = tela;
        recursos = new ArrayList<>();
        Recurso fundo = new Recurso("Recursos/complemento_03.png");
        for(int i=0;i<=26;i++)
            carregarRecurso(new Recurso("Recursos/estr_"+(i/10)+(i%10)+".png"),fundo,false);
        for(int i=0;i<=9;i++)
            carregarRecurso(new Recurso("Recursos/complemento_"+(i/10)+(i%10)+".png"),fundo,true);
        for(int i=0;i<=6;i++)
            carregarRecurso(new Recurso("Recursos/decora_"+(i/10)+(i%10)+".png"),fundo,true);
    }
    private void carregarRecurso(Recurso r, Recurso fundo, boolean colisao)
    {
        BufferedImage escalada = new BufferedImage(escala,escala,r.getImagem().getType());
        escalada.createGraphics().drawImage(fundo.getImagem(),0,0,escala,escala,null);
        escalada.createGraphics().drawImage(r.getImagem(),0,0,escala,escala,null);
        r.setImagem(escalada);
        r.colisao = colisao;
        recursos.add(r);
    }
    public ArrayList<Recurso> getRecursos() {
        return recursos;
    }
    public void setRecursos(ArrayList<Recurso> recursos) {
        this.recursos = recursos;
    }

    
    public void setTamanhoTela(int[] dim)
    {
        maxColVis = (dim[0]/escala)+1;
        maxLinVis = (dim[1]/escala)+1;
    }
    public int getMaxColVis() {
        return maxColVis;
    }
    public int getMaxLinVis() {
        return maxLinVis;
    }
    public int getEscala() {
        return escala;
    }
    public int[][] getMapa() {
        return mapa;
    }
    public void carregarMapa(String caminho) throws IOException
    {
        InputStream is = getClass().getResourceAsStream(caminho);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String[] ns = br.readLine().split(" ");
        mapa = new int[ns.length-1][ns.length-1];
        System.out.println(mapa.length);
        for(int i=0;i<mapa[0].length;i++){
            for(int j=0;j<mapa.length;j++)
                mapa[j][i] = Integer.parseInt(ns[j]);
            ns = br.readLine().split(" ");
        }
    }
    
    public void desenhar(Graphics2D g)
    {
        for(int i=0;i<mapa.length;i++)
            for(int j=0;j<mapa[0].length;j++)
                if(i*escala + escala*2>= tela.getJogador().getX()-tela.getJogador().getPosTela()[0]
                && j*escala + escala*2>= tela.getJogador().getY()-tela.getJogador().getPosTela()[1]
                && i*escala - escala*2<= tela.getJogador().getX()+tela.getJogador().getPosTela()[0]
                && j*escala - escala*2<= tela.getJogador().getY()+tela.getJogador().getPosTela()[1])
                    g.drawImage(recursos.get(mapa[i][j]).getImagem(),(int)(i*escala-tela.getJogador().getX()+tela.getJogador().getPosTela()[0]),(int)(j*escala-tela.getJogador().getY()+tela.getJogador().getPosTela()[1]),null);
    }
}