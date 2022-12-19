public class Automovel implements TransporteCarga
{
	private String matricula;
	private String fabricante;
	private String marca;
	private final int MAX=200;
	private int quantItem=0;
	private Item[] armazenamento = new Item[MAX];
	public Automovel(String matricula, String fabricante, String marca)
	{
		setMatricula(matricula);
		setFabricante(fabricante);
		setMarca(marca);
	}
	//Métodos Modificadores
	public void setMatricula(String matricula)
	{
		this.matricula=matricula;
	}
	public void setFabricante(String fabricante)
	{
		this.fabricante=fabricante;
	}
	public void setMarca(String marca)
	{
		this.marca=marca;
	}
	//Métodos Acessores
	public String getMatricula()
	{
		return this.matricula;
	}
	public String getFabricante()
	{
		return this.fabricante;
	}
	public String getMarca()
	{
		return this.marca;
	}
	/*******************************************************************************************************************************
	**->	Objectivo: Mostrar o resumo dos dados do automóvel
	**->	Parâmetros: Não recebe parâmetro algum
	**->	Retorno: Retorna uma string
	********************************************************************************************************************************/
	public String resumo()
	{
		String resumo="Dados Carro\n";
		resumo+="[ Matricula: "+getMatricula();
		resumo+=", Modelo: "+getMarca();
		resumo+=", Fabricante: "+getFabricante()+" ]";
		return resumo;
	}
	@Override
	public void carregar(Item item)
	{
		this.armazenamento[quantItem++]=item;
	}
	@Override
	public void descarregar()
	{
		String itensRemovidos="Produtos Descarregados\n";
		for(int i=0;i<this.quantItem;i++)
			itensRemovidos+=this.armazenamento[i].getProduto().getDescricao()+" Quantidade: "+this.armazenamento[i].getQuantidade()+"\n";
		System.out.println(itensRemovidos);
		this.quantItem=0;
	}
}