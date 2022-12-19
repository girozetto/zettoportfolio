const comecarJogo = () => {
	tratarInput();
	checarItemEm(...pontoPartida);
	colocarFrangoEm(...obterPosicaoAleatoria());
};
comecarJogo();