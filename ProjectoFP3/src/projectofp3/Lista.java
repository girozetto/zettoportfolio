package projectofp3;

public class Lista {
    No<Medico> primeiroMedico=new No<>();
    No<Medico> ultimoMedico=new No<>();
    int tamanho=0;
    No<Paciente> primeiroPaciente= new No<>();
    No<Paciente> ultimoPaciente= new No<>();
    int tamanhoPacientes=0;
    public Lista()
    {
        primeiroMedico=null;
        ultimoMedico=null;
        primeiroPaciente=null;
        ultimoPaciente=null;
    }
    public boolean ListaMedicoVazia()
    {
        return tamanho==0;
    }
    public boolean ListaPacienteVazia()
    {
        return tamanhoPacientes==0;
    }
    public void inserirMedicoNoFim(Medico medico)
    {
        No<Medico> no = new No<>();
        no.elemento=medico;
        no.proximo=null;
        if(ListaMedicoVazia())
            primeiroMedico=ultimoMedico=no;
        else
        {
            ultimoMedico.proximo=no;
            ultimoMedico=no;
        }
        tamanho++;
    }
    public void inserirPacienteNoFim(Paciente paciente)
    {
        No<Paciente> no = new No<>();
        no.elemento=paciente;
        no.proximo=null;
        if(ListaPacienteVazia())
            primeiroPaciente=ultimoPaciente=no;
        else
        {
            ultimoPaciente.proximo=no;
            ultimoPaciente=no;
        }
        tamanhoPacientes++;
    }
    public No<Medico> removerMedicoInicio()
    {
        if(ListaMedicoVazia())
            return null;
        No<Medico> removido=primeiroMedico;
        primeiroMedico=primeiroMedico.proximo;
        return removido;
    }
    public Paciente removerPacienteInicio()
    {
        if(ListaPacienteVazia())
            return null;
        No<Paciente> removido=primeiroPaciente;
        primeiroPaciente=primeiroPaciente.proximo;
        return removido.elemento;
    }
    public Medico removerMedicoPorID(int id)
    {    
        No<Medico> removido=new No<>();
        if(ListaMedicoVazia())
            return null;
        else
        {
            
            if(primeiroMedico.elemento.obterId()==id)
            {
                removido=removerMedicoInicio();
            }
            else
            {
                No<Medico> no=primeiroMedico;
                boolean encontrado=false;
                No<Medico> anterior = no;
                no=no.proximo;
                while(no!=null && !encontrado)
                {
                    if(no.elemento.obterId() == id)
                        encontrado=true;
                    else
                    {
                        anterior=no;
                        no = no.proximo;
                    }
                }
                if(encontrado)
                {
                    removido = no;
                    anterior.proximo=no.proximo;
                    tamanho--;
                }
            }
        } 
        return removido.elemento;
    }
    public boolean editarMedicoID(Medico editado)
    {    
        if(ListaMedicoVazia())
            return false;
        else
        {
                No<Medico> no=primeiroMedico;
                boolean encontrado=false;
                while(no!=null && !encontrado)
                {
                    if(no.elemento.obterId() == editado.obterId())
                        encontrado=true;
                    else
                        no = no.proximo;
                }
                if(encontrado)
                {
                    no.elemento.definirNome(editado.obterNome());
                    no.elemento.definirEspecialidade(editado.obterEspecialidade());
                }
        }     
        return true;
    }
    public Medico obterMedicoPorPosicao(int pos)
    {    
        No<Medico> obtido=new No<>();
        int cont=0;
        if(ListaMedicoVazia())
            return null;
        else
        {
            if(pos>=0)
            {
                No<Medico> no=primeiroMedico;
                boolean encontrado=false;
                while(no!=null && !encontrado)
                {
                    if(pos == cont)
                    {
                        encontrado=true;
                    }
                    else
                    {
                        cont++;
                        no = no.proximo;
                    }
                }
                if(encontrado)
                {
                    obtido=no;
                }
            }
        }     
        return obtido.elemento;
    }
    
    public String verPacienteLista()
    {
        No<Paciente> i=primeiroPaciente;
        String texto="";
        while(i!=null)
        {
            texto += i.elemento.fichaMedica();
            texto += "______________________________________________"+"\n";
            i=i.proximo;
        }
        return texto;
    }
}
