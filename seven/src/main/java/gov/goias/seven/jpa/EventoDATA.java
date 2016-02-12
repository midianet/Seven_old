package gov.goias.seven.jpa;

import gov.goias.seven.entidade.EEvento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoDATA extends JpaRepository<EEvento, Long> {

}