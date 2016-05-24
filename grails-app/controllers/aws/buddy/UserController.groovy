package aws.buddy

import grails.plugin.springsecurity.SpringSecurityService

class UserController extends AbstractApiController {

    SpringSecurityService springSecurityService

    def get(long id) {
        User user = User.read(id)
        renderJson(ApiResponse.getOk([user: user]))
    }

    def list() {

    }

    def delete() {

    }

    def me() {
        renderJson(ApiResponse.getOk([user: springSecurityService.currentUser]))
    }
}
