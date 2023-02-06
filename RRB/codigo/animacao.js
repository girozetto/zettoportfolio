function carregando()
{
    listaJogadores.push({id:1, jogador: new Jogador(dimJog.afastamento,tela.height-dimJog.altura, dimJog.altura, dimJog.largura)})
    listaJogadores.push({id:2, jogador:new Jogador(tela.width-dimJog.afastamento-dimJog.largura,tela.height-dimJog.altura, dimJog.altura, dimJog.largura)});
    listaJogadores.forEach((jog,i)=>{
        jog.jogador.controle=new Controle(controles[i]);
    });
    barra = new Barra(tipoContador.baixo,...listaJogadores);

    listaJogadores[0].jogador.sprites=new CharSprite(spr.jogador1,tiposacoes);
    listaJogadores[1].jogador.sprites=new CharSprite(spr.jogador1,tiposacoes);

    listaJogadores[0].jogador.inserirImagem(imgs[0].caminho);
    window.onresize=(e)=>{
        atualizarDimensao(proporcao = (window.innerWidth/DIM.largura));    
     };
}
var frames=0;
function animando()
{
    listaJogadores[0].jogador.ladoDireito=listaJogadores[0].jogador.x>listaJogadores[1].jogador.x;
    listaJogadores[1].jogador.ladoDireito=!listaJogadores[0].jogador.ladoDireito;
    lapis.clearRect(0,0,tela.width,tela.height);
    listaJogadores.forEach((j)=>{
        j.jogador.desenhar(lapis);
        j.jogador.movimentar(tela.height,tela.width,frames);
        if(j.jogador.controle)
            if(j.jogador.controle.movimento.soco.estado || j.jogador.controle.movimento.pontape.estado)
                listaJogadores.forEach((j2)=>{
                    if(j.id!=j2.id){
                        barra.danificar(j2.id,j.jogador.atacar(j2.jogador));
                        j.jogador.colisor.desenhar(lapis);
                        j.jogador.colisor=null;
                        m=false;
                    }
                });
    });

    frames=(frames+1)%framePorSprite;
    barra.desenhar(lapis,tela.width);
    requestAnimationFrame(animando);
}
carregando();
animando();