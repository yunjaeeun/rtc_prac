package moja.back.repo;

import moja.back.entity.Room;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RoomRepository {
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();

    public List<Room> findAllRooms() {
        return new ArrayList<>(rooms.values());
    }

    public Room createRoom(String name) {
        String roomId = UUID.randomUUID().toString();

        Room room = new Room(roomId, name, new ArrayList<>());
        rooms.put(roomId, room);
        return  room;
    }

    public Room findRoomById(String roomId) {
        return rooms.get(roomId);
    }
}
