package org.example.omok.netty.server.packets

enum class PacketType {
    MESSAGE,
    NOTIFY,
    ATTENDANCE,
    EXIT,
    COORDINATE,
    SET_PLAYER_ID,
    SET_ROOM,
    SET_COLOR,
    MATCH_RESULT,
}