import spock.lang.*

class UserSpock extends Specification {
    void 'user follows user'() {
        setup:
            def user = new User()

        when:
            user.addFollowing(nick)

        then:
            user.followings.size() == 1
            user.followings.contains(nick)

        where:
            nick = "@pichon"
    }

    void 'no repetitions inside follower list'() {
        setup:
            def user = new User()
            user.addFollowing(nick)

        when:
            user.addFollowing(nick)

        then:
            user.followings.size() == 1
            user.followings.contains(nick)

        where:
            nick = "@pichon"
    }
}
