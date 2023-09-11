package com.winnow.bestchoice.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private String roomId;
    private long userCount; // 채팅방 인원수

    public static ChatRoom create(String roomId) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = roomId;

        return chatRoom;
    }

}
