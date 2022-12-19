var lista = [];

class Usuario
{
	constructor(nome,email,senha)
	{
		this.nome=nome;
		this.email=email;
		this.senha=senha;
	}
}

lista.push(new Usuario("José Manuel Francisco","jose@gmail.com","123456789"));
lista.push(new Usuario("Malonga Filipe Aurélio","mfilipe@gmail.com","2312134"));
lista.push(new Usuario("Gilberto Alexandre","gilalex@gmail.com","abcdefgh"));

var botao=document.getElementById("entrar");
botao.onclick=()=>{
	var email = document.getElementById("emailE").value;
	var senha = document.getElementById("senhaE").value;
	if(email.length==0)
	{
		alert("Campo Email Vazio")
		return;
	}
	if(senha.length==0)
	{
		alert("Campo Senha Vazio")
		return;
	}
	for(var i=0;;i<lista.length;i++)
	{
		if(lista[i].email===email && lista[i].senha===senha)
		{	
			alert("Olá Sr. "+lista[i].nome)
			break;
		}
	}
}