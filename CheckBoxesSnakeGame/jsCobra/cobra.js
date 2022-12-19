const linhas=document.querySelectorAll(".linha");
const obterItemEm = (x,y) => linhas[y-1].children[x-1];
const checarItemEm = (x,y) => obterItemEm(x,y).checked=true;
const dischecarItemEm = (x,y) => obterItemEm(x,y).checked=false;
const colocarFrangoEm = (x,y) => {
	obterItemEm(x,y).type="radio";
	checarItemEm(x,y);
};
const removerFrangoEm = (x,y) => {
	obterItemEm(x,y).type="checkbox";
	dischecarItemEm(x,y);
};
const obterPosicaoFrango = () => {
	const posicao = [1,1];
	linhas.forEach((linha,linI)=>{
		Array.from(linha.children).forEach((input,inputI)=>
		{
			if(input.type === "radio")
			{
				posicao[0] = inputI+1;
				posicao[1] = linI+1;
			}
		});
	});
	return posicao;
};
const obterPosicaoAleatoria= () => {
	const posicaoDisponivel =[];
	linhas.forEach((linha,linI)=>{
		Array.from(linha.children).forEach((input,inputI)=>{
			if(input.type === "checkbox" && input.checked===false)
			{
				posicaoDisponivel.push([inputI+1,linI+1]);
			}
		});
	});
	const i = Math.floor(Math.random()*(posicaoDisponivel.length)-1)+1;
	return posicaoDisponivel[i];
};
const incrementarPontos=()=>{
	const ponto = document.getElementById("pontos");
	var p = parseInt(ponto.innerText,10);
	ponto.innerText=p+1;
};
const mover = direcao => {
	const posicaoFrango = obterPosicaoFrango();
	const cabeca = [...cobra[0]];
	const cauda = [...cobra[cobra.length-1]];
	const atualizarCobra = () => {
		cobra.unshift(cabeca);
		cobra.pop();
		cobra.forEach(parteCobra => checarItemEm(...parteCobra));
	}
	switch(direcao)
	{
		case "cima": cabeca[1] = cabeca[1]==1? tamanhoMundo : cabeca[1]-1;break;
		case "baixo": cabeca[1] = cabeca[1]==tamanhoMundo? 1 : cabeca[1]+1;break;
		case "esquerda": cabeca[0] = cabeca[0]==1? tamanhoMundo : cabeca[0]-1;break;
		case "direita": cabeca[0] = cabeca[0]==tamanhoMundo? 1 : cabeca[0]+1;break;
	}
	if(obterItemEm(...cabeca).type==="checkbox" && obterItemEm(...cabeca).checked){
		document.querySelector("h1").innerText="Game Over...";
		document.querySelectorAll("input").forEach(input => input.disabled = true);
		clearInterval(intervaloMovimento);
	}
	if(cabeca[0] === posicaoFrango[0] && cabeca[1] === posicaoFrango[1])
	{
		cobra.push(cauda);
		colocarFrangoEm(...obterPosicaoAleatoria());
		removerFrangoEm(...posicaoFrango);
		incrementarPontos();
		atualizarCobra();
	}
	else
	{
		atualizarCobra();
		dischecarItemEm(...cauda);
	}
};
const tratarInput = () => {
	document.addEventListener("keydown",e => {
		switch(e.keyCode)
		{
			case tecla.setaCima: direcaoMovimento = direcaoMovimento==="baixo"?"baixo":"cima"; break;
			case tecla.setaBaixo: direcaoMovimento = direcaoMovimento==="cima"?"cima":"baixo"; break;
			case tecla.setaEsquerda: direcaoMovimento = direcaoMovimento==="direita"?"direita":"esquerda"; break;
			case tecla.setaDireita: direcaoMovimento = direcaoMovimento==="esquerda"?"esquerda":"direita"; break;
		}
		if(intervaloMovimento === undefined)
		{
			intervaloMovimento = setInterval(()=>{mover(direcaoMovimento || "esquerda")},1000/velocidade);
		}
	});
};