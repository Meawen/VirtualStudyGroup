package virtualstudygroup.backend.backend.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import virtualstudygroup.backend.backend.controllers.StudyGroupController;
import virtualstudygroup.backend.backend.models.StudyGroup;
import virtualstudygroup.backend.backend.service.StudyGroupService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudyGroupControllerTest {

    @InjectMocks
    private StudyGroupController studyGroupController;

    @Mock
    private StudyGroupService studyGroupService;

    @Test
    public void testCheckAccessToGroup_Success() {
        StudyGroup group = new StudyGroup();
        group.setId(1);
        group.setName("Math Group");

        when(studyGroupService.getGroupIfUserHasAccess(1, 2)).thenReturn(Optional.of(group));

        ResponseEntity<StudyGroup> response = studyGroupController.checkAccessToGroup(1, 2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Math Group", response.getBody().getName());
    }

    @Test
    public void testCheckAccessToGroup_Forbidden() {
        when(studyGroupService.getGroupIfUserHasAccess(1, 2)).thenReturn(Optional.empty());

        ResponseEntity<StudyGroup> response = studyGroupController.checkAccessToGroup(1, 2);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetAllPublicStudyGroups() {
        List<StudyGroup> groups = List.of(new StudyGroup(), new StudyGroup());
        when(studyGroupService.findAllPublic()).thenReturn(groups);

        ResponseEntity<List<StudyGroup>> response = studyGroupController.getAllPublicStudyGroups();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetPrivateGroupsForUser() {
        List<StudyGroup> groups = List.of(new StudyGroup(), new StudyGroup());
        when(studyGroupService.findPrivateGroupsByUser(1)).thenReturn(groups);

        ResponseEntity<List<StudyGroup>> response = studyGroupController.getPrivateGroupsForUser(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testCreateStudyGroup() {
        StudyGroup newGroup = new StudyGroup();
        newGroup.setId(1);
        newGroup.setName("Science Group");

        when(studyGroupService.create(any(StudyGroup.class))).thenReturn(newGroup);

        ResponseEntity<StudyGroup> response = studyGroupController.createStudyGroup(newGroup);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Science Group", response.getBody().getName());
    }
}
