class CharSprite
{
    constructor(lista,acoes)
    {
        this.lista = [];
        this.tipoActual="";
        acoes.forEach((act,i)=>{
            this.lista.push({indice:0,tipo:act,sequencia:[]});
            lista[i].forEach((img,i2)=>{
                this.lista[i].sequencia.push({imagem:new Image(), carregado:false});
                this.lista[i].sequencia[i2].imagem.src="imagens/"+img;
                this.lista[i].sequencia[i2].imagem.onload=(e)=>{
                    this.lista[i].sequencia[i2].carregado=true;
                };
            });
        });
    }
    recuar(acao)
    {
        acao.indice = (i-1)%(acao.sequencia.length-1);
    }
    obterImagem(tipo,frames)
    {
        this.tipoActual=tipo;
        for(var i=0;i<this.lista.length;i++)
            if(this.lista[i].tipo == tipo){
                this.lista[i].indice = frames+2>=framePorSprite ? (this.lista[i].indice+1)%this.lista[i].sequencia.length:this.lista[i].indice;
                    return this.lista[i].sequencia[this.lista[i].indice];
            }
    }
}
class Corpo
{
    constructor(x,y,altura,largura,cor="black")
    {
        this.x=x;
        this.y=y;
        this.altura=altura;
        this.largura=largura;
        this.cor=cor;
        this.ladoDireito =true;
        this.fundo = {imagem:new Image(this.largura,this.altura), carregado:false};
    }
    inserirImagem(caminho)
    {
        this.fundo.imagem.src="imagens/"+caminho;
        this.fundo.imagem.onload=(e)=>{
            this.fundo.carregado=true;
        };
    }
    colisao(corpo,direita=false)
    {
        return !direita && (this.x + this.largura >= corpo.x && this.y <= corpo.y+corpo.altura && this.y + this.altura >= corpo.y) || direita && (this.x <= corpo.x+corpo.largura && this.y <= corpo.y+corpo.altura && this.y + this.altura >= corpo.y);
    }
    desenhar(lapis)
    {
        lapis.beginPath();
        lapis.fillStyle=this.cor;
        lapis.fillRect(this.x,this.y,this.largura,this.altura);
        if(this.fundo.carregado)
        {
            lapis.save();
            lapis.translate(this.x+(this.ladoDireito ? this.largura : 0),this.y);
            lapis.scale(this.ladoDireito ? -1:1,1)
            lapis.drawImage(this.fundo.imagem,0,0,this.largura,this.altura);
            lapis.restore();
        }
        lapis.closePath();
    }
}

class Jogador extends Corpo
{
    constructor(x,y,altura,largura,nome="Gilberto", velocidade={x:0,y:0},cor="#09659700")
    {
        super(x,y,altura,largura,cor);
        this.velocidade = velocidade;
        this.colisor = null;
        this.controle = new Controle();
        this.sprites = null;
        this.nome=nome;
    }
    atacar(adversario)
    {
        this.colisor = this.controle.movimento.soco.estado ? new Corpo(this.x+this.largura,this.y,dimCol.altura,dimCol.largura,"red"):new Corpo(this.x+this.largura,this.y+this.altura-dimCol.altura,dimCol.altura,dimCol.largura,"red");
        if(this.x>adversario.x) this.colisor = this.controle.movimento.soco.estado ? new Corpo(this.x-dimCol.largura,this.y,dimCol.altura,dimCol.largura,"red"):new Corpo(this.x-dimCol.largura,this.y+this.altura-dimCol.altura,dimCol.altura,dimCol.largura,"red");
        if(!this.colisor.colisao(adversario,adversario.x<this.x)) return acoes.semDano;
        if(this.controle.movimento.soco.estado)
            return acoes.soco;
        return acoes.pontape;
    }
    movimentar(altura, largura,frames)
    {
        if(!this.controle) return;
        if(this.sprites)
        {
            if(this.controle.movimento.direita.estado) this.fundo = this.ladoDireito? this.sprites.obterImagem(tiposacoes[3],frames):this.sprites.obterImagem(tiposacoes[1],frames);
            else if(this.controle.movimento.esquerda.estado) this.fundo = this.ladoDireito? this.sprites.obterImagem(tiposacoes[1],frames): this.sprites.obterImagem(tiposacoes[3],frames);
            else this.fundo = this.sprites.obterImagem(tiposacoes[0],frames);
        }

        this.altura = this.controle.movimento.baixo.estado ? dimJog.largura*proporcao: dimJog.altura*proporcao;
        this.largura = this.controle.movimento.baixo.estado ? dimJog.altura*proporcao: dimJog.largura*proporcao;
        this.velocidade.x = (this.controle.movimento.direita.estado?acoes.andar:0) + (this.controle.movimento.esquerda.estado?-acoes.andar:0);
        this.velocidade.y = (this.y+this.altura>=altura)? 0:this.velocidade.y; 
        this.velocidade.y += (this.controle.movimento.cima.estado?-acoes.saltar:0)+((this.y+this.altura>=altura)?0:gravidade);
        this.x += this.velocidade.x;
        if(this.x < 0) this.x =  0 ;
        else if(this.x+this.largura>largura)this.x=largura-this.largura;
        this.y=(this.y+this.altura>altura) ?  altura-this.altura : this.y+this.velocidade.y;
        this.controle.movimento.cima.estado=false;
    }
}
class Barra
{
    constructor(tipoContador,...jogadores)
    {
        this.contador = tipoContador;
        this.barras = [];
        jogadores.forEach((j)=>{
            this.barras.push({id:j.id,vida:100,nome:j.jogador.nome});
        });
        this.contagem = setInterval(()=>{
            if(this.contador==0) clearInterval(this.contagem);
            else this.contador--;
        },1800);
    }
    danificar(id, dano)
    {
        this.barras=this.barras.map((bar)=>{
            return  {id:bar.id,vida:bar.id==id?bar.vida-dano:bar.vida,nome:bar.nome};
        });
    }
    #desenharVidas(lapis, largura)
    {
        var pulo = 0;

        lapis.beginPath();
        lapis.fillStyle="green";
        lapis.rect((largura/2),dimBAR.y+pulo,(largura/2)*(this.barras[1].vida/100),dimBAR.altura*proporcao);
        lapis.fill();
        lapis.closePath();

        lapis.beginPath();
        lapis.fillStyle="green";
        lapis.rect((largura/2)*((100-this.barras[0].vida)/100),dimBAR.y+pulo,(largura/2)*(this.barras[0].vida/100),dimBAR.altura*proporcao);
        lapis.fill();
        lapis.closePath();

        lapis.beginPath();
        lapis.fillStyle="white";
        lapis.font = 20*proporcao+"px Arial"
        lapis.fillText(this.barras[0].nome,10,dimBAR.y+25*proporcao,10*this.barras[0].nome.length*proporcao);
        lapis.fill();
        lapis.closePath();

        lapis.beginPath();
        lapis.fillStyle="white";
        lapis.font = 20*proporcao+"px Arial"
        lapis.fillText(this.barras[1].nome,largura-10*this.barras[1].nome.length*proporcao,dimBAR.y+25*proporcao,10*this.barras[1].nome.length);
        lapis.fill();
        lapis.closePath();
        
        this.barras.forEach((bar,i)=>{
            if(bar.vida<=0) this.contador=bar.vida=0;
            if(i/2>pulo) pulo+=dimBAR.altura*3;
        });
        
    }
    #desenharContador(lapis, largura)
    {
        lapis.beginPath();
        lapis.fillStyle="white";
        lapis.font = 40*proporcao+"px Arial"
        lapis.fillText(""+this.contador,(largura/2)-15,dimBAR.y+30*proporcao,30);
        lapis.fill();
        lapis.closePath();
    }
    desenhar(lapis,largura)
    {
        lapis.beginPath();
        lapis.fillStyle="#9C111F";
        lapis.rect(dimBAR.x,dimBAR.y,largura,dimBAR.altura*proporcao);
        lapis.fill();
        lapis.closePath();
        this.#desenharVidas(lapis,largura);
        lapis.beginPath();
        lapis.fillStyle="black";
        lapis.arc((largura/2),dimBAR.y+20*proporcao,40*proporcao,0,(Math.PI*360)/180);
        lapis.fill();
        lapis.closePath();
        this.#desenharContador(lapis, largura);
    }
}
class Controle
{
    constructor(movimento={cima:{estado:false,tecla:"w"},baixo:{estado:false,tecla:"s"},direita:{estado:false,tecla:"d"}, esquerda:{estado:false,tecla:"a"}, soco:{estado:false,tecla:"n"}, pontape:{estado:false,tecla:"m"}})
    {
        this.movimento = movimento;
        this.#adicionarOuvintes();
    }
    #ataque(tecla,estado)
    {
        switch(tecla)
        {
            case this.movimento.soco.tecla:
                this.movimento.soco.estado=estado;
                break;
            case this.movimento.pontape.tecla:
                this.movimento.pontape.estado=estado;
                break;
        }
    }
    #movimento(tecla,estado)
    {
        switch(tecla)
        {
            case this.movimento.cima.tecla:
                this.movimento.cima.estado=estado;
                break;
            case this.movimento.baixo.tecla:
                this.movimento.baixo.estado=estado;
                break;
            case this.movimento.direita.tecla:
                this.movimento.direita.estado=estado;
                break;
            case this.movimento.esquerda.tecla:
                this.movimento.esquerda.estado=estado;
                break;
        }
    }
    #adicionarOuvintes()
    {

        document.addEventListener("keydown",(evento)=>{
            this.#movimento(evento.key,true);
            this.#ataque(evento.key,true);
        });
        document.addEventListener("keyup",(evento)=>{
            this.#movimento(evento.key,false);
            this.#ataque(evento.key,false);
        });
    }
}