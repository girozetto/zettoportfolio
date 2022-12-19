
package projectofp3;

import java.io.IOException;
import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class TelaMedico extends JFrame {
        //Componentes Visuais
        private JRadioButton adicionar;
        private JButton botaoAcao;
        private JButton botaoCancelar;
        private ButtonGroup grupo;
        private JTextField campoEspecialidade;
        private JTextField campoNome;
        private JRadioButton editar;
        private JRadioButton eliminar;
        private JLabel jLabel1;
        private JLabel jLabel2;
        private JPanel painel1;
        private JPanel painel2;
        private JPanel painel3;
        private JPanel painel4;
        private JScrollPane jScrollPane2;
        private JList<String> listaVista;
    
   
        Medico medico;
        Lista lista = new Lista();
        FicheiroMedico ficheiro;
    public TelaMedico() throws IOException{
           super("Menu Médico");
            medico=null;
            ficheiro = new FicheiroMedico();
            if(ficheiro.carregarLista()!=null)
                lista = ficheiro.carregarLista();
        
        iniciarComponentes();
        
        setSize(350,250);
    }
    
    private void iniciarComponentes() {

        grupo = new ButtonGroup();
        painel4 = new JPanel();
        adicionar = new JRadioButton();
        editar = new JRadioButton();
        eliminar = new JRadioButton();
        jScrollPane2 = new JScrollPane();
        listaVista = new JList<>();
        painel1 = new JPanel();
        jLabel1 = new JLabel();
        campoNome = new JTextField();
        painel2 = new JPanel();
        jLabel2 = new JLabel();
        campoEspecialidade = new JTextField();
        painel3 = new JPanel();
        botaoAcao = new JButton();
        botaoCancelar = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(5, 1));

        adicionar.setSelected(true);
        adicionar.setText("Novo Médico");
        adicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarActionPerformed(evt);
            }
        });
        painel4.add(adicionar);

        editar.setText("Atualizar");
        editar.setToolTipText("");
        editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarActionPerformed(evt);
            }
        });
        painel4.add(editar);

        eliminar.setText("Eliminar");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });
        painel4.add(eliminar);

        getContentPane().add(painel4);

        listaVista.setModel(new javax.swing.AbstractListModel<String>() {
            @Override
            public int getSize() { return lista.tamanho; }
            @Override
            public String getElementAt(int i) { return lista.obterMedicoPorPosicao(i).toString(); }
        });
        listaVista.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaVistaValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(listaVista);

        getContentPane().add(jScrollPane2);

        painel1.setLayout(new java.awt.GridLayout(1, 2));

        jLabel1.setText("Nome");
        painel1.add(jLabel1);
        painel1.add(campoNome);

        getContentPane().add(painel1);

        painel2.setLayout(new java.awt.GridLayout(1, 2));

        jLabel2.setText("Especialidade");
        painel2.add(jLabel2);
        painel2.add(campoEspecialidade);

        getContentPane().add(painel2);

        painel3.setLayout(new java.awt.GridLayout(1, 2));

        botaoAcao.setText("Adicionar");
        botaoAcao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAcaoActionPerformed(evt);
            }
        });
        painel3.add(botaoAcao);

        botaoCancelar.setText("Cancelar");
        botaoCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCancelarActionPerformed(evt);
            }
        });
        painel3.add(botaoCancelar);

        getContentPane().add(painel3);
        grupo.add(adicionar);
        grupo.add(editar);
        grupo.add(eliminar);
        

        pack();
    }
    private void listaVistaValueChanged(javax.swing.event.ListSelectionEvent evt) {                                        
       try
       {
            if(editar.isSelected())
            {
                campoNome.setText(lista.obterMedicoPorPosicao(listaVista.getSelectedIndex()).obterNome());
                campoEspecialidade.setText(lista.obterMedicoPorPosicao(listaVista.getSelectedIndex()).obterEspecialidade());
            }
       }catch(NullPointerException ex){}
    }                                       

    private void botaoAcaoActionPerformed(java.awt.event.ActionEvent evt) {                                          
        
        if(adicionar.isSelected())
        {
            if(!campoNome.getText().isEmpty())
                    if(!campoEspecialidade.getText().isEmpty())
                    {
                        int id;
                        if(lista.ListaMedicoVazia())
                            id=1;
                        else
                            id=lista.ultimoMedico.elemento.obterId()+1;
                        
                        medico=new Medico(id,campoNome.getText(),campoEspecialidade.getText());
                        lista.inserirMedicoNoFim(medico);
                        ficheiro=new FicheiroMedico(lista);
                        try {
                            ficheiro.salvarLista();
                        } 
                        catch (IOException ex) {}
                        try {
                    lista=ficheiro.carregarLista();
                            } catch (IOException ex) {
                                  } 
                            listaVista.setModel(new AbstractListModel<String>() {
                                @Override
                                public int getSize() { return lista.tamanho; }
                                @Override
                                public String getElementAt(int i) { return lista.obterMedicoPorPosicao(i).toString(); }
                            });
                            JOptionPane.showMessageDialog(null,"Um novo Médico entrou em Serviço!!!");
                            campoNome.setText("");
                            campoEspecialidade.setText("");
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Campo Especialidade Vazio!!!");
                else
                    JOptionPane.showMessageDialog(null,"Campo Nome Vazio!!!");
        }
        else if(editar.isSelected())
        {
            if(!listaVista.isSelectionEmpty())
            {
                if(!campoNome.getText().isEmpty())
                    if(!campoEspecialidade.getText().isEmpty())
                    {
                        int id = lista.obterMedicoPorPosicao(listaVista.getSelectedIndex()).obterId();
                            medico=new Medico(id,campoNome.getText(),campoEspecialidade.getText());
                            lista.editarMedicoID(medico);
                            ficheiro=new FicheiroMedico(lista);
                            try {
                                ficheiro.salvarLista();
                            } 
                            catch (IOException ex) {}
                            try {
                            lista=ficheiro.carregarLista();
                            } catch (IOException ex) {
                                  } 
                            listaVista.setModel(new javax.swing.AbstractListModel<String>() {
                                @Override
                                public int getSize() { return lista.tamanho; }
                                @Override
                                public String getElementAt(int i) { return lista.obterMedicoPorPosicao(i).toString(); }
                            });
                            JOptionPane.showMessageDialog(null,"Médico Editado com Sucesso!!!");
                            campoNome.setText("");
                            campoEspecialidade.setText("");
                       }
                    else
                        JOptionPane.showMessageDialog(null,"Campo Especialidade Vazio!!!");
                else
                    JOptionPane.showMessageDialog(null,"Campo Nome Vazio!!!");
            }
            else
                JOptionPane.showMessageDialog(null,"Nada foi Selecionado!!!");
        }
        else
        {
            
            if(!listaVista.isSelectionEmpty())
            {    
                JOptionPane.showMessageDialog(null,"Médico Eliminado"+"\n"+lista.removerMedicoPorID(lista.obterMedicoPorPosicao(listaVista.getSelectedIndex()).obterId()).toString());
                ficheiro=new FicheiroMedico(lista);
                            try {
                                ficheiro.salvarLista();
                            } 
                            catch (IOException ex) {}
                listaVista.removeAll();
                try {
                    lista=ficheiro.carregarLista();
                } catch (IOException ex) {
                      } 
                listaVista.setModel(new javax.swing.AbstractListModel<String>() {
                    @Override
                    public int getSize() { return lista.tamanho; }
                    @Override
                    public String getElementAt(int i) { return lista.obterMedicoPorPosicao(i).toString(); }
                });
            }
            else
                JOptionPane.showMessageDialog(null,"Nada foi Selecionado para Remover!!!");
            campoNome.setText("");
            campoEspecialidade.setText("");
        }
        
    }                                         

    private void botaoCancelarActionPerformed(java.awt.event.ActionEvent evt) {                                              
        dispose();
    }                                             

    private void adicionarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        botaoAcao.setText("Adicionar");
        campoNome.setText("");
        campoEspecialidade.setText("");
    }                                         

    private void editarActionPerformed(java.awt.event.ActionEvent evt) {                                       
        botaoAcao.setText("Editar");
        campoNome.setText("");
        campoEspecialidade.setText("");
    }                                      

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {                                         
        botaoAcao.setText("Eliminar");
    }                                        

                        
    }