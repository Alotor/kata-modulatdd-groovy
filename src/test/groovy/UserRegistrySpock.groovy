import spock.lang.*

class UserRegistrySpock extends Specification {
    void "user registration"() {
        setup:
            def userRegistration = new UserRegistry()

        when:
            def result = userRegistration.registerUser(userName)

        then:
            result == true

        where:
            userName = "@alonso"

    }

    void "user cannot register twice"() {
        setup:
            def userRegistration = new UserRegistry()
            userRegistration.registerUser(userName)

        when:
            def result = userRegistration.registerUser(userName)

        then:
            thrown Exception

        where:
            userName = "@alonso"
    }
}
