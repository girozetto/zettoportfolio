import java.util.*;
import java.text.SimpleDateFormat;
public class Usuario
{
	private int id;
	private String nome;
	private Date dataNasc;
	private String username;
	private String senha;
	
	//Métodos Constutores
	public Usuario(){}
	public Usuario(int id, String nome, Date dataNasc, String username, String senha)
	{
		setId(id);
		setNome(nome);
		setDataNasc(dataNasc);
		setUserName(username);
		setSenha(senha);
	}
	
	//Métodos Acessores
	public int getId(){return this.id;}
	
	public String getNome(){return this.nome;}
	
	public String getDataNasc()
	{
		SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
		return s.format(this.dataNasc);
	}
	
	public String getUserName(){return this.username;}
	
	public String getSenha(){return this.senha;}
	
	//Métodos Modificadores
	public void setId(int id)
	{
		this.id = id;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public void setDataNasc(Date dataNasc)
	{
		this.dataNasc = dataNasc;
	}
	public void setUserName(String username)
	{
		this.username = username;
	}
	public void setSenha(String senha)
	{
		this.senha = senha;
	}
}