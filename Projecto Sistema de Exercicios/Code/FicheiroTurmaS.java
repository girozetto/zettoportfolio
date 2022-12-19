import java.io.FileWriter;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
import java.util.*;

public class FicheiroTurmaS
{
	private final String ficheiro="listaTurma.txt";
	public void inicializar() throws Exception
	{
		File f = new File(ficheiro);
        if(!f.exists())
            f.createNewFile();
	}
	public int gerarId() throws Exception
	{
		Calendar c = Calendar.getInstance();
		int id = c.get(Calendar.YEAR);
		while(true)
		{
			if(procurarTurma(id)==null) return id;
			id += c.get(Calendar.SECOND);
		}
	}
	public int total() throws Exception
	{
		int i=0;
		Scanner ler = new Scanner(new FileReader(ficheiro));
		FicheiroProfessorS fP = new FicheiroProfessorS();
		FicheiroDisciplinaS fD = new FicheiroDisciplinaS();
		while(ler.hasNext())
		{
			Turma turma = new Turma(ler.nextInt(),fD.procurarDisciplina(ler.nextInt()),fP.procurarProfessor(ler.nextInt()),ler.nextInt());
			i++;
		}
		return i;
	}
	public String listarTurmas() throws Exception
	{
		String lista = "Turmas";
		Scanner ler = new Scanner(new FileReader(ficheiro));
		FicheiroProfessorS fP = new FicheiroProfessorS();
		FicheiroDisciplinaS fD = new FicheiroDisciplinaS();
		while(ler.hasNext())
		{
			Turma turma = new Turma(ler.nextInt(),fD.procurarDisciplina(ler.nextInt()),fP.procurarProfessor(ler.nextInt()),ler.nextInt());
			lista += "\n"+turma.resumo();
		}
		return lista.equals("Turmas") ? "Sem "+lista : lista;
	}
	public void adicionar(Turma turma) throws Exception
	{
		if(turma==null)throw new ExcecoesGerais.ExcecaoElementoNulo();
		PrintWriter arq = new PrintWriter(new FileWriter(ficheiro,true));
        arq.printf("%d %d %d %d%n",turma.getId(),(turma.getDisciplina()==null? -1 : turma.getDisciplina().getId()),(turma.getProfessor()==null? -1 : turma.getProfessor().getId()),turma.getCapacidade());
        arq.close();
	}
	public boolean turmaLotada(int idTurma) throws Exception
	{
		FicheiroRendEscolarS fRE = new FicheiroRendEscolarS();
		Turma turma = procurarTurma(idTurma);
		return fRE.totalAlunos(idTurma) == turma.getCapacidade();
	}
	public String listarTurmasLec(int idProfessor, boolean lecionada) throws Exception
	{
		String lista = "Turmas"+(lecionada?"":" nao")+" Lecionadas";
		FicheiroProfessorS fP = new FicheiroProfessorS();
		FicheiroDisciplinaS fD = new FicheiroDisciplinaS();
		Scanner ler = new Scanner(new FileReader(ficheiro));
		while(ler.hasNext())
		{
			Turma turma = new Turma(ler.nextInt(),fD.procurarDisciplina(ler.nextInt()),fP.procurarProfessor(ler.nextInt()),ler.nextInt());
			if(lecionada)
			{
				if(turma.getProfessor()!=null)
					if(turma.getProfessor().getId()==idProfessor)	
						lista += "\n"+turma.resumo();
			}
			else
				if(turma.getProfessor()==null)
					lista += "\n"+turma.resumo();
		}
		return lista.equals("Turmas"+(lecionada?"":" nao")+" Lecionadas") ? "Sem "+lista : lista;
	}
	public String turmasNM(int idAluno) throws Exception
	{
		String lista = "Turmas Disponiveis";
		FicheiroProfessorS fP = new FicheiroProfessorS();
		FicheiroDisciplinaS fD = new FicheiroDisciplinaS();
		FicheiroRendEscolarS fRE = new FicheiroRendEscolarS();
		Scanner ler = new Scanner(new FileReader(ficheiro));
		while(ler.hasNext())
		{
			Turma turma = new Turma(ler.nextInt(),fD.procurarDisciplina(ler.nextInt()),fP.procurarProfessor(ler.nextInt()),ler.nextInt());
			if(fRE.procurarRendEscolar(turma.getId(), idAluno)==null)
				lista += "\n"+turma.resumo();
		}
		return lista.equals("Turmas Disponiveis") ? "Sem "+lista : lista;
	}
	public Turma procurarTurma(int id) throws Exception
	{
		FicheiroProfessorS fP = new FicheiroProfessorS();
		FicheiroDisciplinaS fD = new FicheiroDisciplinaS();
		Scanner ler = new Scanner(new FileReader(ficheiro));
		while(ler.hasNext())
		{
			Turma i = new Turma(ler.nextInt(),fD.procurarDisciplina(ler.nextInt()),fP.procurarProfessor(ler.nextInt()),ler.nextInt());
			if(i.getId()==id) return i;
		}
		return null;
	}
	public void remover(int id) throws Exception
	{
		if(procurarTurma(id)==null)
			throw new ExcecoesGerais.ExcecaoIdInexistente();
		else
		{
			int tot=total();
			if(tot>1)
			{
				Turma[] turmas = new Turma[tot-1];
				FicheiroProfessorS fP = new FicheiroProfessorS();
				FicheiroDisciplinaS fD = new FicheiroDisciplinaS();
				Scanner ler = new Scanner(new FileReader(ficheiro));
				for(int i=0;i<turmas.length;i++)
				{
					Turma t = new Turma(ler.nextInt(),fD.procurarDisciplina(ler.nextInt()),fP.procurarProfessor(ler.nextInt()),ler.nextInt());
					if(t.getId()==id) i--;
					else turmas[i] = t;
				}
				PrintWriter arq = new PrintWriter(new FileWriter(ficheiro,false));
				arq.printf("");
				arq.close();
				for(int i=0;i<turmas.length;i++)
				{
					adicionar(turmas[i]);
				}
			}
			else 
			{
				PrintWriter arq = new PrintWriter(new FileWriter(ficheiro,false));
				arq.printf("");
				arq.close();
			}
		}
	}
	public void editar(Turma turma) throws Exception
	{
		if(procurarTurma(turma.getId())==null)
			throw new ExcecoesGerais.ExcecaoIdInexistente();
		else
		{
			int tot=total();
			Turma[] turmas = new Turma[tot];
			FicheiroProfessorS fP = new FicheiroProfessorS();
			FicheiroDisciplinaS fD = new FicheiroDisciplinaS();
			Scanner ler = new Scanner(new FileReader(ficheiro));
			for(int i=0;i<turmas.length;i++)
			{
				Turma t = new Turma(ler.nextInt(),fD.procurarDisciplina(ler.nextInt()),fP.procurarProfessor(ler.nextInt()),ler.nextInt());
				turmas[i] = t.getId()==turma.getId() ? turma : t;
			}
			PrintWriter arq = new PrintWriter(new FileWriter(ficheiro,false));
			arq.printf("");
			arq.close();
			for(int i=0;i<turmas.length;i++)
			{
				adicionar(turmas[i]);
			}
		}
	}
}