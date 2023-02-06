from rembg import remove
from PIL import Image
import os
import json


def obterConfig(caminho):
    arq = open(caminho)
    dados = json.load(arq)
    arq.close()
    return dados

def obterFicheiros(pasta):
    os.chdir(pasta)
    lista=[]
    for ficheiro in os.listdir():
        if(ficheiro.endswith(".jpg")):
            lista.append(ficheiro)
    return lista

print("Carregando as definições")
config=obterConfig("config.json")
print("Configs: ",config)
ficheiros = obterFicheiros(config["origem"])
print("Ficheiros na pasta: ",ficheiros)
ind=0
tam=len(ficheiros)
for f in ficheiros:
    print("Convertendo imagem: ",f)
    img=Image.open(f)
    saida=remove(img)
    saida.save(str(f).replace(".jpg",".png"))
    ind=ind+1
    print("Progresso: {} %".format(round((ind/tam)*100,2)))