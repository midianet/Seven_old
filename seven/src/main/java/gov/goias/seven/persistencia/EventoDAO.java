package gov.goias.seven.persistencia;

import gov.goias.excecao.InfraExcecao;
import gov.goias.seven.modelo.Evento;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class EventoDAO {
    private final Logger logger = Logger.getLogger(EventoDAO.class);
    
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private MapSqlParameterSource getParametros(){
        return null; //TODO:Testar usar isso
    }

    private Evento getEvento(ResultSet rs, int i) throws SQLException {
        final Evento e = new Evento();
        e.setId(rs.getLong("EVEN_ID"));
        e.setDescricao(rs.getString("EVEN_DESC"));
        e.setDataInicio(rs.getDate("EVEN_INICIO"));
        e.setDataFim(rs.getDate("EVEN_FIM"));
        return e;
    }

    public Evento obterPorId(Long id)throws InfraExcecao {
        final String sql = "SELECT EVEN_ID,EVEN_DESC,EVEN_INICIO,EVEN_FIM FROM EVENTO WHERE EVEN_ID = :id";
        final MapSqlParameterSource param = new MapSqlParameterSource();
        Evento retorno;
        try {
            param.addValue("id",id);
            retorno = jdbcTemplate.queryForObject(sql, param, this::getEvento);
        }catch(EmptyResultDataAccessException e){
            retorno = null;
        }catch(Exception e){
            logger.debug(sql);
            logger.debug(param.getValues());
            logger.error(e);
            throw new InfraExcecao(e);
        }
        return retorno;
    }

    public Evento incluir(final Evento evento) throws InfraExcecao{
        final String sql = "INSERT INTO EVENTO(EVEN_DESC,EVEN_INICIO,EVEN_FIM)VALUES(:id,:desc,:inicio,:fim)";
        final MapSqlParameterSource param = new MapSqlParameterSource();
        final KeyHolder id = new GeneratedKeyHolder();
        try {
            param.addValue("desc", evento.getDescricao());
            param.addValue("inicio", evento.getDataInicio());
            param.addValue("fim", evento.getDataFim());
            jdbcTemplate.update(sql,param,id);
            evento.setId((Long) id.getKey());
        }catch(Exception e){
            logger.debug(sql);
            logger.debug(param.getValues());
            logger.error(e);
            throw new InfraExcecao(e);
        }
        return evento;
    }

    public Evento alterar(Evento evento) throws InfraExcecao{
        final String sql = "UPDATE EVENTO SET EVEN_DESC = :desc , EVEN_INICIO = :inicio ,EVEN_FIM = :fim WHERE EVEN_ID = :id";
        final MapSqlParameterSource param = new MapSqlParameterSource();
        try {
            param.addValue("id", evento.getId());
            param.addValue("desc", evento.getDescricao());
            param.addValue("inicio", evento.getDataInicio());
            param.addValue("fim", evento.getDataFim());
            jdbcTemplate.update(sql,param);
        }catch(Exception e){
            logger.debug(sql);
            logger.debug(param.getValues());
            logger.error(e);
            throw new InfraExcecao(e);
        }
        return evento;
    }

    public void excluir(Evento evento) throws InfraExcecao{
        final String sql = "DELETE FROM EVENTO WHERE EVEN_ID = :id";
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
        final String sql = "SELECT EVEN_ID,EVEN_DESC,EVEN_INICIO,EVEN_FIM FROM EVENTO";
        List<Evento> retorno;
        try {
            retorno = jdbcTemplate.query(sql, this::getEvento);
        }catch(EmptyResultDataAccessException e){
            retorno = null;
        }catch(Exception e){
            logger.debug(sql);
            logger.error(e);
            throw new InfraExcecao(e);
        }
        return retorno;
    }

    public List<Evento> listarPorDescricao(final String descricao) throws InfraExcecao{
        final String sql = "SELECT EVEN_ID,EVEN_DESC,EVEN_INICIO,EVEN_FIM FROM EVENTO WHERE EVEN_DESC LIKE :filtro";
        final MapSqlParameterSource param = new MapSqlParameterSource();
        List<Evento> retorno;
        try {
            param.addValue("filtro","%".concat(descricao).concat("%"));
            retorno = jdbcTemplate.query(sql,this::getEvento);
        }catch(EmptyResultDataAccessException e){
            retorno = null;
        }catch(Exception e){
            logger.debug(sql);
            logger.error(e);
            throw new InfraExcecao(e);
        }
        return retorno;
    }

    public List<Evento> listarPorData(final Date data) throws InfraExcecao{
        final String sql = "SELECT EVEN_ID,EVEN_DESC,EVEN_INICIO,EVEN_FIM FROM EVENTO WHERE EVEN_DATA_INICIO >= :data OR EVEN_DATA_FIM <= :data";
        final MapSqlParameterSource param = new MapSqlParameterSource();
        List<Evento> retorno;
        try {
            param.addValue("data",data);
            retorno = jdbcTemplate.query(sql,this::getEvento);
        }catch(EmptyResultDataAccessException e){
            retorno = null;
        }catch(Exception e){
            logger.debug(sql);
            logger.error(e);
            throw new InfraExcecao(e);
        }
        return retorno;
    }

}