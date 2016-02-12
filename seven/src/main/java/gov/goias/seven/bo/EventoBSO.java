package gov.goias.seven.bo;

import gov.goias.seven.entidade.EEvento;
import gov.goias.seven.jpa.EventoDATA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class EventoBSO {

    @Autowired
    private EventoDATA dao;

    public EEvento obter(final Long id) {
        try {
            return dao.findOne(id);
        }catch(Exception e){
            return null;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public EEvento salvar(final EEvento evento) {
        return dao.saveAndFlush(evento);
    }

    @Transactional(rollbackFor = Exception.class)
    public void excluir(final EEvento evento) {
        dao.delete(evento);
    }

    public List<EEvento> listarTodos() {
        return dao.findAll();
    }

}