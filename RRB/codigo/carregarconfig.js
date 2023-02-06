const DIM = {largura:1360, altura: 768};
const dimBAR = {x:0,y:10,largura: 100, altura: 30};
const dimCol = {largura: 40, altura: 5};
const tipoContador = {baixo: 90, medio: 120, alto: 180};
const gravidade = .3;
const dimJog = {altura: 300, largura: 80, afastamento:10};
const acoes = {andar:2, correr: 4, saltar: 10, soco: 1, pontape: 3, semDano:0};
var contador=0;
var proporcao=0.0;
var barra;
const listaJogadores = [];
const framePorSprite = 15;
var imgs = [{nomeImagem:"giro1", caminho: "1.png"}];
var controles = [{cima:{estado:false,tecla:"w"},baixo:{estado:false,tecla:"s"},direita:{estado:false,tecla:"d"}, esquerda:{estado:false,tecla:"a"}, soco:{estado:false,tecla:"n"}, pontape:{estado:false,tecla:"m"}},{cima:{estado:false,tecla:"ArrowUp"},baixo:{estado:false,tecla:"ArrowDown"},direita:{estado:false,tecla:"ArrowRight"}, esquerda:{estado:false,tecla:"ArrowLeft"}, soco:{estado:false,tecla:"o"}, pontape:{estado:false,tecla:"p"}}]
const tiposacoes = ["parado","andar","defender","recuar"];
const spr = {jogador1:[],jogador2:[]};


//Sprites de Imagens - Parado
spr.jogador1.push([]);
for(var i=1;i<=17;i++)
    spr.jogador1[0].push(tiposacoes[0]+i+".png");

//Sprites de Imagens - Andar
spr.jogador1.push([]);
for(var i=1;i<=9;i++)
    spr.jogador1[1].push(tiposacoes[1]+i+".png");

//Sprites de Imagens - Bloquear
spr.jogador1.push([]);
for(var i=1;i<=4;i++)
    spr.jogador1[2].push(tiposacoes[2]+i+".png");

//Sprites de Imagens - Recuar
spr.jogador1.push([]);
for(var i=9;i>=1;i--)
    spr.jogador1[3].push(tiposacoes[1]+i+".png");