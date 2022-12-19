import java.util.Formatter;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
public class Pista {
    Fila fDec,prat1,prat2;
	public Pista()
    {
        fDec = new Fila();
        prat1 = new Fila();
        prat2 = new Fila();
    }
    public void inserirConteudoFicheiro(String nomeF,String conteudo) throws IOException
    {
        File ficheiro = new File(nomeF);
        if(ficheiro.exists())
			ficheiro.delete();
        ficheiro.createNewFile();
        try(FileWriter escrever = new FileWriter(nomeF))
        {
        	escrever.write(conteudo);
        }catch(IOException e){}
        
    }
	public void salvarConteudo(int num) throws IOException
    {
        File f = new File("Pista "+num);
        if(!f.exists())
            f.mkdir();
        inserirConteudoFicheiro("Pista "+num+"/prateleira1.txt", prat1.toString());
        inserirConteudoFicheiro("Pista "+num+"/prateleira2.txt", prat2.toString());
        inserirConteudoFicheiro("Pista "+num+"/filaDecolagem.txt", fDec.toString());
    }
    public void decrementarIncUnidadeTempo()
    {
        prat1.decrementarInstante();
        prat2.decrementarInstante();
        prat1.incrementarTempo();
        prat2.incrementarTempo();
        fDec.incrementarTempo();
    }
    public void inserirPrateleira(Aviao a)
    {
        if(prat1.tamanho>prat2.tamanho)
            prat2.inserir(a);
        else
            prat1.inserir(a);
    }
    public Aviao decolarAterrar()
    {
        if(fDec.tamanho < prat1.tamanho+prat2.tamanho)
            return prat1.tamanho>prat2.tamanho ? prat1.remover() : prat2.remover();
        return fDec.remover();
    }
    public Aviao aviaoEmergente()
    {
        Aviao a = prat1.remocaoEmergente();
        if(a!=null)
            return a;
		return prat2.remocaoEmergente();
    }
    public String toString(int numero)
    {
        String str="";
        str+="--------Pista "+numero+"--------\n";
        str+="********Prateleira de Aterragem 1*********\n";
        str+=prat1.toString();
        str+="********Prateleira de Aterragem 2*********\n";
        str+=prat2.toString();
        str+="*******Fila de Espera para Decolagem******\n";
        str+=fDec.toString();
        str+="------------------------------------------------\n";
        return str;
    }
}
