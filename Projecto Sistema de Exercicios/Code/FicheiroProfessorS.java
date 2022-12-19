
import java.io.FileWriter;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
import java.util.*;
import java.text.SimpleDateFormat;

public class FicheiroProfessorS
{
	private final String ficheiro="listaProfessores.txt";
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
			if(procurarProfessor(id)==null) return id;
			id += c.get(Calendar.MONTH);
		}
	}
	public String listarProfessores() throws Exception
	{
		String lista = "Professores\n";
		Scanner ler = new Scanner(new FileReader(ficheiro));
		Professor prof;
		SimpleDateFormat s = new SimpleDateFormat("dd-M-yyyy");
		while(ler.hasNext())
		{
			prof = new Professor(ler.nextInt(),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),s.parse(ler.next()),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "));
			lista += prof.resumo()+"\n";
		}
		return lista;
	}
	public void adicionar(Professor prof) throws Exception
	{
		if(prof==null) throw new ExcecoesGerais.ExcecaoElementoNulo();
		PrintWriter arq = new PrintWriter(new FileWriter(ficheiro,true));
        arq.printf("%d %s %s %s %s %s%n",prof.getId(),prof.getNome().replaceAll(" ", "-"),prof.getCargo().replaceAll(" ", "-"),prof.getDataNasc(),prof.getUserName().replaceAll(" ", "-"),prof.getSenha().replaceAll(" ", "-"));
        arq.close();
	}
	public int total() throws Exception
	{
		Professor p;
		int i=0;
		Scanner ler = new Scanner(new FileReader(ficheiro));
		SimpleDateFormat s = new SimpleDateFormat("dd-M-yyy");
		while(ler.hasNext())
		{
			p = new Professor(ler.nextInt(),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),s.parse(ler.next()),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "));
			i++;
		}
		return i;
	}
	public Professor procurarProfessor(int id) throws Exception
	{
		Professor i;
		Scanner ler = new Scanner(new FileReader(ficheiro));
		SimpleDateFormat s = new SimpleDateFormat("dd-M-yyy");
		while(ler.hasNext())
		{
			i = new Professor(ler.nextInt(),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),s.parse(ler.next()),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "));
			if(i.getId()==id) return i;
		}
		return null;
	}
	public void remover(int id) throws Exception
	{
		if(procurarProfessor(id)==null)
			throw new ExcecoesGerais.ExcecaoIdInexistente();
		else
		{
			int t=total();
			if(t>1)
			{
				Professor[] profs = new Professor[t-1];
				Professor p;
				Scanner ler = new Scanner(new FileReader(ficheiro));
				SimpleDateFormat s = new SimpleDateFormat("dd-M-yyy");
				for(int i=0;i<profs.length;i++)
				{
					p = new Professor(ler.nextInt(),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),s.parse(ler.next()),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "));
					if(p.getId()==id) i--;
					else profs[i] = p;
				}
				PrintWriter arq = new PrintWriter(new FileWriter(ficheiro,false));
				arq.printf("");
				arq.close();
				for(int i=0;i<profs.length;i++)
				{
					adicionar(profs[i]);
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
	public void editar(Professor prof) throws Exception
	{
		if(procurarProfessor(prof.getId())==null)
			throw new ExcecoesGerais.ExcecaoIdInexistente();
		else
		{
			int t=total();
			Professor[] profs = new Professor[t];
			Scanner ler = new Scanner(new FileReader(ficheiro));
			SimpleDateFormat s = new SimpleDateFormat("dd-M-yyy");
			for(int i=0;i<profs.length;i++)
			{
				Professor p = new Professor(ler.nextInt(),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),s.parse(ler.next()),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "));
				if(p.getId()==prof.getId()) profs[i]=prof;
				else profs[i] = p;
			}
			PrintWriter arq = new PrintWriter(new FileWriter(ficheiro,false));
			arq.printf("");
			arq.close();
			for(int i=0;i<profs.length;i++)
			{
				adicionar(profs[i]);
			}
		}
	}
	public Professor logar(String username, String senha) throws Exception
	{
		Professor i;
		Scanner ler = new Scanner(new FileReader(ficheiro));
		SimpleDateFormat s = new SimpleDateFormat("dd-M-yyy");
		if(username==null || username.isEmpty()) throw new Exception("Nome de Usuario Vazio");
		else if(senha==null || senha.isEmpty()) throw new Exception("Senha do Usuario Vazia");
		else
			while(ler.hasNext())
			{
				i = new Professor(ler.nextInt(),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "),s.parse(ler.next()),ler.next().replaceAll("-", " "),ler.next().replaceAll("-", " "));
				if(i.getUserName().equals(username) && i.getSenha().equals(senha))
					return i;
			}
		throw new ExcecoesGerais.ExcecaoUsuarioInexistente();
	}
}