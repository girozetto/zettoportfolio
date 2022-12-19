
public class RendEscolar
{
	private final int TAM=4;
	private Turma turma;
	private Aluno aluno;
	private float prova1;
	private float prova2;
	private String[] trabalho;
	private float[] notaTrabalho;
	private String[] estados = {"Aprovado","Final","Reprovado"};
	//Métodos Constutores
	public RendEscolar(Turma turma, Aluno aluno, float prova1, float prova2) throws ExcecoesRend.ExcecaoNotaInvalida
	{
		setTurma(turma);
		setAluno(aluno);
		setProva1(prova1);
		setProva2(prova2);
		this.trabalho = new String[TAM];
		this.notaTrabalho = new float[TAM];
		for(int i=0;i<TAM;i++)
		{
			this.trabalho[i]="";
			this.notaTrabalho[i]=0;
		}
	}
	//Métodos Acessores
	public Turma getTurma(){return this.turma;}
	public Aluno getAluno(){return this.aluno;}
	public float getProva1(){return this.prova1;}
	public float getProva2(){return this.prova2;}
	public String[] getTrabalho(){return this.trabalho;}
	public float[] getNotaTrabalho(){return this.notaTrabalho;}
	public String[] getEstados(){return this.estados;}
	
	//Métodos Modificadores
	public void setTurma(Turma turma)
	{
		this.turma=turma;
	}
	public void setAluno(Aluno aluno)
	{
		this.aluno=aluno;
	}
	public void setProva1(float prova1) throws ExcecoesRend.ExcecaoNotaInvalida
	{
		if(prova1 < 0 || prova1 > 20) throw new ExcecoesRend.ExcecaoNotaInvalida();
		else this.prova1=prova1;
	}
	public void setProva2(float prova2) throws ExcecoesRend.ExcecaoNotaInvalida
	{
		if(prova2 < 0 || prova2 > 20) throw new ExcecoesRend.ExcecaoNotaInvalida();
		else this.prova2=prova2;
	}
	public void carregarTrabalho(String trabalho, int i)
	{
		this.trabalho[i]=trabalho;
	}
	public void carregarNotaTrabalho(float nota, int i)
	{
		this.notaTrabalho[i]=nota;
	}
	public void setTrabalho(String trabalho, int i) throws ExcecoesRend.ExcecaoTrabalhoVazio,ExcecoesRend.ExcecaoPosicaoInvalida,ExcecoesRend.ExcecaoTrabalhoJaEntregue
	{
		if(i>=TAM || i<0) throw new ExcecoesRend.ExcecaoPosicaoInvalida();
		else if(trabalho.trim().equals("") || trabalho == null) throw new ExcecoesRend.ExcecaoTrabalhoVazio();
		else if(!this.trabalho[i].isEmpty()) throw new ExcecoesRend.ExcecaoTrabalhoJaEntregue();
		else this.trabalho[i]=trabalho;
	}
	public void setNotaTrabalho(float nota, int i) throws ExcecoesRend.ExcecaoPosicaoInvalida,ExcecoesRend.ExcecaoNotaInvalida,ExcecoesRend.ExcecaoNotaSemTrabalho
	{
		if(i>=TAM || i<0) throw new ExcecoesRend.ExcecaoPosicaoInvalida();
		else if(this.trabalho[i]==null || this.trabalho[i].trim().isEmpty()) throw new ExcecoesRend.ExcecaoNotaSemTrabalho();
		else if(nota < 0 || nota > 20) throw new ExcecoesRend.ExcecaoNotaInvalida();
		else this.notaTrabalho[i]=nota;
	}
	public float notaComExtra(int i)
	{
		float nota = (float)(((this.notaTrabalho[2]+this.notaTrabalho[3])/2)*0.1+this.prova2);
		if(i==0) nota = (float)(((this.notaTrabalho[0]+this.notaTrabalho[1])/2)*0.1+this.prova1); 
		return nota > 20 ? 20 : nota;
	}
	public float mediaPonderada()
	{
		return (float)( Math.floor(notaComExtra(0)*0.4 + notaComExtra(1)*0.6));
	}
	public float notaParaFinal()
	{
		return (float)( Math.floor((10 - notaComExtra(0)*0.4)/0.6 ));
	}
	public String estado()
	{
		float media = mediaPonderada();
		return media > 9.5 ? estados[0] : media > 7.0 ? estados[1] : estados[2];
	}
	public String resumo()
	{
		 String resumo = "Rendimento Escolar\n";
		 resumo += "Aluno: "+getAluno().resumo()+"\n";
		 resumo += "Turma: "+getTurma().resumo()+"\n";
		 resumo += "Nota da 1a Prova: "+getProva1()+" Valores, Nota da 2a Prova: "+getProva2()+" Valores\n";
		 resumo += "Notas dos Trabalhos\n";
		 for(int i=0;i<this.trabalho.length;i++)
			 resumo += "Trabalho "+(i+1)+": "+(this.trabalho[i]==null || this.trabalho[i].isEmpty() ? "Nao Entregue":this.trabalho[i])+"\n Nota: "+(this.trabalho[i]==null || this.trabalho[i].isEmpty() ? " Sem Nota ": this.notaTrabalho[i])+" Valores\n";
		 return resumo;
	}
}