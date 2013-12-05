import spock.lang.*

class CliExecutorSpock extends Specification {
    def executor
    def userStore

    def setup() {
        Locator.initialize("memory")
        executor = new CliExecutor()
        userStore = Locator.instance.userStore
    }

    void "Register user"() {
        when:
            executor.execute("register $username")
            def result = userStore.getUser(username)

        then:
            result != null
            result.nick == username

        where:
            username = "@algohace"

    }

    void "Follow user"() {
        when:
            userStore.registerUser(userA)
            executor.execute("follow $userA $userB")
            def result = userStore.getUser(userA)

        then:
            result != null
            result.followings != null
            result.followings.contains(userB)

        where:
            userA = "@algohace"
            userB = "@alotor"
    }
}
