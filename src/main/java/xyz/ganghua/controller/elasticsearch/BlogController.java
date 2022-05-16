package xyz.ganghua.controller.elasticsearch;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.ganghua.dao.elasticsearch.BlogModel;

@RestController
@RequestMapping("/blog")
public class BlogController {

    // @Autowired
    // private BlogRespository blogRespository;

    // @Autowired
    // private ElasticsearchTemplate elasticsearchTemplate;

    @PostMapping("/add")
    public String add(@RequestBody BlogModel request) {
        // BlogModel save = blogRespository.save(request);
        // elasticsearchTemplate.createIndex(BlogModel.class);
        return "successfully!";
    }

}
