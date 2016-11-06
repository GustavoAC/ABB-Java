package br.imd.modelo;

/* Uma implementa��o da fun��o de visita dos percursos em ordem da �rvore
 * Simplesmente imprime o conte�do do N� atual no console
 */
public class TreePrinter implements VisitFunction {
	@Override
	public void execute(No no) {
		System.out.println(no.getAluno().getMatricula() + " " + no.getAluno().getNome());
	}
	
}
