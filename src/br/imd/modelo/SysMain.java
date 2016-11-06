package br.imd.modelo;

import br.imd.modelo.No;
import br.imd.visao.TreeDrawer;

public class SysMain {
	public static void main(String[] args) {
		TreeDrawer td = new TreeDrawer(800, 400);
		
		td.insereAluno(4, "Alguem");
		td.insereAluno(2, "Cristina");
		td.insereAluno(1, "João");
		td.insereAluno(3, "Paulo");
		td.insereAluno(6, "Bruna");
		td.insereAluno(5, "Yuri");
		td.insereAluno(7, "Érica");

		No n = td.busca(6);
		System.out.println("Nome do aluno: " + n.getAluno().getNome());
		System.out.println();
		td.percorrerPreOrdem();
		td.percorrerInOrdem();
		td.percorrerPosOrdem();
	
		td.remove(1);
		td.remove(2);
		td.remove(3);
		td.remove(6);
		td.remove(7);
		td.remove(4);
		td.insereAluno(10, "Emanuele");
		System.out.println("== Fim das ações do programa ==");
	}
}
