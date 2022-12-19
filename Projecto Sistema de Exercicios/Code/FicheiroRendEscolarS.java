import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class FicheiroRendEscolarS
{
	private final String ficheiro="listaRendEscolar.txt";
	public void inicializar() throws Exception
	{
		File f = new File(ficheiro);
        if(!f.exists())
            f.createNewFile();
	}
	public void adicionar(RendEscolar rend) throws Exception
	{
		FicheiroTurmaS fT = new FicheiroTurmaS();
		if(rend==null) throw new ExcecoesGerais.ExcecaoElementoNulo();
		if(rend.getAluno()==null || rend.getTurma()==null) throw new ExcecoesGerais.ExcecaoElementoNulo();
		else if(fT.turmaLotada(rend.getTurma().getId())) throw new ExcecoesGerais.ExcecaoTurmaLotada();
		if(procurarRendEscolar(rend.getTurma().getId(),rend.getAluno().getId())!=null) throw new ExcecoesGerais.ExcecaoJaExistente();
		else
		{
			PrintWriter arq = new PrintWriter(new FileWriter(ficheiro,true));
			arq.printf("%d %d %.2f %.2f %s %s %s %s %.2f %.2f %.2f %.2f%n",rend.getTurma().getId(),rend.getAluno().getId(),rend.getProva1(),rend.getProva2(),rend.getTrabalho()[0].isEmpty() || rend.getTrabalho()[0]==null?"null":rend.getTrabalho()[0].replaceAll(" ", "-"),rend.getTrabalho()[1].isEmpty() ||rend.getTrabalho()[1]==null?"null":rend.getTrabalho()[1].replaceAll(" ", "-"),rend.getTrabalho()[2].isEmpty() || rend.getTrabalho()[2]==null?"null":rend.getTrabalho()[2].replaceAll(" ", "-"),rend.getTrabalho()[3].isEmpty() || rend.getTrabalho()[3]==null ? "null":rend.getTrabalho()[3].replaceAll(" ", "-"),rend.getNotaTrabalho()[0],rend.getNotaTrabalho()[1],rend.getNotaTrabalho()[2],rend.getNotaTrabalho()[3]);
			arq.close();
		}
	}
	public int total() throws Exception
	{
		int i=0;
		Scanner ler = new Scanner(new FileReader(ficheiro));
		FicheiroAlunoS fA = new FicheiroAlunoS();
		FicheiroTurmaS fT = new FicheiroTurmaS();
		while(ler.hasNext())
		{
			Turma t =  fT.procurarTurma(ler.nextInt());
			Aluno a = fA.procurarAluno(ler.nextInt());
			float p1 = ler.nextFloat();
			float p2 = ler.nextFloat();
			String[] trab = {ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " ")};
			float[] nt = {ler.nextFloat(),ler.nextFloat(),ler.nextFloat(),ler.nextFloat()};
			if(t != null && a != null) i++;
		}
		return i;
	}
	public int totalAlunos(int idTurma) throws Exception
	{
		int cont=0;
		Scanner ler = new Scanner(new FileReader(ficheiro));
		FicheiroAlunoS fA = new FicheiroAlunoS();
		FicheiroTurmaS fT = new FicheiroTurmaS();
		while(ler.hasNext())
		{
			Turma t =  fT.procurarTurma(ler.nextInt());
			Aluno a = fA.procurarAluno(ler.nextInt());
			float p1 = ler.nextFloat();
			float p2 = ler.nextFloat();
			String[] trab = {ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " ")};
			float[] nt = {ler.nextFloat(),ler.nextFloat(),ler.nextFloat(),ler.nextFloat()};
			if(t != null && a != null) cont = t.getId() == idTurma ? cont+1 : cont;
		}
		return cont;
	}
	public String listarAlunos(int idTurma) throws Exception
	{
		String lista = "Alunos Matriculados";
		Scanner ler = new Scanner(new FileReader(ficheiro));
		FicheiroAlunoS fA = new FicheiroAlunoS();
		FicheiroTurmaS fT = new FicheiroTurmaS();
		while(ler.hasNext())
		{
			Turma t =  fT.procurarTurma(ler.nextInt());
			Aluno a = fA.procurarAluno(ler.nextInt());
			float p1 = ler.nextFloat();
			float p2 = ler.nextFloat();
			String[] trab = {ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " ")};
			float[] nt = {ler.nextFloat(),ler.nextFloat(),ler.nextFloat(),ler.nextFloat()};
			if(t != null && a != null)
				if(t.getId() == idTurma)
				{
					RendEscolar rend = new RendEscolar(t,a,p1,p2);
					for(int i=0;i<trab.length;i++){
						rend.carregarTrabalho(trab[i].equals("null") ? "" : trab[i],i);
						rend.carregarNotaTrabalho(nt[i],i);
					}
					lista += "\n"+rend.getAluno().resumo();
				}
		}
		return lista.equals("Alunos Matriculados") ? "Sem "+lista : lista;
	}
	public int totalTurmas(int idAluno) throws Exception
	{
		int cont=0;
		Scanner ler = new Scanner(new FileReader(ficheiro));
		FicheiroAlunoS fA = new FicheiroAlunoS();
		FicheiroTurmaS fT = new FicheiroTurmaS();
		while(ler.hasNext())
		{
			Turma t =  fT.procurarTurma(ler.nextInt());
			Aluno a = fA.procurarAluno(ler.nextInt());
			float p1 = ler.nextFloat();
			float p2 = ler.nextFloat();
			String[] trab = {ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " ")};
			float[] nt = {ler.nextFloat(),ler.nextFloat(),ler.nextFloat(),ler.nextFloat()};
			if(t != null && a != null) cont = a.getId() == idAluno ? cont+1 : cont;
		}
		return cont;
	}
	
	public String listarTurmas(int idAluno) throws Exception
	{
		String lista = "Turmas Matriculadas";
		Scanner ler = new Scanner(new FileReader(ficheiro));
		FicheiroAlunoS fA = new FicheiroAlunoS();
		FicheiroTurmaS fT = new FicheiroTurmaS();
		while(ler.hasNext())
		{
			Turma t =  fT.procurarTurma(ler.nextInt());
			Aluno a = fA.procurarAluno(ler.nextInt());
			float p1 = ler.nextFloat();
			float p2 = ler.nextFloat();
			String[] trab = {ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " ")};
			float[] nt = {ler.nextFloat(),ler.nextFloat(),ler.nextFloat(),ler.nextFloat()};
			if(t != null && a != null)
				if(a.getId() == idAluno)
				{
					RendEscolar rend = new RendEscolar(t,a,p1,p2);
					for(int i=0;i<trab.length;i++){
						rend.carregarTrabalho(trab[i].equals("null") ? "" : trab[i],i);
						rend.carregarNotaTrabalho(nt[i],i);
					}
					lista += "\n"+rend.getTurma().resumo();
				}
		}
		return lista.equals("Turmas Matriculadas") ? "Sem "+lista : lista;
	}
	
	public String listarAlunosPorcentual(int idTurma) throws Exception
	{
		String lista = "Alunos Matriculados";
		int[] est = {0,0,0};
		int total = totalAlunos(idTurma);
		Scanner ler = new Scanner(new FileReader(ficheiro));
		FicheiroAlunoS fA = new FicheiroAlunoS();
		FicheiroTurmaS fT = new FicheiroTurmaS();
		while(ler.hasNext())
		{
			Turma t =  fT.procurarTurma(ler.nextInt());
			Aluno a = fA.procurarAluno(ler.nextInt());
			float p1 = ler.nextFloat();
			float p2 = ler.nextFloat();
			String[] trab = {ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " ")};
			float[] nt = {ler.nextFloat(),ler.nextFloat(),ler.nextFloat(),ler.nextFloat()};
			if(t != null && a != null)
				if(t.getId() == idTurma)
				{
					RendEscolar rend = new RendEscolar(t,a,p1,p2);
					for(int i=0;i<trab.length;i++){
						rend.carregarTrabalho(trab[i].equals("null") ? "" : trab[i],i);
						rend.carregarNotaTrabalho(nt[i],i);
					}
					lista += "\n"+rend.getAluno().resumo()+"\nMedia: "+rend.mediaPonderada()+"\nEstado: "+(rend.estado().equals(rend.getEstados()[1]) ? rend.estado()+" {Precisa tirar: "+rend.notaParaFinal()+"}":rend.estado());
					for(int i=0;i<est.length;i++)
						if(rend.estado().equals(rend.getEstados()[i]))
							est[i]++;
				}
		}
		if(lista.equals("Alunos Matriculados"))
			lista = "Sem "+lista;
		else
			lista += "\n"+"Alunos Aprovados: "+est[0]+"("+(((float)est[0]/(float)total)*100)+"%)"+ "\n"+"Alunos Na Final: "+est[1]+"("+(((float)est[1]/(float)total)*100)+"%)"+ "\n"+"Alunos Reprovados: "+est[2]+"("+(((float)est[2]/(float)total)*100)+"%)";
		return lista;
	}
	public RendEscolar procurarRendEscolar(int idTurma, int idAluno) throws Exception
	{
		Scanner ler = new Scanner(new FileReader(ficheiro));
		FicheiroAlunoS fA = new FicheiroAlunoS();
		FicheiroTurmaS fT = new FicheiroTurmaS();
		while(ler.hasNext())
		{
			Turma t =  fT.procurarTurma(ler.nextInt());
			Aluno a = fA.procurarAluno(ler.nextInt());
			float p1 = ler.nextFloat();
			float p2 = ler.nextFloat();
			String[] trab = {ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " ")};
			float[] nt = {ler.nextFloat(),ler.nextFloat(),ler.nextFloat(),ler.nextFloat()};
			if(t != null && a != null)
				if(a.getId() == idAluno && t.getId() == idTurma)
				{
					RendEscolar rend = new RendEscolar(t,a,p1,p2);
					for(int i=0;i<trab.length;i++){
						rend.carregarTrabalho(trab[i].equals("null") ? "" : trab[i],i);
						rend.carregarNotaTrabalho(nt[i],i);
					}
					return rend;
				}
		}
		return null;
	}
	public void resetar() throws Exception
	{
		PrintWriter arq = new PrintWriter(new FileWriter(ficheiro,false));
		arq.printf("");
		arq.close();
	}
	public void editar(RendEscolar rendEscolar) throws Exception
	{
		if(procurarRendEscolar(rendEscolar.getTurma().getId(),rendEscolar.getAluno().getId())==null)
			throw new ExcecoesGerais.ExcecaoIdInexistente();
		else
		{
			int tot=total();
			RendEscolar[] rends = new RendEscolar[tot];
			FicheiroTurmaS fT = new FicheiroTurmaS();
			FicheiroAlunoS fA = new FicheiroAlunoS();
			Scanner ler = new Scanner(new FileReader(ficheiro));
			for(int i=0;i<rends.length;i++)
			{
				Turma t =  fT.procurarTurma(ler.nextInt());
				Aluno a = fA.procurarAluno(ler.nextInt());
				float p1 = ler.nextFloat();
				float p2 = ler.nextFloat();
				String[] trab = {ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " ")};
				float[] nt = {ler.nextFloat(),ler.nextFloat(),ler.nextFloat(),ler.nextFloat()};
				if(t != null && a != null){
					RendEscolar rend = new RendEscolar(t,a,p1,p2);
					for(int c=0;c<trab.length;c++){
						rend.carregarTrabalho(trab[c].equals("null") ? "" : trab[c],c);
						rend.carregarNotaTrabalho(nt[c],c);
					}
					rends[i] = rendEscolar.getTurma().getId()==rend.getTurma().getId() && rendEscolar.getAluno().getId()==rend.getAluno().getId() ? rendEscolar : rend;
				}
			}
			resetar();
			for(int i=0;i<rends.length;i++)
			{
				adicionar(rends[i]);
			}
		}
	}
}