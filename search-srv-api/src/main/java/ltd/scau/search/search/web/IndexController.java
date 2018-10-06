package ltd.scau.search.search.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Weijie Wu
 */
@RestController
public class IndexController {

    @GetMapping("/hello-world")
    public String index() {
        return "hello, world";
    }
}
