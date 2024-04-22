package br.com.zlab.loja_virtual;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import br.com.zlab.loja_virtual.controller.PagamentoController;
import junit.framework.TestCase;

@Profile("devp")
@SpringBootTest(classes = LojaVirtualApplication.class)
public class TestePagamentoController extends TestCase {

	@Autowired
	private PagamentoController pagamentoController;
	
	@Test
	public void testfinalizarCompraCartaoAsaas() throws Exception {
	    pagamentoController.finalizarCompraCartaoAsaas(
	    		"5126462892278565",
	    		"Alex F Egidio",
	    		"000", "06", "2025",
	    		15L, "05916564937",
	    		2, "87025758",
	    		"Pioneiro antonio de ganello", 
	    		"365", "PR", "Maringá");
	}

}
