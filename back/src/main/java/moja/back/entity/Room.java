package moja.back.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private String roomId;
    private String name;
    private List<String> participants;

    // 기본 생성자 필요
    public Room() {}

    // 모든 필드 포함 생성자
    public Room(String roomId, String name, List<String> participants) {
        this.roomId = roomId;
        this.name = name;
        this.participants = participants;
    }

    @JsonProperty("roomId") // JSON 필드 이름 명시
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("participants")
    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }
}
