package wepa.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wepa.domain.Account;
import wepa.domain.Profile;
import wepa.repository.ProfileQuestionRepository;

import wepa.repository.ImageObjectRepository;
import wepa.domain.ImageObject;


@Service
public class ImageObjectService {

    @Autowired
    private ImageObjectRepository imageObjectRepository;

    @Transactional
    public ImageObject createImageObject(MultipartFile file) {

        if (file.getContentType().contains("image/gif")
                || file.getContentType().contains("image/jpeg")
                || file.getContentType().contains("image/png")) {

            ImageObject newFile = new ImageObject();

            try {
                newFile.setContent(file.getBytes());
            } catch (IOException ioe) {
                System.out.println("Problem while reading from the file: " + ioe.getMessage());
                return null;
            }

            newFile.setName(file.getOriginalFilename());
            newFile.setContentLength(file.getSize());
            newFile.setContentType(file.getContentType());
            newFile = imageObjectRepository.save(newFile);

            return newFile;
        }
        return null;
    }

}
