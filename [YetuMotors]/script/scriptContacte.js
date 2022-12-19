var botao = document.getElementById("enviar");

botao.onclick=()=>{
	var nome = document.getElementById("nome").value;
	var email = document.getElementById("email").value;
	var mensagem = document.getElementById("mensagem").value;
	console.log(typeof(mensagem));
	
	
	if(nome.length<1)
	{
		alert("Campo Nome Vazio");
		return;
	}
	if(email.length<1)
	{
		alert("Campo Email Vazio");
		return;
	}
	if(mensagem.length<1)
	{
		alert("Campo Mensagem Vazio");
		return;
	}
};