public class Turma
{
	private int id;
	private Disciplina disciplina;
	private Professor professor;
	private int capacidade;
	
	//Métodos Constutores
	public Turma(){}
	public Turma(int id, Disciplina disciplina, Professor professor, int capacidade)
	{
		setId(id);
		setDisciplina(disciplina);
		setProfessor(professor);
		setCapacidade(capacidade);
	}
	
	//Métodos Acessores
	public int getId(){return this.id;}
	public Disciplina getDisciplina(){return this.disciplina;}
	public Professor getProfessor(){return this.professor;}
	public int getCapacidade(){return this.capacidade;}
	
	//Métodos Modificadores
	public void setId(int id)
	{
		this.id = id;
	}
	public void setDisciplina(Disciplina disciplina)
	{
		this.disciplina = disciplina;
	}
	public void setProfessor(Professor professor)
	{
		this.professor = professor;
	}
	public void setCapacidade(int capacidade)
	{
		this.capacidade = capacidade;
	}
	public String resumo()
	{
		String resumo = "";
		resumo += "[ id: " + getId()+",";
		resumo += "Capacidade: "+getCapacidade()+" ]\n";
		resumo += "Disciplina: "+(this.disciplina == null? "Nenhuma Disciplina Associada" : getDisciplina().resumo())+"\n";
		resumo += "Professor: "+(this.professor == null? "Nenhum Professor Associado" : getProfessor().resumo())+"\n";
		return resumo;
	}
}