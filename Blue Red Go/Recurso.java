import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
public class Recurso {
    private BufferedImage imagem;
    public boolean colisao=false;
    public Recurso(String imgCaminho){
        try {
            setImagem(ImageIO.read(new File(imgCaminho)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedImage getImagem() {
        return imagem;
    }
    public void setImagem(BufferedImage imagem) {
        this.imagem = imagem;
    }
}
