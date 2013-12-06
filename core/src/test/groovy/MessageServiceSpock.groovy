import spock.lang.*

class MessageServiceSpock extends Specification {
    def messageService
    def userStore

    void setup() {
        Locator.initialize("memory")
        userStore = Locator.instance.userStore
        messageService = new MessageService()
    }

    def 'some user can post a message'() {
        when:
            def result = messageService.post(username, message)

        then:
            result == true

        where:
            username = "@alotor"
            message = "hellow world"
    }

    def 'you can check the messages from user'() {
        setup:
            messageService.post(username, message1)
            messageService.post(username, message2)

        when:
            def result = messageService.getMessagesFrom(username)

        then:
            result != null
            result.size() == 2
            result.contains(message1)
            result.contains(message2)

        where:
            username = "@alotor"
            message1 = "lorem ipsum"
            message2 = "dolor"
    }

    def 'if the user has not posted any messages return empty list'() {
        when:
            def result = messageService.getMessagesFrom(username)

        then:
            result != null
            result.size() == 0

        where:
            username = "@alotor"
    }
}
