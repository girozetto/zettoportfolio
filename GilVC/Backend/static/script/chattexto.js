var _peer_list = {};
var socket = io.connect("http://localhost:5000");
var ponto = new Peer();
var meuTrans;
var meuId;
var eu={};
var minhaSala="salinha3";
const conexPontos = {};
var troca = false;
const lista = [];
function mensagem(nome,msg,enviar=true)
{
    return "<div class='sms-wrapper "+(enviar?"reverse":"")+"'><div class='sms-conteudo'><p class='nome'>"+nome+"</p><div class='sms'>"+msg+"</div></div></div>";
}
ponto.on("open", id=>{
    meuId = id;
  });
  ponto.on("error", erro=>{
    alert(erro.type);
  });
$("#btnEnv").click((e)=>{
    msg = $("#msg").val();
    socket.emit("enviar",{"msg":msg});
    $(".chat-area").append(mensagem("Eu",msg))
});
socket.on("connect", ()=>{
    console.log("Socket conectado Boy");
    socket.emit("join-room", {"room_id": minhaSala,"idPonto":meuId});
});
socket.on("receber", (dado)=>{
    $(".chat-area").append(mensagem(dado["nome"],dado["msg"],false));
});

socket.on("conta-conectou", (dado)=>{
    let id = dado["sid"];
    let nome = dado["nome"];
    let idPonto = dado["idPonto"];
    $(".chat-area").append(mensagem(nome,"Se conectou",false));
    lista.push({id:id,nome:nome,idPonto:idPonto});
        alert("O usuário "+usuario.nome+" logou");
          eu = {id:id,nome:nome,idPonto:idPonto};
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

socket.on("conta-desconectou", (dado)=>{
    $(".chat-area").append(mensagem(dado["nome"],"Se desconectou",false));
    console.log();
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
        socket.on("usuario-conectado",usuario=>{
          adicionarUsuario(usuario);
          const chamada = ponto.call(usuario.idPonto,meuTrans);
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
        socket.on("usuario desconectado",usuario=>{
          if(conexPontos[usuario.idPonto])
              conexPontos[usuario.idPonto].close();
          removerUsuario(usuario);
        });