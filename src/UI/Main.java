package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Service.ImageService;

public class Main extends JFrame {

	private static final long serialVersionUID = -5939065550291999872L;

	private JPanel contentPane;
	
	private JButton btnStart = new JButton("开始");
	
	private JLabel lblURL = new JLabel("还没有选择图片");
	
	private String path;
	
	private JTextArea textArea = new JTextArea();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 938, 808);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JButton btnChoseImage = new JButton("选择图片");
		btnChoseImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser jfc=new JFileChooser();  
		        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);  
		        jfc.showDialog(new JLabel(), "选择");  
		        File file=jfc.getSelectedFile();
		        path = file.getAbsolutePath();
		        String suffix = path.substring(path.lastIndexOf(".")+1);
		        if(suffix.equals("jpg")||suffix.equals("JPG")){		        	
		        	lblURL.setText(path);
		        	btnStart.setVisible(true);
		        }else{
		        	lblURL.setText("请选择jpg格式的图片");
		        }
			}
		});
		
		panel.add(btnChoseImage);
		btnChoseImage.setHorizontalAlignment(SwingConstants.LEFT);
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					BufferedImage bi = ImageService.getBufferedImage(path);
					int width = bi.getWidth()*150/bi.getHeight();
					int height = 150;
					BufferedImage bi2 = ImageService.getBufferImage(bi, width, height);
					char[][] cs = ImageService.getChars(bi2);
					String s = ImageService.getString(cs);
					textArea.setText(s);
				} catch (Exception e1) {
					textArea.setText("发生错误");
					e1.printStackTrace();
				}
			}
		});
		
		btnStart.setVisible(false);
		panel.add(btnStart);
		btnStart.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblNewLabel = new JLabel("图片路径：");
		panel.add(lblNewLabel);
		
		panel.add(lblURL);
		

		textArea.setFont(new Font("Monospaced", Font.BOLD, 5));
		contentPane.add(textArea, BorderLayout.CENTER);

	}

}
