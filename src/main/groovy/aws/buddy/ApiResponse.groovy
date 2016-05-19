package aws.buddy

import org.springframework.http.HttpStatus

class ApiResponse {

    HttpStatus httpStatus
    Map payload = [:]


    static getOk(Map payload) {
        new ApiResponse(httpStatus: HttpStatus.OK, payload: payload)
    }

    static getNotFound() {
        new ApiResponse(httpStatus: HttpStatus.NOT_FOUND)
    }
}
