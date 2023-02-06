import sqlite3
from operacoes import definicoes,caminho
def inserirTabelas():
    conn = sqlite3.connect(definicoes(caminho,"database"))
    c = conn.cursor()
    for tabela in definicoes(caminho,"tabelas"):
        c.execute("CREATE TABLE "+tabela)
    conn.commit()
inserirTabelas()