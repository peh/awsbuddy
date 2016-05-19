import aws.buddy.HasJsonBody
import aws.buddy.Role
import aws.buddy.User
import aws.buddy.UserRole
import grails.converters.JSON
import grails.plugins.jesque.JesqueService

class BootStrap {

    JesqueService jesqueService

    def init = { servletContext ->
        JSON.registerObjectMarshaller(Enum) {
            it.toString()
        }

        JSON.registerObjectMarshaller(HasJsonBody) { HasJsonBody o ->
            o.jsonBody
        }
        if (User.count == 0) {
            if (Role.count == 0) {
                ["ROLE_ROOT", "ROLE_USER"].each {
                    new Role(it).save()
                }
            }
            User admin = new User('admin', 'admin').save()
            UserRole.create(admin, Role.findByAuthority('ROLE_ROOT'), true)
            println "created default User. you can now login with admin:admin"
        }

        new Timer().runAfter(10000) {
            println "starting workers"
            jesqueService.resumeAllWorkersOnThisNode()
        }
    }
    def destroy = {
    }
}
