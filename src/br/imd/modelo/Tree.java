package br.imd.modelo;

import br.imd.visao.MyCanvas;

/* Implementação da árvore binária de busca
 * Contém tanto os métodos tradicionais quanto métodos
 * focados na implementação visual da árvore
 */

public class Tree {	
	private No root;
	private Tree leftTree;
	private Tree rightTree;
	
	public Tree(){
		// construtor vazio
	}
	
	public Tree getRightTree() {
		return rightTree;
	}
	
	public void setRightTree(Tree rightTree) {
		this.rightTree = rightTree;
	}
	
	public Tree getLeftTree() {
		return leftTree;
	}
	
	public void setLeftTree(Tree leftTree) {
		this.leftTree = leftTree;
	}
	
	 public No getRoot() {
	        return root;
	 }

	public void setRoot(No root) {
	        this.root = root;
	}
	
	/* Insere um Aluno na árvore, de acordo com as informações passadas por parâmetro
	 */ 
	public void insereAluno(int matricula, String nome) {
        Aluno aluno = new Aluno(matricula, nome);
        No no = new No(aluno);
        inserir(no);
    }
	
	/* Insere um No na árvore
	 */ 
	public void inserir(No no) {
		if(this.root == null) {
		   this.root = no;
		} else {
			if (no.getAluno().getMatricula() > this.root.getAluno().getMatricula()) {
				if (this.rightTree == null)
					this.rightTree = new Tree();
				this.rightTree.inserir(no);	
			} else if (no.getAluno().getMatricula() < this.root.getAluno().getMatricula()) {
				if (this.leftTree == null)
					this.leftTree = new Tree();
				this.leftTree.inserir(no);
			}
		}
	}
	
	/* Insere um No na árvore
	 * Imprimindo no console os eventos 
	 */
	public void inserirVisual(No no, MyCanvas canvas) {
		if(this.root == null) {
			System.out.println("O lugar foi encontrado");
			pause();
		   this.root = no;
		} else {
			canvas.highlightNode(root);
			System.out.println("Comparando " + this.root.getAluno().getMatricula() + " com " + no.getAluno().getMatricula());
			pause();
			if (no.getAluno().getMatricula() > this.root.getAluno().getMatricula()) {
				System.out.println("Valor " + root.getAluno().getMatricula() + " eh menor que " + no.getAluno().getMatricula());
				pause();
				System.out.println("Buscando lugar para inserir " + no.getAluno().getMatricula() + " na sub-arvore direita");
				if (this.rightTree == null)
					this.rightTree = new Tree();
				pause();
				canvas.unHighlightNode(root);
				this.rightTree.inserirVisual(no, canvas);	
			} else if (no.getAluno().getMatricula() < this.root.getAluno().getMatricula()) {
				System.out.println("Valor " + root.getAluno().getMatricula() + " eh maior que " + no.getAluno().getMatricula());
				pause();
				System.out.println("Buscando lugar para inserir " +no.getAluno().getMatricula()+ " na sub-arvore esquerda");
				if (this.leftTree == null)
					this.leftTree = new Tree();
				pause();
				canvas.unHighlightNode(root);
				this.leftTree.inserirVisual(no, canvas);
			}
		}
	}
	
	/* Remove um No da árvore
	 * O No a ser removido é determinado pela matricula passada por parâmetro  
	 * A função retorna true se o No for removido e false caso contrário
	 */
	public boolean remove (int matricula) {
		if (this.root == null) {
			return false;
		}
		if (this.root.getAluno().getMatricula() == matricula) {
			if (this.leftTree == null && this.rightTree == null) {
				this.root = null;
			} else if (this.leftTree != null && this.rightTree == null) {
				this.root = this.leftTree.root;
				this.rightTree = this.leftTree.rightTree;
				this.leftTree = this.leftTree.leftTree;
			} else if (this.leftTree == null && this.rightTree != null) {
				this.root = this.rightTree.root;
				this.leftTree = this.rightTree.leftTree;
				this.rightTree = this.rightTree.rightTree;
			} else {
				Tree ant = this;
				Tree atual = this.leftTree;
				while (atual.rightTree != null) {
					ant = atual;
					atual = atual.rightTree;
				}
				this.root = atual.root;
				if (ant == this) {
					this.leftTree = atual.leftTree;
				} else {
					ant.rightTree = atual.leftTree;
				}
			}
			return true;
		}
		Tree ant = null;
		Tree atual = this;
		while (atual != null && atual.root.getAluno().getMatricula() != matricula) {
			if (atual.root.getAluno().getMatricula() > matricula) {
				ant = atual;
				atual = atual.leftTree;
			} else {
				ant = atual;
				atual = atual.rightTree;
			}
		}
		
		if (atual == null) {
			return false;
		}
		
		if (atual.leftTree == null && atual.rightTree == null) {
			if (ant.leftTree == atual) {
				ant.leftTree = null;
			} else {
				ant.rightTree = null;
			}
		} else if (atual.leftTree != null && atual.rightTree == null) {
			if (ant.leftTree == atual) {
				ant.leftTree = atual.leftTree;
			} else {
				ant.rightTree = atual.leftTree;
			}
		} else if (atual.leftTree == null && atual.rightTree != null) {
			if (ant.leftTree == atual) {
				ant.leftTree = atual.rightTree;
			} else {
				ant.rightTree = atual.rightTree;
			}
		} else {
			Tree ant2 = atual;
			Tree atual2 = atual.leftTree;
			while (atual2.rightTree != null) {
				ant2 = atual2;
				atual2 = atual2.rightTree;
			}
			atual.root = atual2.root;
			if (ant2 == atual) {
				atual.leftTree = atual2.leftTree;
			} else {
				ant2.rightTree = atual2.leftTree;
			}
		}
		return true;
	}
	
	/* Remove um No da árvore
	 * Imprimindo no console os eventos 
	 * O No a ser removido é determinado pela matricula passada por parâmetro  
	 * A função retorna true se o No for removido e false caso contrário
	 */
	public boolean removeVisual (MyCanvas canvas, int matricula) {
		if (this.root == null) {
			System.out.println("Arvore vazia");
			return false;
		}
		
		System.out.println("Buscando no " + matricula + " para remover");
		pause();
		if (this.root.getAluno().getMatricula() == matricula) {
			canvas.highlightNode(root);
			System.out.println("No encontrado");
			pause();
			if (this.leftTree == null && this.rightTree == null) {
				System.out.println("No nao tem filhos");
				canvas.unHighlightNode(root);
				canvas.removeNode(this.root);
				this.root = null;
				pause();
			} else if (this.leftTree != null && this.rightTree == null) {
				System.out.println("No tem um filho");
				pause();
				System.out.println("Filho da esquerda fica no seu lugar");
				canvas.changeNodePos(this.leftTree.root, this.root);
				canvas.removeNode(this.root);
				this.root = this.leftTree.root;
				this.rightTree = this.leftTree.rightTree;
				this.leftTree = this.leftTree.leftTree;
				pause();
				canvas.unHighlightNode(root);
			} else if (this.leftTree == null && this.rightTree != null) {
				System.out.println("No tem um filho");
				pause();
				System.out.println("Filho da direita fica no seu lugar");
				canvas.changeNodePos(this.rightTree.root, this.root);
				canvas.removeNode(this.root);
				this.root = this.rightTree.root;
				this.leftTree = this.rightTree.leftTree;
				this.rightTree = this.rightTree.rightTree;
				pause();
				canvas.unHighlightNode(root);
			} else {
				System.out.println("No tem dois filhos");
				pause();
				System.out.println("Buscando maior elemento da arvore da esquerda");
				Tree ant = this;
				Tree atual = this.leftTree;
				canvas.highlightNode(atual.root);
				while (atual.rightTree != null) {
					ant = atual;
					atual = atual.rightTree;
					pause();
					canvas.unHighlightNode(ant.root);
					canvas.highlightNode(atual.root);
				}
				System.out.println("Maior elemento encontrado");
				pause();
				canvas.unHighlightNode(atual.root);
				canvas.changeNodePos(atual.root, this.root);
				canvas.removeNode(this.root);
				this.root = atual.root;
				if (ant == this) {
					this.leftTree = atual.leftTree;
				} else {
					ant.rightTree = atual.leftTree;
				}
				pause();
				canvas.unHighlightNode(root);
			}
			
			return true;
		}
		
		Tree ant = null;
		Tree atual = this;
		while (atual != null && atual.root.getAluno().getMatricula() != matricula) {
			canvas.highlightNode(atual.root);
			if (atual.root.getAluno().getMatricula() > matricula) {
				ant = atual;
				atual = atual.leftTree;
				pause();
				canvas.unHighlightNode(ant.root);
			} else {
				ant = atual;
				atual = atual.rightTree;
				pause();
				canvas.unHighlightNode(ant.root);
			}
		}
		
		if (atual == null) {
			System.out.println("Valor " + matricula + " nao foi encontrado na arvore");
			pause();
			return false;
		}
		
		canvas.highlightNode(atual.root);
		if (atual.leftTree == null && atual.rightTree == null) {
			System.out.println("No nao tem filhos");
			pause();
			
			canvas.unHighlightNode(atual.root);
			canvas.removeNode(atual.root);
			
			if (ant.leftTree == atual) {
				ant.leftTree = null;
			} else {
				ant.rightTree = null;
			}
		} else if (atual.leftTree != null && atual.rightTree == null) {
			System.out.println("No tem um filho");
			pause();
			System.out.println("Filho da esquerda fica no seu lugar");
			canvas.changeNodePos(atual.leftTree.root, atual.root);
			canvas.unHighlightNode(atual.root);
			canvas.removeNode(atual.root);
			
			if (ant.leftTree == atual) {
				ant.leftTree = atual.leftTree;
			} else {
				ant.rightTree = atual.leftTree;
			}
			
		} else if (atual.leftTree == null && atual.rightTree != null) {
			System.out.println("No tem um filho");
			pause();
			System.out.println("Filho da direita fica no seu lugar");
			canvas.changeNodePos(atual.rightTree.root, atual.root);
			canvas.unHighlightNode(atual.root);
			canvas.removeNode(atual.root);
			
			if (ant.leftTree == atual) {
				ant.leftTree = atual.rightTree;
			} else {
				ant.rightTree = atual.rightTree;
			}
		} else {
			System.out.println("No tem dois filhos");
			pause();
			System.out.println("Buscando maior elemento da sub-arvore esquerda");
			Tree ant2 = atual;
			Tree atual2 = atual.leftTree;
			canvas.highlightNode(atual2.root);
			while (atual2.rightTree != null) {
				ant2 = atual2;
				atual2 = atual2.rightTree;
				pause();
				canvas.unHighlightNode(ant2.root);
				canvas.highlightNode(atual2.root);
			}
			System.out.println("Maior elemento encontrado");
			pause();
			
			canvas.changeNodePos(atual2.root, atual.root);
			canvas.unHighlightNode(atual.root);
			canvas.unHighlightNode(atual2.root);
			canvas.removeNode(atual.root);

			atual.root = atual2.root;
			if (ant2 == atual) {
				atual.leftTree = atual2.leftTree;
			} else {
				ant2.rightTree = atual2.leftTree;
			}
		}
		
		pause();
		return true;
	}
	
	/* Busca um No na árvore
	 * O No a ser buscado é determinado pela matricula passada por parâmetro
	 * A função retorna o No em questão, se ele for encontrado
	 * Ou um No vazio, caso contrário  
	 */
	public No busca(int matricula) {
		// Se nao encontrar, retorna no vazio
		if (root == null)
			return new No(new Aluno(0,""));
		
		if (root.getAluno().getMatricula() == matricula) { 
			return root;
		}
		
		if (root.getAluno().getMatricula() > matricula) {
			return leftTree.busca(matricula);
		} else {
			return rightTree.busca(matricula);
		}
	}
	
	/* Busca um No na árvore
	 * Imprimindo no console os eventos 
	 * O No a ser buscado é determinado pela matricula passada por parâmetro
	 * A função retorna o No em questão, se ele for encontrado
	 * Ou um No vazio, caso contrário  
	 */
	public No buscaVisual(MyCanvas canvas, int matricula) {
		// Se nao encontrar, retorna no vazio
		if (root == null) {
			System.out.println("Valor " + matricula + " No foi encontrado na Arvore");
			pause();
			return new No(new Aluno(0,""));
		}
		
		canvas.highlightNode(root);
		System.out.println("Comparando " + root.getAluno().getMatricula() + " com " + matricula);
		pause();
		if (root.getAluno().getMatricula() == matricula) {
			System.out.println("Valor " + matricula + " foi encontrado");
			pause(); 
			canvas.unHighlightNode(root);
			return root;
		}
		
		if (root.getAluno().getMatricula() > matricula) {
			System.out.println("Valor " + root.getAluno().getMatricula() + " eh maior que " + matricula);
			pause();
			System.out.println("Buscando " + matricula + " na arvore da esquerda ");
			pause();
			canvas.unHighlightNode(root);
			return leftTree.buscaVisual(canvas, matricula);
		} else {
			System.out.println("Valor " + root.getAluno().getMatricula() + " eh menor que " + matricula);
			pause();
			System.out.println("Buscando " + matricula + " na arvore da direita ");
			pause();
			canvas.unHighlightNode(root);
			return rightTree.buscaVisual(canvas, matricula);
		}
	}
	
	/* Percorre a árvore em ordem simétrica
	 * Recebe uma classe que implementa VisitFunction
	 * por parâmetro, podendo assim realizar as mais diversas
	 * operações desejadas.
	 */ 
	public void percorrerInOrdem(VisitFunction func) {
		if (root == null) return;
		
		if (leftTree != null) leftTree.percorrerInOrdem(func);
		func.execute(root);
		if (rightTree != null) rightTree.percorrerInOrdem(func);
	}

	/* Percorre a árvore em ordem simétrica imprimindo no console os
	 * eventos e as informações do nó atual
	 */
	public void percorrerInOrdemVisual(MyCanvas canvas) {
		if (root == null) {
			System.out.println("Arvore vazia, retornando...");
			pause();
			return;
		}
		
		canvas.highlightNode(root);
		if (leftTree != null) {		
			System.out.println("Acessando a sub-arvore esquerda:");
			pause();
			canvas.unHighlightNode(root);
			leftTree.percorrerInOrdemVisual(canvas);
			canvas.highlightNode(root);
		}
		
		System.out.println("Acessando o no atual: ");
		System.out.println("Matricula: " + root.getAluno().getMatricula() +
					       " / Nome: " + root.getAluno().getNome());
		pause();
		
		if (rightTree != null) {
			System.out.println("Acessando a sub-arvore direita:");
			pause();
			canvas.unHighlightNode(root);
			rightTree.percorrerInOrdemVisual(canvas);	
			canvas.highlightNode(root);
		}
	
		System.out.println("Retornando...");
		pause();
		canvas.unHighlightNode(root);
	}
	
	/* Percorre a árvore em pré-ordem
	 * Recebe uma classe que implementa VisitFunction
	 * por parâmetro, podendo assim realizar as mais diversas
	 * operações desejadas.
	 */
	public void percorrerPreOrdem(VisitFunction func) {
		if (root == null) return;
		
		func.execute(root);
		if (leftTree != null) leftTree.percorrerPreOrdem(func);
		if (rightTree != null) rightTree.percorrerPreOrdem(func);
	}
	
	/* Percorre a árvore em pré-ordem imprimindo no console os
	 * eventos e as informações do nó atual
	 */
	public void percorrerPreOrdemVisual(MyCanvas canvas) {
		if (root == null) {
			System.out.println("arvore vazia, retornando...");
			pause();
			return;
		}
		
		canvas.highlightNode(root);
		System.out.println("Acessando o no atual: ");
		System.out.println("Matricula: " + root.getAluno().getMatricula() +
					       " / Nome: " + root.getAluno().getNome());
		pause();
		
		if (leftTree != null) {		
			System.out.println("Acessando a sub-arvore esquerda:");
			pause();
			canvas.unHighlightNode(root);
			leftTree.percorrerPreOrdemVisual(canvas);
			canvas.highlightNode(root);
		}
		
		if (rightTree != null) {
			System.out.println("Acessando a sub-arvore direita:");
			pause();
			canvas.unHighlightNode(root);
			rightTree.percorrerPreOrdemVisual(canvas);
			canvas.highlightNode(root);
		}
		
		System.out.println("Retornando...");
		pause();
		canvas.unHighlightNode(root);
	}
	
	/* Percorre a árvore em pós-ordem
	 * Recebe uma classe que implementa VisitFunction
	 * por parâmetro, podendo assim realizar as mais diversas
	 * operações desejadas.
	 */
	public void percorrerPosOrdem(VisitFunction func) {
		if (root == null) return;
		
		if (leftTree != null) leftTree.percorrerPosOrdem(func);
		if (rightTree != null) rightTree.percorrerPosOrdem(func);
		func.execute(root);
	}
	
	/* Percorre a árvore em pós-ordem imprimindo no console os
	 * eventos e as informações do nó atual
	 */
	public void percorrerPosOrdemVisual(MyCanvas canvas) {
		if (root == null) {
			System.out.println("arvore vazia, retornando...");
			pause();
			return;
		}
		
		canvas.highlightNode(root);
		
		if (leftTree != null) {		
			System.out.println("Acessando a sub-arvore esquerda:");
			pause();
			canvas.unHighlightNode(root);
			leftTree.percorrerPosOrdemVisual(canvas);
			canvas.highlightNode(root);
		}
		
		if (rightTree != null) {
			System.out.println("Acessando a sub-arvore direita:");
			pause();
			canvas.unHighlightNode(root);
			rightTree.percorrerPosOrdemVisual(canvas);
			canvas.highlightNode(root);
		}
		
		System.out.println("Acessando o nÃ³ atual: ");
		System.out.println("Matricula: " + root.getAluno().getMatricula() +
					       " / Nome: " + root.getAluno().getNome());
		pause();
		
		System.out.println("Retornando...");
		pause();
		canvas.unHighlightNode(root);
	}
	
	/* Pausa o programa por um curto tempo para deixar as mudanças visíveis
	 * ao usuário
	 */
	public void pause() {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
