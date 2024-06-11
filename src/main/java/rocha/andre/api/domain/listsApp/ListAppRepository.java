package rocha.andre.api.domain.listsApp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ListAppRepository extends JpaRepository<ListApp, UUID> {
    @Query("""
        SELECT CASE WHEN COUNT(la) > 0 THEN true ELSE false END
        FROM ListApp la 
        WHERE LOWER(TRIM(la.name)) = LOWER(TRIM(:name)) AND la.user.id = :userId
        """)
    boolean existsByName(String name, UUID userId);

    @Query("""
            SELECT la FROM ListApp la
            WHERE la.user.id = :userId
            """)
    Page<ListApp> findAllListsByUserId(Pageable pageable, UUID userId);

    @Query("""
            SELECT la FROM ListApp la
            WHERE la.user.id = :userId
            AND la.visibility = true
            """)
    Page<ListApp> findAllListsPublicByUserId(Pageable pageable, UUID userId);


    @Query("""
            SELECT la FROM ListApp la
            WHERE la.id = :id
            """)
    ListApp findByIdToHandle(UUID id);

    @Query("""
            SELECT la FROM ListApp la
            WHERE la.user.id = :userId
            ORDER BY la.name ASC 
            """)
    Page<ListApp> findAllListsByUserIDOrderedByName(Pageable pageable, UUID userId);

    @Query("""
        SELECT la FROM ListApp la
        WHERE la.user.id = :userId
        AND LOWER(la.name) LIKE LOWER(CONCAT('%', :nameCompare, '%'))
        """)
    Page<ListApp> findListsByUserIdAndByNameContaining(String nameCompare, Pageable pageable, UUID userId);

    @Query("""
        SELECT la FROM ListApp la
        WHERE la.id IN :ids
        """)
    Page<ListApp> findAllListsAppById(List<UUID> ids, Pageable pageable);

    @Query("""
            SELECT la FROM ListApp la
            WHERE la.user.id = :userId
           """)
    List<ListApp> findAllByUserId(UUID userId);
}