package gov.goias.seven.persistencia;

import gov.goias.seven.modelo.Evento;
import gov.goias.seven.modelo.Turma;
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

import java.util.ArrayList;
import java.util.Date;
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
        evento.setDescricao("Evento teste");
        Turma t = new Turma();
        t.setInicio(new Date());
        t.setFim   (new Date());
        evento.setTurmas(new ArrayList());
        evento.getTurmas().add(t);
    }

    @Test
    public void listarTodosTest(){
        assertFalse(bo.listarTodos().isEmpty());
    }

    @Test
    public void inserirTest(){
        evento = bo.salvar(evento);
        assertNotNull(evento.getId());
        evento = bo.obter(evento.getId());
        assertNotNull(evento);
    }

    @Test
    public void alterarTest(){
        final List<Evento> lista = bo.listarTodos();
        if(!lista.isEmpty()) {
            evento = lista.get(0);
            evento.setDescricao("Evento teste alterado");
            evento.getTurmas().get(0).setFim(new Date());
            bo.salvar(evento);
            evento = bo.obter(evento.getId());
            assertNotNull(evento);
            assertEquals(evento.getDescricao(), "Evento teste alterado");
        }
    }

    @Test
    public void excluirTest(){
        final List<Evento> lista = bo.listarTodos();
        if(!lista.isEmpty()){
            evento = lista.get(0);
            bo.excluir(evento);
            evento = bo.obter(evento.getId());
            assertNull(evento);
        }
    }

    @Test
    public void obterTest(){
        final List<Evento> lista = bo.listarTodos();
        if(!lista.isEmpty()){
            evento = lista.get(0);
            evento = bo.obter(evento.getId());
            assertNotNull(evento);
        }
    }

    @Test
    public void listarPorDescricaoTest(){
        List<Evento> lista = bo.listarTodos();
        if(!lista.isEmpty()){
            evento = lista.get(0);
            lista = bo.listarPorDescricao(evento.getDescricao().substring(0,1));
            assertFalse(lista.isEmpty());
        }
    }

}