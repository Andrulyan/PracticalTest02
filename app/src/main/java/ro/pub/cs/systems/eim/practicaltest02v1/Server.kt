package ro.pub.cs.systems.eim.practicaltest02v1
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

class Server(private val port: Int) {

    fun start() {
        val serverSocket = ServerSocket(port)
        println("Server started on port $port")

        while (true) {
            val clientSocket: Socket = serverSocket.accept()
            thread {
                val input = clientSocket.getInputStream().bufferedReader()
                val output = clientSocket.getOutputStream().bufferedWriter()

                val request = input.readLine()
                val response = "Rezultatul pentru $request este X"
                output.write(response)
                output.flush()

                clientSocket.close()
            }
        }
    }
}
