public class ExcecoesRend
{
	static class ExcecaoPosicaoInvalida extends Exception
	{
		public ExcecaoPosicaoInvalida()
		{
			super("Posicao Invalida");
		}
	}
	
	static class ExcecaoNotaInvalida extends Exception
	{
		public ExcecaoNotaInvalida()
		{
			super("Nota Invalida");
		}
	}
	static class ExcecaoTrabalhoVazio extends Exception
	{
		public ExcecaoTrabalhoVazio()
		{
			super("O trabalho encontra-se sem conteudo");
		}
	}
	static class ExcecaoNotaSemTrabalho extends Exception
	{
		public ExcecaoNotaSemTrabalho()
		{
			super("Precisa ter um trabalho para atribuir notas");
		}
	}
	static class ExcecaoTrabalhoJaEntregue extends Exception
	{
		public ExcecaoTrabalhoJaEntregue()
		{
			super("Ja foi entregue um trabalho");
		}
	}
}