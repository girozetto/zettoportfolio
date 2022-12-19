//Realizar um ataque especial girat?rio


const campo=document.getElementById("campo");
const areaDesenho=campo.getContext("2d");
campo.height=window.innerHeight;
campo.width=window.innerWidth;
var pontuacao=1;
var esquerda=false;
var cima=false;
var direita=false;
var baixo=false;
const friccaoP=0.02;
const friccao=0.06;
/*var destinoX=campo.clientWidth;
var destinoY=campo.clientHeight;
var origemX=0;
var origemY=0;
var posX=0;
var posY=origemY;*/
var bolas=[];
var projecteis=[];
var inimigos=[];
window.addEventListener('keydown',function(e){
	if(e.keyCode==37)
		esquerda=true;
	if(e.keyCode==38)
		cima=true;
	if(e.keyCode==39)
		direita=true;
	if(e.keyCode==40)
		baixo=true;
});

window.addEventListener('keyup',function(e){
	if(e.keyCode==37)
		esquerda=false;
	if(e.keyCode==38)
		cima=false;
	if(e.keyCode==39)
		direita=false;
	if(e.keyCode==40)
		baixo=false;
});
class Vetor{
	constructor(x,y)
	{
		this.x=x;
		this.y=y;
	}
	adicao(vetor)
	{
		return new Vetor(this.x+vetor.x,this.y+vetor.y);
	}
	subtracao(vetor)
	{
		return new Vetor(this.x-vetor.x,this.y-vetor.y);
	}
	magnitude()
	{
		return Math.sqrt(this.x**2+this.y**2);
	}
	produtoEscalar(escalar)
	{
		return new Vetor(this.x*escalar,this.y*escalar);
	}
	unidade()
	{
		if(this.magnitude()!=0)
			return new Vetor(this.x/this.magnitude(),this.y/this.magnitude());
		else
			return new Vetor(0,0);
	}
	static ponto(vetor1,vetor2)
	{
		return vetor1.x*vetor2.x+vetor1.y*vetor2.y;
	}
	desenhar(xi,yi,num,cor)
	{
		areaDesenho.beginPath();
		areaDesenho.moveTo(xi,yi);
		areaDesenho.lineTo(xi+this.x*num,yi+this.y*num);
		areaDesenho.strokeStyle=cor;
		areaDesenho.lineCap="round";
		areaDesenho.stroke();
	}
}

class Bola
{
	constructor(x,y,raio,massa)
	{
		this.posicao=new Vetor(x,y);
		this.raio=raio;
		
		this.massa=massa;
		if(this.massa===0)
			this.massa_inversa=0;
		else
			this.massa_inversa=1/this.massa;
		this.elasticidade=1;
		this.acelera=new Vetor(0,0);
		this.aceleracao=1;
		
		this.velocidade=new Vetor(0,0);
		
		this.corParagens=new Array(
		{cor:"#0A813C",percentagem:0},
		{cor:"#FFFF00",percentagem:.125},
		{cor:"#00FF00",percentagem:.375},
		{cor:"#0000FF",percentagem:.625},
		{cor:"#FF00FF",percentagem:.875},
		{cor:"#0A81AC",percentagem:1});
		this.jogador=false;
		bolas.push(this);
	}
	desenhar(texto)
	{
		areaDesenho.beginPath();
		areaDesenho.arc(this.posicao.x,this.posicao.y,this.raio,(Math.PI/180)*0,(Math.PI/180)*360,false);
		areaDesenho.shadowOffsetX=5;
		areaDesenho.shadowOffsetY=5;
		areaDesenho.shadowColor="black";
		areaDesenho.shadowBlur=8;
		var gradiente=areaDesenho.createRadialGradient(this.posicao.x,this.posicao.y,this.raio*0.8,this.posicao.x,this.posicao.y,this.raio*0.9);
		for(var i=0;i<this.corParagens.length;i++)
		{
			var tempoCorParagem=this.corParagens[i];
			var cor=tempoCorParagem.cor;
			var percentagem=tempoCorParagem.percentagem;
			gradiente.addColorStop(percentagem,cor);
			percentagem+=.015;
			if(percentagem>1)
			{
				percentagem=0;
			}
			this.corParagens[i].percentagem=percentagem;
		}
		if(this.jogador)
			if(pontuacao%1000==0)
				areaDesenho.fillStyle=gradiente;
			else
				areaDesenho.fillStyle="white"
		else
			areaDesenho.fillStyle="red"
		areaDesenho.fill();
		areaDesenho.closePath();
		
		/*
		areaDesenho.beginPath();
		areaDesenho.arc(this.posicao.x,this.posicao.y,this.raio*0.75,(Math.PI/180)*0,(Math.PI/180)*70,false);
		areaDesenho.lineWidth=3;
		areaDesenho.strokeStyle="#ddd";
		areaDesenho.stroke();
		areaDesenho.closePath();
		*/
	}
	reposicao()
	{
		this.velocidade=this.velocidade.produtoEscalar(1-friccao);
		this.velocidade=this.velocidade.adicao(this.acelera);
		this.posicao=this.posicao.adicao(this.velocidade);
	}
	direcao()
	{
		this.velocidade.desenhar(campo.clientWidth/2,campo.clientHeight/2,10,"green");
		this.acelera.unidade().desenhar(campo.clientWidth/2,campo.clientHeight/2,this.raio,"red");
		areaDesenho.beginPath();
		areaDesenho.arc(campo.clientWidth/2,campo.clientHeight/2,this.raio,(Math.PI/180)*0,(Math.PI/180)*360,false);
		areaDesenho.fillStyle="black";
		areaDesenho.fillText("x: "+parseInt(this.posicao.x)+" y: "+parseInt(this.posicao.y),(campo.clientWidth/2)-50,(campo.clientHeight/2)-40);
		areaDesenho.lineWidth=2;
		areaDesenho.strokeStyle="white";
		areaDesenho.stroke();
		areaDesenho.closePath();
	}
}
class Projectil extends Bola
{
	constructor(x,y,raio,massa)
	{
		super();
		this.posicao=new Vetor(x,y);
		this.raio=raio;
		this.massa=massa;
		if(this.massa===0)
			this.massa_inversa=0;
		else
			this.massa_inversa=1/this.massa;
		this.elasticidade=1;
		this.acelera=new Vetor(1,1);
		this.aceleracao=0.8;
		this.velocidade=new Vetor(0,0);
		bolas.pop();
		projecteis.push(this);
	}
	desenhar()
	{
		areaDesenho.beginPath();
		areaDesenho.arc(this.posicao.x,this.posicao.y,this.raio,(Math.PI/180)*0,(Math.PI/180)*360,false);
		areaDesenho.shadowOffsetX=5;
		areaDesenho.shadowOffsetY=5;
		areaDesenho.shadowColor="black";
		areaDesenho.shadowBlur=8;
		areaDesenho.fillStyle="white"
		areaDesenho.fill();
		areaDesenho.closePath();	
	}
	reposicao()
	{
		this.velocidade=this.velocidade.adicao(this.acelera);
		this.velocidade=this.velocidade.produtoEscalar(1-friccaoP);
		this.posicao=this.posicao.adicao(this.velocidade);
	}
}

function inserirInimigos()
{
	setInterval(()=>{
		const raio = Math.random()*30+10;
		const massa = Math.random()*70+20
		var destinoX=bolas[0].posicao.x;
		var destinoY=bolas[0].posicao.y;
		var x;
		var y;
		if(Math.random()<0.5)
		{
			x=Math.random()<0.5?0-(raio*2):campo.width+(raio*2);
			y=Math.random()*campo.height;
		}
		else
		{
			x=Math.random()*campo.width;
			y=Math.random()<0.5?0-raio:campo.height+raio;
		}
		const angulo=Math.atan2(destinoY-y,destinoX-x);
		const bola = new Bola(x,y,raio,massa);
		bola.velocidade=new Vetor(Math.cos(angulo),Math.sin(angulo));
		bola.acelera=bola.velocidade.unidade().produtoEscalar(0.3);
	},1500);
}

const controle=(bola)=>
{
	if(cima)
		bola.acelera.y=-bola.aceleracao;
	if(baixo)
		bola.acelera.y=bola.aceleracao;
	if(esquerda)
		bola.acelera.x=-bola.aceleracao;
	if(direita)
		bola.acelera.x=bola.aceleracao;
	if((!cima && !baixo) || (cima && baixo))
	{
		bola.acelera.y=0;
	}
	if((!esquerda && !direita) || (esquerda && direita))
	{
		bola.acelera.x=0;
	}
}
function resolverPenetracaoBolas(bola1,bola2)
{
	var distancia = bola1.posicao.subtracao(bola2.posicao);
	var penetracao_depth=bola1.raio+bola2.raio-distancia.magnitude();
	var res_penetracao=distancia.unidade().produtoEscalar(penetracao_depth/(bola1.massa_inversa+bola2.massa_inversa));
	bola1.posicao=bola1.posicao.adicao(res_penetracao.produtoEscalar(bola1.massa_inversa));
	bola2.posicao=bola2.posicao.adicao(res_penetracao.produtoEscalar(-bola2.massa_inversa));
}
function resolverColisaoBolas(bola1,bola2)
{
	var normal=bola1.posicao.subtracao(bola2.posicao).unidade();
	var velocidadeRelativa = bola1.velocidade.subtracao(bola2.velocidade);
	var velocidadeSeparada=Vetor.ponto(velocidadeRelativa,normal);
	var novaVelocidadeSeparada=-velocidadeSeparada*Math.min(bola1.elasticidade,bola2.elasticidade);
	var vetorVelocidadeSeparada=normal.produtoEscalar(novaVelocidadeSeparada);
	
	var diferencaVelocidadeSeparada=novaVelocidadeSeparada-velocidadeSeparada;
	var impulso=diferencaVelocidadeSeparada/(bola1.massa_inversa+bola2.massa_inversa);
	var vetorImpulso=normal.produtoEscalar(impulso);
	
	bola1.velocidade=bola1.velocidade.adicao(vetorImpulso.produtoEscalar(bola1.massa_inversa));
	bola2.velocidade=bola2.velocidade.adicao(vetorImpulso.produtoEscalar(-bola2.massa_inversa));
}
function detectarColisaoBolas(bola1,bola2)
{
	if(bola1.raio+bola2.raio >= bola2.posicao.subtracao(bola1.posicao ).magnitude())
		return true;
	else
		return false;
}

function definirPropriedade(texto)
{
	while(true)
	{
		var numero=prompt(texto);
		if(!isNaN(numero))
			break;
	}
	return numero;
}
function criarBola(x,y)
{
	var massa=definirPropriedade("Inserir Massa");
	var raio=definirPropriedade("Inserir Raio");
	bolas.push(new Bola(x,y,raio,massa));
	if(bolas.length===1)
		bolas[0].jogador=true;	
}
window.onclick=()=>{
	const angulo=Math.atan2(event.clientY-bolas[0].posicao.y,event.clientX-bolas[0].posicao.x);
	var projectil=new Projectil(bolas[0].posicao.x,bolas[0].posicao.y,10,3000);
	projectil.velocidade=new Vetor(Math.cos(angulo),Math.sin(angulo));
	projectil.acelera=projectil.velocidade.unidade().produtoEscalar(projectil.aceleracao);
	/*var posicaoClique=new Vetor(event.clientX,event.clientY);
	bolas.forEach((bola,i)=>{
		var distancia=posicaoClique.subtracao(bola.posicao);
		if(distancia.magnitude()<bola.raio)
		{
			bola.jogador=!bola.jogador;
		}
	});*/
}
function mudarDirecao()
{
	setInterval(()=>{
		var destinoX=bolas[0].posicao.x;
		var destinoY=bolas[0].posicao.y;
		bolas.forEach((bola,i)=>{
			if(!bola.jogador)
			{
				const angulo=Math.atan2(destinoY-bola.posicao.y,destinoX-bola.posicao.x);
				bola.velocidade=new Vetor(Math.cos(angulo),Math.sin(angulo));
				bola.acelera=bola.velocidade.unidade().produtoEscalar(0.3);
			}
		});
	},1500);
}
function ataqueEspecial()
{
	
}
function animar()
{
	areaDesenho.clearRect(0,0,campo.clientWidth,campo.clientHeight);
	
	bolas.forEach((bola,indice)=>{
		bola.desenhar("n?"+(indice+1));
		if(bola.jogador)
		{
			controle(bola);
			bola.direcao();
		}
		for(var i=indice+1;i<bolas.length;i++)
			if(detectarColisaoBolas(bolas[indice],bolas[i]))
			{
				resolverPenetracaoBolas(bolas[indice],bolas[i]);
				resolverColisaoBolas(bolas[indice],bolas[i]);
			}
		bola.reposicao();
	});
	projecteis.forEach((projectil,indice)=>{
		projectil.desenhar();
		for(var i=0;i<bolas.length;i++)
			if(!bolas[i].jogador)	
				if(detectarColisaoBolas(projectil,bolas[i]))
				{
					resolverPenetracaoBolas(projectil,bolas[i]);
					resolverColisaoBolas(projectil,bolas[i]);
					pontuacao+=parseInt(bolas[i].raio);
				}
		projectil.reposicao();
	});
	requestAnimationFrame(animar);
}
var bola1 = new Bola(campo.clientWidth/2,campo.clientHeight/2,30,60);
bola1.jogador=true;
requestAnimationFrame(animar);
inserirInimigos();
/*

const animacao = setInterval(()=>{
	if(posX!=destinoX)
	{
		areaDesenho.clearRect(0,0,campo.clientWidth,campo.clientHeight);
		posX=((posY-origemY)*(destinoX-origemX)/(destinoY-origemY))+origemX;
		areaDesenho.beginPath();
		areaDesenho.moveTo(origemX,origemY);
		areaDesenho.lineTo(posX,posY);
		areaDesenho.lineCap="round";
		areaDesenho.lineWidth=5;
		areaDesenho.strokeStyle="white";
		areaDesenho.stroke();
		posY++;
	}
	else
	{
		posX=origemX;
		posY=origemY;
	}
	cidades.forEach((cidade,i)=>{
		cidade.desenhar();
	});
},1000/70);
*/
