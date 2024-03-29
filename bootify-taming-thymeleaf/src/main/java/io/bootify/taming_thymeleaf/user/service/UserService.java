package io.bootify.taming_thymeleaf.user.service;

import io.bootify.taming_thymeleaf.team.domain.Team;
import io.bootify.taming_thymeleaf.team.repos.TeamRepository;
import io.bootify.taming_thymeleaf.team_player.domain.TeamPlayer;
import io.bootify.taming_thymeleaf.team_player.repos.TeamPlayerRepository;
import io.bootify.taming_thymeleaf.user.domain.User;
import io.bootify.taming_thymeleaf.user.model.UserDTO;
import io.bootify.taming_thymeleaf.user.repos.UserRepository;
import io.bootify.taming_thymeleaf.util.NotFoundException;
import io.bootify.taming_thymeleaf.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeamRepository teamRepository;
    private final TeamPlayerRepository teamPlayerRepository;

    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder,
            final TeamRepository teamRepository, final TeamPlayerRepository teamPlayerRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.teamRepository = teamRepository;
        this.teamPlayerRepository = teamPlayerRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setGender(user.getGender());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setBirthday(user.getBirthday());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setGender(userDTO.getGender());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setBirthday(userDTO.getBirthday());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return user;
    }

    public boolean emailExists(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Team coachTeam = teamRepository.findFirstByCoach(user);
        if (coachTeam != null) {
            referencedWarning.setKey("user.team.coach.referenced");
            referencedWarning.addParam(coachTeam.getId());
            return referencedWarning;
        }
        final TeamPlayer playerTeamPlayer = teamPlayerRepository.findFirstByPlayer(user);
        if (playerTeamPlayer != null) {
            referencedWarning.setKey("user.teamPlayer.player.referenced");
            referencedWarning.addParam(playerTeamPlayer.getId());
            return referencedWarning;
        }
        return null;
    }

}
