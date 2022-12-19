#include<stdio.h>
#define LIN 12
#define COL 10
#define AREA 2.50
typedef enum{TRUE=1,FALSE=0}Boolean;
void inicializarMatriz(int m[][COL]);
int inserirAresta(int m[][COL],char str[],int limite);
void mostrarMatriz(int m[][COL]);
void insercaoDado(int m[][COL]);
void encontrarVizinho(int u[][COL],int m[][COL],int i,int j,int num);
void preencher(int u[][COL],int m[][COL]);
void regiaoAlagada(int m[][COL]);
void areas(int m[][COL],int v[],int tamanho);
int quantidade(int m[][COL]);
void menu(int u[][COL],int m[][COL]);
void visualMenu();
int main()
{
	int U[LIN][COL];
	int M[LIN][COL];
	inicializarMatriz(U);
	inicializarMatriz(M);
	menu(U,M);
	return 0;
}
/******************************************************************
*	Mostrar o conteúdo de uma matriz de forma ordenada
*	Objectivo: Mostrar uma matriz
*	Parâmetros: Recebe como parâmetro uma matriz do tipo inteiro
*	Retorno: Não tem retorno
****************************************************************************/
void mostrarMatriz(int m[][COL])
{
	int i,j;
	printf("  \t");
	for(j=0;j<COL;j++)
		printf("%d\t",j);
	printf("\n\n");	
	for(i=0;i<LIN;i++)
	{
		printf("%d |\t",i);
		for(j=0;j<COL;j++)
			printf("%d\t",m[i][j]);
		printf("|\n");
	}
}
/******************************************************************
*	Inicialização de uma matriz
*	Objectivo: Inicializar uma matriz do tipo inteiro
*	Parâmetros: Recebe como parâmetro uma matriz do tipo inteiro
*	Retorno: Não tem retorno
****************************************************************************/
void inicializarMatriz(int m[][COL])
{
	int i,j;
	for(i=0;i<LIN;i++)
		for(j=0;j<COL;j++)
			m[i][j]=0;
}
/*******************************************************************
*	Visual do Menu Principal
*	Objectivo: Mostrar as opções de escolha para as operações
*	Parâmetros: Não recebe qualquer parâmetro
*	Retorno: Não tem retorno
*********************************************************************/
void visualMenu()
{
	printf("\n\n\t Menu Principal\n\n");
	printf("\n1 -\t Adicionar uma Aresta\n");
	printf("2 -\t Mostrar Areas Totais\n");
	printf("3 -\t Preencher Matriz M\n\n");
	printf("4 -\t Mostrar Matrizes U e M\n\n");
	printf("0 - \t Encerrar O Programa\n>");
}
/******************************************************************
*	Operações do Menu
*	Objectivo: Escolher as opções para realizar uma determinada operacão
*	Parâmetros: Recebe como parâmetros duas matrizes do tipo inteiro
*	Retorno: Não tem retorno
****************************************************************************/
void menu(int u[][COL],int m[][COL])
{
	int operacao;
	do
	{
		visualMenu();
		scanf("%d",&operacao);
		switch(operacao)
		{
			case 1:
				printf("*********Adicionar uma Aresta*********\n");
				insercaoDado(u);
				break;
			case 2:
				printf("*********Mostrar Totais da Areas*******\n");
				regiaoAlagada(m);
				break;
			case 3:
				printf("*********Preenchendo Matriz M**********\n");
				preencher(u,m);
				break;
			case 4:
				printf("*********Mostrando Matrizes U e M********\n");
				printf("\t\t*********Matriz U*********\n");
				mostrarMatriz(u);
				printf("\t\t*********Matriz M*********\n");
				mostrarMatriz(m);
				break;
			case 0:
				printf("Saindo do Programa\n");
				break;
			default:
				printf("Operacao Invalida\n");
				break;
		}
	}
	while(operacao!=0);
}
/*******************************************************************
*	Inserção de Dados na matriz U
*	Objectivo: Inserir Aresta na Matriz U
*	Parâmetros: Recebe uma matriz do tipo inteiro
*	Retorno: Não tem retorno
*********************************************************************/
void insercaoDado(int m[][COL])
{
	int linha=inserirAresta(m,"Linha",LIN),coluna=inserirAresta(m,"Coluna",COL);
	if(m[linha][coluna])
		m[linha][coluna]=0;
	else
		m[linha][coluna]=1;
}
/*******************************************************************
*	Definir uma Linha ou uma Coluna
*	Objectivo: Definir as coordenadas de uma aresta
*	Parâmetros: Recebe como parâmetro uma matriz do tipo inteiro, uma string e um limite do tipo inteiro isso porque é uma matriz do tipo nxm
*	Retorno: retorna uma coordenada inserida do tipo inteiro
*********************************************************************/
int inserirAresta(int m[][COL],char str[],int limite)
{
	int aresta;
	printf("Insira a %s\n>",str);
	while(TRUE)
	{	
		scanf("%d",&aresta);
		if(aresta>=0 && aresta<limite)
			break;
		else 
			printf("%s Invalida - Insira Novamente \n >",str);
	}
	return aresta;
}
/*******************************************************************
*	Encontrar Vizinho
*	Objectivo: Pesquisar e definir as áreas alagadas por ordem numérica
*	Parâmetros: recebe duas matrizes do tipo inteiro, a matriz u e a matriz m, duas coordenadas do tipo inteiro representando a linha e outro a coluna e um inteiro representando o número da área alagada
*	Retorno: Não tem retorno
*********************************************************************/
void encontrarVizinho(int u[][COL],int m[][COL],int i,int j,int num)
{
	if(u[i][j]>0)
		m[i][j]=num;
	else
		return;
	if(COL>j+1)
		if(m[i][j+1]==0)
			encontrarVizinho(u,m,i,j+1,num);
	if(j-1>=0)
		if(m[i][j-1]==0)
			encontrarVizinho(u,m,i,j-1,num);
	if(LIN>i+1)
		if(m[i+1][j]==0)
			encontrarVizinho(u,m,i+1,j,num);
	if(i-1>=0)
		if(m[i-1][j]==0)
			encontrarVizinho(u,m,i-1,j,num);
}
/*******************************************************************
*	Preenchimento da matriz M
*	Objectivo: Preencher a Matriz M através dos dados obtidos da matriz U
*	Parâmetros: Recebe duas matrizes do tipo inteiro, a matriz u e a matriz m
*	Retorno: Não tem retorno
*********************************************************************/
void preencher(int u[][COL],int m[][COL])
{
	int i=0,j=0,num=0;
	inicializarMatriz(m);
	for(j=0;j<COL;j++)
		for(i=0;i<LIN;i++)
			if(u[i][j]>0 && m[i][j]==0)
			{
				num++;
				encontrarVizinho(u,m,i,j,num);
			}
	printf("*********Matriz M Preenchida Com Sucesso**********\n");
}
/*******************************************************************
*	Obter a quantidade de áreas alagadas não ligadas
*	Objectivo: Obter quantidade de áreas alagadas 
*	Parâmetros: Recebe uma matriz do tipo inteiro
*	Retorno: retorna a quantidade em inteiro
*********************************************************************/
int quantidade(int m[][COL])
{
	int i,j,maior=0;
	for(i=0;i<LIN;i++)
		for(j=0;j<COL;j++)
			if(m[i][j]>maior)
				maior=m[i][j];
	return maior;
}
/*******************************************************************
*	Obter tamanhos de cada área alagada
*	Objectivo: Guardar os dados de cada área alagada
*	Parâmetros: Recebe uma matriz do tipo inteiro, um vector do tipo inteiro e uma variável do tipo inteiro
*	Retorno: Não tem retorna nada
*********************************************************************/
void areas(int m[][COL],int v[],int tamanho)
{
	int i,j,k,cont=0;
	for(k=1;k<=tamanho;k++)
		for(i=0;i<LIN;i++)
			for(j=0;j<COL;j++)
				if(k==m[i][j])
					v[k-1]++;
}
/*******************************************************************
*	Resumo das regiões alagadas de acordo a Matriz M
*	Objectivo: Mostrar as estatísticas simplificadas da matriz M
*	Parâmetros: Recebe uma matriz do tipo inteiro
*	Retorno: Não tem retorno
*********************************************************************/
void regiaoAlagada(int m[][COL])
{
	int tamanho=quantidade(m),i=0,j;
	if(tamanho==0)
	{	
		printf("Por Favor Preencha a Matriz M");
		return;
	}
	int total=0,maior=0;
	int v[tamanho];
	for(j=0;j<tamanho;j++)
		v[j]=0;
	areas(m,v,tamanho);
	printf("Regiao\tArea Alagada em metros quadrados\n");
	while(TRUE)
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
				printf("%d\t%.2f\n",maior+1,v[maior]*AREA);
				total+=v[maior];
				i=0;
				v[maior]=0;
				maior=0;
			}
			else
				break;
		}
	}
	printf("Total de Area Alagada:\t%.2f\n",total*AREA);
}
