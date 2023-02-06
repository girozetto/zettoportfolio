function verificarSala()
{
    return sessionStorage.getItem("idSala");
}
function verificarConta()
{
    return sessionStorage.getItem("idConta");
}
function selecionarDestino()
{
    if(verificarConta()){
        if(pag.lugar=="login")
            window.location.href = "home.html";
        else if(pag.lugar=="sala")
            if(!verificarSala())
                window.location.href = "home.html";
            else
                minhaSala=sessionStorage.getItem("idSala");
        else if(pag.lugar=="registrar")
            window.location.href = "home.html";
    }
    else{
        if(pag.lugar=="inicio")
            window.location.href = "login.html";
        else if(pag.lugar=="sala"){
            window.location.href = "login.html";
        }
    }
}

selecionarDestino();