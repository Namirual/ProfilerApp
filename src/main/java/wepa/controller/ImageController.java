/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import wepa.repository.ImageObjectRepository;

/**
 *
 * @author lmantyla
 */

@Controller
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageObjectRepository imageRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String redirectToFirst() {
        return "redirect:/gifs/1";
    }

    /*@RequestMapping(method = RequestMethod.POST)
    public String save(@RequestParam("file") MultipartFile file) throws IOException {
        
        if (file.getContentType().contains("image/gif")) {
            GifBin gif = new GifBin();
            gif.setContent(file.getBytes());
            gifRep.save(gif);
        }

        return "redirect:/gifs";
    }*/
    
    /*@RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String getPage(Model model, @PathVariable Long id) {
        long count = gifRep.count();
        model.addAttribute("count", count);
        if (gifRep.exists(id + 1)) {
            model.addAttribute("next", id + 1);
        }
        if (gifRep.exists(id - 1)) {
            model.addAttribute("previous", id - 1);
        }
        if (gifRep.exists(id)) {
            model.addAttribute("current", id);
        }
        
        return "gifs";
    }*/

    @RequestMapping(value = "{id}/content", method = RequestMethod.GET, produces = {"image/jpg", "image/jpeg", "image/png"})
    @ResponseBody
    public byte[] get(@PathVariable String id) {

        return imageRepository.findOne(id).getContent();
    }
}
