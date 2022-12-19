package projectofp3;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Paciente {
    private Rastreio rastreio=null;
    private boolean pagamento=false;
    private Medico medico=null;
    private String BI;
    private String nome;
    private String morada;
    private int idade;
    private String data;
    private String hora;
    private long tInicial;
    private long tFinal;
    
    
    public Paciente(String BI,String nome,String morada,int idade,Date dataEntrada)
    {
        SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
        this.data=data.format(dataEntrada);
        data = new SimpleDateFormat("HH:mm");
        this.hora=data.format(dataEntrada);
        tInicial = System.currentTimeMillis();
        
        
        this.BI = BI;
        this.nome = nome;
        this.morada = morada;
        this.idade = idade;
    }
    
    public String obterBI()
    {
        return BI;
    }
    public String obterNome()
    {
        return nome;
    }
    public String obterMorada()
    {
        return morada;
    }
    public int obterIdade()
    {
        return idade;
    }
    public String obterData()
    {
        
        return data;
    }
    public String obterHora()
    {
        
        return hora;
    }
    public long obterTinicial()
    {
        return tInicial;
    }
    public String obterInfo()
    {
        String texto = "BI: "+obterBI()+"\n";
        texto += "Nome: "+obterNome()+"\n";
        texto += "Morada: "+obterMorada()+"\n";
        texto += "Idade: "+obterIdade()+"\n";
        texto += "Data: "+obterData()+" Hora: "+obterHora()+"\n";
        return texto;
    }
    public void definirMedico(Medico medico)
    {
        this.medico=medico;
    }
    public void definirRastreio(Rastreio rastreio)
    {
        this.rastreio=rastreio;
    }
    public void definirPagamento(boolean pagamento)
    {
        this.pagamento=pagamento;
    }
    public void definirTFinal(long tFinal)
    {
        this.tFinal=tFinal;
    }
    
    public Rastreio obterRastreio()
    {
        return rastreio;
    }
    public Medico obterMedico()
    {
        return medico;
    }
    public boolean obterPagamento()
    {
        return pagamento;
    }
    public String obterTempoEspera()
    {
        long miliS,h,m,s;
        miliS=(tFinal-obterTinicial())/1000;
        h=miliS/3600;
        m=(miliS%3600)/60;
        s=miliS%3600%60;
        return h+" Horas : "+m+" Minutos : "+s+" Segundos";
    }
    public String fichaMedica()
    {
        String texto="Ficha Médica"+"\n";
        texto += "Nome: "+obterNome()+"\n";
        texto += "Estado: "+obterRastreio().obterEstadoDescodificado(obterRastreio().obterEstado())+"\n";
        texto += "Médico: "+obterMedico().obterNome()+"\n";
        texto += "Tempo de Espera: "+obterTempoEspera()+"\n";
        return texto;
    }
    @Override
    public String toString()
    {
        String texto = " Info Paciente "+"\n";
        texto += obterInfo()+"\n";
        texto += " Rastreio "+"\n";
        texto += obterRastreio().toString()+"\n";
        texto += " Pagamento:  "+(obterPagamento()? "PAGO":"NÃO PAGO")+"\n";
        return texto;
    }
}
