package gov.goias.seven.negocio;

import gov.goias.excecao.InfraExcecao;
import gov.goias.seven.modelo.Evento;
import gov.goias.seven.modelo.Turma;
import gov.goias.seven.persistencia.EventoDAO;
import gov.goias.seven.persistencia.TurmaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EventoBO {

    @Autowired
    private EventoDAO dao;

    @Autowired
    private TurmaDAO turmaDAO;

    public Evento obter(final Long id) throws InfraExcecao{
        final Evento e = dao.obter(id);
        if(e != null){
            e.setTurmas(turmaDAO.listarPorEvento(e));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Evento salvar(final Evento evento) throws InfraExcecao{
        Evento retorno;
        if(evento.getId() == null){
            retorno = dao.incluir(evento);
        }else{
            retorno =  dao.alterar(evento);
        }
        retorno.setTurmas(evento.getTurmas());
        retorno.getTurmas().forEach(t -> t.setEvento(retorno));
        for(final Turma t : evento.getTurmas()) {
            if (t.getId() == null) {
                t.setId(turmaDAO.incluir(t).getId());
            } else {
                turmaDAO.alterar(t);
            }
        }
        final List<Turma> real = turmaDAO.listarPorEvento(evento);//Removendo os removidos na interface
        for(final Turma t : real ){
            if(evento.getTurmas().contains(t)){
                turmaDAO.excluir(t);
            }
        }
        return retorno;
    }

    @Transactional(rollbackFor = Exception.class)
    public void excluir(final Evento evento) throws InfraExcecao{
        final Evento e = dao.obter(evento.getId());
        for (Turma t : e.getTurmas()){
            turmaDAO.excluir(t);
        }
        dao.excluir(evento);
    }

    public List<Evento> listarTodos() throws InfraExcecao{
        return dao.listarTodos();
    }

    public List<Evento> listarPorDescricao(final String descricao) throws InfraExcecao{
        return dao.listarPorDescricao(descricao);
    }

}