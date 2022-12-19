public class Cliente implements TransporteCarga
{
	private final int MAX=200;
	private String nome;
	private long cpf;
	private String enderecoCasa;
	private CarrinhoCompra carrinho=null;
	private Automovel carro=null;
	//Quantidade de Item do Armazenamento do Cliente
	private int quantItem=0;
	//O armazenamento do cliente
	private Item[] armazenamento = new Item[MAX];
	public Cliente(String nome, long cpf,String enderecoCasa,Automovel carro)
	{
		setNome(nome);
		setCpf(cpf);
		setEnderecoCasa(enderecoCasa);
		setCarro(carro);
	}
	//Métodos Modificadores
	public void setNome(String nome)
	{
		this.nome=nome;
	}
	public void setCpf(long cpf)
	{
		this.cpf=cpf;
	}
	public void setEnderecoCasa(String enderecoCasa)
	{
		this.enderecoCasa=enderecoCasa;
	}
	public void setCarro(Automovel carro)
	{
		this.carro=carro;
	}
	public void setCarrinho(CarrinhoCompra carrinho)
	{
		this.carrinho=carrinho;
	}
	//Métodos Acessores
	public CarrinhoCompra getCarrinho()
	{
		return this.carrinho;
	}
	public String getNome()
	{
		return this.nome;
	}
	public long getCpf()
	{
		return this.cpf;
	}
	public String getEnderecoCasa()
	{
		return this.enderecoCasa;
	}
	public Automovel getCarro()
	{
		return this.carro;
	}
	/*******************************************************************************************************************************
	**->	Objectivo: Abandonar o carrinho de compras
	**->	Parâmetros: Recebe uma interface do tipo Interacao
	**->	Retorno: Não devolve nada
	********************************************************************************************************************************/
	public void abandonarCarrinho(Interacao inter)
	{
		if(this.carrinho==null)
			System.out.println("O Cliente ainda não pegou um Carrinho de Compras");	
		else{
			this.carrinho.abandonar(inter);
			this.carrinho=null;
		}
	}
	/*******************************************************************************************************************************
	**->	Objectivo: Pegar um carrinho de compras
	**->	Parâmetros: Recebe um carrinho de compras novo como parâmetro
	**->	Retorno: Não devolve nada
	********************************************************************************************************************************/
	public void pegarCarrinho(CarrinhoCompra carrinho,Interacao inter)
	{
		if(this.carrinho!=null)
			abandonarCarrinho(inter);
		this.carrinho=carrinho;
	}
	/*******************************************************************************************************************************
	**->	Objectivo: Concluir as compras
	**->	Parâmetros: Recebe um carrinho de compras novo
	**->	Retorno: Não devolve nada
	********************************************************************************************************************************/
	public void comprar()
	{
		float total=this.carrinho.obterTotal();
		if(total>0)
		{
			System.out.println("Entregando Kz "+total+" para o caixa");
			this.carrinho.transferir(this);
		}
		else
		{
			System.out.println("Ainda não colocou nada no Carrinho de Compras");
		}
	}
	/*******************************************************************************************************************************
	**->	Objectivo: Mostrar o resumo dos dados do cliente 
	**->	Parâmetros: Não recebe parâmetro algum
	**->	Retorno: Retorna uma string
	********************************************************************************************************************************/
	public String resumo()
	{
		String resumo="Dados Cliente\n\n";
		resumo += "\nNome: "+getNome();
		resumo += "\nCPF: "+getCpf();
		resumo += "\nEndereco: "+getEnderecoCasa();
		resumo += "\n\n"+(this.carro==null?"Nao tem Carro":getCarro().resumo());
		return resumo;
	}
	@Override
	public void carregar(Item item)
	{
		if(this.carro==null)
		{
			this.armazenamento[quantItem++]=item;
		}
		else
		{
			TransporteCarga transporte = this.carro;
			transporte.carregar(item);
		}
	}
	@Override
	public void descarregar()
	{
		if(this.carro==null)
		{
			String itensRemovidos="Produtos Descarregados\n";
			for(int i=0;i<this.quantItem;i++)
				itensRemovidos+=this.armazenamento[i].getProduto().getDescricao()+" Quantidade: "+this.armazenamento[i].getQuantidade()+"\n";
			System.out.println(itensRemovidos);
			this.quantItem=0;
		}
		else
		{
			TransporteCarga transporte = this.carro;
			transporte.descarregar();
		}
	}
}