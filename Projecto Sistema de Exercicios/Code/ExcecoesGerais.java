public class ExcecoesGerais
{
	static class ExcecaoTurmaLotada extends Exception
	{
		public ExcecaoTurmaLotada()
		{
			super("Ja nao ha espaco para novos alunos - Turma Lotada");
		}
	}
	static class ExcecaoUsuarioInexistente extends Exception
	{
		public ExcecaoUsuarioInexistente()
		{
			super("O Nome de Usuario ou A Senha estao Incorrectas");
		}
	}
	static class ExcecaoUsuarioJaExistente extends Exception
	{
		public ExcecaoUsuarioJaExistente()
		{
			super("O Nome de Usuario e Senha ja pertencem a um usuario");
		}
	}
	static class ExcecaoIdInexistente extends Exception
	{
		public ExcecaoIdInexistente()
		{
			super("O Id nao existe nesta lista");
		}
	}
	static class ExcecaoAdminDefinido extends Exception
	{
		public ExcecaoAdminDefinido(String username,String senha)
		{
			super("O Administrador tem credenciais predefinidas\nUsername: "+username+"\nSenha: "+senha);
		}
	}
	static class ExcecaoElementoNulo extends Exception
	{
		public ExcecaoElementoNulo()
		{
			super("O Elemento e nulo");
		}
	}
	static class ExcecaoJaExistente extends Exception
	{
		public ExcecaoJaExistente()
		{
			super("O Elemento ja esta vinculado, nao e possivel vincular mais de uma vez");
		}
	}
	static class ExcecaoVoltando extends Exception
	{
		public ExcecaoVoltando()
		{
			super("Voltando");
		}
	}
}