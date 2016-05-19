package aws.buddy

import grails.converters.JSON

abstract class AbstractApiController {

    static beforeInterceptor = {
        params.max = params.max ?: 50
        params.offset = params.offset ?: 0
        return true
    }

    protected void renderJson(ApiResponse apiResponse) {
        response.status = apiResponse.httpStatus.value()
        if (!apiResponse.payload.status) {
            apiResponse.payload.status = apiResponse.httpStatus.reasonPhrase
        }
        render apiResponse.payload as JSON
    }
}
