package gov.goias.seven.persistencia;

import gov.goias.excecao.InfraExcecao;
import gov.goias.seven.modelo.Evento;
import gov.goias.seven.negocio.EventoBO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring-test.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
                         TransactionalTestExecutionListener.class,
                         DirtiesContextTestExecutionListener.class})
@TransactionConfiguration(transactionManager="txManager", defaultRollback= false)
@Transactional
public class EventoIntegracaoTest {

    @Autowired
    private EventoBO bo;

    private Evento evento;

    @Before
    public void iniciar(){
        evento = new Evento();
        evento.setId(1L);
        evento.setDescricao("Evento teste");
    }

    @Test
    public void listarTodosTest() throws InfraExcecao{
        final List<Evento> lista = bo.listarTodos();
        assertTrue(!lista.isEmpty());
    }

    @Test
    public void inserirTest() throws InfraExcecao{
        evento.setId(null);
        evento = bo.salvar(evento);
        assertNotNull(evento.getId());
        evento = bo.obter(evento.getId());
        assertNotNull(evento);
    }

    @Test
    public void alterarTest() throws InfraExcecao{
        final List<Evento> lista = bo.listarTodos();
        if(!lista.isEmpty()){
            evento = lista.get(0);
            evento.setDescricao("Evento teste alterado");
            bo.salvar(evento);
            evento = bo.obter(evento.getId());
            assertNotNull(evento);
            assertEquals(evento.getDescricao(),"Evento teste alterado");
        }

    }

    @Test
    public void excluirTest() throws InfraExcecao{
        final List<Evento> lista = bo.listarTodos();
        if(!lista.isEmpty()){
            evento = lista.get(0);
            bo.excluir(evento);
            evento = bo.obter(evento.getId());
            assertNull(evento);
        }
    }

    @Test
    public void obterTest()throws InfraExcecao {
        final List<Evento> lista = bo.listarTodos();
        if(!lista.isEmpty()){
            evento = lista.get(0);
            evento = bo.obter(evento.getId());
            assertNotNull(evento);
        }

    }

    @Test
    public void listarPorDescricaoTest() throws InfraExcecao {
        List<Evento> lista = bo.listarTodos();
        if(!lista.isEmpty()){
            evento = lista.get(0);
            lista = bo.listarPorDescricao(evento.getDescricao().substring(0,1));
            assertFalse(lista.isEmpty());
        }
    }

}