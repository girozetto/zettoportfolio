package projectofp3;

public class Rastreio {
    private float temperatura;
    private float peso;
    private float pressaoArterial;
    private int estado;
    public Rastreio(float temperatura, float peso,float pressaoArterial,int estado)
    {
        this.temperatura=temperatura;
        this.peso=peso;
        this.pressaoArterial=pressaoArterial;
        this.estado=estado;
    }
    public String obterEstadoDescodificado(int estado)
    {
        if(estado==0)
            return "GRAVE";
        else if(estado==1)
            return "URGENTE";
        return "MODERADO";
    }
    public float obterTemperatura()
    {
        return temperatura;
    }
    public float obterPeso()
    {
        return peso;
    }
    public float obterPressaoArterial()
    {
        return pressaoArterial;
    }
    public int obterEstado()
    {
        return estado;
    }
    @Override
    public String toString()
    {
        String texto="[ Temperatura: "+obterTemperatura();
        texto += " Peso: "+obterPeso();
        texto += " Pressão Arterial: "+obterPressaoArterial();
        texto += " Estado: "+obterEstadoDescodificado(obterEstado())+"]";
        return texto;
    }
            
}
