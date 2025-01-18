package moja.back.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private String roomId;
    private String name;
    private List<String> participants = new ArrayList<>();
}
