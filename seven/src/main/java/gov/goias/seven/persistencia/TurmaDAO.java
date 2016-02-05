package gov.goias.seven.persistencia;

import gov.goias.excecao.InfraExcecao;
import gov.goias.seven.modelo.Evento;
import gov.goias.seven.modelo.Turma;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TurmaDAO {
    private final Logger logger = Logger.getLogger(TurmaDAO.class);
    
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private MapSqlParameterSource getParametros(){
        return null; //TODO:Testar usar isso
    }

    private Turma getTurma(ResultSet rs, int i) throws SQLException {
        final Turma t = new Turma();
        t.setId(rs.getLong("TURM_ID"));
        t.setInicio(rs.getDate("TURM_INICIO"));
        t.setFim(rs.getDate("TURM_FIM"));
        t.setEvento(new Evento());
        t.getEvento().setId(rs.getLong("EVEN_ID"));
        return t;
    }

    public Turma obter(Long id)throws InfraExcecao {
        final String sql = "SELECT T.TURM_ID, T.TURM_INICIO, T.TURM_FIM, T.EVEN_ID, E.EVEN_DESCRICAO FROM TB_TURMA T INNER JOIN TB_EVENTO ON E.EVEN_ID = T.EVEN_ID WHERE T.TURM_ID = :id";
        final MapSqlParameterSource param = new MapSqlParameterSource();
        Turma retorno;
        try {
            param.addValue("id",id);
            retorno = jdbcTemplate.queryForObject(sql, param, this::getTurma);
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

    public Turma incluir(final Turma turma) throws InfraExcecao{
        final String sql = "INSERT INTO TURMA(TURM_INICIO,TURM_FIM,EVEN_ID)VALUES(:inicio,:fim:eventoId)";
        final MapSqlParameterSource param = new MapSqlParameterSource();
        final KeyHolder gen = new GeneratedKeyHolder();
        try {
            param.addValue("inicio", turma.getInicio());
            param.addValue("fim"   , turma.getFim());
            param.addValue("eventoId",turma.getEvento().getId());
            jdbcTemplate.update(sql,param,gen);
            final Number id = (Number)gen.getKeys().get("turm_id");
            turma.setId(id.longValue());
        }catch(Exception e){
            logger.debug(sql);
            logger.debug(param.getValues());
            logger.error(e);
            throw new InfraExcecao(e);
        }
        return turma;
    }

    public Turma alterar(Turma turma) throws InfraExcecao{
        final String sql = "UPDATE TB_TURMA SET TURM_INICIO = :inicio, TURM_FIM = :fim, EVEN_ID = :eventoId WHERE TURM_ID = :id";
        final MapSqlParameterSource param = new MapSqlParameterSource();
        try {
            param.addValue("id",turma.getId());
            param.addValue("inicio", turma.getInicio());
            param.addValue("fim"   , turma.getFim());
            param.addValue("eventoId",turma.getEvento().getId());
            jdbcTemplate.update(sql,param);
        }catch(Exception e){
            logger.debug(sql);
            logger.debug(param.getValues());
            logger.error(e);
            throw new InfraExcecao(e);
        }
        return turma;
    }

    public void excluir(Turma turma) throws InfraExcecao{
        final String sql = "DELETE FROM TB_TURMA WHERE TURM_ID = :id";
        final MapSqlParameterSource param = new MapSqlParameterSource();
        try {
            param.addValue("id",turma.getId());
            jdbcTemplate.update(sql,param );
        } catch (final Exception e) {
            logger.debug(sql);
            logger.debug(param.getValues());
            logger.error(e);
            throw new InfraExcecao(e);
        }
    }

    public List<Turma> listarPorEvento(final Evento evento) throws InfraExcecao{
        final String sql = "SELECT TURM_ID, TURM_INICIO, TURM_FIM, EVEN_ID FROM TB_TURMA WHERE EVEN_ID = :idEvento";
        final MapSqlParameterSource param = new MapSqlParameterSource();
        List<Turma> retorno;
        try {
            param.addValue("idEvento",evento.getId());
            retorno = jdbcTemplate.query(sql, this::getTurma);
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