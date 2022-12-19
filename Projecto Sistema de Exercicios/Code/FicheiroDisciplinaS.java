
import java.io.FileWriter;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
import java.util.*;

public class FicheiroDisciplinaS
{
	private final String ficheiro="listaDisciplinas.txt";
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
			if(procurarDisciplina(id)==null) return id;
			id += c.get(Calendar.SECOND);
		}
	}
	public String listarDisciplinas() throws Exception
	{
		String lista = "Disciplinas\n";
		Scanner ler = new Scanner(new FileReader(ficheiro));
		Disciplina dis;
		while(ler.hasNext())
		{
			dis = new Disciplina(ler.nextInt(),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "));
			lista += dis.resumo()+"\n";
		}
		return lista;
	}
	public void adicionar(Disciplina dis) throws Exception
	{
		if(dis==null) throw new ExcecoesGerais.ExcecaoElementoNulo();
		PrintWriter arq = new PrintWriter(new FileWriter(ficheiro,true));
        arq.printf("%d %s %s%n",dis.getId(),dis.getNome().replaceAll(" ", "-"),dis.getEmenta().replaceAll(" ", "-"));
        arq.close();
	}
	public int total() throws Exception
	{
		Disciplina dis;
		int i=0;
		Scanner ler = new Scanner(new FileReader(ficheiro));
		while(ler.hasNext())
		{
			dis = new Disciplina(ler.nextInt(),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "));
			i++;
		}
		return i;
	}
	public Disciplina procurarDisciplina(int id) throws Exception
	{
		Disciplina i;
		Scanner ler = new Scanner(new FileReader(ficheiro));
		while(ler.hasNext())
		{
			i = new Disciplina(ler.nextInt(),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "));
			if(i.getId()==id) return i;
		}
		return null;
	}
	public void remover(int id) throws Exception
	{
		if(procurarDisciplina(id)==null)
			throw new ExcecoesGerais.ExcecaoIdInexistente();
		else
		{
			int t=total();
			if(t>1)
			{
				Disciplina[] dis = new Disciplina[t-1];
				Scanner ler = new Scanner(new FileReader(ficheiro));
				for(int i=0;i<dis.length;i++)
				{
					Disciplina d = new Disciplina(ler.nextInt(),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "));
					if(d.getId()==id) i--;
					else dis[i] = d;
				}
				PrintWriter arq = new PrintWriter(new FileWriter(ficheiro,false));
				arq.printf("");
				arq.close();
				for(int i=0;i<dis.length;i++)
				{
					adicionar(dis[i]);
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
	public void editar(Disciplina d) throws Exception
	{
		if(procurarDisciplina(d.getId())==null)
			throw new ExcecoesGerais.ExcecaoIdInexistente();
		else
		{
			int t=total();
			Disciplina[] dis = new Disciplina[t];
			Scanner ler = new Scanner(new FileReader(ficheiro));
			for(int i=0;i<dis.length;i++)
			{
				Disciplina ds = new Disciplina(ler.nextInt(),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "));
				if(ds.getId()==d.getId()) dis[i]=d;
				else dis[i] = ds;
			}
			PrintWriter arq = new PrintWriter(new FileWriter(ficheiro,false));
			arq.printf("");
			for(int i=0;i<dis.length;i++)
			{
				adicionar(dis[i]);
			}
		}
	}
}