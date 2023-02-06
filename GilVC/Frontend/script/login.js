$("#aviso").css("color","red")
$("#aviso").hide()
$("#btn-logar").click(()=>{
    if($("#telefone").val()=="" || $("#senha").val()=="")
        mostrar("Há campos vazios");
    else
    $.ajax(
        {
            url: 'http://127.0.0.1:5000/logarConta',
            type: 'POST',
            data:{telefone:$("#telefone").val(),senha:$("#senha").val()},
            success: (resposta)=>{
                console.table(resposta);
                if(resposta.msg)
                    mostrar("O telefone ou a senha estão incorrectas");
                else if(resposta.conta)
                {
                    mostrar("Iniciando a Sessão...","green");
                    sessionStorage.setItem("idConta",""+resposta.conta.id);
                    window.location.href = "home.html";
                }   
            },
            error: (e)=>{
                mostrar("Houve um erro no Servidor ao Logar: "+e.toString());
            }
        }
    );
});

function mostrar(msg,cor="red")
{
    $("#aviso").css("color",cor)
    $("#aviso").html(msg);
    $("#aviso").show();
    setTimeout(()=>{$("#aviso").hide();},5000);
}