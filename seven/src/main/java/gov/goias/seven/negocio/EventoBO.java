package gov.goias.seven.negocio;

import gov.goias.excecao.InfraExcecao;
import gov.goias.seven.modelo.Evento;
import gov.goias.seven.persistencia.EventoDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class EventoBO {

    @Autowired
    private EventoDAO dao;

    public Evento obterPorId(Long id) throws InfraExcecao{
        return dao.obterPorId(id);
    }

    public Evento salvar(final Evento evento) throws InfraExcecao{
        Evento retorno;
        if(evento.getId() == null){
            retorno = dao.incluir(evento);
        }else{
           retorno =  dao.alterar(evento);
        }
        return retorno;
    }

    public void excluir(final Evento evento) throws InfraExcecao{
        dao.excluir(evento);
    }

    public List<Evento> listarTodos() throws InfraExcecao{
        return dao.listarTodos();
    }

    public List<Evento> listarPorDescricao(final String descricao) throws InfraExcecao{
        return dao.listarPorDescricao(descricao);
    }

    public List<Evento> listarPorData(final Date data) throws InfraExcecao{
        return dao.listarPorData(data);
    }

}