package server.database;

import commons.misc.ActivityImage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Database repository storing images of activities.
 * <p>
 * The IDs of the entries are string IDs of activities (e.g. 00-shower).
 */
public interface ActivityImageRepository extends JpaRepository<ActivityImage, String> {
}
