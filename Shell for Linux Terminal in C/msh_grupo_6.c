/*********************************************************************************
*						PROJECTO DE SISTEMAS OPERATIVOS 2
**********Grupo Nº
**********Autores
**********Nome: Gilberto Alexandre Adão de Jesus ID: 1000026185
**********Nome: Marcolino Estevão Camunda ID: 1000027493
**********Nome: João Jeovane J. Baptista ID: 1000026409
*********************************************************************************/
#include<stdio.h>
#include<stdlib.h>
#include<time.h>
#include<string.h>
#include<unistd.h>
#include<fcntl.h>
#include<signal.h>
#include<sys/wait.h>
//O máximo de letras que podem ser inseridos em cada comando
#define MAXLETRAS 500
//O máximo de comandos que podem ser reconhecidos em cada execução
#define MAXCMDS 50
//Constante Usada para limpar a tela do terminal
#define limparTela() printf("\033[H\033[J")
typedef enum{VERDADEIRO=1,FALSO=0}Boolean;

//Protótipos das funções
void handler(int num);
void iniciarShell();
void imprimirDiretorioActual();
int compararComandos(char* comando1,char* comando2);
void dividirArgumentos(char** argumentos,char* comando,int limite,char separador[]);
int procurarCaracter(char* comando, char caracter);
int redirecionamento(char* comando,char** argumentos,int ficheiro);
int executarComando(char comando[],char **argumentos,int ficheiro);
void executarComandoAnterior(int ficheiro);
void mostrarHistoria(int ficheiro);


//Função Principal da Shell
int main(int quantidadeArgumentos,char* vectorArgumentos[])
{
	struct sigaction sinal;
	sinal.sa_handler=&handler;
	//Ignorando o sinal SIGINT
	sigaction(SIGINT,&sinal,NULL);
	char comando[MAXLETRAS],*argumentos[MAXCMDS];
	iniciarShell();
	//Abrindo o ficheiro de nome msh.hist com permissão de leitura e escrita(O_RDWR), caso não existir, é criado um novo(O_CREAT) e no caso contrário é reinicializado apagando todos os registros anteriores(O_TRUNC)
	int ficheiro = open("msh.hist",O_RDWR | O_CREAT | O_TRUNC,0777);
	if(ficheiro<0)
	{
		printf("Não foi possível criar o ficheiro de história!!!! \n Saindo da Shell\n");
	}
	while(VERDADEIRO)
	{	
		imprimirDiretorioActual();
		printf("Digite o Comando>>>");
		scanf("%[^\n]",comando);
		if(compararComandos(comando,"exit")==0)
		{
			printf("\n****Saindo da Shell****\n");
			close(ficheiro);
			return 2;
		}
		else 
		{
			if(compararComandos(comando,"historia")==0)
				mostrarHistoria(ficheiro);
			else if(compararComandos(comando,"!!")==0)
				executarComandoAnterior(ficheiro);
			else
				if(!redirecionamento(comando,argumentos,ficheiro))
				{
					printf("\n***Executando Comando %s ***\n",comando);
					if(executarComando(comando,argumentos,ficheiro))
						printf("\n****Terminando Execução do Comando***\n\n*");
					else
						printf("\n!!!Execução do Comando Falhou!!!\n");
				}
		}
		getchar();
	}
	return 0;
}
/*******************************************************
*Objectivo da Função: Caso tentar ser cancelado ou interrompido com um SIGINT emitir a mensagem e ignorar
*Parâmetros: Recebe um número inteiro
*Retorno: não retorna nada
********************************************************/
void handler(int num)
{
	write(STDOUT_FILENO,"\nEu não quero ser Morto!!!\n",30);
}
/*******************************************************
*Objectivo da Função: Imprime o Diretorio Actual em que se encontra a Shell
*Parâmetros: não recebe nada como parâmetro
*Retorno: não retorna nada
********************************************************/
void imprimirDiretorioActual()
{
	char diretorio[1024];
	getcwd(diretorio,sizeof(diretorio));
	printf("\nCaminho Actual: %s\n",diretorio);
}
/*******************************************************
*Objectivo da Função: Mostrar uma tela inicial de Apresentação
*Parâmetros: Nenhum parâmetro
*Retorno: não retorna nada
********************************************************/
void iniciarShell()
{
	limparTela();
	printf("************************************\n");
	printf("********Shell Simplificada\t**********\n");
	printf("********Usuário Logado: %s\t**********\n",getenv("USER"));
	printf("***Shell Desenvolvida pelo Grupo: X \t**********\n");
	printf("************************************\n");
	sleep(4);
	limparTela();
}
/*******************************************************
*Objectivo da Função: Comparar dois comandos
*Parâmetros: duas strings
*Retorno: retorna uma inteiro, caso for igual á zero, os comandos são iguais, caso maior que zero, comando1 é maior e caso for menor que zero o comando2 é maior
********************************************************/
int compararComandos(char* comando1,char* comando2)
{
	return strcmp(comando1,comando2);
}
/************************************************************************************************
*Objectivos da Função: Dividir o comando em argumentos e possibilitar usar os comandos exec
*Parâmetros: Um vector de Strings onde estará cada argumento, uma string contendo os comandos inseridos, o limite de argumentos em inteiro e o caracter de separação
*Retorno: Não retorna nada
*************************************************************************************************/
void dividirArgumentos(char** argumentos,char* comando,int limite,char separador[])
{
	int i;
	for(i=0;i<limite;i++)
	{
		argumentos[i]=strsep(&comando,separador);
		if(argumentos[i]==NULL)
			break;
		if(strlen(argumentos[i])==0)
			i--;
	}
}
/************************************************************************************************
*Objectivos da Função: Verificar se um determinado caracter existe num comando
*Parâmetros: Recebe como parâmetro uma string contendo o comando e um caracter
*Retorno: retorna verdadeiro caso existir e retorna falso caso não existir  qualquer ocorrência deste caracter na string
*************************************************************************************************/
int procurarCaracter(char* comando, char caracter)
{
	int i=0;
	while(comando[i]!='\0')
	{
		if(comando[i]==caracter)
			return VERDADEIRO;
		i++;
	}
	return FALSO;
}
/************************************************************************************************
*Objectivos da Função: Fazer o redirecionamento de saída ou entrada caso houver a ocorrência de '<'(Entrada) ou de '>'(saída) ou de '|'(PIPE)
*Parâmetros: Recebe como parâmetro uma string contendo o comando e um conjuntos de strings contendo cada argumento inserido para executar o redirecionamento de saída e entrada
*Retorno: Retorna falso caso nenhum dos redirecionamentos acontecer
*************************************************************************************************/
int redirecionamento(char* comando,char** argumentos,int ficheiro)
{
	char parteCmds[MAXLETRAS],parteFicheiro[MAXLETRAS];
	if(procurarCaracter(comando,'>'))
	{
		write(ficheiro,comando,MAXLETRAS);
		//Separando a parte do comando da parte do ficheiro
		dividirArgumentos(argumentos,comando,MAXCMDS,">");
		strcpy(parteCmds,argumentos[0]);
		//eliminando os espaços do parte do ficheiro
		dividirArgumentos(argumentos,argumentos[1],MAXCMDS," ");
		strcpy(parteFicheiro,argumentos[0]);
		printf("\n Redirecionando Output para %s \n",parteFicheiro);
		//Abrindo ficheiro caso existir ou criar no caso Contrário apenas para escrita
		int ficheiro = open(parteFicheiro,O_WRONLY | O_CREAT,0777);
		if(ficheiro<0)
		{
			printf("\n Houve Algum Erro na Criação do Ficheiro\n");
			return VERDADEIRO;
		}
		//Duplicando a saída padrão para uma outra posição
		int duplicado=dup(STDOUT_FILENO);
		//Definindo o ficheiro criado como nova saída
		int saidaF=dup2(ficheiro,STDOUT_FILENO);
		close(ficheiro);
		//Inserindo o output do comando executado na nova saída
		if(executarComando(parteCmds,argumentos,ficheiro))
		{
			//Retornar a saída padrão como nova saída
			dup2(duplicado,saidaF);
			printf("\n Saída Redirecionada para %s com Sucesso\n",parteFicheiro);
			close(duplicado);
			return VERDADEIRO;
		}
		else
		{
			dup2(duplicado,saidaF);
			printf("\n !!!A execução do comando falhou!!!\n");
			close(duplicado);
			return VERDADEIRO;
		}
	}
	else if(procurarCaracter(comando,'<'))
	{
		write(ficheiro,comando,MAXLETRAS);
		//Separando a parte do comando da parte do ficheiro
		dividirArgumentos(argumentos,comando,MAXCMDS,"<");
		strcpy(parteCmds,argumentos[0]);
		//eliminando os espaços da parte do ficheiro
		dividirArgumentos(argumentos,argumentos[1],MAXCMDS," ");
		strcpy(parteFicheiro,argumentos[0]);
		printf("\n Redirecionando Input para %s \n",parteFicheiro);
		//Abrindo ficheiro apenas para leitura no caso de existir
		int ficheiro = open(parteFicheiro,O_RDONLY,0777);
		if(ficheiro<0)
		{
			printf("\n O ficheiro não existe ou não está no caminho actual\n");
			return VERDADEIRO;
		}
		//Duplicando a entrada padrão para uma outra posição
		int duplicado=dup(STDIN_FILENO);
		//Definindo o ficheiro criado como nova entrada
		int entradaF=dup2(ficheiro,STDIN_FILENO);
		close(ficheiro);
		//Definindo como a entrada do comando executado
		if(executarComando(parteCmds,argumentos,ficheiro))
		{
			//Retornar a entrada padrão como nova entrada
			dup2(duplicado,entradaF);
			printf("\n A Entrada foi Redirecionada para %s com Sucesso\n",parteFicheiro);
			close(duplicado);
			return VERDADEIRO;
		}
		else
		{
			//Retornar a entrada padrão como nova entrada
			dup2(duplicado,entradaF);
			printf("\n A execução do comando falhou\n");
			close(duplicado);
			return VERDADEIRO;
		}
	}
	else if(procurarCaracter(comando,'|'))
	{
		//int processo,p[2];
		printf("\n Usando o PIPE");
		//int estado=pipe(p);
		return VERDADEIRO;
	}
	return FALSO;
}
/************************************************************************************************
*Objectivos da Função: Executar os comandos que serem inseridos pelo teclado
*Parâmetros: Recebe como uma string contendo o comando e um conjuntos de strings contendo cada argumento inserido para executar o comando
*Retorno: Não retorna nada
*************************************************************************************************/
int executarComando(char comando[],char **argumentos,int ficheiro)
{
		int estado;
		write(ficheiro,comando,MAXLETRAS);
		dividirArgumentos(argumentos,comando,MAXCMDS," ");
		if(compararComandos(argumentos[0],"cd")==0)
		{
			chdir(argumentos[1]);
			return VERDADEIRO;
		}
		else
		{
			int processo=fork();
			if(processo==0)
			{	
				estado=execvp(argumentos[0],argumentos);
				if(estado<0)
					return FALSO;
				exit(0);
			}
			else
			{
				wait(NULL);
				return VERDADEIRO;
			}
		}
}
/************************************************************************************************
*Objectivos da Função: Executar os comandos que foram inseridos anteriormente
*Parâmetros: Não recebe parâmetros
*Retorno: Não retorna nada
*************************************************************************************************/
void executarComandoAnterior(int ficheiro)
{
	char buf[MAXLETRAS],*args[MAXCMDS];
	int i=0;
	printf("\n\n**** Executando O Último Comando Executado ***\n\n");
	while(VERDADEIRO)
	{
		lseek(ficheiro,i,SEEK_SET);
		if(read(ficheiro,buf,MAXLETRAS)<=0)
			break;
		i+=MAXLETRAS;
	}
	printf("\n O último comando executado foi o %s\n",buf);
	if(executarComando(buf,args,ficheiro))
		printf("\n\n Terminou correctamente a execução do comando\n");
	else
		printf("\n A execução do comando falhou\n");
}
/************************************************************************************************
*Objectivos da Função: Mostrar o histórico de todos comandos executados até agora
*Parâmetros: Não recebe parâmetros
*Retorno: Não retorna nada
*************************************************************************************************/
void mostrarHistoria(int ficheiro)
{
	char bf[MAXLETRAS];
	int i=0;
	printf("\n\n****Mostrando o Histórico de Comandos Executados****\n\n");
	while(VERDADEIRO)
	{
		lseek(ficheiro,i,SEEK_SET);
		if(read(ficheiro,bf,MAXLETRAS)>0)
			printf("%s\n",bf);
		else
		{
			printf("\n********Fim dos Comandos********\n\n");
			break;
		}
		i+=MAXLETRAS;
	}
}
