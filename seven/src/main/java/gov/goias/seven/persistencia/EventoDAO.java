package gov.goias.seven.persistencia;

import gov.goias.excecao.InfraExcecao;
import gov.goias.seven.modelo.Evento;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class EventoDAO {
    private final Logger logger = Logger.getLogger(EventoDAO.class);
    
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private Evento getEvento(final ResultSet rs, final int i) throws SQLException {
        final Evento e = new Evento();
        e.setId(rs.getLong("EVEN_ID"));
        e.setDescricao(rs.getString("EVEN_DESCRICAO"));
        return e;
    }

    public Evento obter(Long id)throws InfraExcecao {
        final String sql = "SELECT EVEN_ID,EVEN_DESCRICAO FROM TB_EVENTO WHERE EVEN_ID = :id";
        final MapSqlParameterSource param = new MapSqlParameterSource();
        Evento retorno;
        try {
            param.addValue("id", id);
            retorno = jdbcTemplate.queryForObject(sql, param, this::getEvento);
        } catch (DataAccessException e) {
            logger.debug(sql);
            logger.debug(param.getValues());
            logger.error(e);
            throw new InfraExcecao(e);
        }
        return retorno;
    }

    public Evento incluir(final Evento evento) throws InfraExcecao{
        final String sql = "INSERT INTO TB_EVENTO(EVEN_DESCRICAO)VALUES(:desc)";
        final MapSqlParameterSource param = new MapSqlParameterSource();
        final KeyHolder gen = new GeneratedKeyHolder();
        try {
            param.addValue("desc", evento.getDescricao());
            jdbcTemplate.update(sql,param,gen);
            final Number id = (Number)gen.getKeys().get("even_id");
            evento.setId(id.longValue());
        }catch(Exception e){
            logger.debug(sql);
            logger.debug(param.getValues());
            logger.error(e);
            throw new InfraExcecao(e);
        }
        return evento;
    }

    public Evento alterar(final Evento evento) throws InfraExcecao{
        final String sql = "UPDATE TB_EVENTO SET EVEN_DESCRICAO = :desc WHERE EVEN_ID = :id";
        final MapSqlParameterSource param = new MapSqlParameterSource();
        try {
            param.addValue("id", evento.getId());
            param.addValue("desc", evento.getDescricao());
            jdbcTemplate.update(sql,param);
        }catch(Exception e){
            logger.debug(sql);
            logger.debug(param.getValues());
            logger.error(e);
            throw new InfraExcecao(e);
        }
        return evento;
    }

    public void excluir(final Evento evento) throws InfraExcecao{
        final String sql = "DELETE FROM TB_EVENTO WHERE EVEN_ID = :id";
        final MapSqlParameterSource param = new MapSqlParameterSource();
        try {
            param.addValue("id", evento.getId());
            jdbcTemplate.update(sql,param );
        } catch (final Exception e) {
            logger.debug(sql);
            logger.debug(param.getValues());
            logger.error(e);
            throw new InfraExcecao(e);
        }
    }

    public List<Evento> listarTodos() throws InfraExcecao{
        final String sql = "SELECT EVEN_ID,EVEN_DESCRICAO FROM TB_EVENTO";
        List<Evento> retorno;
        try {
            retorno = jdbcTemplate.query(sql, this::getEvento);
        }catch(EmptyResultDataAccessException e){
            retorno = new ArrayList();
        }catch(Exception e){
            logger.debug(sql);
            logger.error(e);
            throw new InfraExcecao(e);
        }
        return retorno;
    }

    public List<Evento> listarPorDescricao(final String descricao) throws InfraExcecao{
        final String sql = "SELECT EVEN_ID,EVEN_DESCRICAO FROM TB_EVENTO WHERE EVEN_DESCRICAO LIKE :filtro";
        final MapSqlParameterSource param = new MapSqlParameterSource();
        List<Evento> retorno;
        try {
            param.addValue("filtro","%".concat(descricao).concat("%"),Types.VARCHAR); //".concat(descricao).concat("
            retorno = jdbcTemplate.query(sql,param,this::getEvento);
        }catch(EmptyResultDataAccessException e){
            retorno = new ArrayList();
        }catch(Exception e){
            logger.debug(sql);
            logger.debug(param.getValues());
            logger.error(e);
            throw new InfraExcecao(e);
        }
        return retorno;
    }

}