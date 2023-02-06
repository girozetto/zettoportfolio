const tela = document.getElementById("desenho")
const lapis = tela.getContext("2d");
proporcao = (window.innerWidth/DIM.largura);
const atualizarDimensao = (proporcao)=>{
    tela.width = window.innerWidth;
    tela.height = proporcao*window.innerHeight;
};
atualizarDimensao(proporcao);