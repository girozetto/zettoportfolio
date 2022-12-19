import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
public class Jogo {
    private Controle controle;
    private Carro jogador;
    public int[] dim;
    Recurso[] recs;
    private Mapa mapa;
    
    private boolean carregado=false;
    private int tamMundo[];
    private ArrayList<Carro> carros = new ArrayList<>();
    public Jogo() {
        dim = new int[2];
        this.controle = new Controle();
        int altura = 80;
        int largura = 80;
        gerarCarros(10,altura,largura);
        recs = optimizarRecursos(altura,largura,new Recurso("Recursos/car_01.png"),new Recurso("Recursos/p_01.png"),new Recurso("Recursos/p_02.png"),new Recurso("Recursos/p_03.png"));
        mapa = new Mapa(this);
        this.jogador = new Carro(1960,5100,0,12,recs[0]);
        this.jogador.setAltura(altura);
        this.jogador.setLargura(largura);
        this.jogador.setAceleracao(0.25);
    }
    private void gerarCarros(int num,int altura,int largura)
    {
        Recurso[] op = new Recurso[9];
        for(int i=0;i<op.length;i++)
            op[i]=new Recurso("Recursos/car_0"+(i+1)+".png");
        op = optimizarRecursos(altura,largura,op);
        for(int i=0;i<num;i++){
            Carro carro = new CNJ(1000,1000,0,Math.random()*12+1,op[(int)(Math.random()*9)]);
            carro.setAltura(altura);
            carro.setLargura(largura);
            carro.setAceleracao(Math.random()+0.001);
            Controle c = new Controle();
            c.setDireita(true);
            carro.setControle(c);
            carros.add(carro);
        }
    }
    public Mapa getMapa() {
        return mapa;
    }
    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }
    private Recurso[] optimizarRecursos(int altura, int largura,Recurso ...img)
    {
        for(int i=0;i<img.length;i++){
            BufferedImage escalada = new BufferedImage(largura,altura,img[i].getImagem().getType());
            escalada.createGraphics().drawImage(img[i].getImagem(),0,0,largura,altura,null);
            img[i].setImagem(escalada); 
        }
        return img;
    }
    public Carro getJogador() {
        return jogador;
    }
    public void setJogador(Carro jogador) {
        this.jogador = jogador;
    }
    public Controle getControle() {
        return controle;
    }
    public void setControle(Controle controle) {
        this.controle = controle;
    }
    public void desenhar(Graphics2D g)
    {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        //Desenhar o mapa
        mapa.setTamanhoTela(dim);
        if(!carregado)
            try {
                mapa.carregarMapa("Mapas/mapa00.txt");
                tamMundo = new int[]{mapa.getMapa().length*mapa.getEscala(),mapa.getMapa().length*mapa.getEscala()};
                jogador.setPosTela(new int[]{dim[0]/2,dim[1]/2});
                carregado=true;
            } catch (IOException e) {}
        mapa.desenhar(g);
        //Desenhar os carros CNJ
        for(int i=0;i<carros.size();i++)
        {
            carros.get(i).mover(0.02);
            carros.get(i).setPosTela(new int[]{(int)(carros.get(i).getX()-getJogador().getX()+getJogador().getPosTela()[0]),(int)(carros.get(i).getY()-getJogador().getY()+getJogador().getPosTela()[1])});
            carros.get(i).desenhar(g);
        }
        //Informacoes do Carro 
        g.drawString("Velocidade: "+this.jogador.getVely()+" px/Frame", dim[0]-200, dim[1]-20);
        g.drawString("Velocidade Máxima: "+this.jogador.getMaxVel()+" px/Frame", dim[0]-200, dim[1]-30);
        g.drawString("Rotacao: "+this.jogador.getRotacao()+"º", dim[0]-200, dim[1]-40);
        g.drawString("Posição: ("+new DecimalFormat("0.00").format(this.jogador.getX())+", "+new DecimalFormat("0.00").format(this.jogador.getY())+") px", dim[0]-200, dim[1]-50);
        
        //Posicionar o jogador no centro da tela
        if(this.jogador.getVely()==0) this.jogador.setImagem(recs[0]);
        else this.jogador.setImagem(recs[1],recs[2],recs[3]);
        
        mostrarMapa(g);
        
        this.jogador.setControle(controle);
        this.jogador.mover(0.02);
        this.jogador.desenhar(g);
    }
    private void mostrarMapa(Graphics2D g)
    { 
        // Mundo (Mapa e localizacao jogador)
        g.setPaint(Color.white);
        g.drawRect(20, dim[1]-100, 100, 100);   
        g.fillArc(20+(int)((this.jogador.getX()/tamMundo[0])*100),dim[1]-100+ (int)((this.jogador.getY()/tamMundo[1])*100), 5, 5, 0, 360);
        for(int i=0;i<carros.size();i++)
        {
            g.setPaint(Color.blue);  
            g.fillArc(20+(int)((carros.get(i).getX()/tamMundo[0])*100),dim[1]-100+ (int)((carros.get(i).getY()/tamMundo[1])*100), 5, 5, 0, 360);
        }
    }
}
