package br.imd.visao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import br.imd.modelo.No;

/*
 * Classe Canvas na qual a árvore é desenhada
 * Mantém várias informações sobre cada nó atual da árvore
 * e as desenhas sempre que redraw é chamada
 */
@SuppressWarnings("serial")
public class MyCanvas extends JPanel {
	
	private JFrame frame;
	private Image canvasImage;
	private Graphics2D graphic;
	private ArrayList<No> nodeReferences;
	private HashMap<No, ArrayList<Shape>> shapes;
	private HashMap<No, Color> nodeColor;
	private HashMap<No, Point2D.Float> nodePosition;
	private static int NODESIZE = 50;
	
	public MyCanvas(String name, int width, int height) {
		frame = new JFrame(name);
		frame.setContentPane(this);
		this.setPreferredSize(new Dimension(width, height));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		nodeReferences = new ArrayList<No>();
		shapes = new HashMap<No, ArrayList<Shape>>();
		nodeColor = new HashMap<No, Color>();
		nodePosition = new HashMap<No, Point2D.Float>();
		
		
		canvasImage = this.createImage(width, height);
		graphic = (Graphics2D)canvasImage.getGraphics();
		graphic.setColor(Color.WHITE);
		graphic.fillRect(0, 0, width, height);
		graphic.setColor(Color.BLACK);
		
		frame.setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(canvasImage, 0, 0, null);
	}
	
	/* Usada para desenhar as linhas entre os nós.
	 * Chamada pela função TreeDrawer
	 */
	public void drawConnection(int x1, int y1, int x2, int y2) {
		graphic.drawLine(x1, y1, x2, y2);
	}
	
	/* Guarda as informações do nó passado por parâmetro, incluindo
	 * a posição onde será desenhado
	 */
	public void storeNode(No no, int x, int y) {
		if (nodeReferences.contains(no))
			return;
		
		nodeReferences.add(no);
		nodeColor.put(no, Color.BLACK);
		nodePosition.put(no, new Point2D.Float(x, y));
		
		shapes.put(no, new ArrayList<Shape>());
		shapes.get(no).add(new Ellipse2D.Double(x - NODESIZE/2, y, NODESIZE, NODESIZE));
		shapes.get(no).add(new Ellipse2D.Double(x - (NODESIZE*0.90)/2,
												y + (NODESIZE*0.05),
												(NODESIZE*0.9),
												(NODESIZE*0.9)));
		redraw();
	}
	
	/* Remove as informações guardadas sobre o nó passado
	 * por parâmetro
	 */
	public void removeNode(No no) {	
		nodeReferences.remove(no);
		nodeColor.remove(no);
		nodePosition.remove(no);
		shapes.remove(no);
		redraw();
	}

	/* Atribui a posição do segundo nó para o primeiro */
	public void changeNodePos(No orig, No dest) {
		nodePosition.put(orig, nodePosition.get(dest));
		shapes.put(orig, shapes.get(dest));
	}
	
	/* Muda a cor externa do nó para verde, dando foco ao mesmo.
	 */
	public void highlightNode(No no) {
		nodeColor.put(no, Color.GREEN);
		redraw();
	}
	
	/* Torna a cor externa do nó para a cor padrão preto.
	 */
	public void unHighlightNode(No no) {
		nodeColor.put(no, Color.BLACK);
		redraw();
	}
	
	/* Limpa o canvas */
	public void clear() {
		graphic.setColor(Color.WHITE);
		graphic.fillRect(0, 0, this.getWidth(), this.getHeight());
		graphic.setColor(Color.BLACK);
	}
	
	/*
	 * Itera por todos os nos armazenados na classe e os desenha
	 * no canvas levando em conta sua cor e seu conteudo.
	 * Essa função desenha somente os nós, as linhas que os ligam são
	 * feitas pela classe TreeDrawer. 
	 */
	public void redraw() {
		for (No no : nodeReferences) {
			Color color = nodeColor.get(no);
			for (Shape shape : shapes.get(no)) {
				graphic.setColor(color);
				graphic.fill(shape);
				color = Color.WHITE;
			}
			graphic.setColor(Color.BLACK);
			graphic.drawString("" + no.getAluno().getMatricula(), (int) nodePosition.get(no).getX()-4, (int) nodePosition.get(no).getY() + NODESIZE/2);
		}
		repaint();
	}
}
