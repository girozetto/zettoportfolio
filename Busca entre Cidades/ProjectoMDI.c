#include<stdio.h>
#define MAX 16
#define TAM 18
typedef enum{TRUE=1,FALSE=0}Boolean;
//Protótipos das Funções
void visualMenu();
void menuOperacoes(int m[][TAM],char str[][MAX]);
void inicializarMatriz(int m[][TAM]);
void mostrarMatriz(int m[][TAM]);
void inicializarNomes(char str[][MAX]);
void mostrarNomes(char str[][MAX]);
int inserirCidade(char str[][MAX]);
int obterMaiorEntradas(int m[][TAM]);
int obterMaiorSaidas(int m[][TAM]);
int obterMaiorEntradasSaidas(int m[][TAM]);
int foiVisitado(int v[],int nElem,int cidade);
int proximo(int m[][TAM],int v[],int nElem,int cidade,int actual);
void mostrarResultado(int m[][TAM],char str[][MAX],int v[],int nElem,int destino);
int menorDistancia(int m[][TAM],int v1[],int nElem1,int v2[],int nElem2);
void copiarConteudo(int v1[],int nElem,int v2[]);
void operacaoA(int m[][TAM],char str[][MAX]);
void operacaoB(int m[][TAM],char str[][MAX]);
void operacaoC(int m[][TAM],char str[][MAX]);
void operacaoD(int m[][TAM]);
void operacaoE(int m[][TAM],char str[][MAX]);
void operacaoF(int m[][TAM],char str[][MAX]);
void operacaoG(int m[][TAM],char str[][MAX]);
void operacaoH(int m[][TAM],char str[][MAX]);
void operacaoI(int m[][TAM],char str[][MAX]);
void operacaoJ(int m[][TAM],char str[][MAX]);
void operacaoK(int m[][TAM],char str[][MAX]);
//
//Função Principal
int main()
{
	int matriz[TAM][TAM];
	char matrizString[TAM][MAX]={"Benguela","Cabinda","Caxito","Cuito","Dundo","Huambo","Luanda","Lubango","Luena","Malange","MbanzaCongo","Menongue","Mocamedes","Ndalatando","Ondjiva","Saurimo","Sumbe","Uige"};
	inicializarMatriz(matriz);
	mostrarMatriz(matriz);
	mostrarNomes(matrizString);
	menuOperacoes(matriz,matrizString);
	return 0;
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
	printf("\n1 -\t Estradas de Saida de uma dada cidade\n");
	printf("2 -\t Anel rodoviario de uma dada cidade\n");
	printf("3 -\t Estradas de entrada de uma dada cidade\n");
	printf("4 -\t Quantidade de Cidades Isoladas\n");
	printf("5 -\t Cidades do tipo: A.destino = B, B.destino = A\n");
	printf("6 -\t Cidades com a maior quantidade de estradas de entrada\n");
	printf("7 -\t Cidades com a maior quantidade de estradas de saida\n");
	printf("8 -\t Cidades com a maior quantidade de estradas de saida e entrada\n");
	printf("9 -\t Cidades com apenas estradas de saida\n");
	printf("10 -\t Cidades com apenas estradas de entrada\n");
	printf("11 -\t Percurso entre duas cidades quaisquer\n");
	printf("12 -\t Mostrar Matriz\n");
	printf("13 -\t Mostrar Cidades\n\n");
	printf("0 - \t Encerrar O Programa\n>");
}
/******************************************************************
*	Operações do Menu
*	Objectivo: Escolher as opções para realizar uma determinada operacão
*	Parâmetros: Recebe como parâmetros uma matriz do tipo inteiro contendo as estradas entre as cidades e uma matriz de strings contendo os nomes das cidades
*	Retorno: Não tem retorno
****************************************************************************/
void menuOperacoes(int m[][TAM],char str[][MAX])
{
	int operacao;
	do
	{
		visualMenu();
		scanf("%d",&operacao);
		switch(operacao)
		{
			case 1:
				printf("Saidas de uma Cidade\n");
				
				operacaoA(m,str);
				break;
			case 2:
				printf("Cidade com Anel Rodoviario\n");
				operacaoB(m,str);
				break;
			case 3:
				printf("Entradas de uma Cidade\n");
				operacaoC(m,str);
				break;
			case 4:
				printf("Quantidade de Cidades Isoladas - Sem entrada e nem saida\n");
				operacaoD(m);
				break;
			case 5:
				printf("Cidades do tipo: A.destino = B, B.destino = A\n");
				operacaoE(m,str);
				break;
			case 6:
				printf("Cidades com a maior quantidade de estradas de entrada\n");
				operacaoF(m,str);
				break;
			case 7:
				printf("Cidades com a maior quantidade de estradas de saida\n");
				operacaoG(m,str);
				break;
			case 8:
				printf("Cidades com a maior quantidade de estradas de saida e entrada - Total\n");
				operacaoH(m,str);
				break;
			case 9:
				printf("Cidades com apenas estradas de saida\n");
				operacaoI(m,str);
				break;
			case 10:
				printf("Cidades com apenas estradas de entrada\n");
				operacaoJ(m,str);
				break;
			case 11:
				printf("Percurso entre duas cidades quaisquer\n");
				mostrarNomes(str);
				operacaoK(m,str);
				break;
			case 12:
				printf("Matriz de Adjacencia das cidades\n");
				mostrarMatriz(m);
				break;
			case 13:
				printf("Nome das cidades\n");
				mostrarNomes(str);
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
/******************************************************************
*	Inicialização de uma matriz
*	Objectivo: Inicializar uma matriz do tipo inteiro
*	Parâmetros: Recebe como parâmetro uma matriz do tipo inteiro contendo as estradas entre as cidades e uma matriz de strings contendo os nomes das cidades
*	Retorno: Não tem retorno
****************************************************************************/
void inicializarMatriz(int m[][TAM])
{
	int i,j;
	for(i=0;i<TAM;i++)
		for(j=0;j<TAM;j++)
			m[i][j]=0;
	m[0][16]=m[16][0]=213;
	m[0][5]=m[5][0]=344;
	m[0][3]=m[3][0]=456;
	m[0][11]=m[11][0]=721;
	m[0][7]=m[7][0]=359;
	m[0][12]=m[12][0]=403;
	m[2][6]=m[6][2]=61;
	m[2][3]=m[3][2]=750;
	m[2][10]=m[10][2]=419;
	m[2][17]=m[17][2]=284;
	m[2][5]=m[5][2]=640;
	m[2][13]=m[13][2]=251;
	m[2][9]=m[9][2]=418;
	m[3][5]=m[5][3]=154;
	m[3][6]=m[6][3]=715;
	m[3][8]=m[8][3]=413;
	m[3][9]=m[9][3]=427;
	m[4][15]=m[15][4]=278;
	m[4][9]=m[9][4]=742;
	m[5][7]=m[7][5]=408;
	m[5][9]=m[9][5]=531;
	m[6][5]=m[5][6]=604;
	m[6][16]=m[16][6]=329;
	m[6][10]=m[10][6]=215;
	m[7][12]=m[12][7]=189;
	m[9][17]=m[17][9]=304;
	m[9][10]=m[10][9]=942;
	m[11][13]=m[13][11]=820;
	m[11][3]=m[3][11]=322;
	m[11][7]=m[7][11]=508;
	m[11][14]=m[14][11]=391;
	m[11][5]=m[5][11]=436;
	m[13][3]=m[3][13]=593;
	m[13][9]=m[9][13]=167;
	m[13][16]=m[16][13]=383;
	m[13][5]=m[5][13]=487;
	m[13][17]=m[17][13]=274;
	m[14][5]=m[5][14]=574;
	m[14][7]=m[7][14]=381;
	m[14][3]=m[3][14]=707;
	m[14][12]=m[12][14]=556;
	m[15][8]=m[8][15]=265;
	m[16][3]=m[3][16]=540;
	m[16][9]=m[9][16]=431;
	m[17][10]=m[10][17]=516;
}
//Inicialização e Apresentação de dados
/******************************************************************
*	Objectivo: Apresentar os dados de uma matriz do tipo inteiro
*	Parâmetros: Recebe como parâmetro uma matriz do tipo inteiro contendo as estradas entre as cidades
*	Retorno: Não tem retorno
****************************************************************************/
void mostrarMatriz(int m[][TAM])
{
	int i,j;
	for(i=0;i<TAM;i++)
	{
		printf("|\t");
		for(j=0;j<TAM;j++)
			printf("%d\t",m[i][j]);
		printf("|\n");
	}
}
/******************************************************************
*	Objectivo: Apresentar os dados de uma matriz de Strings
*	Parâmetros: Recebe como parâmetro uma matriz de strings contendo os nomes das cidades
*	Retorno: Não tem retorno
****************************************************************************/
void mostrarNomes(char str[][MAX])
{
	int i;
	for(i=0;i<TAM;i++)
		printf("%d \t- %s\n",i,str[i]);
}
//
//Funções Auxiliares
/******************************************************************
*	Objectivo: Inserir uma posição válida de uma cidade
*	Parâmetros: Recebe como parâmetro uma string contendo rótulo da cidade
*	Retorno: Retorna um inteiro, a posição inserida apartir do teclado
****************************************************************************/
int inserirCidade(char str[])
{
	int cidade;
	printf("Insira a Cidade - %s \n>",str);
	while(TRUE)
	{	
		scanf("%d",&cidade);
		if(cidade>=0 && cidade<TAM)
			break;
		else 
			printf("Cidade Invalida - Insira Novamente \n >");
	}
	return cidade;
}
/******************************************************************
*	Objectivo: Obter o maior número de estradas que entram entre todas as cidades
*	Parâmetros: Recebe como parâmetro uma matriz do tipo inteiro contendo as estradas entre as cidades
*	Retorno: Retorna o maior número de estradas que entram em inteiros 
****************************************************************************/
int obterMaiorEntradas(int m[][TAM])
{
	int i,j,maior=1,cont;
	for(i=0;i<TAM;i++)
	{
		cont=0;
		for(j=0;j<TAM;j++)
			if(m[j][i]>0)
				cont++;
		if(cont>maior)
			maior=cont;
	}
	return maior;
}
/******************************************************************
*	Objectivo: Obter o maior número de estradas que saem entre todas as cidades
*	Parâmetros: Recebe como parâmetro uma matriz do tipo inteiro contendo as estradas entre as cidades
*	Retorno: Retorna o maior número de estradas que saem em inteiros
****************************************************************************/
int obterMaiorSaidas(int m[][TAM])
{
	int i=0,j=0,maior=1,cont;
	for(i=0;i<TAM;i++)
	{
		cont=0;
		for(j=0;j<TAM;j++)
			if(m[i][j]>0)
				cont++;
		if(cont>maior)
			maior=cont;
	}
	return maior;
}
/******************************************************************
*	Objectivo: Obter o maior número de estradas que saem e entram entre todas as cidades
*	Parâmetros: Recebe como parâmetro uma matriz do tipo inteiro contendo as estradas entre as cidades
*	Retorno: Retorna o maior número de estradas que entram e saem em inteiros
****************************************************************************/
int obterMaiorEntradasSaidas(int m[][TAM])
{
	int i,j,maior=0,cont;
	for(i=0;i<TAM;i++)
	{
		cont=0;
		for(j=0;j<TAM;j++)
		{
			if(m[i][j]>0)
				cont++;
			if(m[j][i]>0)
				cont++;
		}
		if(cont>maior)
				maior=cont;
	}
	return maior;
}
//Funções auxiliares da Alínea K
/******************************************************************
*	Objectivo: Verificar se uma cidade já foi visitada
*	Parâmetros: Recebe como parâmetros um vector do tipo inteiro contendo todas cidades já visitadas, o número de elementos desse vector em inteiro e a posição da cidade que se deseja verificar em inteiro
*	Retorno: Retorna TRUE se já foi visitada e FALSE no caso contrário
****************************************************************************/
int foiVisitado(int v[],int nElem,int cidade)
{
	int i;
	for(i=0;i<nElem;i++)
		if(v[i]==cidade)
			return TRUE;
	return FALSE;
}
/******************************************************************
*	Objectivo: Verificar se existe uma próxima estrada de saída para uma cidade que não foi ainda visitada
*	Parâmetros: Recebe como parâmetros uma matriz contendo as estradas, um vector do tipo inteiro contendo todas cidades já visitadas, o número de elementos desse vector do tipo inteiro, posição da cidade de origem que se deseja verificar em inteiro e o destino actual do parâmetro anterior em inteiro
*	Retorno: Retorna -1 se não existe uma próxima cidade e a posição da próxima cidade no caso contrário
****************************************************************************/
int proximo(int m[][TAM],int v[],int nElem,int cidade,int actual)
{
	int i;
	for(i=actual+1;i<TAM;i++)
		if(m[cidade][i]>0 && !foiVisitado(v,nElem,i))
			return i;
	return -1;
}
/******************************************************************
*	Objectivo: Mostrar as rotas caso existirem e o total do percurso em quilómetros
*	Parâmetros: Recebe como parâmetros uma matriz contendo as estradas, uma matriz de strings contendo os nomes das cidades, um vector do tipo inteiro contendo todas cidades já visitadas, o número de elementos desse vector do tipo inteiro e a cidade de destino em inteiro
*	Retorno: Não tem retorno
****************************************************************************/
void mostrarResultado(int m[][TAM],char str[][MAX],int v[],int nElem,int destino)
{
	int km=0,i;
	if(nElem>1)
	{
		for(i=0;i<nElem-1;i++)
		{
			km += m[v[i]][v[i+1]];
			printf("%s ->",str[v[i]]);
		}
		printf("%s \n",str[destino]);
		printf("Percurso de %d km entre %s e %s\n",km,str[v[0]],str[destino]);
	}
	else
		printf("Nao existe percurso entre %s e %s \n",str[v[0]],str[destino]);
}
/******************************************************************
*	Objectivo: Verificar se o percurso de um caminho é menor do que outro 
*	Parâmetros: Recebe como parâmetros uma matriz contendo as estradas, um primeiro vector do tipo inteiro contendo uma rota, o número de elementos desse primeiro vector do tipo inteiro, um segundo vector do tipo inteiro contendo uma rota e o número de elementos desse segundo vector do tipo inteiro
*	Retorno: Retorna TRUE se for menor e FALSE no caso contrário
****************************************************************************/
int menorDistancia(int m[][TAM],int v1[],int nElem1,int v2[],int nElem2)
{
	int km1=0,km2=0,i1=0,i2=0;
	while(i1<nElem1-1 || i2<nElem2-1)
	{
		if(i1<nElem1-1)
		{
			km1+=m[v1[i1]][v1[i1+1]];
			i1++;
		}
		if(i2<nElem2-1)
		{
			km2+=m[v2[i2]][v2[i2+1]];
			i2++;
		}
	}
	if(km1<km2 || km2==0)
		return TRUE;
	return FALSE;
}
/******************************************************************
*	Objectivo: Copiar o conteúdo de um vector para um outro vector
*	Parâmetros: Recebe como parâmetros um primeiro vector do tipo inteiro a ser copiado, o número de elementos desse primeiro vector do tipo inteiro e um segundo vector do tipo inteiro a copiar
*	Retorno: Não tem retorno
****************************************************************************/
void copiarConteudo(int v1[],int nElem,int v2[])
{
	int i;
	for(i=0;i<nElem;i++)
		v2[i]=v1[i];
}
//
//Funções das Operações realizadas
//Alínea A
//Função que 
/******************************************************************
*	Objectivo: Encontrar todas estradas que saem de uma dada cidade
*	Parâmetros: Recebe como parâmetros uma matriz do tipo inteiro contendo as estradas entre as cidades e uma matriz de strings contendo os nomes das cidades
*	Retorno: Não tem retorno
****************************************************************************/
void operacaoA(int m[][TAM],char str[][MAX])
{
	int j,cidade=inserirCidade("Origem");
	for(j=0;j<TAM;j++)
		if(m[cidade][j]>0)
			printf("Cidade de Destino: %s Estrada de Saida: %d km\n",str[j],m[cidade][j]);
}
//Alínea B
/******************************************************************
*	Objectivo: Verificar se uma dada cidade tem ou não anel rodoviario
*	Parâmetros: Recebe como parâmetros uma matriz do tipo inteiro contendo as estradas entre as cidades e uma matriz de strings contendo os nomes das cidades
*	Retorno: Não tem retorno
****************************************************************************/
void operacaoB(int m[][TAM],char str[][MAX])
{
	int cidade=inserirCidade("Dada");
	if(m[cidade][cidade]>0)
		printf("A Cidade %s possui um Anel Rodoviario\n",str[cidade]);
	else
		printf("A Cidade %s nao possui um Anel Rodoviario\n",str[cidade]);
}
//Alínea C
/******************************************************************
*	Objectivo: Encontrar todas estradas que entram de uma dada cidade
*	Parâmetros: Recebe como parâmetros uma matriz do tipo inteiro contendo as estradas entre as cidades e uma matriz de strings contendo os nomes das cidades
*	Retorno: Não tem retorno
****************************************************************************/
void operacaoC(int m[][TAM],char str[][MAX])
{
	int i,cidade=inserirCidade("Destino");
	for(i=0;i<TAM;i++)
		if(m[i][cidade]>0)
			printf("Cidade de Origem: %s Estrada de Entrada: %d km\n",str[i],m[i][cidade]);
}
//Alínea D
/******************************************************************
*	Objectivo: Mostrar a quantidade de cidades isoladas na matriz de adjacencia
*	Parâmetros: Recebe como parâmetro uma matriz do tipo inteiro contendo as estradas entre as cidades
*	Retorno: Não tem retorno
****************************************************************************/
void operacaoD(int m[][TAM])
{
	int i,j,cont=0;
	for(i=0;i<TAM;i++)
	{
		for(j=0;j<TAM;j++)
			if(m[i][j]>0 || m[j][i]>0)
				break;
		if(j==TAM)
			cont++;
	}
	printf("Existem %d Cidades Isoladas\n",cont);
}
//Alínea E
/******************************************************************
*	Objectivo: Mostrar todas cidades com as seguinte característica: a cidade tem um destino que tem como destino esta mesma cidade
*	Parâmetros: Recebe como parâmetros uma matriz do tipo inteiro contendo as estradas entre as cidades e uma matriz de strings contendo os nomes das cidades
*	Retorno: Não tem retorno
****************************************************************************/
void operacaoE(int m[][TAM],char str[][MAX])
{
	int i,j;
	for(i=0;i<TAM;i++)
		for(j=0;j<TAM;j++)
			if(m[i][j]>0 && m[j][i]>0 && m[i][j]!=m[j][i])
						printf("Cidade Origem: %s Cidade Destino: %s Estrada Saida: %d km Estrada Entrada: %d km\n",str[i],str[j],m[i][j],m[j][i]);			
}
//Alínea F
/******************************************************************
*	Objectivo: Mostrar as cidades com o maior número de estradas que entram e suas respectivas estradas
*	Parâmetros: Recebe como parâmetros uma matriz do tipo inteiro contendo as estradas entre as cidades e uma matriz de strings contendo os nomes das cidades
*	Retorno: Não tem retorno
****************************************************************************/
void operacaoF(int m[][TAM],char str[][MAX])
{
	int i,j,maior=obterMaiorEntradas(m),cont,continuar=TRUE;
	for(i=0;i<TAM;i++)
	{
		cont=0;
		for(j=0;j<TAM;j++)
			if(continuar)
			{	
				if(m[j][i]>0)
					cont++;
				if(cont==maior)
				{
					j=-1;
					continuar=FALSE;
				}
			}	
			else
				if(m[j][i]>0)
					printf("Cidade: %s Estrada de Entrada: %d km\n",str[i],m[j][i]);
		continuar=TRUE;
	}
		
}
//Alínea G
/******************************************************************
*	Objectivo: Mostrar as cidades com o maior número de estradas que saem e suas respectivas estradas
*	Parâmetros: Recebe como parâmetros uma matriz do tipo inteiro contendo as estradas entre as cidades e uma matriz de strings contendo os nomes das cidades
*	Retorno: Não tem retorno
****************************************************************************/
void operacaoG(int m[][TAM],char str[][MAX])
{
	int i,j,maior=obterMaiorSaidas(m),cont,continuar=TRUE;
	for(i=0;i<TAM;i++)
	{	
		cont=0;
		for(j=0;j<TAM;j++)
			if(continuar)
			{	
				if(m[i][j]>0)
					cont++;
				if(cont==maior)
				{
					j=-1;
					continuar=FALSE;
				}
			}	
			else
				if(m[i][j]>0)
					printf("Cidade: %s Estrada de Saida: %d km\n",str[i],m[i][j]);	
		continuar=TRUE;
	}
}
//Alínea H
/******************************************************************
*	Objectivo: Mostrar as cidades com o maior número de estradas que saem e entram e suas respectivas estradas
*	Parâmetros: Recebe como parâmetros uma matriz do tipo inteiro contendo as estradas entre as cidades e uma matriz de strings contendo os nomes das cidades
*	Retorno: Não tem retorno
****************************************************************************/
void operacaoH(int m[][TAM],char str[][MAX])
{
	int i,j,maior=obterMaiorEntradasSaidas(m),cont,continuar=TRUE;
	for(i=0;i<TAM;i++)
	{
		cont=0;
		for(j=0;j<TAM;j++)
			if(continuar)
			{	
				if(m[i][j]>0)
					cont++;
				if(m[j][i]>0)
					cont++;
				if(cont==maior)
				{
					j=-1;
					continuar=FALSE;
				}
			}	
			else
			{
				if(m[i][j]>0)
					printf("Cidade: %s Estrada de Saida: %d km\n",str[i],m[i][j]);
				if(m[j][i]>0)
					printf("Cidade: %s Estrada de Entrada: %d km\n",str[i],m[j][i]);
			}
		continuar=TRUE;
	}
}
//Alínea I
/******************************************************************
*	Objectivo: Mostrar cidades com apenas estradas que saem e suas respectivas estradas
*	Parâmetros: Recebe como parâmetros uma matriz do tipo inteiro contendo as estradas entre as cidades e uma matriz de strings contendo os nomes das cidades
*	Retorno: Não tem retorno
****************************************************************************/
void operacaoI(int m[][TAM],char str[][MAX])
{
	int i,j;
	for(i=0;i<TAM;i++)
	{
		for(j=0;j<TAM;j++)
			if(m[j][i]>0)
				break;
		if(j==TAM)
			for(j=0;j<TAM;j++)
				if(m[i][j]>0)
					printf("Cidade: %s Estrada de Saida: %d km\n",str[i],m[i][j]);
	}
}
//Alínea J
/******************************************************************
*	Objectivo: Mostrar cidades com apenas estradas que entram e suas respectivas estradas
*	Parâmetros: Recebe como parâmetros uma matriz do tipo inteiro contendo as estradas entre as cidades e uma matriz de strings contendo os nomes das cidades
*	Retorno: Não tem retorno
****************************************************************************/
void operacaoJ(int m[][TAM],char str[][MAX])
{
	int i,j;
	for(i=0;i<TAM;i++)
	{
		for(j=0;j<TAM;j++)
			if(m[i][j]>0)
				break;
		if(j==TAM)
			for(j=0;j<TAM;j++)
				if(m[j][i]>0)
					printf("Cidade: %s Estrada de Entrada: %d km\n",str[i],m[j][i]);
	}
}
//Alínea K
/******************************************************************
*	Objectivo: Mostrar o percurso entre duas cidades quaisquer dadas caso existir incluindo as cidades por onde passar
*	Parâmetros: Recebe como parâmetros uma matriz do tipo inteiro contendo as estradas entre as cidades e uma matriz de strings contendo os nomes das cidades
*	Retorno: Não tem retorno
****************************************************************************/
void operacaoK(int m[][TAM],char str[][MAX])
{
	int j=0,nElem=0,visitado[TAM],menorC[TAM],mc=0;
	int i=inserirCidade("Origem"),destino=inserirCidade("Destino");
	visitado[nElem]=i;
	nElem++;
	
	while(TRUE)
	{
		if(m[i][destino]>0)
		{
			visitado[nElem]=destino;
			nElem++;
			/*if(menorDistancia(m,visitado,nElem,menorC,mc))
			{
				copiarConteudo(visitado,nElem,menorC);
				mc=nElem;
			}
			j=TAM;*/
			break;
		}
		if(j<TAM)
		{
			
			if(m[i][j]>0 && !foiVisitado(visitado,nElem,j))
			{
				visitado[nElem]=j;
				nElem++;
				i=j;
				j=-1;
			}
			j++;
		}
		else
		{
			nElem--;
			if(nElem<=1)
				break;
			else
			{
				i=proximo(m,visitado,nElem,visitado[nElem-2],visitado[nElem-1]);
				if(i<0)
					i=visitado[nElem-2];
				else
				{
					j=0;
					visitado[nElem-1]=i;
				}						
			}
		}
	}
	//mostrarResultado(m,str,menorC,mc,destino);
	mostrarResultado(m,str,visitado,nElem,destino);
}
//