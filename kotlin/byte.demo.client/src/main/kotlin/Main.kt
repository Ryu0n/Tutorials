package org.example

import org.example.packet.Packet
import org.example.packet.deserialize
import org.example.packet.serialize
import java.net.Socket


//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
fun main() {
    val buffer = ByteArray(32)
    val socket = Socket("localhost", 9090)
    val outputStream = socket.outputStream
    val inputStream = socket.inputStream

    val packet = Packet("Hello, Server!")
    val bytes = serialize(packet)
    outputStream.write(bytes)
    println("Sent bytes: ${bytes.joinToString(", ")}")

    while (true) {
        val byteRead = inputStream.read(buffer)
        if (byteRead == -1) {
            println("No more data to read.")
            break
        }
        val convertedMessageByte = buffer.copyOf(byteRead)
        val packet = deserialize(convertedMessageByte)
        println("Received message: ${packet.message}")
    }
}