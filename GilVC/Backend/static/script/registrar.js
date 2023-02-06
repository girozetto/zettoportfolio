$("#aviso").hide()
function mostrar(msg,cor="red")
{
    $("#aviso").css("color",cor)
    $("#aviso").html(msg);
    $("#aviso").show();
    setTimeout(()=>{$("#aviso").hide();},3000);
}
$("#btn-cadastrar").click(()=>{
    if($("#telefone").val()=="" || $("#senha").val()=="" || $("#nome").val()=="")
        mostrar("Há campos vazios");
    else if($("#nome").val().split(" ").length < 2 )
        mostrar("Precisa ter no mínimo 2 nomes");
    else if(isNaN(parseInt($("#telefone").val())) || $("#telefone").val().length!=9)
        mostrar("Coloque um número de telemóvel válido");
    else if($("#senha").val()!=$("#confsenha").val())
        mostrar("As senhas são diferentes");
    else {
        $.ajax(
            {
                url: 'http://127.0.0.1:5000/cadastrarConta',
                type: 'POST',
                data:{nome:$("#nome").val().split(" ").map((n)=>{n[0]=n[0].toUpperCase();return n;}).join(" "),telefone:$("#telefone").val(),senha:$("#senha").val(),foto:$("#foto").val()},
                success: (resposta)=>{
                    if(resposta.conta){
                        if(resposta.conta.erro)
                            mostrar(resposta.conta.erro);
                        else{
                            mostrar("A conta foi cadastrada com sucesso","green");
                            setTimeout(() => {
                                window.location.href = 'login.html'
                            }, 3000)
                        }
                    }
                    else{
                        mostrar("Alguns campos estão vazios");
                    }

                },
                error: (e)=>{
                    mostrar("Erro no Servidor ao Cadastrar: "+e.toString())
                }
            }
        );
    }
    
});