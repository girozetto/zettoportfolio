public class CarrinhoCompra
{
	//O tamanho máximo de itens no carrinho de compras
	private final int TAMANHO=200;
	//A quantidade de itens colocados no carrinho de compras
	private int quantidade=0;
	//Os itens contidos no carrinho
	private Item[] itens = new Item[TAMANHO];
	//Método acessor
	public int getQuantidade()
	{
		return this.quantidade;
	}
	/*******************************************************************************************************************************
	**->	Objectivo: Encontrar alguma ocorrência de um determinado produto
	**->	Parâmetros: Recebe como parâmetro o produto que deseja procurar.
	**->	Retorno: um inteiro, a posição do item encontrado no array caso existir(retorna uma posicao inválida no caso contrário).
	********************************************************************************************************************************/
	private int procurarOcorrencia(Produto produto)
	{
		for(int i=0;i<this.quantidade;i++)
			if(this.itens[i].getProduto().getId()==produto.getId())
				return i;
		return -1;
	}
	/*******************************************************************************************************************************
	**->	Objectivo: Eliminar do array algum produto.
	**->	Parâmetros: Recebe como parâmetro a posição do produto que deseja eliminar.
	**->	Retorno: Não retorna nada.
	********************************************************************************************************************************/
	private void apagarItemArray(int posicao)
	{
		for(int i=posicao;i<this.quantidade;i++)
		{
			itens[i]=itens[i+1];
		}
		this.quantidade--;
	}
	/*******************************************************************************************************************************
	**->	Objectivo: Adicionar um produto ao carrinho de compras, caso não existir no carrinho, no caso de já estar presente no carrinho apenas acrescentar a quantidade do produto.
	**->	Parâmetros: Recebe como parâmetro o produto que deseja adicionar e a quantidade.
	**->	Retorno: Não retorna nada.
	********************************************************************************************************************************/
	public void adicionar(Produto produto, int quantidade,int desconto)
	{
		if(quantidade<=0)
		{
			System.out.println("Quantidade de Produto Inválida");
			return;
		}
		int posicao;
		//Caso já existir um produto com mesmo id, então apenas será adicionado a quantidade
		if((posicao=procurarOcorrencia(produto))>=0)
		{
			if(produto.getQuantStoque()<quantidade)
			{
				System.out.println("Operação Inválida \n"+"A quantidade existente no Stock para:"+"\n"+produto.getDescricao()+"\n é de "+produto.getQuantStoque()+" Unidades");
			}
			else
			{
				produto.setQuantStoque(produto.getQuantStoque()-quantidade);
				this.itens[posicao].setQuantidade(this.itens[posicao].getQuantidade()+quantidade);
				System.out.println("Foram adicionadas "+quantidade+" Unidades ao Produto: "+produto.getDescricao());
			}
		}
		else
		{
			if(this.quantidade<this.TAMANHO)
			{
				if(produto.getQuantStoque()<quantidade)
				{
					System.out.println("Operação Inválida \n"+"A quantidade existente no Stock para:"+"\n"+produto.getDescricao()+"\n é de "+produto.getQuantStoque()+" Unidades");
				}
				else
				{
					produto.setQuantStoque(produto.getQuantStoque()-quantidade);
					this.itens[this.quantidade++]=(desconto>1?new ItemDesconto(produto,quantidade,desconto):new Item(produto,quantidade));
					System.out.println("Produto Adicionado Com Sucesso ao Carrinho de Compras");
				}
			}
			else
			{
				System.out.println("Carrinho de Compras Cheio - Não é possível adicionar mais itens");
			}
		}	
	}
	/*******************************************************************************************************************************
	**->	Objectivo: Remover um item do carrinho de compras, caso a quantidade que se pretende retirar for maior ou igual que a quantidade existente no carrinho de compras, no caso contrário, apenas decrescer a quantidade do produto.
	**->	Parâmetros: Recebe como parâmetro o produto que deseja adicionar e a quantidade.
	**->	Retorno: Não retorna nada.
	********************************************************************************************************************************/
	public void remover(Produto produto, int quantidade)
	{
		if(quantidade<=0)
		{
			System.out.println("Quantidade de Produto Inválida");
			return;
		}
		int posicao;
		if((posicao=procurarOcorrencia(produto))>=0)
		{
			if(this.itens[posicao].getQuantidade()<=quantidade)
			{
				produto.setQuantStoque(produto.getQuantStoque()+this.itens[posicao].getQuantidade());
				apagarItemArray(posicao);
				System.out.println("O Produto:\n"+produto.getDescricao()+"\n foi removido do Carrinho de Compras");
			}
			else
			{
				produto.setQuantStoque(produto.getQuantStoque()+quantidade);
				this.itens[posicao].setQuantidade(this.itens[posicao].getQuantidade()-quantidade);
				System.out.println("Foi retirado "+quantidade+" Unidades do Produto:\n"+produto.getDescricao()+"\n no Carrinho de Compras");
			}
		}
		else
		{
			System.out.println("Este produto nao existe no Carrinho de Compras");
		}
	}
	/*******************************************************************************************************************************
	**->	Objectivo: Carregar as compras para serem levadas por um transporte ou não
	**->	Parâmetros: Recebe como parâmetro o transporte do tipo transporte carga.
	**->	Retorno: Não retorna nada.
	********************************************************************************************************************************/
	public void transferir(TransporteCarga transporte)
	{
		if(this.quantidade>0)
		{
			while(this.quantidade>0)
			{
				transporte.carregar(this.itens[--this.quantidade]);
			}
			System.out.println("O Carrinho de Compras foi Transferido");
		}
		else
		{
			System.out.println("O Carrinho de Compras está Vazio");
		}
	}
	/*******************************************************************************************************************************
	**->	Objectivo: Abandonar o carrinho e devolver ao Estoque
	**->	Parâmetros: Recebe como parâmetro uma interface do tipo interacao
	**->	Retorno: Não retorna nada.
	********************************************************************************************************************************/
	public void abandonar(Interacao inter)
	{
		if(this.quantidade>0)
		{
			while(this.quantidade>0)
			{
				inter.deixar(this.itens[--this.quantidade]);
			}
			System.out.println("O Carrinho de Compras foi Abandonado");
		}
		else
		{
			System.out.println("O Carrinho de Compras está Vazio");
		}
	}
	/*******************************************************************************************************************************
	**->	Objectivo: Calcular o preço total de compra
	**->	Parâmetros: Não recebe parâmetro algum
	**->	Retorno: devolve o valor total á pagar
	********************************************************************************************************************************/
	public float obterTotal()
	{
		float total=0;
		for(int i=0;i<this.quantidade;i++)
			total+=itens[i].calcularPreco();
		return total;
	}
	/*******************************************************************************************************************************
	**->	Objectivo: Calcular o preço total de compra e mostrar o Cupom Fiscal da compra 
	**->	Parâmetros: Não recebe parâmetro algum
	**->	Retorno: Não retorna nada
	********************************************************************************************************************************/
	public void calcularPreco()
	{
		float total=obterTotal();
		String cupomFiscal="Cupom Fiscal\n";
		cupomFiscal+="______________________________\n";
		cupomFiscal+="Item_______ID____Descricao____PrecoUnitario_____Quantidade\n";
		for(int i=0;i<this.quantidade;i++)
			cupomFiscal+=(i+1)+"_______"+itens[i].getProduto().getId()+"____"+itens[i].getProduto().getDescricao()+"____"+itens[i].getProduto().getPrecoPorUnidade()+"kz"+"________"+itens[i].getQuantidade()+"Unidades\n";
		cupomFiscal+="___________________________________________\n";
		cupomFiscal+="Total a Pagar:  "+total+"kz\n";
		System.out.println(cupomFiscal);
	}
}