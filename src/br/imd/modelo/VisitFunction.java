package br.imd.modelo;

/* Interface b�sica para os percorrimentos em ordem da �rvore
 * Permite que qualquer classe a implemente para realizar procedimentos
 * diversos na �rvore percorrida na ordem desejada
 */
public interface VisitFunction {
	public void execute(No no);
}
