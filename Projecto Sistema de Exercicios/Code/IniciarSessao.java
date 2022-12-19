import java.util.*;
import java.text.SimpleDateFormat;
public class IniciarSessao
{
	public void executar()
	{
		System.out.println("Iniciando o Sistema...");
		inicializarFicheiros();
		animacao(true);
		inicioSessao();
		
	}
	public void inicializarFicheiros()
	{
		try{
			FicheiroAlunoS fA = new FicheiroAlunoS();
			fA.inicializar();
			FicheiroProfessorS fP = new FicheiroProfessorS();
			fP.inicializar();
		}catch(Exception e){System.out.println(e.getMessage());}
	}
	public int escolher(int i)
	{
		Scanner ler = new Scanner(System.in);
		String[] menus = {
			"//Tipo de Usuario\\\n 1 - Professor\n 2 - Aluno\n 3 - Administrador\n\n 0 - Voltar\nInsira:",
			"//Inicio de Sessao\\\n 1 - Iniciar Sessao \n2 - Cadastrar Usuario \n\n0 - Encerrar o Programa\nInsira:"
		};
		System.out.println(menus[i]);
		return ler.nextInt();
	}
	public void animacao(boolean iniciando)
	{
		if(iniciando)
			for(int i=1;i<=5;i++)
			{
				for(int a=1;a<=i;a++)
				{
					System.out.print("+");
					try{Thread.sleep(500);}catch(InterruptedException excecao){}
				}
				System.out.print("\n");
			}
		else
			for(int i=5;i>=1;i--)
			{
				for(int a=1;a<=i;a++)
				{
					System.out.print("+");
					try{Thread.sleep(500);}catch(InterruptedException excecao){}
				}
				System.out.print("\n");
			}
	}
	public void inicioSessao()
	{
		int esc;
		do{
			esc = escolher(1);
			switch(esc)
			{
				case 1:
					try{
						TipoUsuario tipo = selecionarTipo();
						Usuario user = inserirCredenciais(tipo);
						new MenuPrincipal(user,tipo).principal();	
					}catch(Exception e){System.out.println(e.getMessage());}
				break;
				case 2:
					try{
						TipoUsuario tipo = selecionarTipo();
						Usuario user = cadastrarUsuario(tipo);
						new MenuPrincipal(user,tipo).principal();
					}catch(Exception e){System.out.println(e.getMessage());}
				break;
				case 0:
					animacao(false);
					System.out.println("Encerrando o Programa...");
					break;
				default:
					System.out.println("Opcao Invalida - Insira uma Opcao Valida");
				break;
			}
		}while(esc!=0);
	}
	public TipoUsuario selecionarTipo() throws Exception
	{
		int esc;
		while(true)
		{
			esc = escolher(0);
			switch(esc)
			{
				case 1:
					return TipoUsuario.PROFESSOR;
				case 2:
					return TipoUsuario.ALUNO;
				case 3:
					return TipoUsuario.ADMINISTRADOR;
				case 0:
					throw new ExcecoesGerais.ExcecaoVoltando();
				default:
					System.out.println("Opcao Invalida - Insira uma Opcao Valida");
				break;
			}
		}
	}
	public Date lerData() throws Exception
	{
		Scanner ler = new Scanner(System.in);
		SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
		System.out.println("Insira a Data no Formato(dd-MM-yyyy)");
		String data = ler.next();
		return s.parse(data);
	}
	public Usuario cadastrarUsuario(TipoUsuario tipo) throws Exception
	{
		Scanner ler = new Scanner(System.in);
		if(tipo == TipoUsuario.ADMINISTRADOR) throw new ExcecoesGerais.ExcecaoAdminDefinido("admin","1234");
		System.out.print("Nome: ");
		String nome = ler.nextLine();
		Date data = lerData();
		System.out.print("Nome de Usuario: ");
		String username = ler.nextLine();
		System.out.print("Senha: ");
		String senha = ler.nextLine();
		
		if(tipo==TipoUsuario.PROFESSOR)
		{
			FicheiroProfessorS fP = new FicheiroProfessorS();
			try{fP.logar(username,senha);}
			catch(Exception e){ 
				System.out.print("Cargo: ");
				String cargo = ler.nextLine();
				Professor p = new Professor(fP.gerarId(data),nome,cargo,data,username,senha);
				fP.adicionar(p);
				return p;
			}
			throw new ExcecoesGerais.ExcecaoUsuarioJaExistente();
		}
		FicheiroAlunoS fA = new FicheiroAlunoS();
		try{fA.logar(username,senha);}
		catch(Exception e){ 
			System.out.print("Periodo(Inteiro): ");
			int periodo = ler.nextInt();
			Aluno a = new Aluno(fA.gerarId(data),nome,data,periodo,username,senha);
			fA.adicionar(a);
			return a;
		}
		throw new ExcecoesGerais.ExcecaoUsuarioJaExistente();
	}
	public Usuario inserirCredenciais(TipoUsuario tipo) throws Exception
	{
		Scanner ler = new Scanner(System.in);
		FicheiroAlunoS fA = new FicheiroAlunoS();
		FicheiroProfessorS fP = new FicheiroProfessorS();
		System.out.print("Nome de Usuario: ");
		String username = ler.next();
		System.out.print("Senha: ");
		String senha = ler.next();
		if(tipo == TipoUsuario.PROFESSOR) return fP.logar(username,senha);
		else if(tipo == TipoUsuario.ALUNO) return fA.logar(username,senha);
		if(username.equals("admin") && senha.equals("1234")) return new Usuario(11235, "admin", new Date(), "admin", "1234");
		throw new ExcecoesGerais.ExcecaoUsuarioInexistente();
	}
}