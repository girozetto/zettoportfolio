/*************
 * Autor: Gilberto Alexandre de Jesus 
 * ID: 1000026185
 * Arquivo Javascript
 */
const tmundo = document.getElementById("tmundo");
const tela = document.getElementById("tela");
const desenho = tela.getContext("2d");
tela.width=window.innerWidth;
tela.height=window.innerHeight;
const gravidade = .1;
const coefAtrito = .008;
const rato = {x:0, y:0};
var carrega = false;
const bolas = [];
window.onresize=(e)=>{
    tela.width=window.innerWidth;
    tela.height=window.innerHeight;
};
tela.ondblclick = (e)=>{
    rato.x = e.x;
    rato.y = e.y;
    var raio =bolas[0].raio;
    bolas.push(new Bola({x:rato.x,y:rato.y},raio))
};
tela.onclick = (e)=>{
    rato.x = e.x;
    rato.y = e.y;
    var pos = bolaSelecionada(rato);
    if(pos>=0)
    {
        carrega=false;
        buscarSelecionado();
        bolas[pos].selecionado=true;
    }
    else{
        var ps = elemSelecionado();
        const angulo = Math.atan2(rato.y-bolas[ps].posicao.y,rato.x-bolas[ps].posicao.x);
        bolas[ps].velocidade.x = Math.cos(angulo)*20;
        bolas[ps].velocidade.y = Math.sin(angulo)*20;
    }
};

class Bola
{
    constructor(posicao, raio){
        this.selecionado = false;
        this.rotacao = 0;
        this.posicao = posicao;
        this.raio=raio;
        this.elasticidade=.4;
        this.velocidade={x:Math.random()*8-4,y:Math.random()*8-4};
    }
    colisaoParedes()
    {
        if(this.posicao.y+this.raio >= tela.height)
        {
            this.velocidade.y = -this.velocidade.y;
            this.posicao.y=this.posicao.y-(this.posicao.y+this.raio-tela.height);
            this.velocidade.y -= this.velocidade.y*this.elasticidade;
        }    
        if(this.posicao.x+this.raio >= tela.width || this.posicao.x+this.raio<=0){
            this.velocidade.x = -this.velocidade.x;
        }
        if(Math.abs(this.velocidade.y)<=gravidade*5 && Math.round(this.posicao.y+this.raio) >= tela.height){
            this.velocidade.y=0;
            this.velocidade.x -= (this.velocidade.x*coefAtrito);
        }
    }
    actualizar()
    {
        this.velocidade.y+=gravidade;
        this.colisaoParedes();
        this.rotacao += this.velocidade.x;
        this.posicao.x += this.velocidade.x;
        this.posicao.y += this.velocidade.y;
    }
    desenhar()
    {
        desenho.beginPath();
        desenho.fillStyle=this.selecionado?"green":"white";
        desenho.arc(this.posicao.x,this.posicao.y,this.raio,0,Math.PI*2);
        desenho.fill();
        desenho.closePath();
        desenho.beginPath();
        desenho.save();
        desenho.translate(this.posicao.x,this.posicao.y);
        desenho.rotate((Math.PI/180)*this.rotacao);
        desenho.fillStyle="red";
        desenho.arc(this.raio*0.9,0,this.raio*0.1,0,Math.PI*2);
        desenho.arc(0,0,this.raio*0.1,0,Math.PI*2);
        desenho.arc(-this.raio*0.9,0,this.raio*0.1,0,Math.PI*2);
        desenho.fill();
        desenho.restore();
        desenho.closePath();
        
    }
}
function bolaSelecionada(posicao)
{
    var pos = -1;
    bolas.forEach((bola,i)=>{
        var x = bola.posicao.x-posicao.x;
        var y = bola.posicao.y-posicao.y;
        if(bola.raio >= Math.sqrt(x**2+y**2))    
        {
            pos = i;
            return;
        }    
    });
    return pos;
}
function buscarSelecionado()
{
    bolas.forEach((bola,i)=>{
        if(bola.selecionado)
            pos=i;
        bola.selecionado=false;
    });
}
function elemSelecionado()
{
    var pos=-1;
    bolas.forEach((bola,i)=>{
        if(bola.selecionado){
            pos=i;
        } 
    });
    return pos;
}
function animarTela()
{
    desenho.clearRect(0,0,tela.width,tela.height);
    bolas.forEach((bola,i)=>{
        bola.actualizar();
        bola.desenhar();
    });
    requestAnimationFrame(animarTela);
}
bolas.push(new Bola({x:100,y:100},Math.random()*20+20));
bolas[0].selecionado=true;
animarTela();