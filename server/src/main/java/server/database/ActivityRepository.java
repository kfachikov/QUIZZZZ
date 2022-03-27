package server.database;

import commons.misc.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Database repository of Activity entities.
 */
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
