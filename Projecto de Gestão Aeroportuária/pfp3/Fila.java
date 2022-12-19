
public class Fila {
    Aviao primeiro;
    Aviao ultimo;
    int tamanho;
    public Fila()
    {
        primeiro=null;
        ultimo=null;
        tamanho=0;
    }
    public boolean filaVazia()
    {
        return primeiro==null;
    }
    public void inserir(Aviao a)
    {
        if(filaVazia())
            primeiro=ultimo=a;
        else
        {
            ultimo.proximo=a;
            ultimo=a;
        }
        tamanho++;
    }
    public Aviao remover()
    {
        Aviao removido;
        if(filaVazia())
            return null;
        removido = primeiro;
        primeiro=primeiro.proximo;
        tamanho--;
        return removido;
    }
    public Aviao remocaoEmergente()
    {
        Aviao removido=null;
        if(filaVazia())
		{
            return null;
        }
		else if(primeiro.obterUnidadeTempo()<=2)
        {
            removido=primeiro;
			primeiro=primeiro.proximo;
			tamanho--;
        }
        else
        {
            Aviao aviao = primeiro;
            Aviao anterior = aviao;
            aviao=aviao.proximo;
            boolean encontrado=false;
            while(aviao != null)
            {
                if(aviao.obterUnidadeTempo()<=2)
                {
                    encontrado=true;
                    break;
                }
                anterior=aviao;
                aviao=aviao.proximo;
            }
            if(encontrado)
            {
                removido = aviao;
                anterior.proximo=aviao.proximo;
                tamanho--;
            }    
        }
        return removido;
    }
    public void decrementarInstante()
    {
        Aviao aviao = primeiro;
        while(aviao != null)
        {
            if(aviao.obterUnidadeTempo()!=0)
                aviao.uniTempo--;
            aviao=aviao.proximo;
        }
    }
    public void incrementarTempo()
    {
    	Aviao aviao = primeiro;
        while(aviao != null)
        {
            aviao.espera++;
            aviao=aviao.proximo;
        }
    }
    public String toString()
    {
        Aviao i=primeiro;
        String str = "Total de Aviões: "+tamanho+"\n";
        while(i!=null)
        {
            str += ""+i.toString()+"\n";
            i=i.proximo;
        }
        return str;
    }
}
