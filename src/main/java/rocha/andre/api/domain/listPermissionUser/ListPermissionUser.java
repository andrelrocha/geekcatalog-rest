package rocha.andre.api.domain.listPermissionUser;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserCreateDTO;
import rocha.andre.api.domain.listsApp.ListApp;
import rocha.andre.api.domain.permission.Permission;
import rocha.andre.api.domain.user.User;

import java.util.UUID;

@Table(name = "lists_permission_user")
@Entity(name = "ListPermissionUser")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ListPermissionUser {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User participant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Permission permission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ListApp list;

    public ListPermissionUser(ListPermissionUserCreateDTO data) {
        this.participant = data.participant();
        this.owner = data.owner();
        this.permission = data.permission();
        this.list = data.list();
    }
}
