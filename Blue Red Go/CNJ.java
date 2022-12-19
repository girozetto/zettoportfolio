public class CNJ extends Carro{
    //Carros nao joagaveis e movimentacao linear
    public CNJ(double x, double y, double rotacao, double maxVel, Recurso ...img) {
        super(x, y, rotacao, maxVel, img);
    }
    @Override
    public void mover(double friccao) {
        setVely(getVely()+(getControle().isAcelerar()?getAceleracao():0)+(getControle().isRecuar()?-getAceleracao():0));
        setVely((Math.abs(getVely())<=2*friccao?0:1)*getVely()*(1-friccao));
        setVelx(getVelx()+(getControle().isDireita()?getAceleracao():0)+(getControle().isEsquerda()?-getAceleracao():0));
        setVelx((Math.abs(getVelx())<=2*friccao?0:1)*getVelx()*(1-friccao));
        setVely(Math.abs(getVely()) > getMaxVel()?getMaxVel():getVely());
        setVelx(Math.abs(getVelx()) > getMaxVel()?getMaxVel():getVelx());
        setRotacao((getControle().isRecuar() ? -180:0)+(getControle().isDireita() ? -90:0)+(getControle().isEsquerda() ? 90:0));
        setY(getY()+getVely());
        setX(getX()+getVelx());
    }
    
}
