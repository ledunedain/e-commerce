package tps.pruebatecnica.e_commerce.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenericResponse<T> {
    
    private String status;
    private String message;
    private T data;
}
