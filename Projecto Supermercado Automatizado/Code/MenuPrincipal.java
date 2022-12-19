import java.util.Scanner;
public class MenuPrincipal implements Interacao
{
	private final int MAX = 200;
	private Produto[] produtos = new Produto[MAX];
	private int[] desconto = new int[MAX];
	private int quantidade=0;
	private Cliente cliente;
	private long cpf=10200000;
	private int nMat=100;
	private int menuTela(int i)
	{
		String[] menu = new String[4];
		Scanner ler = new Scanner(System.in);
		menu[0]="//Menu Principal\\\\ \n\n"+"1 - Menu Cliente \n"+"2 - Menu Admin\n"+"3 - Proximo Cliente"+"\n\n\n0 - Sair do SuperMercado";
		menu[1]="//Menu Cliente\\\\ \n\n"+"1 - Ver Catalogo de Produtos\n"+"2 - Pegar Carrinho de Compras\n"+"3 - Abandonar o Carrinho de Compras\n"+"4 - Menu Compras\n"+"5 - Concluir Compras"+"\n\n\n0 - Voltar ao Menu Principal";
		menu[2]="//Menu Admin\\\\ \n\n"+"1 - Adicionar Produto ao Estoque \n"+"2 - Remover Produto do Estoque\n"+"3 - Editar Produto no Estoque\n"+"4 - Listar Produtos do Estoque"+"\n\n\n0 - Voltar ao Menu Principal\n";
		menu[3]="//Menu Compras\\\\ \n\n"+"1 -  Adicionar Item\n"+"2 - Remover Item\n"+"3 - Calcular Preco"+"\n\n\n0 - Voltar ao Menu Cliente";
		System.out.println(menu[i]);
		return ler.nextInt();
	}
	private String listarProdutos()
	{
		String lista="Lista Produtos\n";
		for(int i=0;i<this.quantidade;i++)
			lista+=(i+1)+" - "+produtos[i].resumo()+(desconto[i]<2?"":" Desconto: LEVE "+desconto[i]+" e PAGUE "+(desconto[i]-1))+"\n";
		return lista;
	}
	public void executar()
	{
		int escolha;
		while(true)
		{
			escolha=menuTela(0);
			if(escolha==0)
			{
				break;
			}
			else if(escolha==1)
			{
				if(this.cliente!=null)
					menuCliente();
				else
					System.out.println("Nenhum Cliente esperando...");
			}
			else if(escolha==2)
			{
				menuAdmin();
			}
			else if(escolha==3)
			{
				if(this.cliente==null)
				{
					this.cliente=gerarCliente();
					System.out.println("Novo cliente entrou...");
					System.out.println(this.cliente.resumo());
				}
				else
					System.out.println("Atenda o Cliente em Espera...");
			}
			else
				System.out.println("Opcao Invalida - Escolha uma das opcoes mencionadas");
		}
		System.out.println("Desligando o sistema...");
	}
	private void menuCliente()
	{
		int escolha;
		do
		{
			escolha=menuTela(1);
			switch(escolha)
			{
				case 1:
					System.out.println(listarProdutos());
				break;
				case 2:
					System.out.println("Pegando um carrinho...");
					this.cliente.pegarCarrinho(new CarrinhoCompra(),this);
				break;
				case 3:
					System.out.println("Abandonando o carrinho ...");
					this.cliente.abandonarCarrinho(this);
				break;
				case 4:
					if(this.cliente.getCarrinho()!=null)
						menuCompras();
					else
						System.out.println("Precisa Pegar um carrinho de compras primeiro");
				break;
				case 5:
					System.out.println("Terminando as Compras...");
					if(this.cliente.getCarrinho()!=null)
					{	
						this.cliente.comprar();
						System.out.println("Saindo do Supermercado...");
						TransporteCarga carga = this.cliente;
						carga.descarregar();
					}
					else
						System.out.println("Saindo do Supermercado sem Comprar Nada");
					escolha=0;
					this.cliente=null;
				break;
				case 0:
					System.out.println("Voltando para o Menu Principal...");
				break;
				default:
					System.out.println("Opcao Invalida - Escolha uma das opcoes mencionadas");
				break;
			}
		}
		while(escolha!=0);
	}
	
	private void menuAdmin()
	{
		int escolha;
		do
		{
			escolha=menuTela(2);
			switch(escolha)
			{
				case 1:
					produtos[this.quantidade]=adicionarProduto();
					this.quantidade++;
					System.out.println("Adicionando Produto ao Estoque...");
				break;
				case 2:
					removerProduto();
				break;
				case 3:
					editarProduto();
				break;
				case 4:
					System.out.println(listarProdutos());
				break;
				case 0:
					System.out.println("Voltando para o Menu Principal...");
				break;
				default:
					System.out.println("Opcao Invalida - Escolha uma das opcoes mencionadas");
				break;
			}
		}
		while(escolha!=0);
	}
	private void menuCompras()
	{
		int escolha;
		do
		{
			escolha=menuTela(3);
			switch(escolha)
			{
				case 1:
					adicionarItem();
				break;
				case 2:
					removerItem();
				break;
				case 3:
					this.cliente.getCarrinho().calcularPreco();
				break;
				case 0:
					System.out.println("Voltando para o Menu Cliente");
				break;
				default:
					System.out.println("Opcao Invalida - Escolha uma das opcoes mencionadas");
				break;
			}
		}
		while(escolha!=0);
	}
	private void editarProduto()
	{
		Scanner ler = new Scanner(System.in);
		System.out.println(listarProdutos());
		System.out.println("Insira a Posicao do Produto que deseja Editar");
		
		int posicao = ler.nextInt()-1;
		if(posicao<this.quantidade && posicao>=0)
		{
			ler = new Scanner(System.in);
			System.out.println("Descricao: "+this.produtos[posicao].getDescricao()+"\nInsira:");
			String descricao=ler.nextLine();
			System.out.println("Quantidade do Estoque: "+this.produtos[posicao].getQuantStoque()+"\nInsira:");
			int quant= ler.nextInt();
			System.out.println("Preco Por Unidade: "+this.produtos[posicao].getPrecoPorUnidade()+"\nInsira:");
			float preco = ler.nextFloat();
			this.produtos[posicao].setDescricao(descricao);
			this.produtos[posicao].setQuantStoque(quant);
			this.produtos[posicao].setPrecoPorUnidade(preco);
			System.out.println("Desconto: "+this.desconto[posicao]+"(digite 0 - para produtos sem desconto)");
			int desconto=ler.nextInt();
			this.desconto[posicao]=Math.abs(desconto);
		}
		else
		{
			System.out.println("Posicao Nao Valida");
		}
	}
	private void removerItem()
	{
		Scanner ler = new Scanner(System.in);
		System.out.println(listarProdutos());
		this.cliente.getCarrinho().calcularPreco();
		System.out.println("Insira a Posicao do Produto que deseja remover");
		
		int posicao = ler.nextInt()-1;
		if(posicao<this.quantidade && posicao>=0)
		{
			System.out.println("Insira a Quantidade do Produto que deseja Remover");
			int quantidade=ler.nextInt();
			this.cliente.getCarrinho().remover(this.produtos[posicao],Math.abs(quantidade>0?quantidade:1));
		}
		else
		{
			System.out.println("Posicao Nao Valida");
		}
	}
	private void adicionarItem()
	{
		Scanner ler = new Scanner(System.in);
		System.out.println(listarProdutos());
		System.out.println("Insira a Posicao do Produto que deseja Adicionar");
		int posicao = ler.nextInt()-1;
		if(posicao<this.quantidade && posicao>=0)
		{
			System.out.println("Insira a Quantidade do Produto que deseja Adicionar");
			int quantidade=ler.nextInt();
			this.cliente.getCarrinho().adicionar(this.produtos[posicao],Math.abs(quantidade>0?quantidade:1),desconto[posicao]);
		}
		else
		{
			System.out.println("Posicao Nao Valida");
		}
	}
	private void removerProduto()
	{
		Scanner ler = new Scanner(System.in);
		System.out.println(listarProdutos());
		System.out.println("Insira a Posicao do Produto que deseja eliminar");
		int posicao = ler.nextInt()-1;
		if(posicao<this.quantidade && posicao>=0)
		{	
			System.out.println(this.produtos[posicao].resumo());
			for(int i=posicao;i<this.quantidade;i++)
			{	
				this.produtos[i]=this.produtos[i+1];
				this.desconto[i]=this.desconto[i+1];
			}System.out.println("Produto Removido");
			this.quantidade--;
		}
		else
		{
			System.out.println("Posicao Nao Valida");
		}
	}
	private Produto adicionarProduto()
	{
		int id=this.quantidade>0?produtos[this.quantidade-1].getId()+1:1;
		Scanner ler = new Scanner(System.in);
		System.out.println("Insira a Descricao do Produto");
		String descricao=ler.nextLine();
		System.out.println("Insira a quantidade do Produto");
		int quant=ler.nextInt();
		System.out.println("Insira o Preco por Unidade");
		float preco=ler.nextFloat();
		System.out.println("Insira o desconto - (numero menor que 2 - para produto sem desconto)");
		int desconto=ler.nextInt();
		this.desconto[this.quantidade]=Math.abs(desconto);
		return new Produto(id,descricao,Math.abs(quant),Math.abs(preco));
	}
	private Cliente gerarCliente()
	{
		String[] primeiroNome={"Fonseca","Joao","Alexandre","Manuel","Cassia","Carlos","Alberto","Charles","Yussuf","Erivaldo","Teresa","Manuela","Carvalho","Nuno"};
		String[] sobreNome={"Adao","Jesus","Mufasa","Antonio","Armando","Nunes","Mendes","Silva","Sahim","Estenio","Maria","Leopoldina","Harry","Daves"};
		String[] bairro = {"Machado Saldanha", "Van-dunem Loy Talatona","Mulevos", "Paraiso", "Golfe 1", "Golfe 2", "Dangereux","Palanca","Camama I","Zango 1","Zango 2","Zango 0","Zango 8000"};
		String nome= primeiroNome[(int)(Math.random()*primeiroNome.length)]+" "+sobreNome[(int)(Math.random()*sobreNome.length)];
		long cpf=this.cpf++;
		String endereco= "Bairro: "+bairro[(int)(Math.random()*bairro.length)]+" Rua: "+((int)(Math.random()*20)+1)+" Casa n: "+((int)(Math.random()*20)+1);
		Automovel carro = Math.random()<=0.5? null:gerarAutomovel();
		return new Cliente(nome,cpf,endereco,carro);
	}
	private Automovel gerarAutomovel()
	{
		String[] fabricante={"Volvo","BMW","Audi","Tesla","Ferrari","Camaro","Toyota","Suzuki"};
		String[] modelo={"S8","A2","A1","ES20","WER30","SE3","80S","D20D","ER20","10SE","90SF"};
		String fab = fabricante[(int)(Math.random()*fabricante.length)];
		String mod = modelo[(int)(Math.random()*modelo.length)];
		String mat = "LD-"+(nMat++);
		return new Automovel(mat,fab,mod);
	}
	@Override
	public void deixar(Item item)
	{
		for(int i=0;i<this.quantidade;i++)
		{
			if(item.getProduto().getId()==produtos[i].getId())
				produtos[i].setQuantStoque(produtos[i].getQuantStoque()+item.getQuantidade());
		}
	}
}