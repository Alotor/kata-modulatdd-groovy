import spock.lang.*

class UserSpock extends Specification {
    void 'user follows user'() {
        setup:
            def user = new User()

        when:
            user.addFollowing(nick)

        then:
            user.followings.contains(nick)

        where:
            nick = "@pichon"
    }
}
