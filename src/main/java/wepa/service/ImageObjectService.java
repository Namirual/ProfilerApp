package wepa.service;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
    public List<String> createImageObject(MultipartFile file) {

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
            
            ImageObject thumbnail = createScaled(newFile, 100, 100);

            if (file.getSize() / 1024 > 500) {
                newFile = createScaled(newFile, 640, 480);
            }
            
            newFile = imageObjectRepository.save(newFile);
            thumbnail = imageObjectRepository.save(thumbnail);

            List<String> images = new ArrayList<String>();
            images.add(newFile.getId());
            images.add(thumbnail.getId());
            
            return images;
        }
        return null;
    }

    public static ImageObject createScaled(ImageObject file, int maxHeight, int maxWidth) {
        BufferedImage originalImage;
        try {
            originalImage = ImageIO.read(new ByteArrayInputStream(file.getContent()));
        } catch (IOException ioe) {
            System.out.println("Problem while reading from the file: " + ioe.getMessage());
            return null;
        }

        BufferedImage newImage = null;

        if (originalImage.getWidth() > originalImage.getHeight()) {
            newImage = scale(originalImage, originalImage.getType(), maxWidth,
                    (maxWidth * originalImage.getHeight()) / originalImage.getWidth());
        } else {
            newImage = scale(originalImage, originalImage.getType(),
                    (maxHeight * originalImage.getWidth()) / originalImage.getHeight(), maxHeight);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(newImage, "jpeg", outputStream);
        } catch (IOException ioe) {
            System.out.println("Problem while reading from the file: " + ioe.getMessage());
            return null;
        }

        byte[] bytes = outputStream.toByteArray();

        ImageObject resized = new ImageObject();

        resized.setContent(bytes);
        resized.setName(file.getName());
        resized.setContentLength((long) bytes.length);
        resized.setContentType(file.getContentType());

        return resized;
    }

    public static BufferedImage scale(BufferedImage originalImage, int imageType, int dWidth, int dHeight) {
        BufferedImage newImage = null;
        if (originalImage != null) {
            newImage = new BufferedImage(dWidth, dHeight, imageType);

            Graphics2D g = newImage.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            g.drawImage(originalImage, 0, 0, dWidth, dHeight, null);
            //AffineTransform at = AffineTransform.getScaleInstance(1.0 * dWidth / originalImage.getWidth(), 1.0 * dHeight / originalImage.getHeight());
            //g.drawRenderedImage(originalImage, at);
        }

        return newImage;
    }

}
