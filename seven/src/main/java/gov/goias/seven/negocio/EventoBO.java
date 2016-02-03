package gov.goias.seven.negocio;

import gov.goias.excecao.InfraExcecao;
import gov.goias.seven.modelo.Evento;
import gov.goias.seven.persistencia.EventoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventoBO {

    @Autowired
    private EventoDAO dao;

    public Evento obter(final Long id) throws InfraExcecao{
        return dao.obter(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Evento salvar(final Evento evento) throws InfraExcecao{
        Evento retorno;
        if(evento.getId() == null){
            retorno = dao.incluir(evento);
        }else{
           retorno =  dao.alterar(evento);
        }
        return retorno;
    }

    @Transactional(rollbackFor = Exception.class)
    public void excluir(final Evento evento) throws InfraExcecao{
        dao.excluir(evento);
    }

    public List<Evento> listarTodos() throws InfraExcecao{
        return dao.listarTodos();
    }

    public List<Evento> listarPorDescricao(final String descricao) throws InfraExcecao{
        return dao.listarPorDescricao(descricao);
    }

}