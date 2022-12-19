
public class Aviao {
    private int id;
    public int uniTempo;
    public int espera;
    Aviao proximo;
    
    public Aviao(int id)
    {
        this.id=id;
        this.uniTempo=-1;
        this.proximo=null;
        this.espera=0;
    }
    public Aviao(int id,int uniTempo)
    {
        this.id=id;
        this.uniTempo=uniTempo;
        this.proximo=null;
        this.espera=0;
    }
    public int tempoEspera()
    {
        return espera;
    }
    public int obterId()
    {
        return id;
    }
    public int obterUnidadeTempo()
    {
        return uniTempo;
    }
    @Override
    public String toString()
    {
        String str="";
        str += "{ID: "+obterId()+" } - { Unidade de Tempo: "+obterUnidadeTempo()+" }";
        return str;
    }
}
