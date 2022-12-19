
package projectofp3;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

public class Principal {
    private static Fila filaEntrada;
    private static Fila filaRastreados;
    private static Lista listaAtendidos;
    static JPanel jp;
    
    //Cidadão
    static String BI;
    static String nome;
    static String morada;
    static int idade;
    //Rastreio
    static float temperatura;
    static float peso;
    static float pressaoArterial;
    static int estado;
    //Menu Médico
    static TelaMedico menuMedico;
    //
    static MenuAtendimento telaAtendimento;
    static FicheiroMedico ficheiro = new FicheiroMedico();
    //Opções do Menu Principal 
    private static void darEntrada() 
    {
        while(true)
        {
            BI=JOptionPane.showInputDialog(null, "Insira o BI");
            nome=JOptionPane.showInputDialog(null, "Insira o Nome");
            morada=JOptionPane.showInputDialog(null, "Insira o Morada");
            idade=Integer.parseInt(JOptionPane.showInputDialog("Insira a Idade"));
            Paciente c=new Paciente(BI,nome,morada,idade,new Date());
            int op = Integer.parseInt(JOptionPane.showInputDialog("Paciente"+"\n"+c.obterInfo()+"\n"+"0 - Salvar"+"\n"+"1 - Reinserir Dados"+"\n"+"Outro - Cancelar e Sair"));
            if(op==0)
            {
                filaEntrada.inserirElemento(c);
                JOptionPane.showMessageDialog(null,"Paciente Cadastrado com Sucesso");
                break;
            }
            else if(op==1)
                JOptionPane.showMessageDialog(null,"Reinserindo os Dados...");
            else
            {
               break;
            }
        }
    }
    private static void realizarRastreio() 
    {
        if(filaEntrada.primeiro==null)
            JOptionPane.showMessageDialog(null,"A Fila está Vazia");
        else
        {
            while(true)
            {
                temperatura=Float.parseFloat(JOptionPane.showInputDialog(null, "Paciente A Rastrear"+"\n"+filaEntrada.primeiro.elemento.obterInfo()+"\n"+"Insira A Temperatura"));
                peso=Float.parseFloat(JOptionPane.showInputDialog(null, "Insira o Peso"));
                pressaoArterial=Float.parseFloat(JOptionPane.showInputDialog(null, "Insira a Pressão Arterial"));
                estado=Integer.parseInt(JOptionPane.showInputDialog("Estado"+"\n"+"0 - GRAVE"+"\n"+"1 - URGENTE"+"\n"+"Outro - MODERADO"));
                if(estado!=0)
                    if (estado!=1)
                        estado=2;
                Paciente p = filaEntrada.primeiro.elemento;
                Rastreio r = new Rastreio(temperatura,peso,pressaoArterial,estado);
                p.definirRastreio(r);
                int op = Integer.parseInt(JOptionPane.showInputDialog("Paciente Rastreado \n"+p.toString()+"0 - Salvar"+"\n"+"1 - Reinserir Dados"+"\n"+"Outro - Cancelar e Sair"));
                if(op==0)
                {
                    filaEntrada.primeiro.elemento.definirRastreio(r);
                    filaRastreados.inserirElemento(filaEntrada.removerElemento());
                    JOptionPane.showMessageDialog(null,"Paciente Rastreado Com Sucesso!!!");
                    break;
                }
                else if(op==1)
                    JOptionPane.showMessageDialog(null,"Reinserindo os Dados...");
                else
                   break;
            }
        }
    }
    private static void realizarPagamento() 
    {
        if(filaRastreados.primeiro==null)
            JOptionPane.showMessageDialog(null,"A Fila está Vazia");
        else
        {
            while(true)
            {
                int op=Integer.parseInt(JOptionPane.showInputDialog("Paciente A Pagar"+"\n"+filaRastreados.primeiro.elemento.toString()+"\n"+"0 - Pagar"+"\n"+"1 - Não tenho como pagar"+"\n"+"Outro - Cancelar e Sair"));
                if(op==0)
                {
                    if(filaRastreados.primeiro.elemento.obterRastreio()!=null)
                    {
                        filaRastreados.primeiro.elemento.definirPagamento(true);
                        filaRastreados.inserirPaciente(filaRastreados.removerElemento());
                        JOptionPane.showMessageDialog(null,"Pagamento Realizado");
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Precisa realizar antes o Rastreio...");
                    break;
                }
                //Caso não tenha como pagar basta apenas ir para o fim da fila para não atrasar a vida de outros
                else if(op==1)
                {
                    JOptionPane.showMessageDialog(null,"Próximo");
                    filaRastreados.inserirElemento(filaRastreados.removerElemento());
                }
                else
                   break;
            }
        }
    }
    
    private static void atendimento() throws IOException 
    {
        //Mostrar o Menu de Atendimento
       if(ficheiro.carregarLista()!=null)
       {
            if(filaRastreados.tamanhoGRAVE==0 && filaRastreados.tamanhoURGENTE==0 && filaRastreados.tamanhoMODERADO==0)
            {
                JOptionPane.showMessageDialog(null, "Fila Vazia - Nenhum Paciente Disponível");
            }
            else
            {
                telaAtendimento = new MenuAtendimento();
                telaAtendimento.setLocationRelativeTo(null);
                telaAtendimento.setVisible(true);
            }
       }
       else
           JOptionPane.showMessageDialog(null,"Não tem Médicos Disponíveis");
       
    }
    private static void listarAtendidos()
    {
        if(!listaAtendidos.ListaPacienteVazia())
        {
            JTextArea lista = new JTextArea(20,20);
            lista.setText(listaAtendidos.verPacienteLista());
            JOptionPane.showMessageDialog(null,new JScrollPane(lista));
        }
        else
            JOptionPane.showMessageDialog(null, "Ainda não foi Atendido qualquer Paciente");
    }
    private static void listarMedicos()
    {
        
        try {
            //Mostrar o Menu do Médicos
            menuMedico = new TelaMedico();
        } catch (IOException ex) {}
        menuMedico.setLocationRelativeTo(null);
        menuMedico.setVisible(true);
    }
    private static void fecho() 
    {
        
            if(filaRastreados.tamanhoGRAVE==0 && filaRastreados.tamanhoURGENTE==0 && filaRastreados.tamanhoMODERADO==0)
            {
                if(!listaAtendidos.ListaPacienteVazia())
                {
                    JTextArea lista = new JTextArea(20,20);
                    lista.setText("Foram Atendidos: "+listaAtendidos.tamanhoPacientes+" Pacientes"+"\n"+listaAtendidos.verPacienteLista());
                    JOptionPane.showMessageDialog(null,new JScrollPane(lista));
                    JOptionPane.showMessageDialog(null, "Adeus");
                    System.exit(0);
                }
                else
                    JOptionPane.showMessageDialog(null, "Não foi Atendido Algum Paciente");
            }
            else
                JOptionPane.showMessageDialog(null, "Ainda tem pacientes na Fila de Atendimento");     
    }
    //
    
    
    public static String menu()
    {
        String menu = "Menu Principal"+"\n";
        menu += "1 - Dar Entrada" + "\n";
        menu += "2 - Rastreio" + "\n";
        menu += "3 - Pagamento" + "\n";
        menu += "4 - Atendimento" + "\n";
        menu += "5 - Listar Atendidos" + "\n";
        menu += "6 - Listar Medicos" + "\n";
        menu += "9 - Fecho" + "\n";
        menu += "0 - Sair";
        return menu;
    }
    //Escolha das operações do Menu Principal
    public static void backMenu(int op) throws IOException
    {    
        switch(op){
            case 1:
                darEntrada();
                break;
            case 2:
                realizarRastreio();
                break;
            case 3:
                realizarPagamento();
                break;
            case 4:
                atendimento();
                break;
            case 5:
                listarAtendidos();
                break;
            case 6:
                listarMedicos();
                break;
            case 9:
                fecho();
                break;    
            case 0:
                JOptionPane.showMessageDialog(null,"Saindo do Programa");
                System.exit(0);
                break;
            default:
                JOptionPane.showMessageDialog(null,"Opcao Invalida - Tente Novamente");
                break;
        }
    }
    static class MenuAtendimento extends JFrame
    {
        private JScrollPane rolagem,rolarTextArea;
        private JTextArea areaTexto;
        private JLabel labelMedico;
        private JList listaVista;
        private JPanel painel;
        private JButton botaoAtender,botaoCancelar;
        Medico medicoSelecionado=null;
        Lista listaMedicos = new Lista();
        
        public MenuAtendimento()
        {
            super("Tela de Atendimento");
            areaTexto = new JTextArea(20,20);
            areaTexto.setText("Informação Paciente");
            painel = new JPanel();
            ficheiro = new FicheiroMedico();
            rolagem = new JScrollPane();
            labelMedico = new JLabel("Médico");
            listaVista = new JList();
            botaoAtender = new JButton("Atender");
            botaoCancelar = new JButton("Cancelar");
            botaoAtender.addActionListener((ActionEvent e) -> {
                Atender();
            });
            rolarTextArea = new JScrollPane(areaTexto);
            
            botaoCancelar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    }
            });
            painel.setLayout(new GridLayout(1,2));
            painel.add(botaoAtender);
            painel.add(botaoCancelar);
            listaVista.setModel(new AbstractListModel<String>() {
            @Override
            public int getSize() { return listaMedicos.tamanho; }
            @Override
            public String getElementAt(int i) { return listaMedicos.obterMedicoPorPosicao(i).toString(); }});
            listaVista.addListSelectionListener((ListSelectionEvent evt) -> {
                   medicoSelecionado = listaMedicos.obterMedicoPorPosicao(listaVista.getSelectedIndex());
                   labelMedico.setText("Médico: "+medicoSelecionado.obterNome());
            });
            rolagem.setViewportView(listaVista);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            getContentPane().setLayout(new GridLayout(4, 1));
            getContentPane().add(rolarTextArea);
            getContentPane().add(rolagem);
            getContentPane().add(labelMedico);
            getContentPane().add(painel);
            try {
                if(ficheiro.carregarLista()!=null)
                    listaMedicos = ficheiro.carregarLista();
            } catch (IOException ex) {}
            if(filaRastreados.tamanhoGRAVE != 0)
                areaTexto.setText(filaRastreados.primeiroNoGRAVE.elemento.toString());
            else if(filaRastreados.tamanhoURGENTE !=0)
                areaTexto.setText(filaRastreados.primeiroNoURGENTE.elemento.toString());
            else if (filaRastreados.tamanhoMODERADO != 0)
                areaTexto.setText(filaRastreados.primeiroNoMODERADO.elemento.toString());
            setSize(350,250);
        }
        private void Atender()
        {
            if(medicoSelecionado!=null)
            {
                if(filaRastreados.tamanhoGRAVE != 0)
                {
                    filaRastreados.primeiroNoGRAVE.elemento.definirMedico(medicoSelecionado);
                    filaRastreados.primeiroNoGRAVE.elemento.definirTFinal(System.currentTimeMillis());
                    listaAtendidos.inserirPacienteNoFim(filaRastreados.removerGRAVE());  
                }
                else if(filaRastreados.tamanhoURGENTE !=0)
                {
                    filaRastreados.primeiroNoURGENTE.elemento.definirMedico(medicoSelecionado);
                    filaRastreados.primeiroNoURGENTE.elemento.definirTFinal(System.currentTimeMillis());
                    listaAtendidos.inserirPacienteNoFim(filaRastreados.removerURGENTE()); 
                }
                else if (filaRastreados.tamanhoMODERADO != 0)
                {
                    filaRastreados.primeiroNoMODERADO.elemento.definirMedico(medicoSelecionado);
                    filaRastreados.primeiroNoMODERADO.elemento.definirTFinal(System.currentTimeMillis());
                    listaAtendidos.inserirPacienteNoFim(filaRastreados.removerMODERADO());
                }
                JOptionPane.showMessageDialog(null,"O Paciente foi Atendido ");
                dispose();
            }
            else
                JOptionPane.showMessageDialog(null,"Nenhum Médico Selecionado");
        }
    }
    
    public static void main(String[] args) throws IOException
    {     
        boolean medico = false;
        boolean atendimento=false;
        filaEntrada = new Fila();
        filaRastreados = new Fila();
        listaAtendidos = new Lista();
        menuMedico = new TelaMedico();
        telaAtendimento = new MenuAtendimento();
        System.out.println("Seja Bem-Vindo - Carregando...");
        filaEntrada=new Fila();
        try {Thread.sleep(2500);}
        catch (Exception ex) {}
        while(true)
        {
            if(!menuMedico.isVisible() && !telaAtendimento.isVisible())
            {
                int op=Integer.parseInt(JOptionPane.showInputDialog(menu()));
                    backMenu(op);
            }
            
        }
    }
}
