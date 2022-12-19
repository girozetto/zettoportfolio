
package projectofp3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;

public class FicheiroMedico {
    private Lista lista;
    private Formatter saida;
    private final String ficheiro="listaMedicos.txt";
    public FicheiroMedico(Lista lista)
    {
        this.lista=lista;
    }
    public FicheiroMedico()
    {
    }
    public Lista obterLista()
    {
        return lista;
    }
    public void salvarLista() throws IOException
    {
        File f = new File(ficheiro);
        if(!f.exists())
            f.createNewFile();
        else
        {
            f.delete();
            f.createNewFile();
        }
        saida= new Formatter(f);
        No<Medico> i=lista.primeiroMedico;
        while(i!=null)
        {
            saida.format("%s %s %s "+"\n",""+i.elemento.obterId(),i.elemento.obterNome().replaceAll(" ", "-"),i.elemento.obterEspecialidade().replaceAll(" ", "-") );
            i=i.proximo;
        }  
        saida.close();
    }
    public Lista carregarLista() throws FileNotFoundException, IOException
    {
        Lista lista = new Lista();
        File f = new File(ficheiro);
        if(!f.exists())
            f.createNewFile();
        Scanner entrada = new Scanner(f);
        while(entrada.hasNext())
            lista.inserirMedicoNoFim(new Medico(entrada.nextInt(),entrada.next().replaceAll("-", " "),entrada.next().replaceAll("-", " ")));
        return lista;
    }
    
    /*public void salvarLista() throws IOException
    {
        File f = new File(ficheiro);
        if(!f.exists())
            f.createNewFile();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(obterLista());
        oos.close();
    }
    */
    /*public Lista carregarLista() throws IOException, ClassNotFoundException
    {
        File f = new File(ficheiro);
        if(!f.exists())
            return null;
        if(!f.canRead())
        {
            f.setReadable(true);
        }
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
        //ArrayList<Lista> al =(ArrayList<Lista>)ois.readObject();
        Lista l=(Lista)ois.readObject();
        ois.close();
        return l;
    }*/
}
