
import java.util.Date;
public class Professor extends Usuario
{
	private String cargo;

	//Métodos Constutores	
	public Professor(){super();}
	public Professor( int id, String nome, String cargo, Date dataNasc, String username, String senha )
	{
		super( id, nome, dataNasc, username, senha );
		setCargo(cargo);
	}
	
	//Métodos Acessores
	public String getCargo(){return this.cargo;}
	
	//Métodos Modificadores
	public void setCargo(String cargo)
	{
		this.cargo = cargo;
	}
	
	public String resumo()
	{
		String resumo = "";
		resumo += "[ id: " + getId()+", Nome: "+getNome()+", Cargo: "+getCargo()+", BirthDate: "+getDataNasc()+", Nome de Usuario: "+getUserName()+", Senha: "+getSenha()+" ]";
		return resumo;
	}
}