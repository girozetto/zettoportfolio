import java.util.ArrayList;

public class EncontraCaminho {
    Jogo jogo;
    No[][] no;
    ArrayList<No> listaAberta = new ArrayList<>();
    public ArrayList<No> listaCaminho = new ArrayList<>();
    No comeco , objetivo, actual;
    boolean encontrado = false;
    int passo =0;
    public EncontraCaminho(Jogo jogo){
        this.jogo=jogo;
        instanciarNos();
    }
    public void instanciarNos()
    {
        no = new No[jogo.getMapa().getMapa().length][jogo.getMapa().getMapa()[0].length];
        for(int i = 0;i<jogo.getMapa().getMapa().length;i++)
            for(int j = 0;i<jogo.getMapa().getMapa()[0].length;j++) no[i][j] = new No(i,j);
    }
    public void reiniciar()
    {
        for(int i = 0;i<jogo.getMapa().getMapa().length;i++)
            for(int j = 0;i<jogo.getMapa().getMapa()[0].length;j++){ 
                no[i][j].abrir=false;
                no[i][j].checado=false;
                no[i][j].solido=false;
            }
        listaAberta.clear();
        listaCaminho.clear();
        encontrado=false;
        passo=0;
    }
    public void setNos(int colInicio, int linInicio, int objCol, int objLin, Carro carro)
    {
        reiniciar();
        comeco = no[linInicio][colInicio];
        actual = comeco;
        objetivo = no[objLin][objCol];
        for(int i = 0;i<jogo.getMapa().getMapa().length;i++)
            for(int j = 0;i<jogo.getMapa().getMapa()[0].length;j++){ 
                no[i][j].solido = jogo.getMapa().getRecursos().get(jogo.getMapa().getMapa()[i][j]).colisao;
                obterCusto(no[i][j]);
            } 
    }
    private void obterCusto(No no) {
        no.gCusto = Math.abs(no.col-comeco.col)+Math.abs(no.lin-comeco.lin);
        no.hCusto = Math.abs(no.col-objetivo.col)+Math.abs(no.lin-objetivo.lin);
        no.fCusto = no.gCusto+no.hCusto;
    }
    private boolean procura(){
        while(encontrado && passo<500){

        }
        return true;
    }
    private void abrirNo(No no){
        if(!no.abrir && !no.checado && !no.solido){

        }
    }
}
