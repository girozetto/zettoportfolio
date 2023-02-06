from flask import Flask,redirect,url_for,render_template,request,jsonify,session
from flask_socketio import SocketIO,emit,join_room,close_room,leave_room,rooms
from flask_cors import CORS
from operacoes import definicoes,caminho,iniciarSessao,salvarConta, obterConta

aplicativo=Flask(__name__)
aplicativo.secret_key="giroZetto"
CORS(aplicativo)
sockio = SocketIO(aplicativo,cors_allowed_origins="*")

salasid={}
contasEmSalas={}
nomesid={}
idsponto={}
@aplicativo.after_request
def after_request(response):
    header = response.headers
    header['Access-Control-Allow-Origin'] = '*'
    header['Access-Control-Allow-Headers'] = 'Content-Type, Authorization'
    header['Access-Control-Allow-Methods'] = 'OPTIONS, HEAD, GET, POST, DELETE, PUT'
    return response

@aplicativo.route('/',methods=['GET'])
def inicio():
    return redirect(url_for("static",filename="login.html"))

@aplicativo.route('/cadastrarConta',methods=['POST'])
def cadastrarConta():
    nome = request.form.get("nome")
    telefone = request.form.get("telefone")
    senha = request.form.get("senha")
    if len(nome)>0 and len(telefone)>0 and len(senha)>0: 
        conta=salvarConta(nome,telefone,senha)
        return jsonify({"conta":conta})
    return jsonify({"msg":"ERRO"})

@aplicativo.route('/infoConta',methods=['POST'])
def infoConta():
    id = request.form.get("id")
    conta = obterConta(id)
    return jsonify({"conta":conta})

@aplicativo.route('/verificarSala',methods=['POST'])
def verificarSala():
    idsala=request.form.get("idsala")
    return jsonify({"msg":"SUCESSO","existe":idsala in salasid.values()})

@aplicativo.route('/logarConta',methods=['POST'])
def logarConta():
    resultado=iniciarSessao(request.form.get("telefone"),request.form.get("senha"))
    if resultado != False:
        return jsonify({"conta":resultado})
    return jsonify({"msg":"SUCESSO" if resultado else "ERRO"})

@aplicativo.route('/desconectarConta',methods=['POST'])
def desconectar():
    session.pop("idConta")
    return redirect(url_for("static",filename="login.html"))

#Eventos de Sockets
@sockio.on("message")
def handleMessage(msg):
    print("Mensagem ",msg)

#Ao conectar-se no servidor
@sockio.on("connect")
def on_connect():
    sid = request.sid
    print("Um novo socket foi conectado: ", sid)
    print("Salas: ",salasid)
    print("NomesId: ",nomesid)
    print("NomesId: ",idsponto)
    
#Ao enviar mensagem nos outros sockets
@sockio.on("enviar")
def ao_enviar(info):
    idsala=salasid[request.sid]
    emit("receber",{"sid":request.sid,"nome":nomesid[request.sid],"msg":info["msg"]}, broadcast=True, include_self=False, room=idsala)


#Ao se juntar á uma nova sala
@sockio.on("join-room")
def on_join_room(data):
    print("Entrando na sala")
    sid = request.sid
    idsala=data["room_id"]
    nome = obterConta(data["idConta"])["nome"]
    join_room(idsala)
    salasid[sid] = idsala
    nomesid[sid] = nome
    idsponto[sid] = data["idPonto"]
    print("[{}] Novo Membro se juntou: {}<{}>".format(idsala, nome, sid))
    emit("conta-conectou",{"sid":sid, "nome":nome,"idPonto":data["idPonto"]}, broadcast=True, include_self=False, room=idsala)
    emit("usuario-conectado",{"sid":sid, "nome":nome,"idPonto":data["idPonto"]}, broadcast=False, include_self=True, room=idsala)

#Ao se desconectar de uma sala
@sockio.on("disconnect")
def on_disconnect():
    sid = request.sid
    idsala = salasid[sid]
    nome = nomesid[sid]
    print("[{}] Um Membro deixou: {}<{}>".format(idsala, nome, sid))
    emit("conta-desconectou", {"sid": sid, "nome":nome},broadcast=True, include_self=False, room=idsala)
    salasid.pop(sid)
    nomesid.pop(sid)

    print("\nUsuarios: ", idsala, "\n")

if __name__ == '__main__':
    sockio.run(aplicativo,debug=definicoes(caminho,"debug"))