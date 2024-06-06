package com.nocountry.swapitup.service;

import com.nocountry.swapitup.dto.*;
import com.nocountry.swapitup.enums.StatusName;
import com.nocountry.swapitup.exception.NotFoundDataException;
import com.nocountry.swapitup.model.Meeting;
import com.nocountry.swapitup.model.Profile;
import com.nocountry.swapitup.model.Tutor;
import com.nocountry.swapitup.repository.MeetingRepository;
import com.nocountry.swapitup.repository.ProfileRepository;
import com.nocountry.swapitup.repository.TutorRepository;
import com.nocountry.swapitup.utils.MapTemplatesMeetings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.nocountry.swapitup.utils.infoTokenUtils.getUsernameToken;

@Service
@RequiredArgsConstructor
public class StudentMeetingService {

    private final ProfileRepository profileRepository;
    private final TutorRepository tutorRepository;
    private final MeetingRepository meetingRepository;

    public Meeting requestExchange(Integer idTutor, PendingMeetingDto meetingDTO) {
        Profile profile = profileRepository.findByUser_Username(getUsernameToken())
                .orElseThrow(() -> new NotFoundDataException("Usuario " + getUsernameToken() + "no ha sido encontrado"));
        Tutor tutor = tutorRepository.findById(idTutor)
                .orElseThrow(() -> new NotFoundDataException("Tutor " + idTutor + "no ha sido encontrado"));
        Meeting meeting = Meeting.builder()
                .fullname(profile.getUser().getName() + " " + profile.getUser().getLastname())
                .username(profile.getUser().getUsername())
                .image(profile.getImage())
                .message(meetingDTO.getMessage())
                .schule(meetingDTO.getSchule())
                .date(meetingDTO.getDate())
                .status(StatusName.PENDIENTES)
                .tutor(tutor)
                .build();
        tutor.addMeeting(meeting);
        tutorRepository.save(tutor);
        return meeting;
    }

    public Meeting endMeeting(Integer idMeeting, ScoreMeeting scoreMeeting) {
        Meeting meeting = meetingRepository.findById(idMeeting)
                .orElseThrow(() -> new NotFoundDataException("Reunión con " + idMeeting + " no ha sido encontrado"));
        if (meeting.getStatus().equals(StatusName.PROXIMAS)) {
            meeting.setStatus(StatusName.HISTORIAL);
            meeting.setMeetingScore(scoreMeeting.getMeetingScore());
            meetingRepository.save(meeting);
            return meeting;
        }
        return null;
    }

    //TODO: Listado de Reuniones de los Estudiantes

    public List<UpcomingMeetingDto> getUpcomingMeetingsByStudent(String username) {
        List<Meeting> allMeetings = meetingRepository.findByUsername(username);
        return allMeetings.stream()
                .filter(meeting -> meeting.getStatus() == StatusName.PROXIMAS)
                .map(MapTemplatesMeetings::mapToUpcomingMeetingDTO)
                .collect(Collectors.toList());
    }

    public List<PendingMeetingDto> getPendingMeetingsByStudent(String username) {
        List<Meeting> allMeetings = meetingRepository.findByUsername(username);
        return allMeetings.stream()
                .filter(meeting -> meeting.getStatus() == StatusName.PENDIENTES)
                .map(MapTemplatesMeetings::mapToPendingMeetingDTO)
                .collect(Collectors.toList());
    }

    public List<HistoryMeetingDto> getHistoryMeetingsByStudent(String username) {
        List<Meeting> allMeetings = meetingRepository.findByUsername(username);
        return allMeetings.stream()
                .filter(meeting -> meeting.getStatus() == StatusName.HISTORIAL)
                .map(MapTemplatesMeetings::mapToHistoryMeetingDTO)
                .collect(Collectors.toList());
    }

}