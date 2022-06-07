package uz.pdp.appjwtemailauth.Servis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ApiResponce {
    private String messega;
    private boolean success;
    private Object object;

    public ApiResponce(String messega, boolean success) {
        this.messega = messega;
        this.success = success;
    }
}
