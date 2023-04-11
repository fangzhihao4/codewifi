package codewifi.repository.model;

import lombok.Data;

@Data
public class VerystatusMenuConfigModel {
    Integer id;
    Integer type;
    String name;
    String pageUrl;
    Byte status;
    Integer sort;
}
