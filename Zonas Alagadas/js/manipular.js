var elemento = document.getElementById("inicio");
var pincel = elemento.getContext("2d");
pincel.beginPath();
//Linha
pincel.moveTo(10,20);
pincel.lineTo(100,20);
pincel.lineTo(100,50);
pincel.lineTo(10,20);
pincel.strokeStyle="green";
pincel.fillStyle="red"
pincel.stroke();
pincel.fill();
//Ret?ngulo
pincel.rect(10,20,150,150);
pincel.strokeStyle="black";
pincel.stroke();
for(var x=70;x>0;x-=5)
{	
	pincel.rect(100-(x/2),100-(x/2),x,x);
	pincel.strokeStyle="#f"+parseInt(Math.random()*16,16)+"ff"+parseInt(Math.random()*16,16)+parseInt(Math.random()*16,16);
}
pincel.stroke();