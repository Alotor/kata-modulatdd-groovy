import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

public class TestServlet extends HttpServlet {
    def registry = Locator.locateUserRegistry()

    @Override
    void doGet(HttpServletRequest request, HttpServletResponse response){
        renderOutput(response)
    }

    @Override
    void doPost(HttpServletRequest request, HttpServletResponse response) {
        def userName = request.getParameter("user")
        try {
            registry.registerUser(userName)
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
                    <input type="submit" value="Registrar"/>
                </form>
                ${getUserListElement()}
            </body>
        """
        response.outputStream.write(output.getBytes())
    }

    String getUserListElement() {
        def userList = "<ul class='user-list'>"
        registry.users.each {
            userList += "<li>${it.nick}</li>"
        }
        userList += "</ul>"
        return userList
    }
}
