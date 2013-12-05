class FollowService {
    def userStore

    public boolean follow(String nickA, String nickB) {
        def userA = userStore.getUser(nickA)
        def result = userA.addFollowing(nickB)

        userStore.updateUser(userA)

        return result
    }

    public List getFollowings(String user) {
        def result = userStore.getUser(user).followings
        return result
    }
}
