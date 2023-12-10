package dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOInput {
    private int id;
    private String name;
    private String password;
}
