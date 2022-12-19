var pupilas=document.getElementsByClassName("pupila");
document.onmousemove= function(){
	var x = event.clientX*100/window.innerWidth+"%";
	var y = event.clientY*100/window.innerHeight+"%";
	console.log(x);
	console.log(y);
	for(var i=0;i<pupilas.length;i++)
	{
		pupilas[i].style.left=x;
		pupilas[i].style.top=y;
		pupilas[i].style.transform="translate(-"+x+",-"+y+")";
	}
}