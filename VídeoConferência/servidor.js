const expresso = require('express')
const aplicativo = expresso();
const servweb = require('http').createServer(aplicativo);
const {Server} = require('socket.io');
const io = new Server(servweb);
const porta = process.env.PORT || 3000;

var cont = 0;
aplicativo.use(expresso.static(__dirname));

io.on('connection',(socket)=>{
    console.log('Uma nova sessao do app foi adicionada: '+socket.id);
    
    var adicion
    ado = false;
    const usuario = {nome:"",id:socket.id,conexao:false,idPonto:""};
    
    socket.on('disconnect',()=>{
        if(usuario.conexao){
            cont--;
            socket.broadcast.emit("usuario desconectado",usuario);
            console.log('O '+usuario.nome+' se desconectou');    
        }
        else
            console.log('Sessão '+usuario.id+' Terminada');
        console.log("Existem "+cont+" Usuarios conectados");
    });
    socket.on('alerta',(alrt)=>{
        console.log(alrt);
    });
    socket.on("desconectar usuario",()=>{
        cont--;
        socket.broadcast.emit("usuario desconectado",usuario);
        console.log('O '+usuario.nome+' se desconectou');  
        usuario.nome = "";
        usuario.conexao = false;

        }
    );
    socket.on('novo usuario',(nome,idPonto)=>{
        usuario.nome = nome;
        if(!usuario.conexao){
            usuario.idPonto=idPonto;
            socket.broadcast.emit("usuario conectado",usuario);
            cont++;
            console.log("O usuário "+usuario.nome+" entrou na conferência");
            usuario.conexao=true;
            socket.emit("usuario logado",usuario);
        }
        else{
            console.log("O usuário já está logado");
        }
        console.log("Existem "+cont+"Usuarios conectados");
    });
});
servweb.listen(porta,()=>{
    console.log('Ouvindo a porta '+porta);
});