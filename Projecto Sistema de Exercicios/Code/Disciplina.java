
public class Disciplina
{
	private int id;
	private String nome;
	private String ementa;
	
	//Métodos Constutores
	public Disciplina(){}
	public Disciplina(int id, String nome, String ementa)
	{
		setId(id);
		setNome(nome);
		setEmenta(ementa);
	}
	
	//Métodos Acessores
	public int getId(){return this.id;}
	public String getNome(){return this.nome;}
	public String getEmenta(){return this.ementa;}
	//Métodos Modificadores
	public void setId(int id)
	{
		this.id = id;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public void setEmenta(String ementa)
	{
		this.ementa = ementa;
	}
	
	public String resumo()
	{
		String resumo = "";
		resumo += "[ id: " + getId()+", Nome: "+getNome()+", Ementa: "+getEmenta()+" ]";
		return resumo;
	}
}