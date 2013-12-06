
class MessageService {
    Map messages = [:]

    def post(String username, String message) {
        if (!messages[username]) {
            messages[username] = []
        }
        messages[username] << message
        return true
    }

    def getMessagesFrom(String username) {
        return messages[username]?:[]
    }
}
