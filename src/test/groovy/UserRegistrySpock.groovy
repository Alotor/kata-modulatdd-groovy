import spock.lang.*
import groovy.sql.*

class UserRegistrySpock extends Specification {
    def setup() {
        def sql = Sql.newInstance('jdbc:sqlite:sample.db','org.sqlite.JDBC' )
        sql.execute("drop table users")
        sql.execute("drop table user_following")
        sql.execute("create table users( username varchar primary_key )")
        sql.execute("create table user_following( user varchar, following varchar, primary key(user, following))")
    }

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

    void "when i register a user i can return it"() {
        setup:
            def userRegistration = new UserRegistry()
            userRegistration.registerUser(userName)

        when:
            def result = userRegistration.getUser(userName)

        then:
            result != null
            result.nick == userName

        where:
            userName = "@alonso"
    }

    void "when you are not registered return null"() {
        setup:
            def userRegistration = new UserRegistry()

        when:
            def result = userRegistration.getUser(userName)

        then:
            result == null

        where:
            userName = "@alonso"
    }
}
