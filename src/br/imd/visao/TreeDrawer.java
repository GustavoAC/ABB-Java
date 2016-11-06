package br.imd.visao;

import br.imd.modelo.Aluno;
import br.imd.modelo.No;
import br.imd.modelo.Tree;

/* Classe que administra a arvore e o canvas e faz a ligação entre eles */
public class TreeDrawer {
	private Tree tree;
	private MyCanvas canvas;
	private int NODESIZE = 50;
	private int VERTICALDIST = 70;

	/* Construtor simples.
	 * Constroi uma nova árvore e um canvas com o tamanho dado
	 */
	public TreeDrawer(int width, int height) {
		tree = new Tree();
		canvas = new MyCanvas("Representacao visual", width, height);
	}
	
	/* Age como o construtor anterior, mas recebe uma árvore já construida
	 * como parâmetro e em seguida já a desenha no canvas
	 */
	public TreeDrawer(Tree tree, int width, int height) {
		this.tree = tree;
		canvas = new MyCanvas("Representacao visual", width, height);
		drawTree();
		canvas.redraw();
	}
	
	/* Função que desenha a árvore no canvas
	 * Passo base da recursão
	 * Responsável por desenhar as linhas entre os nós e dar a posição
	 * correta a cada um eles para a função do canvas que armazena os nos
	 */
	public void drawTree() {
		if (tree.getRoot() == null) {
			System.out.println("�rvore vazia");
			return;
		}
		
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
	
	/* Passo recursivo da função acima */
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
	
	/* Percorre a árvore em ordem simétrica */
	public void percorrerInOrdem() {
		System.out.println("=== Percorrimento em ordem simetrica ===");
		tree.percorrerInOrdemVisual(canvas);
		System.out.println();
	}

	/* Percorre a árvore em pré-ordem */
	public void percorrerPreOrdem() {
		System.out.println("=== Percorrimento em pre-ordem ===");
		tree.percorrerPreOrdemVisual(canvas);
		System.out.println();
	}

	/* Percorre a árvore em pós-ordem*/
	public void percorrerPosOrdem() {
		System.out.println("=== Percorrimento em pos-ordem ===");
		tree.percorrerPosOrdemVisual(canvas);
		System.out.println();
	}
	
	/* Inicia a busca pelo elemento da árvore através da matrícula */
	public No busca(int matricula) {
		System.out.println("=== Busca ===");
		No no = tree.buscaVisual(canvas, matricula);
		System.out.println();
		return no;
	}
	
	/* Insere um aluno na árvore */
	public void insereAluno(int matricula, String nome) {
        Aluno aluno = new Aluno(matricula, nome);
        No no = new No(aluno);
        inserir(no);
    }
	
	/* Insere um no na árvore */
	public void inserir(No no) {
		System.out.println("=== Inserir ===");
		tree.inserirVisual(no, canvas);
		drawTree();
		canvas.redraw();
		System.out.println("Insercao do " + no.getAluno().getMatricula() + " foi concluida");
		System.out.println();
	}
	
	/* Remove um no da árvore de acordo com a matricula passada no parâmetro */
	public boolean remove(int matricula) {
		System.out.println("=== Remove ===");
		boolean resultado = tree.removeVisual(canvas, matricula);
		canvas.clear();
		drawTree();
		canvas.redraw();
		if (resultado) {
			System.out.println("Remocao de " + matricula + " feita com sucesso");
			System.out.println();
		}
		return resultado; 
	}	 
}
