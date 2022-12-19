let pesquisa = document.querySelector('.bar-pesquisa');

document.querySelector('#pesquisa').onclick = () => {
    pesquisa.classList.toggle('active');
    menu.classList.remove('active')
}

let header=document.querySelector('header');

window.addEventListener('scroll', ()=>{
    header.classList.toggle('down',window.scrollY > 0);
});


let menu= document.querySelector('.navbar');

document.querySelector('#menu-btn').onclick = () => {
    menu.classList.toggle('active');
    pesquisa.classList.remove('active');
}

window.onscroll = () =>
{
    menu.classList.remove('active');
    pesquisa.classList.remove('active');
}


document.querySelector('#login').onclick = () =>{
    window.location.href="_pages/Login.html" 
  }
