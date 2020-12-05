package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.xml.PostImportXMLDto;
import softuni.exam.instagraphlite.models.dto.xml.PostRootImportXMLDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.Post;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.util.ValidatorUtil;
import softuni.exam.instagraphlite.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PostServiceImpl implements PostService {

    private static final String POST_PATH = "C:\\Users\\Arcerr\\Desktop\\Instagraph Lite_Skeleton\\skeleton\\src\\main\\resources\\files\\posts.xml";

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final XmlParser xmlParser;

    @Autowired
    private final ValidatorUtil validatorUtil;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PictureRepository pictureRepository;

    public PostServiceImpl(PostRepository postRepository, XmlParser xmlParser, ValidatorUtil validatorUtil, ModelMapper modelMapper, UserRepository userRepository, PictureRepository pictureRepository) {
        this.postRepository = postRepository;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Boolean аreImported() {
        return postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(POST_PATH)));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        PostRootImportXMLDto postRootImportXMLDto = xmlParser.parseXml(PostRootImportXMLDto.class, POST_PATH);
        for (PostImportXMLDto postImportXMLDto : postRootImportXMLDto.getPostImportXMLDtoList()) {

            Picture picture = pictureRepository.findPictureByPath(postImportXMLDto.getPictureImportXMLDto().getPath());
            User user = userRepository.findUserByUsername(postImportXMLDto.getUsernameImportXMLDto().getUsername());


            if (validatorUtil.isValid(postImportXMLDto) && picture != null && user != null){
                Post post = modelMapper.map(postImportXMLDto, Post.class);
                post.setPicture(picture);
                post.setUser(user);

                postRepository.saveAndFlush(post);

                sb.append(String.format("Successfully imported Post, made by %s", post.getUser().getUsername()))
                        .append(System.lineSeparator());

            }else {
                sb.append("Invalid Post").append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}
