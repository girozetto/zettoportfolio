import json
import sqlite3

caminho="config.json"

def definicoes(caminho,campo):
    ficheiro=open(caminho)
    infos = json.load(ficheiro)
    ficheiro.close()
    return infos[campo]

def contaExistente(telefone):
    conn = sqlite3.connect(definicoes(caminho,"database")).cursor()
    return len(conn.execute("SELECT * FROM contas WHERE telefone=?", (telefone,)).fetchall())>0
    
def salvarConta(nome, telefone, senha, foto=""):
    if(contaExistente(telefone)):
        return {"erro":"Um usuário já se cadastrou com este Telefone"}
    conn = sqlite3.connect(definicoes(caminho,"database"))
    c = conn.cursor()
    c.execute("INSERT INTO contas (nome, telefone, senha, foto) VALUES (?,?,?,?)", (nome,telefone,senha,foto))
    conn.commit()
    return iniciarSessao(telefone,senha)
    
def iniciarSessao(telefone, senha):
    conn = sqlite3.connect(definicoes(caminho,"database"))
    c = conn.cursor()
    linhas = c.execute("SELECT * FROM contas WHERE telefone=? AND senha=?",(telefone,senha)).fetchall()
    return {"id":linhas[0][0],"nome":linhas[0][1],"telefone":linhas[0][2],"senha":linhas[0][3],"foto":linhas[0][4]} if len(linhas)>0 else False

def obterConta(id):
    conn = sqlite3.connect(definicoes(caminho,"database"))
    c = conn.cursor()
    linhas = c.execute("SELECT * FROM contas WHERE id=?",(id,)).fetchall()
    return {"id":linhas[0][0],"nome":linhas[0][1],"telefone":linhas[0][2],"senha":linhas[0][3],"foto":linhas[0][4]} if len(linhas)>0 else False