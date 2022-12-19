
package projectofp3;
public class Fila
{
	public No<Paciente> primeiro = new No<>();
	public No<Paciente> ultimo = new No<>();
        public int tamanho;
        No<Paciente> primeiroNoGRAVE = new No<>();
        No<Paciente> ultimoNoGRAVE = new No<>();
        int tamanhoGRAVE = 0;
        No<Paciente> primeiroNoURGENTE = new No<>();
        No<Paciente> ultimoNoURGENTE = new No<>();
        int tamanhoURGENTE = 0;
        No<Paciente> primeiroNoMODERADO = new No<>();
        No<Paciente> ultimoNoMODERADO = new No<>();
        int tamanhoMODERADO = 0;
	public Fila()
        {
            primeiro=null;
            ultimo=null;
            primeiroNoGRAVE=null;
            ultimoNoGRAVE=null;
            primeiroNoURGENTE=null;
            ultimoNoURGENTE=null;
            primeiroNoMODERADO=null;
            ultimoNoMODERADO=null;
            tamanho=0;
        }
        
        public void inserirPaciente(Paciente p)
        {
            No<Paciente> no = new No();
            no.elemento=p;
            no.proximo=null;
            if(p.obterRastreio().obterEstado()==0)
            {
                
                if(primeiroNoGRAVE==null)
                    primeiroNoGRAVE=no;
                else
                    ultimoNoGRAVE.proximo=no;
                ultimoNoGRAVE=no;
                tamanhoGRAVE++;
            }
            else if(p.obterRastreio().obterEstado()==1)
            {
                if(primeiroNoURGENTE==null)
                    primeiroNoURGENTE=no;
                else
                    ultimoNoURGENTE.proximo=no;
                ultimoNoURGENTE=no;
                tamanhoURGENTE++;
            }
            else
            {
                if(primeiroNoMODERADO==null)
                    primeiroNoMODERADO=no;
                else
                    ultimoNoMODERADO.proximo=no;
                ultimoNoMODERADO=no;
                tamanhoMODERADO++;
            }
        }
        public Paciente removerGRAVE()
        {
            No<Paciente> removido=null;
            if(primeiroNoGRAVE==null)
                ultimoNoGRAVE=null;
            else
            {
               removido=primeiroNoGRAVE;
               primeiroNoGRAVE=primeiroNoGRAVE.proximo;
               tamanhoGRAVE--;
               if(tamanhoGRAVE==0)
                    ultimoNoGRAVE=null;
            }
            return removido.elemento;
        }
        public Paciente removerURGENTE()
        {
            No<Paciente> removido=null;
            if(primeiroNoURGENTE==null)
                ultimoNoURGENTE=null;
            else
            {
               removido=primeiroNoURGENTE;
               primeiroNoURGENTE=primeiroNoURGENTE.proximo;
               tamanhoURGENTE--;
               if(tamanhoURGENTE==0)
                    ultimoNoURGENTE=null;
            }
            return removido.elemento;
        }
        public Paciente removerMODERADO()
        {
            No<Paciente> removido=null;
            if(primeiroNoMODERADO==null)
                ultimoNoMODERADO=null;
            else
            {
               removido=primeiroNoMODERADO;
               primeiroNoMODERADO=primeiroNoMODERADO.proximo;
               tamanhoMODERADO--;
               if(tamanhoMODERADO==0)
                    ultimoNoMODERADO=null;
            }
            return removido.elemento;
        }
	
        
	public void inserirElemento(Paciente paciente)
	{
            No<Paciente> no = new No();
            no.elemento=paciente;
            no.proximo=null;
            if(primeiro==null)
                primeiro=no;
            else
                ultimo.proximo=no;
            ultimo = no;
            tamanho++;
	}
        
	public Paciente removerElemento()
	{
             No<Paciente> removido;
		if(primeiro==null)
                    removido=null;
		else
		{
                    removido=primeiro;
                    primeiro=primeiro.proximo;
                    tamanho--;
                    if(tamanho==0)
                        ultimo=null;
		}
                return removido.elemento;
	}
	
}
