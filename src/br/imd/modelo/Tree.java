package br.imd.modelo;

import br.imd.visao.MyCanvas;

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
	
	public void insereAluno(int matricula, String nome) {
        Aluno aluno = new Aluno(matricula, nome);
        No no = new No(aluno);
        inserir(no);
    }

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
	
	public boolean remove (int matricula) {
		if (this.root == null) {
			return false;
		}
		if (this.root.getAluno().getMatricula() == matricula) {
			if (this.leftTree == null && this.rightTree == null) {
				this.root = null;
			} else if (this.leftTree != null && this.rightTree == null) {
				this.root = this.leftTree.root;
				this.leftTree = this.leftTree.leftTree;
				this.rightTree = this.leftTree.rightTree;
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
	
	public No busca(int matricula) {
		// Se nao encontrar, retorna no vazio
		if (root == null)
			return new No(new Aluno(0,""));
		
		if (root.getAluno().getMatricula() == matricula)
			return root;
		
		if (root.getAluno().getMatricula() > matricula)
			return leftTree.busca(matricula);
		else
			return rightTree.busca(matricula);
	}
	
	public void percorrerInOrdem(VisitFunction func) {
		if (root == null) return;
		
		if (leftTree != null) leftTree.percorrerInOrdem(func);
		func.execute(root);
		if (rightTree != null) rightTree.percorrerInOrdem(func);
	}

	public void percorrerInOrdemVisual(MyCanvas canvas) {
		if (root == null) {
			System.out.println("�rvore vazia, retornando...");
			pause();
			return;
		}
		
		canvas.highlightNode(root);
		if (leftTree != null) {		
			System.out.println("Acessando a sub-�rvore esquerda:");
			pause();
			canvas.unHighlightNode(root);
			leftTree.percorrerInOrdemVisual(canvas);
			canvas.highlightNode(root);
		}
		
		System.out.println("Acessando o n� atual: ");
		System.out.println("Matricula: " + root.getAluno().getMatricula() +
					       " / Nome: " + root.getAluno().getNome());
		pause();
		
		if (rightTree != null) {
			System.out.println("Acessando a sub-�rvore direita:");
			pause();
			canvas.unHighlightNode(root);
			rightTree.percorrerInOrdemVisual(canvas);	
			canvas.highlightNode(root);
		}
	
		System.out.println("Retornando...");
		pause();
		canvas.unHighlightNode(root);
	}
	
	public void percorrerPreOrdem(VisitFunction func) {
		if (root == null) return;
		
		func.execute(root);
		if (leftTree != null) leftTree.percorrerPreOrdem(func);
		if (rightTree != null) rightTree.percorrerPreOrdem(func);
	}
	
	public void percorrerPreOrdemVisual(MyCanvas canvas) {
		if (root == null) {
			System.out.println("�rvore vazia, retornando...");
			pause();
			return;
		}
		
		canvas.highlightNode(root);
		System.out.println("Acessando o n� atual: ");
		System.out.println("Matricula: " + root.getAluno().getMatricula() +
					       " / Nome: " + root.getAluno().getNome());
		pause();
		
		if (leftTree != null) {		
			System.out.println("Acessando a sub-�rvore esquerda:");
			pause();
			canvas.unHighlightNode(root);
			leftTree.percorrerPreOrdemVisual(canvas);
			canvas.highlightNode(root);
		}
		
		if (rightTree != null) {
			System.out.println("Acessando a sub-�rvore direita:");
			pause();
			canvas.unHighlightNode(root);
			rightTree.percorrerPreOrdemVisual(canvas);
			canvas.highlightNode(root);
		}
		
		System.out.println("Retornando...");
		pause();
		canvas.unHighlightNode(root);
	}
	
	public void percorrerPosOrdem(VisitFunction func) {
		if (root == null) return;
		
		if (leftTree != null) leftTree.percorrerPosOrdem(func);
		if (rightTree != null) rightTree.percorrerPosOrdem(func);
		func.execute(root);
	}
	
	public void percorrerPosOrdemVisual(MyCanvas canvas) {
		if (root == null) {
			System.out.println("�rvore vazia, retornando...");
			pause();
			return;
		}
		
		canvas.highlightNode(root);
		
		if (leftTree != null) {		
			System.out.println("Acessando a sub-�rvore esquerda:");
			pause();
			canvas.unHighlightNode(root);
			leftTree.percorrerPosOrdemVisual(canvas);
			canvas.highlightNode(root);
		}
		
		if (rightTree != null) {
			System.out.println("Acessando a sub-�rvore direita:");
			pause();
			canvas.unHighlightNode(root);
			rightTree.percorrerPosOrdemVisual(canvas);
			canvas.highlightNode(root);
		}
		
		System.out.println("Acessando o n� atual: ");
		System.out.println("Matricula: " + root.getAluno().getMatricula() +
					       " / Nome: " + root.getAluno().getNome());
		pause();
		
		System.out.println("Retornando...");
		pause();
		canvas.unHighlightNode(root);
	}
	
	public void pause() {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
