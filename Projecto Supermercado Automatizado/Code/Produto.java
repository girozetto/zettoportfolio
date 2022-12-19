public class Produto
{
	private int id;
	private String descricao;
	private int quantStoque;
	private float precoPorUnidade;
	public Produto(int id,String descricao,int quantStoque,float precoPorUnidade)
	{
		setId(id);
		setQuantStoque(quantStoque);
		setDescricao(descricao);
		setPrecoPorUnidade(precoPorUnidade);
	}
	//Construtor Padrão
	public Produto(){}
	
	//Métodos Acessores
	public int getId()
	{
		return this.id;
	}
	public int getQuantStoque()
	{
		return this.quantStoque;
	}
	public String getDescricao()
	{
		return this.descricao;
	}
	public float getPrecoPorUnidade()
	{
		return this.precoPorUnidade;
	}
	//Métodos Modificadores
	public void setId(int id)
	{
		this.id=id;
	}
	public void setQuantStoque(int quantStoque)
	{
		this.quantStoque=quantStoque;
	}
	public void setDescricao(String descricao)
	{
		this.descricao=descricao;
	}
	public void setPrecoPorUnidade(float precoPorUnidade)
	{
		this.precoPorUnidade=precoPorUnidade;
	}
	public String resumo()
	{
		String resumo="[ ";
		resumo+="ID: "+getId();
		resumo+=", Descricao: "+getDescricao();
		resumo+=", Quantidade em Stoque: "+getQuantStoque();
		resumo+=", Preco por Unidade: "+getPrecoPorUnidade()+" ]";
		return resumo;
	}
}