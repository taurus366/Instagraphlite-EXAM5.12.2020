package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.json.UserImportDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.Post;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidatorUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final String USER_PATH = "C:\\Users\\Arcerr\\Desktop\\Instagraph Lite_Skeleton\\skeleton\\src\\main\\resources\\files\\users.json";

    @Autowired
    private final Gson gson;

    @Autowired
    private final ValidatorUtil validatorUtil;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PictureRepository pictureRepository;

    public UserServiceImpl(Gson gson, ValidatorUtil validatorUtil, ModelMapper modelMapper, UserRepository userRepository, PictureRepository pictureRepository) {
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
    }


    @Override
    public Boolean аreImported() {
        return userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(USER_PATH)));
    }

    @Override
    public String importUsers() throws IOException {
        StringBuilder sb = new StringBuilder();

        UserImportDto[] userImportDtos = gson.fromJson(readFromFileContent(), UserImportDto[].class);
        for (UserImportDto userImportDto : userImportDtos) {
            User user = userRepository.findUserByUsername(userImportDto.getUserName());
            Picture picture = pictureRepository.findPictureByPath(userImportDto.getProfilePicture());

            if (validatorUtil.isValid(userImportDto) && user == null && picture != null){
                user = modelMapper.map(userImportDto, User.class);
                user.setPicture(picture);

                userRepository.saveAndFlush(user);

                sb.append(String.format("Successfully imported User: %s", user.getUsername()))
                        .append(System.lineSeparator());

            }else {
                sb.append("Invalid User").append(System.lineSeparator());
            }
        }

        return sb.toString();
    }

    @Override
    public String exportUsersWithTheirPosts() {
        StringBuilder sb = new StringBuilder();

        userRepository.exportAllUsersByPostOrderedByCountOfPostDescThenByUserId()
                .forEach(u -> {
                    sb.append(String.format("User: %s", u.getUsername()))
                            .append(System.lineSeparator());
                    sb.append(String.format("Post count: %d", u.getPost().size()))
                            .append(System.lineSeparator());
                List<Post> postDetails =   u.getPost().stream()
                           .sorted(Comparator.comparing(p -> p.getPicture().getSize())).collect(Collectors.toList());

                    postDetails
                            .forEach(p -> {
                                sb.append("==Post Details:")
                                        .append(System.lineSeparator());
                                sb.append(String.format("----Caption: %s", p.getCaption()))
                                        .append(System.lineSeparator());
                                sb.append(String.format("----Picture Size: %.2f", p.getPicture().getSize()))
                                        .append(System.lineSeparator());

                            });

                });


        return sb.toString();
    }
}
