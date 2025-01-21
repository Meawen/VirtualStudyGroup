package virtualstudygroup.backend.backend.RepoTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import virtualstudygroup.backend.backend.models.Message;
import virtualstudygroup.backend.backend.models.User;
import virtualstudygroup.backend.backend.models.VirtualRoom;
import virtualstudygroup.backend.backend.repo.MessageRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageRepositoryTest {

    @Mock
    private MessageRepository messageRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveMessage() {
        // Mock VirtualRoom
        VirtualRoom virtualRoom = new VirtualRoom();
        virtualRoom.setId(1);
        virtualRoom.setName("Test Room");

        // Mock User
        User user = new User();
        user.setId(1);
        user.setName("Test User");

        // Mock Message
        Message message = new Message();
        message.setId(1);
        message.setVirtualRoom(virtualRoom);
        message.setUser(user);
        message.setContent("Hello World");
        message.setMessageType("TEXT");
        message.setCreatedAt(Instant.now());

        // Mock ponašanje
        when(messageRepository.save(message)).thenReturn(message);

        // Test - save
        Message savedMessage = messageRepository.save(message);
        assertNotNull(savedMessage);
        assertEquals("Hello World", savedMessage.getContent());
        assertEquals("TEXT", savedMessage.getMessageType());
        assertEquals(virtualRoom, savedMessage.getVirtualRoom());
        assertEquals(user, savedMessage.getUser());

        // Verifikacija
        verify(messageRepository, times(1)).save(message);
    }

    @Test
    void testFindAllMessagesInRoom() {
        // Mock VirtualRoom
        VirtualRoom virtualRoom = new VirtualRoom();
        virtualRoom.setId(1);
        virtualRoom.setName("Test Room");

        // Mock Messages
        Message message1 = new Message();
        message1.setId(1);
        message1.setVirtualRoom(virtualRoom);
        message1.setContent("Message 1");
        message1.setMessageType("TEXT");
        message1.setCreatedAt(Instant.now());

        Message message2 = new Message();
        message2.setId(2);
        message2.setVirtualRoom(virtualRoom);
        message2.setContent("Message 2");
        message2.setMessageType("IMAGE");
        message2.setCreatedAt(Instant.now());

        List<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);

        // Mock ponašanje
        when(messageRepository.findAll()).thenReturn(messages);

        // Test - findAll
        List<Message> retrievedMessages = messageRepository.findAll();
        assertNotNull(retrievedMessages);
        assertEquals(2, retrievedMessages.size());
        assertEquals("Message 1", retrievedMessages.get(0).getContent());
        assertEquals("Message 2", retrievedMessages.get(1).getContent());

        // Verifikacija
        verify(messageRepository, times(1)).findAll();
    }

    @Test
    void testFindMessageById() {
        // Mock Message
        Message message = new Message();
        message.setId(1);
        message.setContent("Find by ID Test");
        message.setMessageType("TEXT");
        message.setCreatedAt(Instant.now());

        // Mock ponašanje
        when(messageRepository.findById(1)).thenReturn(java.util.Optional.of(message));

        // Test - findById
        Message retrievedMessage = messageRepository.findById(1).orElse(null);
        assertNotNull(retrievedMessage);
        assertEquals("Find by ID Test", retrievedMessage.getContent());

        // Verifikacija
        verify(messageRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteMessage() {
        // Mock Message
        Message message = new Message();
        message.setId(1);
        message.setContent("To be deleted");

        // Test - delete
        doNothing().when(messageRepository).delete(message);

        messageRepository.delete(message);

        // Verifikacija
        verify(messageRepository, times(1)).delete(message);
    }

    @Test
    void testFindMessagesEmpty() {
        // Mock ponašanje
        when(messageRepository.findAll()).thenReturn(new ArrayList<>());

        // Test - no messages
        List<Message> retrievedMessages = messageRepository.findAll();
        assertNotNull(retrievedMessages);
        assertTrue(retrievedMessages.isEmpty());

        // Verifikacija
        verify(messageRepository, times(1)).findAll();
    }
}
