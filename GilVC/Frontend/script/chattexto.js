var _peer_list = {};
var protocol = window.location.protocol;
var socket = io.connect("http://localhost:5000");

//Instanciação do ponto = new Peer()
var ponto = new Peer();

//Minha transmissão
var meuTrans;

//Meu ID ponto á ponto
var meuId;

//
var eu={};

//conexões P2P listadas
const conexPontos = {};

var troca = false;

//Lista de nomes ou usuários ligados á mim
const lista = [];

function mensagem(nome,msg,enviar=true)
{
    return "<div class='sms-wrapper "+(enviar?"reverse":"")+"'><div class='sms-conteudo'><p class='nome'>"+nome+"</p><div class='sms'>"+msg+"</div></div></div>";
}


//Evento para enviar mensagens no CHAT
$("#btnEnv").click((e)=>{
    msg = $("#msg").val();
    $("#msg").val("");
    socket.emit("enviar",{"msg":msg});
    $(".chat-area").append(mensagem("Eu",msg))
});

//Evento do Socket quando se conecta ao servidor SOCKETIO
socket.on("connect", ()=>{
    console.log("O Socket foi conectado com sucesso.");
    ponto.on("open", id=>{
      meuId = id;
      socket.emit("join-room", {"room_id": minhaSala,"idPonto":meuId,"idConta":sessionStorage.getItem("idConta")});
      });
    ponto.on("error", erro=>{
        alert("Não conseguiu obter o id ponto á ponto");
      });
    navigator.mediaDevices.getUserMedia({
      video:true,
      audio:true
    })
    .then((transmissao)=>{
      meuTrans=transmissao;
      var meuvideo = document.createElement("video");
      adicionarVideo(transmissao,meuvideo);
      ponto.on("call",call=>{
        call.answer(transmissao);
        const vid = document.createElement("video");
        call.on("stream",transuser=>{
          adicionarVideo(transuser,vid);
        });
        call.on("error",erro=>{
          alert(erro);
        });
        call.on("close",()=>{
          console.log(vid);
          vid.remove();
        });
        conexPontos[call.peer] = call;
      });
    })
    .catch(erro=>{
      alert(erro.message);
    });
});

//Evento do Socket quando recebe uma mensagem de um outro usuário
socket.on("receber", (dado)=>{
    $(".chat-area").append(mensagem(dado["nome"],dado["msg"],false));
});

//Evento quando um socket é conectado com sucesso
socket.on("conta-conectou", (dado)=>{
    let id = dado["sid"];
    let nome = dado["nome"];
    let idPonto = dado["idPonto"];
    $(".chat-area").append(mensagem(nome,"Se conectou",false));
    //Adicionar o usuário á lista de usuários na sala
    lista.push({id:id,nome:nome,idPonto:idPonto});
    adicionarUsuario(usuario);
    const chamada = ponto.call(idPonto,meuTrans);
    const vid = document.createElement("video");
    chamada.on("error",(erro)=>{
      alert(erro);
    });
    chamada.on("stream",(usuariotrans)=>{
      adicionarVideo(usuariotrans,vid);
    });
    chamada.on("close",()=>{
      vid.remove();
    });
    conexPontos[usuario.idPonto] = chamada;
});

//Evento de conta desconectada
socket.on("conta-desconectou", (dado)=>{
    $(".chat-area").append(mensagem(dado["nome"],"Se desconectou",false));
});

//Parte da video conferência
        $("#desconectar").click((e)=>{
            e.preventDefault();
            socket.emit("desconectar usuario");
        });
        
        function adicionarVideo(transmissao,video)
        {
          video.srcObject = transmissao;
          video.addEventListener("loadedmetadata",()=>{
            video.play();
          });
          var dv = document.createElement("div");
          dv.className="video-participant";
          dv.append(video);
          $(".video-call-wrapper").append(dv);
        }
        function adicionarUsuario(usuario)
        {
            lista.push(usuario);
            alert("Usuários Existentes "+lista.toString())
        }
        function removerUsuario(usuario)
        {
            lista.splice(lista.indexOf(usuario),1);
            alert("Usuários Existentes "+lista.toString())
        }
       /* 
        
        socket.on("usuario desconectado",usuario=>{
          if(conexPontos[usuario.idPonto])
              conexPontos[usuario.idPonto].close();
          removerUsuario(usuario);
        });*/