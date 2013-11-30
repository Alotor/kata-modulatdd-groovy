import spock.lang.*

class CliExecutorSpock extends Specification {
    void "Register user"() {
        setup:
            Locator.type = "memory"

            def executor = new CliExecutor()

        when:
            executor.execute("register $username")
            def result = Locator.locateUserRegistry().getUser(username)

        then:
            result != null
            result.nick == username

        where:
            username = "@algohace"

    }

    void "Follow user"() {
        setup:
            Locator.type = "memory"
            def executor = new CliExecutor()

        when:
            executor.execute("follow $userA $userB")
            def result = Locator.locateUserRegistry().getUser(userA)

        then:
            result != null
            result.followings != null
            result.followings.contains(userB)

        where:
            userA = "@algohace"
            userB = "@alotor"
    }
}
