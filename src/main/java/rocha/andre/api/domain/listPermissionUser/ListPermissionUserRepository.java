package rocha.andre.api.domain.listPermissionUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ListPermissionUserRepository extends JpaRepository<ListPermissionUser, UUID> {
    @Query("""
            SELECT CASE WHEN COUNT(lpu) > 0 THEN true ELSE false END
            FROM ListPermissionUser lpu
            WHERE lpu.participant.id = :participantId
            AND lpu.list.id = :listId
            AND lpu.permission.id = :permissionId
            """)
    boolean existsByParticipantIdAndListIdAndPermissionId(UUID participantId, UUID listId, UUID permissionId);

    @Query("""
            SELECT lpu
            FROM ListPermissionUser lpu
            WHERE lpu.participant.id = :participantId
            AND lpu.list.id = :listId
            AND lpu.permission.id = :permissionId
            """)
    ListPermissionUser findByParticipantIdAndListIdAndPermissionId(UUID participantId, UUID listId, UUID permissionId);

    @Query("""
            SELECT lpu
            FROM ListPermissionUser lpu
            WHERE lpu.participant.id = :participantId
            AND lpu.list.id = :listId
            """)
    List<ListPermissionUser> findAllByParticipantIdAndListId(UUID participantId, UUID listId);
}