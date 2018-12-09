package sailboatScraper.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sailboatScraper.Models.Sailboats;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class IndexController {

    private static final String template = "Fair winds and following seas, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/")
    public Sailboats getSailboats(@RequestParam(value="name", defaultValue="World") String name) {
        return new Sailboats(counter.incrementAndGet(),
                String.format(template, name));
    }
}



