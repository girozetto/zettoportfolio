import java.io.FileWriter;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
import java.util.*;
import java.text.SimpleDateFormat;
public class FicheiroAlunoS
{
	private final String ficheiro="listaAlunos.txt";
	public void inicializar() throws Exception
	{
		File f = new File(ficheiro);
        if(!f.exists())
            f.createNewFile();
	}
	public int gerarId(Date dataNasc) throws Exception
	{
		Calendar c = Calendar.getInstance();
		c.setTime(dataNasc);
		int id = c.get(Calendar.YEAR);
		while(true)
		{
			if(procurarAluno(id)==null) return id;
			id += c.get(Calendar.MONTH);
		}
	}
	public String listarAlunos() throws Exception
	{
		String lista = "Alunos";
		Scanner ler = new Scanner(new FileReader(ficheiro));
		Aluno a;
		SimpleDateFormat s = new SimpleDateFormat("dd-M-yyyy");
		while(ler.hasNext())
		{
			a = new Aluno(ler.nextInt(),ler.next().replaceAll("-", " "),s.parse(ler.next()),ler.nextInt(),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "));
			lista += "\n"+a.resumo();
		}
		return lista;
	}
	public void adicionar(Aluno aluno) throws Exception
	{
		if(aluno==null) throw new ExcecoesGerais.ExcecaoElementoNulo();
		PrintWriter arq = new PrintWriter(new FileWriter(ficheiro,true));
        arq.printf("%d %s %s %d %s %s%n",aluno.getId(),aluno.getNome().replaceAll(" ", "-"),aluno.getDataNasc(),aluno.getPeriodo(),aluno.getUserName().replaceAll(" ", "-"),aluno.getSenha().replaceAll(" ", "-"));
        arq.close();
	}
	public int total() throws Exception
	{
		Aluno a;
		int i=0;
		Scanner ler = new Scanner(new FileReader(ficheiro));
		SimpleDateFormat s = new SimpleDateFormat("dd-M-yyy");
		while(ler.hasNext())
		{
			a = new Aluno(ler.nextInt(),ler.next().replaceAll("-", " "),s.parse(ler.next()),ler.nextInt(),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "));
			i++;
		}
		return i;
	}
	public Aluno procurarAluno(int id) throws Exception
	{
		Aluno i;
		Scanner ler = new Scanner(new FileReader(ficheiro));
		SimpleDateFormat s = new SimpleDateFormat("dd-M-yyy");
		while(ler.hasNext())
		{
			i = new Aluno(ler.nextInt(),ler.next().replaceAll("-", " "),s.parse(ler.next()),ler.nextInt(),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "));
			if(i.getId()==id) return i;
		}
		return null;
	}
	public void remover(int id) throws Exception
	{
		if(procurarAluno(id)==null)
			throw new ExcecoesGerais.ExcecaoIdInexistente();
		else
		{
			int t=total();
			if(t>1)
			{
				Aluno[] as = new Aluno[t-1];
				Scanner ler = new Scanner(new FileReader(ficheiro));
				SimpleDateFormat s = new SimpleDateFormat("dd-M-yyy");
				for(int i=0;i<as.length;i++)
				{
					Aluno a = new Aluno(ler.nextInt(),ler.next().replaceAll("-", " "),s.parse(ler.next()),ler.nextInt(),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "));
					if(a.getId()==id) i--;
					else as[i] = a;
				}
				PrintWriter arq = new PrintWriter(new FileWriter(ficheiro,false));
				arq.printf("");
				arq.close();
				for(int i=0;i<as.length;i++)
				{
					adicionar(as[i]);
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
	public void editar(Aluno aluno) throws Exception
	{
		if(procurarAluno(aluno.getId())==null)
			throw new ExcecoesGerais.ExcecaoIdInexistente();
		else
		{
			int t=total();
			Aluno[] as = new Aluno[t];
			Scanner ler = new Scanner(new FileReader(ficheiro));
			SimpleDateFormat s = new SimpleDateFormat("dd-M-yyy");
			for(int i=0;i<as.length;i++)
			{
				Aluno a = new Aluno(ler.nextInt(),ler.next().replaceAll("-", " "),s.parse(ler.next()),ler.nextInt(),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "));
				if(a.getId()==aluno.getId()) as[i]=aluno;
				else as[i] = a;
			}
			PrintWriter arq = new PrintWriter(new FileWriter(ficheiro,false));
			arq.printf("");
			arq.close();
			for(int i=0;i<as.length;i++)
			{
				adicionar(as[i]);
			}
		}
	}
	public Aluno logar(String username, String senha) throws Exception
	{
		Aluno i;
		Scanner ler = new Scanner(new FileReader(ficheiro));
		SimpleDateFormat s = new SimpleDateFormat("dd-M-yyy");
		while(ler.hasNext())
		{
			i = new Aluno(ler.nextInt(),ler.next().replaceAll("-", " "),s.parse(ler.next()),ler.nextInt(),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "));
			if(i.getUserName().equals(username) && i.getSenha().equals(senha))
				return i;
		}
		throw new ExcecoesGerais.ExcecaoUsuarioInexistente();
	}
}