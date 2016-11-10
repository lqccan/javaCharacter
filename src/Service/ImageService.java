package Service;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageService {
	/**
	 * 图像二值化的一个阙值，默认127
	 */
	private static int tag = 127;
	
	/**
	 * 绘制字符画所能用的字符
	 */
	private static char[] cs = new char[]{'*', ' '};
	
	/**
	 * 将图片缩放后返回BufferedImage
	 * @param img
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage getBufferImage(BufferedImage img, int width, int height){
		BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		
		bi.getGraphics().drawImage(img.getScaledInstance(width, height,  
                        Image.SCALE_SMOOTH), 0, 0, null);
		return bi;
	}
	
	/**
	 * 通过路径获取BufferedImage对象
	 * @param path
	 * @return
	 * @throws IOException 
	 */
	public static BufferedImage getBufferedImage(String path) throws IOException{
		BufferedImage img = ImageIO.read(new File(path));
		return img;
	}
	
	/**
	 * 通过bufferedImage 获取相应的字符数组
	 * @param bi
	 * @return
	 */
	public static char[][] getChars(BufferedImage bi){
		char[][] result = new char[bi.getHeight()][bi.getWidth()];
		
		for(int i=0;i<bi.getHeight();i++){
			for(int j=0;j<bi.getWidth();j++){
				int rgb = bi.getRGB(j, i);
				Color c = new Color(rgb);
				int cc = (c.getRed()+c.getGreen()+c.getBlue())/3;
				cc = cc>=tag?1:0;//大于等于阙值就显示星，小于显示空白
				result[i][j] = cs[cc];
			}
		}
		return result;
	}
	
	/**
	 * 将二维的字符数组转成字符串
	 * @param cs
	 * @return
	 */
	public static String getString(char[][] cs){
		StringBuffer sb = new StringBuffer();
		
		for(int i=0;i<cs.length;i++){
			for(int j=0;j<cs[0].length;j++){
				//因为字符的高一般是长的两倍，一行中字符重复一遍
				sb.append(cs[i][j]);
				sb.append(cs[i][j]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
