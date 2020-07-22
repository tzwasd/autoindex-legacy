package com.tzwjkl.bowl.autoindex.web;

import com.tzwjkl.bowl.autoindex.entity.FileEntry;
import com.tzwjkl.bowl.autoindex.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@SuppressWarnings("SpringMVCViewInspection")
@RequestMapping("/")
public class IndexController {

    private final LocalService localService;

    public IndexController(@Autowired LocalService localService) {
        this.localService = localService;
    }

    @GetMapping("**")
    public ModelAndView index(HttpServletRequest request) throws IOException {
        String requestUri = request.getServletPath();
        List<FileEntry> fileEntryList = localService.access(requestUri);
        if (fileEntryList == null) {
            return new ModelAndView("error", HttpStatus.NOT_FOUND);
        } else {
            return new ModelAndView("index", Map.of("requestUri", requestUri, "fileEntryList", fileEntryList));
        }
    }

}
