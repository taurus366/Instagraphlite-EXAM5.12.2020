package softuni.exam.instagraphlite.models.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "posts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PostRootImportXMLDto {

    @XmlElement(name = "post")
    private List<PostImportXMLDto> postImportXMLDtoList;

    public PostRootImportXMLDto() {
    }

    public List<PostImportXMLDto> getPostImportXMLDtoList() {
        return postImportXMLDtoList;
    }

    public void setPostImportXMLDtoList(List<PostImportXMLDto> postImportXMLDtoList) {
        this.postImportXMLDtoList = postImportXMLDtoList;
    }
}
