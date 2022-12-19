var matriz=[];
var alagadas=[];
const AREA=2.50;
const atualizar=()=>{
		preencher();
		mostrar();
	};
function criarMatrizes(tamanho)
{
	var input,span;
	var linha1,linha2;
	for(var i=0;i<tamanho;i++)
	{
		linha1 = document.createElement("div");
		linha2 = document.createElement("div");
		linha1.classList.add("linhaA");
		linha2.classList.add("linhaB");
		for(var j=0;j<tamanho;j++)
		{	
			input=document.createElement("input");
			input.type="checkbox";
			input.onchange=atualizar;
			span=document.createElement("span");
			span.innerText="-";
			linha1.appendChild(input);
			linha2.appendChild(span);
		}
		document.querySelector(".matriz").appendChild(linha1);
		document.querySelector(".zonas").appendChild(linha2);
	}
	matriz=document.querySelectorAll(".linhaA");
	alagadas=document.querySelectorAll(".linhaB");
}
function reiniciarMatriz()
{
	matriz.forEach((linha,linI)=>{
		Array.from(linha.children).forEach((coluna,colI)=>
		{
			coluna.checked=false;
		});
	});
}
function reiniciarZonas()
{
	alagadas.forEach((linha,linI)=>{
		Array.from(linha.children).forEach((coluna,colI)=>
		{
			coluna.innerText="-";
			coluna.style="color:black";
		});
	});
}
function encontrarVizinho(i,j,num,cor)
{
	if(matriz[i].children[j].checked)
	{
		alagadas[i].children[j].innerText=num;
		alagadas[i].children[j].style="color:"+cor;
	}
	else
		return;
	if(matriz[i].children.length>j+1)
		if(alagadas[i].children[j+1].innerText==="-")
			encontrarVizinho(i,j+1,num,cor);
	if(j-1>=0)
		if(alagadas[i].children[j-1].innerText==="-")
			encontrarVizinho(i,j-1,num,cor);
	if(matriz.length>i+1)
		if(alagadas[i+1].children[j].innerText==="-")
			encontrarVizinho(i+1,j,num,cor);
	if(i-1>=0)
		if(alagadas[i-1].children[j].innerText==="-")
			encontrarVizinho(i-1,j,num,cor);
}
function preencher()
{
	var num=0;
	var cor="";
	reiniciarZonas();
	for(var j=0;j<matriz.length;j++)
		for(var i=0;i<matriz[j].children.length;i++)
			if(matriz[i].children[j].checked && alagadas[i].children[j].innerText=="-")
			{
				num++;
				cor="rgb("+Math.floor(Math.random()*255+1)+","+Math.floor(Math.random()*255+1)+","+Math.floor(Math.random()*255+1)+")";
				encontrarVizinho(i,j,num,cor);
			}
}
function quantidade()
{
	var maior=0;
	var l;
	for(var i=0;i<matriz.length;i++)
		for(var j=0;j<matriz[i].children.length;j++)
		{	
			l=alagadas[i].children[j].innerText;
			if(!isNaN(l))
				if(parseInt(l)>maior)
					maior=parseInt(l);
		}
	return maior;
}
function areas(v,tamanho)
{
	for(var k=1;k<=tamanho;k++)
		for(var i=0;i<alagadas.length;i++)
			for(var j=0;j<alagadas[i].children.length;j++)
				if(k==parseInt(alagadas[i].children[j].innerText))
					v[k-1]++;
}
function regiaoAlagada()
{
	var tamanho=quantidade();
	var i=0;
	if(tamanho==0)
	{	
		alert("Por Favor Preencha a Primeira Matriz");
		return;
	}
	var total=0;
	var maior=0;
	var v=[];
	for(var j=0;j<tamanho;j++)
		v.push(0);
	areas(v,tamanho);
	var resumo = "<table border=1px bgColor='#00feef'><tr align='center'><th>Regiao</th><th>Metros Quadrados</th></tr>";
	while(true)
	{	
		if(i<tamanho)
		{
			if(v[i]>v[maior])
			{
				maior=i;
			}
			i++;
		}
		else
		{
			if(v[maior]>0)
			{
				resumo+= "<tr><td>"+(maior+1)+"</td><td>"+(v[maior]*AREA)+"</td></tr>";
				total+=v[maior];
				i=0;
				v[maior]=0;
				maior=0;
			}
			else
				break;
		}
	}
	return resumo + "<tr><td colspan='2'> Total de Área Alagada: "+(total*AREA)+" m<sup>2</sup></td></tr></table>";
}

function mostrar()
{
	var v1 = document.querySelector(".descricao");
	var resumo=regiaoAlagada();
	v1.innerHTML="<p>"+(resumo===undefined? "Sem Zonas Alagadas":resumo);
}
function inserirTamanho()
{
	var tamanho;
	while(true)
	{	
		tamanho=prompt("Insira o tamanho da Matriz");
		if(isNaN(tamanho))
			alert("Por Favor Insira um Number");
		else if(tamanho<=0)
			alert("Por Favor Insira um Valor maior do que 0");
		else
			break;
	}
	return tamanho;
}

window.onload=()=>{
	criarMatrizes(inserirTamanho());
	mostrar();
};

