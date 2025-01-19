package moja.back.controller;

import moja.back.dto.RoomRequest;
import moja.back.entity.Room;
import moja.back.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Room createRoom(@RequestBody RoomRequest roomRequest) {
        return roomRepository.createRoom(roomRequest.getName());
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomRepository.findAllRooms();
    }
}
