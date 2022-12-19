
import java.util.Date;
public class Aluno extends Usuario
{
	private int periodo;
	
	//Métodos Constutores
	public Aluno(){super();}
	public Aluno(int id, String nome, Date dataNasc, int periodo, String username, String senha)
	{
		super(id, nome, dataNasc, username, senha);
		setPeriodo(periodo);
	}
	
	//Métodos Acessores
	public int getPeriodo(){return this.periodo;}
	
	//Métodos Modificadores
	public void setPeriodo(int periodo)
	{
		this.periodo = periodo;
	}
	
	public String resumo()
	{
		String resumo = "";
		resumo += "[ id: " + getId()+", Nome: "+getNome()+", Periodo: "+getPeriodo()+"º, BirthDate: "+getDataNasc()+", Nome de Usuario: "+getUserName()+", Senha: "+getSenha()+" ]";
		return resumo;
	}
}