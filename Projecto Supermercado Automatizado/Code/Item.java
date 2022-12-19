public class Item
{
	//O produto que pretende ser comprado
	private Produto produto;
	//Quantidade do produto á ser comprado
	private int quantidade;
	public Item(Produto produto, int quantidade)
	{
		setProduto(produto);
		setQuantidade(quantidade);
	}
	//Métodos Acessores
	public Produto getProduto()
	{
		return this.produto;
	}
	public int getQuantidade()
	{
		return this.quantidade;
	}
	//Métodos Modificadores
	public void setProduto(Produto produto)
	{
		this.produto=produto;
	}
	public void setQuantidade(int quantidade)
	{
		this.quantidade=quantidade;
	}
	/***************************************************
	**->	Objectivo: Calcular o preço do item, ou seja, quanto custará o item de acordo a quantidade pretendida
	**->	Parâmetros: Não recebe algum parâmetro.
	**->	Retorno: retorna a multiplicação do Preço Unitário do Produto pela quantidade do produto á ser comprado. 
	***************************************************/
	public float calcularPreco()
	{
		return this.produto.getPrecoPorUnidade()*this.quantidade;
	}
}