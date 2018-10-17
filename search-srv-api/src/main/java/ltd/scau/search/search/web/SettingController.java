package ltd.scau.search.search.web;

import ltd.scau.search.commons.entity.PageStructure;
import ltd.scau.search.commons.entity.ResponseData;
import ltd.scau.search.commons.service.PageStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * @author Weijie Wu
 */
@RestController
public class SettingController {

    @Autowired
    private PageStructureRepository pageStructureRepository;

    @PostMapping("/page-structure")
    public ResponseData addStructure(PageStructure pageStructure) {
        PageStructure s = pageStructureRepository.saveAndFlush(pageStructure);
        return ResponseData.aData().data(s).build();
    }

    @GetMapping("/page-structure")
    public ResponseData pageStructure(String uri) {
        URI u = URI.create(uri);
        List<PageStructure> structures = pageStructureRepository.findAllByHostAndPathRegexPathPattern(u.getHost(), u.getPath());
        return ResponseData.aData().data(structures).build();
    }
}
