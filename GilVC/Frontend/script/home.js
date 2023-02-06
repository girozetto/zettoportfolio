function mostrar(msg,num,cor="red")
{
    $("#aviso"+num).css("color",cor)
    $("#aviso"+num).html(msg);
    $("#aviso"+num).show();
    setTimeout(()=>{$("#aviso"+num).hide();},6000);
}
function verificarExistencia(idsala){
    $.ajax(
        {
            url: 'http://127.0.0.1:5000/verificarSala',
            type: 'POST',
            data:{idsala:idsala},
            success: (resposta)=>{
                return resposta.existe;
            },
            error: (e)=>{
                return false;
            }
        }
    );
}
$("#criarbtn").click((e)=>{
    if($("#campocriar").val().trim()=="")
        mostrar(" Campos vazios ",1);
    else if($("#campocriar").val().length<6)
        mostrar(" O nome precisa ter no mínimo 6 caracteres",1);
    else if($("#campocriar").val().split(" ").length>1)
        mostrar(" Não deve ter espaços em branco ",1);
    else
        if(!verificarExistencia($("#campocriar").val()))
        {
            mostrar("Criando a Sala "+$("#campocriar").val(),1,"green");
            sessionStorage.setItem("idSala",$("#campocriar").val());
            $("#campocriar").val("");
            setTimeout(()=>{window.location.href = "index.html";},3000);
        }
        else
            mostrar("A sala "+$("#campocriar").val()+" já existe !!!",1);
});
$("#entrarbtn").click((e)=>{
    if(verificarExistencia($("#campoentrar").val()))
    {
        mostrar("Entrando na Sala "+$("#campoentrar").val(),2,"green");
        sessionStorage.setItem("idSala",$("#campoentrar").val());
        $("#campoentrar").val("");
        setTimeout(()=>{window.location.href = "index.html";},3000);
    }
    else
        mostrar("A Sala "+$("#campoentrar").val()+" Não existe, Tente criar uma nova sala",2);
});
$("#deslogar").click((e)=>{
    e.preventDefault();
    sessionStorage.removeItem("idConta");
    sessionStorage.removeItem("idSala");
    setTimeout(()=>{window.location.href = "login.html";},3000);
});