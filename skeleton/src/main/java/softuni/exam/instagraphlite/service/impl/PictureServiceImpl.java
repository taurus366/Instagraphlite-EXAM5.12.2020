package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.json.PictureImportDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ValidatorUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {

    private static final String PICTURE_PATH = "C:\\Users\\Arcerr\\Desktop\\Instagraph Lite_Skeleton\\skeleton\\src\\main\\resources\\files\\pictures.json";

    @Autowired
    private final PictureRepository pictureRepository;

    @Autowired
    private final Gson gson;

    @Autowired
    private final ValidatorUtil validatorUtil;

    @Autowired
    private final ModelMapper modelMapper;

    public PictureServiceImpl(PictureRepository pictureRepository, Gson gson, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(PICTURE_PATH)));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb = new StringBuilder();

        PictureImportDto[] pictureImportDtos = gson.fromJson(readFromFileContent(), PictureImportDto[].class);
        for (PictureImportDto pictureImportDto : pictureImportDtos) {

            Picture picture = pictureRepository.findPictureByPath(pictureImportDto.getPath());

            if (validatorUtil.isValid(pictureImportDto) && picture == null){
                Picture picture1 = modelMapper.map(pictureImportDto, Picture.class);

                pictureRepository.saveAndFlush(picture1);

                sb.append(String.format("Successfully imported Picture, with size %.2f", picture1.getSize()))
                        .append(System.lineSeparator());

            }else {
                sb.append("Invalid Picture").append(System.lineSeparator());
            }

        }

        return sb.toString();
    }

    @Override
    public String exportPictures() {
        StringBuilder sb = new StringBuilder();

        List<Picture> pictures = pictureRepository.ExportAllPicturesWithSizeBiggerThan30000()
                .stream()
                .sorted(Comparator.comparing(Picture::getSize))
                .collect(Collectors.toList());

        pictures
              .forEach(p -> {
                  sb.append(String.format("%.2f - %s", p.getSize(),p.getPath()))
                          .append(System.lineSeparator());
              });
        return sb.toString();
    }
}
