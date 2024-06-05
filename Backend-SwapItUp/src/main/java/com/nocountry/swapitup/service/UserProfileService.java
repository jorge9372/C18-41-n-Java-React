package com.nocountry.swapitup.service;

import com.nocountry.swapitup.dto.UserProfileDto;
import com.nocountry.swapitup.exception.NotFoundDataException;
import com.nocountry.swapitup.model.Profile;
import com.nocountry.swapitup.model.User;
import com.nocountry.swapitup.repository.ProfileRepository;
import com.nocountry.swapitup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.nocountry.swapitup.utils.infoTokenUtils.getUsernameToken;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public UserProfileDto getProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundDataException("Usuario " + username + "no ha sido encontrado"));
        Profile profile = profileRepository.findByUser_IdUser(user.getIdUser()).orElse(null);
        return UserProfileDto.builder()
                .name(user.getName())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .gender(profile.getGender())
                .country(profile.getCountry())
                .timeZone(profile.getTimeZone())
                .lenguage(profile.getLenguage())
                .aboutMe(profile.getAboutMe())
                .skills(profile.getSkills())
                .experience(profile.getExperience())
                .link_linkedin(profile.getLink_portfolio())
                .link_portfolio(profile.getLink_portfolio())
                .image(profile.getImage())
                .build();
    }

    public User updateUserInfo(String username, User user) {
        User newUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundDataException("Usuario " + username + "no ha sido encontrado"));
        if (newUser.getUsername().equals(getUsernameToken())) {
            newUser.setName(user.getName());
            newUser.setLastname(user.getLastname());
            return userRepository.save(newUser);
        }
        return null;
    }

    public Profile updateProfileInfo(Integer id, Profile profile) {
        Profile newprofile = profileRepository.findByUser_IdUser(id)
                .orElseThrow(() -> new NotFoundDataException("Usuario con el " + id + "no ha sido encontrado"));
        User user = userRepository.findById(id).orElse(null);
        if (user.getUsername().equals(getUsernameToken())) {
            newprofile.setGender(profile.getGender());
            newprofile.setCountry(profile.getCountry());
            newprofile.setTimeZone(profile.getTimeZone());
            newprofile.setLenguage(profile.getLenguage());
            newprofile.setIndustry(profile.getIndustry());
            newprofile.setAboutMe(profile.getAboutMe());
            newprofile.setSkills(profile.getSkills());
            newprofile.setExperience(profile.getExperience());
            newprofile.setLink_linkedin(profile.getLink_linkedin());
            newprofile.setLink_portfolio(profile.getLink_portfolio());
            return profileRepository.save(newprofile);
        }
        return null;
    }

    public String eliminarUsuario(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundDataException("Usuario " + username + "no ha sido encontrado"));
        if (user.getUsername().equals(getUsernameToken())) {
            userRepository.deleteById(user.getIdUser());
            return "El usuario junto con su perfil han ido eliminado con exito";
        } else {
            return "El usuario no ha sido encontrado";
        }
    }

}