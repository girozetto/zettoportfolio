import java.io.IOException;
import java.util.Scanner;
public class OperacaoGeral {
    int numAvioesDec=0,numAvioesAte=0, totalAvioes=0,ateEmergentes=0;
    float tEsperaDec=0,tEsperaAte=0;
    float totalDecolados=0,totalAterrados=0,media;
	String resumo;
    Pista[] pista = {new Pista(),new Pista(),new Pista()};
    public void principal() throws IOException
    {
        int decSorteado,ateSorteado;
        Scanner ler = new Scanner(System.in);
        while(true)
        {
            decSorteado=(int) (Math.random() * 4);
            ateSorteado=(int) (Math.random() * 4);
            System.out.println("Chegaram "+decSorteado+" Aviões ás Filas de Espera de Decolagem");
            System.out.println("Chegaram "+ateSorteado+" Aviões ás Prateleiras de Aterragem");
            inserirAvioesAterragem(ateSorteado);
            inserirAvioesDecolagem(decSorteado);
            numAvioesDec+=decSorteado;
            numAvioesAte+=ateSorteado;
            totalAvioes+=decSorteado+ateSorteado;
            decolarAterrar();
            System.out.println(resumoGeral());
            decrementarIncremUnidadeTempo();
            try {Thread.sleep(1000);}catch(InterruptedException excecao){}
            if(totalAvioes >= 30)
            {
            	System.out.println("Deseja continuar a Simulação?[s/n]");
            	String c=ler.nextLine();
            	if(c.trim().equalsIgnoreCase("n"))
            		break;
            	totalAvioes=0;
            }
        }
		System.out.println("Guardando os Dados das Filas Nos Ficheiros");
        for(int i=0;i<pista.length;i++)
            pista[i].salvarConteudo(i+1);
		pista[0].inserirConteudoFicheiro("ResumoGeral.txt",resumoGeral());
    }
    public void inserirAvioesAterragem(int sorteado)
    {
        for(int i=0;i<sorteado;i++)
        {
            if(pista[0].prat1.tamanho+pista[0].prat2.tamanho <= pista[1].prat1.tamanho+pista[1].prat2.tamanho)
                pista[0].inserirPrateleira(new Aviao((i+numAvioesAte)*2+1, (int) (Math.random() * 20 + 1)));
            else
                pista[1].inserirPrateleira(new Aviao((i+numAvioesAte)*2+1, (int) (Math.random() * 20 + 1)));
        }
    }
    public void inserirAvioesDecolagem(int sorteado)
    {
        for(int i=0;i<sorteado;i++)
        {
			if(pista[2].fDec.tamanho<=pista[1].fDec.tamanho)
				pista[2].fDec.inserir(new Aviao((i+numAvioesAte)*2));
            else if(pista[1].fDec.tamanho<=pista[0].fDec.tamanho)
                pista[1].fDec.inserir(new Aviao((i+numAvioesAte)*2));
            else
                pista[0].fDec.inserir(new Aviao((i+numAvioesAte)*2));
        }
    }
    public String resumoGeral()
    {
		resumo="\n\n\n+++++Conteúdos das Filas de Cada Pista+++++\n";
		for(int i=0;i<pista.length;i++)
			resumo+=pista[i].toString(i+1)+"\n";
        resumo+="++++++++++++++++++++++++++++++++++++++++++\n";
		resumo+="Até ao momento Decolaram: "+totalDecolados+" Aviões\n";
        media=(totalDecolados==0 ? totalDecolados : (tEsperaDec/totalDecolados));
	    resumo+="Tempo médio para Decolagem: "+media+" Segundos\n";
	    media = (totalAterrados==0 ? totalAterrados : (tEsperaAte/totalAterrados));
        resumo+="Até ao momento Aterraram: "+totalAterrados+" Aviões\n";
		resumo+="Tempo médio para Aterragem: "+media+" Segundos\n";
        resumo+="Número de Aviões que aterraram sem Reserva de Combustível: "+ateEmergentes+"\n\n\n";
		return resumo;
	}
    public void decrementarIncremUnidadeTempo()
    {
        pista[0].decrementarIncUnidadeTempo();
        pista[1].decrementarIncUnidadeTempo();
        pista[2].decrementarIncUnidadeTempo();
    }
    public void decolarAterrar()
    {   
        Aviao v=null,v1=null,v2=null;
        for(int i=pista.length-1;i>=0;i--)
        {   
            v1=pista[0].aviaoEmergente();
            v2=pista[1].aviaoEmergente();
            if(v1!=null)
            {    
                tEsperaAte+=v1.tempoEspera();
                totalAterrados++;
                ateEmergentes++;
                System.out.println("!!!!!!Emergência: O Avião "+v1.toString()+" Aterrou na Pista "+(i+1)+" Sem Reserva de Combustível<<");        
            }
            else if(v2!=null)
            {
                tEsperaAte+=v2.tempoEspera();
                totalAterrados++;
                ateEmergentes++;
                System.out.println("!!!!!!!Emergência: O Avião "+v2.toString()+" Aterrou na Pista "+(i+1)+" Sem Reserva de Combustível<<");
            }
            else
            {
                v = pista[i].decolarAterrar();
                if(v!=null)    
                    if(v.obterId()%2==0)
                    {
                        tEsperaDec+=v.tempoEspera();
                        totalDecolados++;
                        System.out.println("<<O Avião "+v.toString()+" Decolou da Pista "+(i+1)+">>");
                    }
                    else
                    {
                        tEsperaAte+=v.tempoEspera();
                        totalAterrados++;
                        System.out.println(">>O Avião "+v.toString()+" Aterrou na Pista "+(i+1)+"<<");
                    }  
                else
                    System.out.println("Pista "+(i+1)+" sem nenhum Avião nas Filas de Espera");
            }
        }
    }       
}
