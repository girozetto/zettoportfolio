package projectofp3;

public class Medico {
    private int id;
    private String nome;
    private String especialidade;
    public Medico(int id,String nome,String especialidade)
    {
        this.id=id;
        this.nome=nome;
        this.especialidade=especialidade;
    }
    public void definirNome(String nome)
    {
        this.nome=nome;
    }
    public void definirEspecialidade(String especialidade)
    {
        this.especialidade=especialidade;
    }
    public int obterId()
    {
        return id;
    }
    public String obterNome()
    {
        return nome;
    }
    public String obterEspecialidade()
    {
        return especialidade;
    }
    @Override
    public String toString()
    {
        String texto = "[ ID: "+obterId();
        texto += " Nome: "+obterNome();
        texto += " Especialidade: "+obterEspecialidade()+" ]";
        return texto;
    }
}
