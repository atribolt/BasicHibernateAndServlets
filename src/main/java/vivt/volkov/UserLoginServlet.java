package vivt.volkov;

import org.apache.commons.io.IOUtils;
import vivt.volkov.models.User;
import vivt.volkov.services.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet("/user/login")
public class UserLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        Logger log = Logger.getLogger(this.getClass().getName());

        try {
            String body = IOUtils.toString(req.getReader());
            String decoded = URLDecoder.decode(body, StandardCharsets.UTF_8);

            Map<String, String> params =
                    Arrays.stream(decoded.split("&"))
                            .map(x -> x.split("="))
                            .collect(Collectors.toMap(x -> x[0], x -> x[1]));

            String login = params.get("login");
            String password = params.get("password");

            if (login == null || password == null) {
                res.sendError(400, "incorrect data for login");
                return;
            }

            Optional<User> user = UserService.instance().getUserByLogin(login);
            if (user.isEmpty() || !user.get().getPassword().equals(password)) {
                res.sendError(400, "invalid login or password");
                return;
            }

            UserService.instance().loginUser(user.get(), req.getRemoteHost());
            res.setContentType("text/plain");
            res.getWriter().write(String.format("Здравствуйте %s %s",
                    user.get().getName(),
                    user.get().getPatronymic()));
        }
        catch (Exception exc) {
            log.log(Level.SEVERE, "error during handle", exc);
        }
    }
}
