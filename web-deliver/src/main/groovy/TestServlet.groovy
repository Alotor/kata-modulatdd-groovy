import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

public class TestServlet extends HttpServlet {
    def registry = Locator.locateUserRegistry()
    def following = Locator.locateUserFollowing()

    @Override
    void doGet(HttpServletRequest request, HttpServletResponse response){
        renderOutput(response)
    }

    @Override
    void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getParameter("registerButton")) {
                def userName = request.getParameter("user")
                registry.registerUser(userName)
            } else {
                def userA = request.getParameter("userA")
                def userB = request.getParameter("userB")
                following.follow(userA, userB)
            }
            renderOutput(response)
        } catch(Exception e) {
            renderOutput(response, e.message)
        }
    }

    void renderOutput(response, error="") {
        def output = """\
            <head>
                <title>ROAR</title>
                <link rel="stylesheet" type="text/css" href="/css/main.css"></link>
            </head>
            <body>
                <div class="error" style="${(error)?:"display:none"}">$error</div>
                <form method="POST">
                    <label for="user">Usuario:</label>
                    <input type="text" name="user" id="user"></input>
                    <input type="submit" name="registerButton" value="Registrar"/>

                    <hr/>

                    <label for="userA">Usuario A:</label>
                    <input type="text" name="userA" id="userA"></input>
                    <label for="userB">Usuario B:</label>
                    <input type="text" name="userB" id="userB"></input>
                    <input type="submit" name="followButton" value="Follow"/>
                </form>
                ${getUserListElement()}
            </body>
        """
        response.outputStream.write(output.getBytes())
    }

    String getUserListElement() {
        def userList = "<ul class='user-list'>"
        registry.users.each {
            userList += "<li>${it.nick} - ${it.followings}</li>"
        }
        userList += "</ul>"
        return userList
    }
}
