class User {
    String nick
    List followings = []

    public addFollowing(String nick) {
        if (!followings.contains(nick)){
            followings << nick
        }
    }
}
