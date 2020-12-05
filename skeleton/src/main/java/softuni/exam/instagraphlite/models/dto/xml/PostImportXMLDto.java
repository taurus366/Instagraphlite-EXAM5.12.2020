package softuni.exam.instagraphlite.models.dto.xml;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "post")
@XmlAccessorType(XmlAccessType.FIELD)
public class PostImportXMLDto {

        @XmlElement(name = "caption")
        @Length(min = 21)
        @NotNull
        private String caption;

        @XmlElement(name = "user")
        @NotNull
        private UsernameImportXMLDto usernameImportXMLDto;

        @XmlElement(name = "picture")
        @NotNull
        private PictureImportXMLDto pictureImportXMLDto;


    public PostImportXMLDto() {
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public UsernameImportXMLDto getUsernameImportXMLDto() {
        return usernameImportXMLDto;
    }

    public void setUsernameImportXMLDto(UsernameImportXMLDto usernameImportXMLDto) {
        this.usernameImportXMLDto = usernameImportXMLDto;
    }

    public PictureImportXMLDto getPictureImportXMLDto() {
        return pictureImportXMLDto;
    }

    public void setPictureImportXMLDto(PictureImportXMLDto pictureImportXMLDto) {
        this.pictureImportXMLDto = pictureImportXMLDto;
    }
}
