package com.example.carrotMarket.exception;


import com.example.carrotMarket.exception.response.ErrorResponse;
import com.example.carrotMarket.exception.response.FailResponse;
import com.example.carrotMarket.exception.response.SuccessResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class CustomBody {

    /**
     요청 성공시 전달하는 ResponseBody
     get 메소드는 data에 프론트에게 필요한 데이터를 전달
     post 메소드는 data에 저장된 값을 전달
     */
    public static ResponseEntity<Object> successResponse(CustomMessage customMessage, Object data) {

        return ResponseEntity
                .status(customMessage.getHttpStatus())
                .body(SuccessResponse.builder().
                        message(customMessage.getMessage()).
                        detail(customMessage.getDetail()).
                        data(data).build());
    }

    /**
     서비스 로직상 정상적으로 처리를 하지 못할 때 전달하는 ResponseBody
     예외처리 할때는 전달할 data가 없으니 data를 뺴고 처리
     */
    public static ResponseEntity<Object> errorResponse(CustomMessage customMessage) {

        return ResponseEntity
                .status(customMessage.getHttpStatus())
                .body(ErrorResponse.builder().
                        message(customMessage.getMessage()).
                        detail(customMessage.getDetail())
                        .build());
    }

    /**
     요청 데이터 문제시 전달하는 ResponseBody(validated에 걸릴때)
     error에 문제점을 해당 필드와 함꼐 넣어준다
     */
    public static ResponseEntity<Object> failResponse(CustomMessage customMessage, Object cause) {

        return ResponseEntity
                .status(customMessage.getHttpStatus())
                .body(FailResponse.builder().
                        message(customMessage.getMessage()).
                        detail(customMessage.getDetail()).
                        cause(cause)
                        .build());
    }

}
