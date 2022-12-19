public interface TransporteCarga
{
	//Carregar a mercadoria para um transporte pessoal tendo ou não
	void carregar(Item item);
	//Descarregar a mercadoria
	void descarregar();
}