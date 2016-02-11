package gov.goias.seven.negocio;

import gov.goias.seven.modelo.Evento;
import gov.goias.seven.modelo.Turma;
import gov.goias.seven.persistencia.EventoDAO;
import gov.goias.seven.persistencia.TurmaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EventoBO {

    @Autowired
    private EventoDAO dao;

    @Autowired
    private TurmaDAO turmaDAO;

    public Evento obter(final Long id){
        final Optional<Evento> e = Optional.ofNullable(dao.obter(id));
        e.ifPresent(ev -> ev.setTurmas(turmaDAO.listarPorEvento(ev)));
        return e.orElse(null);
    }

    @Transactional(rollbackFor = Exception.class)
    public Evento salvar(final Evento evento){
        final Optional<Long> id = Optional.ofNullable(evento.getId());
        (id.isPresent() == true ? dao.alterar(evento) : dao.incluir(evento)).longValue();
        evento.getTurmas().forEach(t -> t.setEvento(evento));
        evento.getTurmas().stream().filter(t -> t.getId() == null).forEach(t -> turmaDAO.incluir(t));
        evento.getTurmas().stream().filter(t -> t.getId() != null).forEach(t -> turmaDAO.alterar(t));
        final List<Turma> real = turmaDAO.listarPorEvento(evento);//Removendo os removidos na interface
        real.stream().filter(to-> !evento.getTurmas().contains(to)).forEach(t -> turmaDAO.excluir(t));
        return  evento;
    }

    @Transactional(rollbackFor = Exception.class)
    public void excluir(final Evento evento){
        final Evento e = dao.obter(evento.getId());
        e.setTurmas(turmaDAO.listarPorEvento(e));
        e.getTurmas().forEach( t -> turmaDAO.excluir(t));
        dao.excluir(evento);
    }

    public List<Evento> listarTodos(){
        final List<Evento> l = dao.listarTodos();
        for(Evento e : l){
            e.setTurmas(turmaDAO.listarPorEvento(e));
        }
        return l;
    }

    public List<Evento> listarPorDescricao(final String descricao){
        return dao.listarPorDescricao(descricao);
    }

}