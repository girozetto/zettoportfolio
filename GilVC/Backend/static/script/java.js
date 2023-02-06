
const modoTrocar = document.querySelector('button.modo-trocar'),
    body = document.querySelector('body'),
    closeBtn = document.querySelector('.btn-close-right'),
    ladoDireito = document.querySelector('.lado-direito'),
    expandirBtn = document.querySelector('.expandir-btn');

modoTrocar.addEventListener('click', () => {
    body.classList.toggle('escuro');
});

if(closeBtn)
    closeBtn.addEventListener('click', () => {
        ladoDireito.classList.remove('show');
        expandirBtn.classList.add('show');
    });
if(expandirBtn)
    expandirBtn.addEventListener('click', () => {
        ladoDireito.classList.add('show');
        expandirBtn.classList.remove('show');
    });
$("#deslogar").click((e)=>{
        e.preventDefault();
        sessionStorage.removeItem("idConta");
        sessionStorage.removeItem("idSala");
        setTimeout(()=>{window.location.href = "login.html";},3000);
    });
$(document).ready(()=>{
    $.ajax(
        {
            url: 'http://127.0.0.1:5000/infoConta',
            type: 'POST',
            data:{id:sessionStorage.getItem("idConta")},
            success: (resposta)=>{
                $(".toast-body").html(resposta.conta.nome+" entrou");
                var toastLiveExample = document.getElementById('liveToast');
                var toast = new bootstrap.Toast(toastLiveExample);
                toast.show();
            },
            error: (e)=>{
                console.log("Ocorreu um erro: "+e);
            }
        }
    );
});
