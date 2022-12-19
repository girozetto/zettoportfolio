var matriz=[];
var alagadas=[];
const AREA=2.50;
function criarMatrizes(tamanho)
{
	for(var i=0;i<tamanho;i++)
	{
		matriz.push([]);
		alagadas.push([]);
		for(var j=0;j<tamanho;j++)
		{	
			matriz[i].push(0);
			alagadas[i].push(0);
		}
	}
}
function reiniciarMatriz(m)
{
	for(var i=0;i<m.length;i++)
	{
		for(var j=0;j<m[i].length;j++)
		{	
			m[i][j]=0;
		}
	}
}
function encontrarVizinho(i,j,num)
{
	if(matriz[i][j]>0)
		alagadas[i][j]=num;
	else
		return;
	if(matriz[i].length>j+1)
		if(alagadas[i][j+1]==0)
			encontrarVizinho(i,j+1,num);
	if(j-1>=0)
		if(alagadas[i][j-1]==0)
			encontrarVizinho(i,j-1,num);
	if(matriz.length>i+1)
		if(alagadas[i+1][j]==0)
			encontrarVizinho(i+1,j,num);
	if(i-1>=0)
		if(alagadas[i-1][j]==0)
			encontrarVizinho(i-1,j,num);
}
function preencher()
{
	var num=0;
	reiniciarMatriz(alagadas);
	for(var j=0;j<matriz.length;j++)
		for(var i=0;i<matriz[j].length;i++)
			if(matriz[i][j]>0 && alagadas[i][j]==0)
			{
				num++;
				encontrarVizinho(i,j,num);
			}
	alert("*********Matrizes Preenchidas Com Sucesso**********");
}
function quantidade()
{
	var maior=0;
	for(var i=0;i<matriz.length;i++)
		for(var j=0;j<matriz[i].length;j++)
			if(alagadas[i][j]>maior)
				maior=alagadas[i][j];
	return maior;
}
function areas(v,tamanho)
{
	for(var k=1;k<=tamanho;k++)
		for(var i=0;i<alagadas.length;i++)
			for(var j=0;j<alagadas[i].length;j++)
				if(k==alagadas[i][j])
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
	var resumo = "Regiao\tMetros Quadrados\n";
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
				resumo+= "\n"+(maior+1)+"\t"+(v[maior]*AREA);
				total+=v[maior];
				i=0;
				v[maior]=0;
				maior=0;
			}
			else
				break;
		}
	}
	return resumo + "\n Total de Area Alagada: "+(total*AREA);
}
function gerar()
{
	for(var i = parseInt(Math.random()*5+1);i>0;i--)
	{
		matriz[parseInt(Math.random()*matriz.length)][parseInt(Math.random()*matriz[0].length)]=1;
	}
}
function mostrar()
{
	var v1 = document.getElementById("texto");
	var v2 = document.getElementById("texto2");
	var m1 = "";
	var m2 = "";
	for(var i=0;i<matriz.length;i++)
	{
		for(var j=0;j<matriz.length;j++)
		{
			m1+= matriz[i][j]+"\t";
			m2+= alagadas[i][j]+"\t";
		}
		m1+=" \n";
		m2+=" \n";
	}
	v1.innerText=m1;
	var resumo=regiaoAlagada();
	v2.innerText=m2+"\n"+(resumo==undefined? "Sem Zonas Alagadas":resumo);
	v1.setAttribute("rows",""+matriz.length);
	v1.setAttribute("cols",""+matriz[0].length);
	v2.setAttribute("cols",""+matriz[0].length);
	v2.setAttribute("rows",""+(matriz.length+quantidade+1));
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
window.onload=function(){
	
	criarMatrizes(inserirTamanho());
	var botaoGerar = document.getElementById("bGerar");
	mostrar();
	botaoGerar.onclick=function(){
		gerar();
		preencher();
		mostrar();
	};
	var botaoResetar = document.getElementById("bResetar");
	botaoResetar.onclick=function(){
		reiniciarMatriz(matriz);
		reiniciarMatriz(alagadas);
		mostrar();
	};
};

