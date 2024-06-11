package rocha.andre.api.domain.user.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import rocha.andre.api.domain.gameList.GameListRepository;
import rocha.andre.api.domain.gameRating.GameRatingRepository;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUserRepository;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.domain.profilePic.ProfilePicRepository;
import rocha.andre.api.domain.profilePic.useCase.DeleteProfilePic;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class DeleteUser {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GetUserByTokenJWT getUserByTokenJWT;
    @Autowired
    private ProfilePicRepository profilePicRepository;
    @Autowired
    private DeleteProfilePic deleteProfilePic;
    @Autowired
    private GameListRepository gameListRepository;
    @Autowired
    private GameRatingRepository gameRatingRepository;
    @Autowired
    private ListPermissionUserRepository listPermissionUserRepository;
    @Autowired
    private ListAppRepository listAppRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;

    public void deleteUser(String tokenJWT) {
        var userDTO = getUserByTokenJWT.getUserByID(tokenJWT);
        var user = userRepository.findById(UUID.fromString(userDTO.id()))
                .orElseThrow(() -> new ValidationException("Não foi encontrado usuário com o id informado"));

        transactionTemplate.execute(status -> {
            try {
                var profilePicToDelete = profilePicRepository.findProfilePicByUserId(user.getId());
                var gameListsToDelete = gameListRepository.findAllByUserId(user.getId());
                var gameRatingsToDelete = gameRatingRepository.findAllByUserId(user.getId());
                var listsPermissionUserToDelete = listPermissionUserRepository.findAllByUserId(user.getId());
                var listsAppToDelete = listAppRepository.findAllByUserId(user.getId());


                if (!gameListsToDelete.isEmpty()) {
                    gameListRepository.deleteAll(gameListsToDelete);
                }
                if (!gameListsToDelete.isEmpty()) {
                    gameListRepository.deleteAll(gameListsToDelete);
                }
                if (!gameRatingsToDelete.isEmpty()) {
                    gameRatingRepository.deleteAll(gameRatingsToDelete);
                }
                if (!listsPermissionUserToDelete.isEmpty()) {
                    listPermissionUserRepository.deleteAll(listsPermissionUserToDelete);
                }
                if (!listsAppToDelete.isEmpty()) {
                    listAppRepository.deleteAll(listsAppToDelete);
                }
                if (profilePicToDelete != null) {
                    deleteProfilePic.deleteProfilePic(user.getId());
                }

                userRepository.delete(user);
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException("Ocorreu um erro na transação de delete do jogo e de suas entidades relacionadas", e);
            }
            return null;
        });
    }
}
