package vivt.volkov;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import vivt.volkov.models.User;
import vivt.volkov.services.UserService;


import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@WebServlet("/user/registration")
public class UserRegistrationServlet extends HttpServlet {
    static final Logger log = Logger.getLogger("UserRegistrationServlet");
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try
        {
            String body = IOUtils.toString(req.getReader());
            String decoded = URLDecoder.decode(body, StandardCharsets.UTF_8);

            Map<String, String> params =
                    Arrays.stream(decoded.split("&"))
                            .map(x -> x.split("="))
                            .collect(Collectors.toMap(x -> x[0], x -> x[1]));

            String userName = params.get("UserName");
            String userLogin = params.get("UserLogin");
            String userSurname = params.get("UserSurname");
            String userPatronymic = params.getOrDefault("UserPatronymic", "");

            if (userLogin == null || userLogin.isEmpty()) {
                log.warning("client not send login");
                res.sendError(400, "User login required");
                return;
            }
            if (userName == null || userName.isEmpty()) {
                log.warning("client not send name");
                res.sendError(400, "User name required");
                return;
            }
            if (userSurname == null || userSurname.isEmpty()) {
                log.warning("client not send surname");
                res.sendError(400, "User surname required");
                return;
            }

            log.fine(String.format("user want registration: %s", userLogin));

            Optional<User> user = UserService.instance()
                    .registrationUser(userLogin, userName, userSurname, userPatronymic, req.getRemoteHost());

            if (user.isEmpty()) {
                log.warning("user is not created");
                res.sendError(500, "Error while create user");
                return;
            }

            res.setStatus(200);
            String reply = String.format("Login='%s', Password='%s'", user.get().getLogin(), user.get().getPassword());

            res.setContentType("test/plain; charset=utf-8;");
            res.getWriter().write(reply);

            log.fine("request handled");
        }
        catch (Exception exc)
        {
            log.log(Level.SEVERE, "error during handle", exc);
            res.sendError(500, "Exception: " + exc.getMessage());
        }
    }
}
