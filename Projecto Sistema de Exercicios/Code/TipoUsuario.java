public enum TipoUsuario
{
	PROFESSOR(1),
	ALUNO(2),
	ADMINISTRADOR(3);
	private final int valor;
	private TipoUsuario(int valor)
	{
		this.valor = valor;
	}
}