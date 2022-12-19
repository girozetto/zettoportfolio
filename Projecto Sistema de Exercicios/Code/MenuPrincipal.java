import java.util.Scanner;
public class MenuPrincipal
{
	private Usuario usuario;
	private TipoUsuario tipo;
	public MenuPrincipal(Usuario usuario, TipoUsuario tipo)
	{
		this.usuario = usuario;
		this.tipo = tipo;
		inicializarFicheiros();
	}
	public void inicializarFicheiros()
	{
		try{
			FicheiroAlunoS fA = new FicheiroAlunoS();
			fA.inicializar();
			FicheiroProfessorS fP = new FicheiroProfessorS();
			fP.inicializar();
			FicheiroRendEscolarS fRE = new FicheiroRendEscolarS();
			fRE.inicializar();
			FicheiroTurmaS fT = new FicheiroTurmaS();
			fT.inicializar();
			FicheiroDisciplinaS fD = new FicheiroDisciplinaS();
			fD.inicializar();
		}catch(Exception e){System.out.println(e.getMessage());}
	}
	public int escolher(int i)
	{
		Scanner ler = new Scanner(System.in);
		String[] menus;
		if(this.tipo == TipoUsuario.PROFESSOR){
			String[] m = {
			"//Selecionar Elemento\\\nInsira o Id\n\n0 - Sair\nInsira:",
			"//Menu Professor\\\n 1 - Listar Turmas Lecionadas\n 2 - Listar Medias com Estatisticas\n 3 - Listar Turmas Disponiveis\n\n 0 - Encerrar Sessao\nInsira:",
			"//Inserir Notas\\\n1 - Prova 1\n 2 - Prova 2\n 3 - Trabalho 1\n 4 - Trabalho 2 \n 5 - Trabalho 3 \n 6 - Trabalho 4 \n\n 0 - Sair\nInsira:"
			};
			menus=m;
		}
		else if(this.tipo == TipoUsuario.ALUNO){
			String[] m = {
				"//Selecionar Elemento\\\nInsira o Id\n\n0 - Sair\nInsira:",
				"//Menu Aluno\\\n 1 - Listar Turmas Matriculadas\n 2 - Listar Turmas Disponiveis\n\n 0 - Encerrar Sessao\nInsira:",
				"//Entregar Trabalho\\\n 1 - Trabalho1 \n 2 - Trabalho2\n 3 - Trabalho3\n 4 - Trabalho4\n\n 0 - Voltar \nInsira:"
			};
			menus=m;
		}
		else
		{
			String[] m = {
			"//Selecionar Elemento\\\nInsira o Id\n\n0 - Sair\nInsira:",
			"//Menu Admin\\\n 1 - Disciplinas\n 2 - Turmas\n 3 - Alunos\n 4 - Professores\n\n 0 - Encerrar Sessao\nInsira:",
			"//Menu Admin-Disciplinas\\\n 1 - Adicionar\n 2 - Remover\n 3 - Consultar \n 4 - Listar\n\n 0 - Voltar \nInsira:",
			"//Menu Admin-Turmas\\\n 1 - Adicionar\n 2 - Remover\n 3 - Consultar \n 4 - Listar\n\n 0 - Voltar \nInsira:",
			"//Menu Admin-Professores\\\n 1 - Remover\n 2 - Consultar \n 3 - Listar\n\n 0 - Voltar \nInsira:",
			"//Menu Admin-Alunos\\\n 1 - Remover\n 2 - Consultar \n 3 - Listar\n\n 0 - Voltar \nInsira:"
			};
			menus=m;
		}
		System.out.println(menus[i]);
		return ler.nextInt();
	}
	public void principal()
	{
		System.out.println("Seja Bem-Vindo Sr(a) "+this.usuario.getNome());
		if(this.tipo == TipoUsuario.PROFESSOR)
		{
			professorMenu();
		}
		else if(this.tipo == TipoUsuario.ALUNO)
		{
			alunoMenu();
		}
		else
		{
			while(adminMenu());
		}
	}
	
	public boolean adminMenu()
	{
		int escolha;
		escolha=escolher(1);
		switch(escolha)
		{
			case 1:
				try{while(subDisciplina());}catch(Exception e){System.out.println(e.getMessage());}
			break;
			case 2:
				try{while(subTurma());}catch(Exception e){System.out.println(e.getMessage());}
			break;
			case 3:
				try{while(subAluno());}catch(Exception e){System.out.println(e.getMessage());}
			break;
			case 4:
				try{while(subProfessor());}catch(Exception e){System.out.println(e.getMessage());}
			break;
			case 0:
				System.out.println("Encerrando Sessao...");
				return false;
			default:
				System.out.println("Opcao Invalida - Insira Novamente");
			break;
		}
		return true;
	}
	public Disciplina addDisciplina() throws Exception
	{
		Scanner ler = new Scanner(System.in);
		FicheiroDisciplinaS fD = new FicheiroDisciplinaS();
		System.out.println("Nome da Disciplina: ");
		String nome = ler.nextLine();
		System.out.println("Ementa: ");
		String ementa = ler.nextLine();
		int id=fD.gerarId();
		return new Disciplina(id,nome,ementa);
	}
	public Turma addTurma() throws Exception
	{
		Scanner ler = new Scanner(System.in);
		FicheiroTurmaS fT = new FicheiroTurmaS();
		FicheiroDisciplinaS fD = new FicheiroDisciplinaS();
		Disciplina d;
		while(true)
		{
			System.out.println(fD.listarDisciplinas());
			int id = escolher(0);
			if(id==0)
				throw new ExcecoesGerais.ExcecaoVoltando();
			else
				{
					d=fD.procurarDisciplina(id);
					if(d==null) System.out.println("Id Invalido");
					else break;
				}
		}
		System.out.println("Capacidade da Turma: ");
		int c = ler.nextInt();
		int id=fT.gerarId();
		return new Turma(id,d,null,c);
	}
	public boolean subDisciplina() throws Exception
	{
		int escolha;
		FicheiroDisciplinaS fD = new FicheiroDisciplinaS();
		escolha=escolher(2);
		switch(escolha)
		{
			case 1:
				Disciplina d = addDisciplina();
				fD.adicionar(d);
			break;
			case 2:
				while(true)
				{
					System.out.println(fD.listarDisciplinas());
					int id = escolher(0);
					if(id==0)
						break;
					else
					{
						Disciplina d1=fD.procurarDisciplina(id);
						if(d1==null) System.out.println("Id Invalido");
						else 
						{	
							fD.remover(d1.getId());
							System.out.println("Removido Com Sucesso!!!");
						}
					}
				}	
			break;
			case 3:
				while(true)
				{
					System.out.println(fD.listarDisciplinas());
					int id = escolher(0);
					if(id==0)
						break;
					else
					{
						Disciplina d2=fD.procurarDisciplina(id);
						if(d2==null) System.out.println("Id Invalido");
						else System.out.println(d2.resumo());
					}
				}				
			break;
			case 4:
				System.out.println(fD.listarDisciplinas());
			break;
			case 0:
				System.out.println("Saindo...");
				return false;
			default:
				System.out.println("Opcao Invalida - Insira Novamente");
			break;
		}
		return true;
	}
	public boolean subTurma() throws Exception
	{
		int escolha;
		FicheiroTurmaS fT = new FicheiroTurmaS();
		escolha=escolher(3);
		switch(escolha)
		{
			case 1:
				try{
					Turma t = addTurma();
					fT.adicionar(t);
				}catch(Exception e){System.out.println(e.getMessage());}
			break;
			case 2:
				while(true)
				{
					System.out.println(fT.listarTurmas());
					int id = escolher(0);
					if(id==0)
						break;
					else
					{
						Turma t1=fT.procurarTurma(id);
						if(t1==null) System.out.println("Id Invalido");
						else 
						{
							fT.remover(t1.getId());
							System.out.println("Removido Com Sucesso!!!");
						}
					}
				}
			break;
			case 3:
				while(true)
				{
					System.out.println(fT.listarTurmas());
					int id = escolher(0);
					if(id==0)
						break;
					else
					{
						Turma t2=fT.procurarTurma(id);
						if(t2==null) System.out.println("Id Invalido");
						else System.out.println(t2.resumo());
					}
				}
			break;
			case 4:
				System.out.println(fT.listarTurmas());
			break;
			case 0:
				System.out.println("Saindo...");
				return false;
			default:
				System.out.println("Opcao Invalida - Insira Novamente");
			break;
		}
		return true;
	}
	public boolean subProfessor() throws Exception
	{
		int escolha;
		FicheiroProfessorS fP = new FicheiroProfessorS();
		escolha=escolher(4);
		switch(escolha)
		{
			case 1:
				while(true)
				{
					System.out.println(fP.listarProfessores());
					int id = escolher(0);
					if(id==0)
						break;
					else
					{
						Professor p1=fP.procurarProfessor(id);
						if(p1==null) System.out.println("Id Invalido");
						else 
						{	
							fP.remover(p1.getId());
							System.out.println("Removido Com Sucesso!!!");
						}
					}
				}
			break;
			case 2:
				while(true)
				{
					System.out.println(fP.listarProfessores());
					int id = escolher(0);
					if(id==0)
						break;
					else
					{
						Professor p2=fP.procurarProfessor(id);
						if(p2==null) System.out.println("Id Invalido");
						else System.out.println(p2.resumo());
					}
				}
			break;
			case 3:
				System.out.println(fP.listarProfessores());
			break;
			case 0:
				System.out.println("Saindo...");
				return false;
			default:
				System.out.println("Opcao Invalida - Insira Novamente");
			break;
		}
		return true;
	}
	public boolean subAluno() throws Exception
	{
		int escolha;
		FicheiroAlunoS fA = new FicheiroAlunoS();
		escolha=escolher(5);
		switch(escolha)
		{
			case 1:
				while(true)
				{
					System.out.println(fA.listarAlunos());
					int id = escolher(0);
					if(id==0)
						break;
					else
					{
						Aluno a1=fA.procurarAluno(id);
						if(a1==null) System.out.println("Id Invalido");
						else 
						{
							fA.remover(a1.getId());
							System.out.println("Removido Com Sucesso!!!");
						}
					}
				}
			break;
			case 2:
				while(true)
				{
					System.out.println(fA.listarAlunos());
					int id = escolher(0);
					if(id==0)
						break;
					else
					{
						Aluno a2=fA.procurarAluno(id);
						if(a2==null) System.out.println("Id Invalido");
						else System.out.println(a2.resumo());
					}
				}
			break;
			case 3:
				System.out.println(fA.listarAlunos());
			break;
			case 0:
				System.out.println("Saindo...");
				return false;
			default:
				System.out.println("Opcao Invalida - Insira Novamente");
			break;
		}
		return true;
	}
	
	public void alunoMenu()
	{
		int escolha;
		do{
			escolha=escolher(1);
			switch(escolha)
			{
				case 1:
					try{escolhaTurmaAluno(true);}
					catch(Exception e){System.out.println(e.getMessage());}
				break;
				case 2:
					try{escolhaTurmaAluno(false);}
					catch(Exception e){System.out.println(e.getMessage());}
				break;
				case 0:
					System.out.println("Saindo...");
				break;
				default:
					System.out.println("Opcao Invalida - Insira Novamente");
				break;
			}
		}while(escolha!=0);
	}
	public void escolhaTurmaAluno(boolean matriculada) throws Exception
	{
		Turma t = selecionarTurmaAluno(matriculada);
		if(matriculada)
			entregarTrabalho(t);
		else
		{
			FicheiroRendEscolarS fRE = new FicheiroRendEscolarS();
			RendEscolar rend = new RendEscolar(t,(Aluno)this.usuario,0,0);
			fRE.adicionar(rend);
			System.out.println("-------->Agora Estas Matriculado(a) na Turma \n"+t.resumo());
		}
	}
	public Turma selecionarTurmaAluno(boolean matriculada) throws Exception
	{
		try{
			FicheiroTurmaS fT = new FicheiroTurmaS();
			FicheiroRendEscolarS fRE = new FicheiroRendEscolarS();
			int id;
			do
			{
				if(matriculada)
					System.out.println(fRE.listarTurmas(this.usuario.getId()));
				else
					System.out.println(fT.turmasNM(this.usuario.getId()));
				id=escolher(0);
				if(id==0){ 
					System.out.println("Saindo!!!");
					break;
				}
				else
				{
					Turma t = fT.procurarTurma(id);
					if(t!=null)
					{
						if(matriculada){
							if(fRE.procurarRendEscolar(t.getId(),this.usuario.getId())!=null)
								return t;
							else
								System.out.println("Turma Nao Matriculada");
						}
						else
							if(fRE.procurarRendEscolar(t.getId(),this.usuario.getId())==null)
								return t;
							else
								System.out.println("Turma Ja Matriculada");
					}		
					else
						System.out.println("Id Inexistente");
				}	
			}
			while(id!=0);
		}
		catch(Exception e){System.out.println(e.getMessage());}
		throw new ExcecoesGerais.ExcecaoVoltando();
	}
	public void entregarTrabalho(Turma turma) throws Exception
	{
		FicheiroRendEscolarS fRE = new FicheiroRendEscolarS();
		int escolha;
		while(true)
		{
			try{
				RendEscolar rend = fRE.procurarRendEscolar(turma.getId(),this.usuario.getId());
				if(rend==null)
					break;
				System.out.println(rend.resumo());
				escolha=escolher(2);
				if(escolha==0)
				{
					System.out.println("Saindo!!!");
					break;
				}
				else
				{
					if(escolha>0 && escolha<5)
					{
						Scanner ler = new Scanner(System.in);
						System.out.println("Entregue o Trabalho");
						String trabalho = ler.nextLine();
						rend.setTrabalho(trabalho,escolha-1);
						fRE.editar(rend);
						System.out.println("Trabalho Entregue Com Sucesso");
					}
					else
					{
						System.out.println("Opcao Invalida");
					}
				}
			}catch(Exception e){System.out.println(e.getMessage());}
		}
		throw new Exception("Voltando");
	}
	public void professorMenu()
	{
		int escolha;
		do{
			escolha=escolher(1);
			switch(escolha)
			{
				case 1:
					try{escolhaTurma(true);}
					catch(Exception e){System.out.println(e.getMessage());}
				break;
				case 2:
					try{
						Turma turma = selecionarTurma(true);
						FicheiroRendEscolarS fRE = new FicheiroRendEscolarS();
						System.out.println(fRE.listarAlunosPorcentual(turma.getId()));
					}catch(Exception e){System.out.println(e.getMessage());}
				break;
				case 3:
					try{escolhaTurma(false);}
					catch(Exception e){System.out.println(e.getMessage());}
				break;
				case 0:
					System.out.println("Saindo...");
				break;
				default:
					System.out.println("Opcao Invalida - Insira Novamente");
				break;
			}
		}while(escolha!=0);
	}
	public Turma selecionarTurma(boolean lecionada) throws Exception
	{
		try{
			FicheiroTurmaS fT = new FicheiroTurmaS();
			int id;
			do
			{
				
				System.out.println(fT.listarTurmasLec(this.usuario.getId(),lecionada));
				id=escolher(0);
				if(id==0){ 
					System.out.println("Saindo!!!");
					break;
				}
				else
				{
					Turma t = fT.procurarTurma(id);
					if(t!=null)
					{
						if(lecionada && t.getProfessor()!=null)
							if(t.getProfessor().getId()==this.usuario.getId())
								return t;
							else
								System.out.println("Turma nao Lecionada");
						else
							if(t.getProfessor()==null)
								return t;
							else
								System.out.println("Turma Ja Lecionada");
					}else
						System.out.println("Id Inexistente");
				}	
			}
			while(id!=0);
		}
		catch(Exception e){System.out.println(e.getMessage());}
		throw new ExcecoesGerais.ExcecaoVoltando();
	}
	public void escolhaTurma(boolean lecionada) throws Exception
	{
		Turma t = selecionarTurma(lecionada);
		if(lecionada)
			avaliarAluno(t);
		else
		{
			FicheiroTurmaS fT = new FicheiroTurmaS();
			t.setProfessor((Professor)this.usuario);
			fT.editar(t);
			System.out.println("Agora Lecionas a Turma \n"+t.resumo());
		}
	}
	
	public boolean inserirNotas(Turma turma, Aluno a)
	{
		try{
			FicheiroRendEscolarS fRE = new FicheiroRendEscolarS();
			RendEscolar rend;
			int esc;
			rend = fRE.procurarRendEscolar(turma.getId(), a.getId());
			if(rend==null) return false;
			System.out.println(rend.resumo());
			esc = escolher(2);
			if(esc == 0)
			{
				System.out.println("Saindo!!!");
				return false;
			}
			else if(esc==1)
			{
				System.out.println("Insira a Nota para a Prova 1");
				Scanner ler = new Scanner(System.in);
				float nota = ler.nextFloat();
				rend.setProva1(nota);
				fRE.editar(rend);
			}
			else if(esc==2)
			{
				System.out.println("Insira a Nota para a Prova 2");
				Scanner ler = new Scanner(System.in);
				float nota = ler.nextFloat();
				rend.setProva2(nota);
				fRE.editar(rend);
			}
			else if(esc==3)
			{
				System.out.println("Insira a Nota para o trabalho 1");
				Scanner ler = new Scanner(System.in);
				float nota = ler.nextFloat();
				rend.setNotaTrabalho(nota,0);
				fRE.editar(rend);
			}
			else if(esc==4)
			{						
				System.out.println("Insira a Nota para o trabalho 2");
				Scanner ler = new Scanner(System.in);
				float nota = ler.nextFloat();
				rend.setNotaTrabalho(nota,1);
				fRE.editar(rend);
			}
			else if(esc==5)
			{						
				System.out.println("Insira a Nota para o trabalho 3");
				Scanner ler = new Scanner(System.in);
				float nota = ler.nextFloat();
				rend.setNotaTrabalho(nota,2);
				fRE.editar(rend);
			}
			else if(esc==6)
			{							
				System.out.println("Insira a Nota para o trabalho 4");
				Scanner ler = new Scanner(System.in);
				float nota = ler.nextFloat();
				rend.setNotaTrabalho(nota,3);
				fRE.editar(rend);
			}
			else
			{
				System.out.println("Opcao Invalida - Tente Novamente");
			}
		}catch(Exception e){System.out.println(e.getMessage());}
		return true;
	}
	public void avaliarAluno(Turma turma)
	{
		FicheiroRendEscolarS fRE = new FicheiroRendEscolarS();
		FicheiroAlunoS fA = new FicheiroAlunoS();
		int escolha;
		while(true)
		{
			System.out.println(turma.resumo());
			try{
				System.out.println(fRE.listarAlunos(turma.getId()));
				escolha=escolher(0);
				if(escolha==0)
				{
					System.out.println("Saindo!!!");
					break;
				}
				else
				{
					Aluno a;
					try{
						a = fA.procurarAluno(escolha);
						if(a==null)
						{
							System.out.println("Nao Existe um Aluno com este Id");
						}
						else
						{
							while(inserirNotas(turma,a));
						}
					}catch(Exception e){System.out.println("Ocorreu um erro ao Procurar o Aluno "+e.getMessage());}
				}
			}catch(Exception e){System.out.println("Ocorreu um erro Ao Listar os Alunos desta Turma "+e.getMessage());}
		}
	}
	
}