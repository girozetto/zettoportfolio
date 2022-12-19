const gerarMundo = ()=>{
	for(var linI=0;linI < tamanhoMundo;linI++)
	{
		const linha = document.createElement("div");
		linha.classList.add("linha");
		for(var colI = 0;colI < tamanhoMundo;colI++)
		{
			const input = document.createElement("input");
			input.type="checkbox";
			linha.appendChild(input);
		}
		document.querySelector(".mundo").appendChild(linha);
	}
	
};
gerarMundo(tamanhoMundo);