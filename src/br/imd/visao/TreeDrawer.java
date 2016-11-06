package br.imd.visao;

import br.imd.modelo.Aluno;
import br.imd.modelo.No;
import br.imd.modelo.Tree;

/* Classe que administra a arvore e o canvas e faz a liga��o entre eles */
public class TreeDrawer {
	private Tree tree;
	private MyCanvas canvas;
	private int NODESIZE = 50;
	private int VERTICALDIST = 70;

	/* Construtor simples.
	 * Constroi uma nova �rvore e um canvas com o tamanho dado
	 */
	public TreeDrawer(int width, int height) {
		tree = new Tree();
		canvas = new MyCanvas("Representacao visual", width, height);
	}
	
	/* Age como o construtor anterior, mas recebe uma �rvore j� construida
	 * como par�metro e em seguida j� a desenha no canvas
	 */
	public TreeDrawer(Tree tree, int width, int height) {
		this.tree = tree;
		canvas = new MyCanvas("Representacao visual", width, height);
		drawTree();
	}
	
	/* Fun��o que desenha a �rvore no canvas
	 * Passo base da recurs�o
	 * Respons�vel por desenhar as linhas entre os n�s e dar a posi��o
	 * correta a cada um eles para a fun��o do canvas que armazena os nos
	 */
	public void drawTree() {
		int leftX = 0;
		int rightX = canvas.getWidth();
		int currDepth = 1;

		if (tree.getLeftTree() != null) {
			canvas.drawConnection(leftX/2 + rightX/2, currDepth*VERTICALDIST + NODESIZE/2, 3*leftX/4 + rightX/4, (currDepth+1)*VERTICALDIST + NODESIZE/2);
			drawTree(tree.getLeftTree(), currDepth+1, leftX, leftX/2 + rightX/2);
		}
		if (tree.getRightTree() != null) {
			canvas.drawConnection(leftX/2 + rightX/2, currDepth*VERTICALDIST + NODESIZE/2, leftX/4 + 3*rightX/4, (currDepth+1)*VERTICALDIST + NODESIZE/2);
			drawTree(tree.getRightTree(), currDepth+1, leftX/2 + rightX/2, rightX);
		}
		
		canvas.storeNode(tree.getRoot(), leftX/2 + rightX/2, currDepth*VERTICALDIST);
	}
	
	/* Passo recursivo da fun��o acima */
	private void drawTree(Tree tree, int currDepth, int leftX, int rightX) {
		if (tree.getLeftTree() != null) {
			canvas.drawConnection(leftX/2 + rightX/2, currDepth*VERTICALDIST + NODESIZE/2, 3*leftX/4 + rightX/4, (currDepth+1)*VERTICALDIST + NODESIZE/2);
			drawTree(tree.getLeftTree(), currDepth+1, leftX, leftX/2 + rightX/2);
		}
		if (tree.getRightTree() != null) {
			canvas.drawConnection(leftX/2 + rightX/2, currDepth*VERTICALDIST + NODESIZE/2, leftX/4 + 3*rightX/4, (currDepth+1)*VERTICALDIST + NODESIZE/2);
			drawTree(tree.getRightTree(), currDepth+1, leftX/2 + rightX/2, rightX);
		}
		
		canvas.storeNode(tree.getRoot(), leftX/2 + rightX/2, currDepth*VERTICALDIST);
	}
	
	/* Percorre a �rvore em ordem sim�trica */
	public void percorrerInOrdem() {
		System.out.println("=== Percorrimento em ordem simetrica ===");
		tree.percorrerInOrdemVisual(canvas);
		System.out.println();
	}

	/* Percorre a �rvore em pr�-ordem */
	public void percorrerPreOrdem() {
		System.out.println("=== Percorrimento em pre-ordem ===");
		tree.percorrerPreOrdemVisual(canvas);
		System.out.println();
	}

	/* Percorre a �rvore em p�s-ordem*/
	public void percorrerPosOrdem() {
		System.out.println("=== Percorrimento em pos-ordem ===");
		tree.percorrerPosOrdemVisual(canvas);
		System.out.println();
	}
	
	/* Inicia a busca pelo elemento da �rvore atrav�s da matr�cula */
	public void busca(int matricula) {
		System.out.println("=== Busca ===");
		tree.buscaVisual(canvas, matricula);
		System.out.println();
	}
	
	/* Insere um aluno na �rvore */
	public void insereAluno(int matricula, String nome) {
        Aluno aluno = new Aluno(matricula, nome);
        No no = new No(aluno);
        inserir(no);
    }
	
	/* Insere um no na �rvore */
	public void inserir(No no) {
		System.out.println("=== Inserir ===");
		tree.inserirVisual(no, canvas);
		drawTree();
		System.out.println("Insercao do " + no.getAluno().getMatricula() + " foi concluido");
		System.out.println();
	}
	
	/* Remove um no da �rvore de acordo com a matricula passada no par�metro */
	public void remove(int matricula) {
		System.out.println("=== Remove ===");
		tree.removeVisual(canvas, matricula);
		canvas.clear();
		drawTree();
		System.out.println("Remocao de "+ matricula +" feita com sucesso");
		System.out.println();
	}	 
}
