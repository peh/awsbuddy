package aws.buddy

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'home', action: 'index')
        "500"(view: '/error')
        "404"(view: '/notFound')


        "/api/tools/refresh"(controller: 'tools', action: 'refresh')
        "/api/instances/$id"(controller: 'instance', action: 'get')
        "/api/instances"(controller: 'instance', action: 'list')

        "/api/users/$id"(controller: 'user', action: 'get')
        "/api/users/me"(controller: 'user', action: 'me')
        "/api/users/"(controller: 'user', action: [GET: 'list', POST: 'create'])
    }
}
