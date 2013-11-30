class UserFollowing {
    def registry = new UserRegistry()

    public boolean follow(String nickA, String nickB) {
        def userA = registry.getUser(nickA)
        def result = userA.addFollowing(nickB)

        registry.updateUser(userA)

        return result
    }

    public List getFollowings(String user) {
        def result = registry.getUser(user).followings
        return result
    }
}
