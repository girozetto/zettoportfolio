public class ItemDesconto extends Item
{
	//Leva (n) quantidade de itens e pague apenas por (n-1)
	private int n;
	public ItemDesconto(Produto produto, int quantidade, int n)
	{
		super(produto,quantidade);
		this.n=n;
	}
	/*****************************************************************
	**->	Objectivo: Calcular o preço do item de acordo ao desconto(Explicado no Relatório)
	**->	Parâmetros: Não recebe algum parâmetro.
	**->	Retorno: o valor descontado 
	*****************************************************************/
	@Override
	public float calcularPreco()
	{
		return super.getProduto().getPrecoPorUnidade()*((super.getQuantidade()/this.n)*(this.n-1)+(super.getQuantidade()%this.n));
	}
}